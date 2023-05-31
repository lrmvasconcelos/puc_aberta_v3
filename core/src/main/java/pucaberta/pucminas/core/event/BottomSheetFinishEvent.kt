package pucaberta.pucminas.core.event

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class BottomSheetFinishEvent(private val externalScope: CoroutineScope) {
    private val _finishFlow = MutableSharedFlow<String>()
    val finishFlow: SharedFlow<String> = _finishFlow

    fun trigger(value: String) {
        externalScope.launch {
            _finishFlow.emit(value)
        }
    }
}