package dev.koga.poc_kmp_submodule

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@JsExport()
class CounterJs {
    private val counter = Counter()

    val count = StateFlowObserver(
        counter.counter,
        CoroutineScope(Dispatchers.Main)
    )

    fun increment() {
        counter.increment()
    }
}