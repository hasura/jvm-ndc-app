package io.hasura.ndc.app.interfaces

import io.hasura.ndc.common.NativeQueryInfo
import io.hasura.ndc.ir.SchemaResponse
import javax.sql.DataSource

data class ConfigParts(val dataSource: DataSource, val schemaResponse: SchemaResponse, val nativeQueries: Map<String, NativeQueryInfo>)
interface ConfigHandler {
    fun parseConfig(pathToConfig: String): ConfigParts
}
