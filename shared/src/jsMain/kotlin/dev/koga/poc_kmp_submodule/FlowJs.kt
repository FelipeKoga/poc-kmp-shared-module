package dev.koga.poc_kmp_submodule

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow

@JsExport
interface FlowObserver<T> {
    fun stopObserving()
    fun startObserving(
        onEach: (T) -> Unit,
        onError: (Throwable) -> Unit = {},
        onComplete: () -> Unit = {},
    )
}

fun <T> FlowObserver(
    delegate: Flow<T>,
    coroutineScope: CoroutineScope
): FlowObserver<T> =
    FlowObserverImpl(delegate, coroutineScope)

class FlowObserverImpl<T>(
    private val delegate: Flow<T>,
    private val coroutineScope: CoroutineScope
) : FlowObserver<T> {
    private var observeJobs: List<Job> = emptyList()

    override fun startObserving(
        onEach: (T) -> Unit,
        onError: (Throwable) -> Unit,
        onComplete: () -> Unit,
    ) {
        observeJobs += delegate
            .onEach(onEach)
            .onCompletion { onComplete() }
            .catch { onError(it) }
            .launchIn(coroutineScope)
    }

    override fun stopObserving() {
        observeJobs.forEach { it.cancel() }
    }
}

@JsExport
interface StateFlowObserver<T> : FlowObserver<T> {
    val value: T
}

fun <T> StateFlowObserver(
    delegate: StateFlow<T>,
    coroutineScope: CoroutineScope
): StateFlowObserver<T> =
    StateFlowObserverImpl(delegate, coroutineScope)

class StateFlowObserverImpl<T>(
    private val delegate: StateFlow<T>,
    private val coroutineScope: CoroutineScope
) : StateFlowObserver<T> {
    private var jobs = mutableListOf<Job>()
    override val value: T
        get() = delegate.value

    override fun startObserving(
        onEach: (T) -> Unit,
        onError: (Throwable) -> Unit,
        onComplete: () -> Unit,
    ) {
        jobs += delegate
            .onEach(onEach)
            .launchIn(coroutineScope)
    }

    override fun stopObserving() {
        jobs.forEach { it.cancel() }
        jobs.clear()
    }
}
