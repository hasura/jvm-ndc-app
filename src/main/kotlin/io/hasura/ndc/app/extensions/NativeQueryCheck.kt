package io.hasura.ndc.app.extensions

import io.hasura.ndc.app.services.StateContainer
import io.hasura.ndc.ir.QueryRequest

fun QueryRequest.getNativeQuery() = StateContainer.nativeQueries[this.collection]
fun QueryRequest.isNativeQuery() = getNativeQuery() != null
