package pucaberta.pucminas.core.event

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class BottomSheetFinishEvent(private val externalScope: CoroutineScope) {
    private val _finishFlow = MutableSharedFlow<Float>()
    val finishFlow: SharedFlow<Float> = _finishFlow

    fun trigger(id: Float) {
        externalScope.launch {
            _finishFlow.emit(id)
        }
    }
}