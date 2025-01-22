package dev.koga.poc_kmp_submodule

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


@OptIn(ExperimentalJsExport::class)
@JsExport
class Counter {

    private val _state = MutableStateFlow(0)
    val state = _state.asStateFlow()

    fun increment() {
        _state.update {
            it + 1
        }
    }
}