package pucaberta.pucminas.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import pucaberta.pucminas.core.databinding.BaseBottomSheetBinding
import pucaberta.pucminas.core.event.BottomSheetFinishEvent

class BaseBottomSheetDialog : BottomSheetDialogFragment() {

    private val finishEvent: BottomSheetFinishEvent by inject()

    private lateinit var binding: BaseBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BaseBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.actvRegisterText.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                finishEvent.trigger()
                this.cancel()
            }
            dismissAllowingStateLoss()
        }
    }

    companion object {
        fun newInstance(): BaseBottomSheetDialog {
            return BaseBottomSheetDialog()
        }
    }
}