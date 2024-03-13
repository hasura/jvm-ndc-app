package io.hasura.ndc.app.models

data class ConnectorConfig(val jdbcUrl: String, val properties: Map<String, Any> = emptyMap())
