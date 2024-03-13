package io.hasura.ndc.app.services

import io.hasura.ndc.app.interfaces.ConfigParts
import io.hasura.ndc.common.NativeQueryInfo
import io.hasura.ndc.ir.SchemaResponse
import javax.sql.DataSource


object StateContainer  {

    var datasource: DataSource? = null
        private set

    var schema: SchemaResponse = SchemaResponse(
        scalar_types = emptyMap(),
        object_types = emptyMap(),
        collections = emptyList(),
        functions = emptyList(),
        procedures = emptyList()
    )
        private set


    var nativeQueries: Map<String, NativeQueryInfo> = emptyMap()
        private set

    fun setState(configParts: ConfigParts) {
        datasource = configParts.dataSource
        schema = configParts.schemaResponse
        nativeQueries = configParts.nativeQueries
    }
}

