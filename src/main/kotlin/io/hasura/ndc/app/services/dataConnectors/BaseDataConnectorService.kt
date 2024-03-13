package io.hasura.ndc.app.services.dataConnectors

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import io.hasura.ndc.sqlgen.BaseQueryGenerator
import io.hasura.ndc.app.interfaces.IDataConnectorService
import io.hasura.ndc.app.models.ExplainResponse
import io.hasura.ndc.app.services.StateContainer
import io.opentelemetry.api.trace.Tracer
import io.opentelemetry.instrumentation.annotations.WithSpan
import jakarta.enterprise.inject.Produces
import jakarta.inject.Inject
import io.hasura.ndc.ir.*
import io.hasura.ndc.sqlgen.BaseMutationTranslator
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.jooq.ResultQuery
import org.jooq.SQLDialect
import org.jooq.conf.Settings
import org.jooq.conf.StatementType
import org.jooq.impl.DSL

abstract class BaseDataConnectorService(
    private val tracer: Tracer,
) : IDataConnectorService {

    @Inject
    lateinit var objectMapper: ObjectMapper

    abstract val jooqDialect: SQLDialect
    abstract val jooqSettings: Settings
    abstract val sqlGenerator: BaseQueryGenerator
    abstract val mutationTranslator: BaseMutationTranslator
    abstract val capabilitiesResponse: CapabilitiesResponse

    val commonDSLContextSettings: Settings =
        Settings()
            .withRenderFormatted(true)
            .withStatementType(StatementType.STATIC_STATEMENT)

    @WithSpan
    override fun getCapabilities(): CapabilitiesResponse {
        return this.capabilitiesResponse
    }

    @WithSpan
    fun buildQuery(queryRequest: QueryRequest): ResultQuery<*> =
        sqlGenerator.handleRequest(queryRequest)

    @WithSpan
    open fun processQueryDbRows(dbRows: Result<out Record>): List<RowSet> {
        val json = (dbRows.getValue(0, 0).toString())
        val typeRef = object: TypeReference<List<RowSet>>(){}
        return objectMapper.readValue(json,typeRef)
    }

    @WithSpan
    fun executeQuery(query: ResultQuery<*>, ctx: DSLContext): List<RowSet> {
        val rows = executeDbQuery(query, ctx)
        return processQueryDbRows(rows)
    }

    @WithSpan
    open fun executeDbQuery(query: ResultQuery<*>, ctx: DSLContext): Result<out Record> {
        val t = ctx.render(query)
        return ctx.fetch(query)
    }

    protected open fun executeAndSerializeMutation(
        request: MutationRequest,
        ctx: DSLContext
    ): MutationResponse {
        val results = mutationTranslator.translate(
            request,
            ctx,
            sqlGenerator::mutationQueryRequestToSQL
        )
        return MutationResponse(results)
    }

    @WithSpan
    open fun mkDSLCtx(): DSLContext {
        return DSL.using(StateContainer.datasource, jooqDialect, jooqSettings)
    }

    @WithSpan
    override fun getSchema() = StateContainer.schema


    @WithSpan
    override fun explainQuery(request: QueryRequest): ExplainResponse {
        val dslCtx = mkDSLCtx()
        val query = buildQuery(request)
        val explain = dslCtx.explain(query)
        return ExplainResponse(query = query.sql, lines = listOf(explain.plan()))
    }

    @WithSpan
    override fun handleQuery(request: QueryRequest): List<RowSet> {
        val dslCtx = mkDSLCtx()
        val query = buildQuery(request)
        return executeQuery(query, dslCtx)
    }

    @WithSpan
    override fun handleMutation(request: MutationRequest): MutationResponse {
        val dslCtx = mkDSLCtx()
        return executeAndSerializeMutation(request, dslCtx)
    }

    @WithSpan
    override fun runHealthCheckQuery(): Boolean {
        val dslCtx = mkDSLCtx()
        val query = dslCtx.selectOne()
        return try {
            dslCtx.fetch(query)
            true
        } catch (e: Exception) {
            false
        }
    }

    @Produces
    fun createDataConnectorService(): IDataConnectorService = this
}
