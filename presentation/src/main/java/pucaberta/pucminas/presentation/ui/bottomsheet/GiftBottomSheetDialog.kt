package pucaberta.pucminas.presentation.ui.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import models.BottomSheetTypeEnum
import org.koin.android.ext.android.inject
import pucaberta.pucminas.core.event.BottomSheetFinishEvent
import pucaberta.pucminas.presentation.databinding.GiftBottomSheetBinding
import pucaberta.pucminas.presentation.databinding.SuccessBottomSheetBinding

class GiftBottomSheetDialog : BottomSheetDialogFragment() {

    private val finishEvent: BottomSheetFinishEvent by inject()


    private lateinit var binding: GiftBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = GiftBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
        binding.btnPrimary.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                finishEvent.trigger(BottomSheetTypeEnum.GIFT.name)
                this.cancel()
            }
            dismissAllowingStateLoss()
        }
    }

    companion object {

        fun newInstance(): GiftBottomSheetDialog {
            return GiftBottomSheetDialog()
        }
    }
}