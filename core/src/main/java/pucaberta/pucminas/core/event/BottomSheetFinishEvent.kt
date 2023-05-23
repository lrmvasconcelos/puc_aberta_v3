package pucaberta.pucminas.core.event

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class BottomSheetFinishEvent(private val externalScope: CoroutineScope) {
    private val _finishFlow = MutableSharedFlow<Unit>()
    val finishFlow: SharedFlow<Unit> = _finishFlow

    fun trigger(){
        externalScope.launch {
            _finishFlow.emit(Unit)
        }
    }
}