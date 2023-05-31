package pucaberta.pucminas.presentation.ui.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import models.BottomSheetTypeEnum
import org.koin.android.ext.android.inject
import pucaberta.pucminas.core.event.BottomSheetFinishEvent
import pucaberta.pucminas.presentation.R
import pucaberta.pucminas.presentation.databinding.QrCodeBottomSheetBinding

class BaseBottomSheetDialog : BottomSheetDialogFragment() {

    private val finishEvent: BottomSheetFinishEvent by inject()

    private val descriptionId: Int? by lazy {
        arguments?.getInt(DESCRIPTION)
    }

    private val lottieKey: String? by lazy {
        arguments?.getString(LOTTIE_KEY)
    }

    private lateinit var binding: QrCodeBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = QrCodeBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnQrCode.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                lottieKey?.let { finishEvent.trigger(it) }
                this.cancel()
            }
            dismissAllowingStateLoss()
        }
        binding.actvRegisterText.setText(descriptionId ?: R.string.qr_code_hint_description)

        lottieKey?.let {
            binding.animationView.isVisible = true
            when (it) {
                BottomSheetTypeEnum.QRCODE.name -> {
                    binding.btnQrCode.setText(R.string.read_qr_code_button_text)
                    binding.animationView.setAnimation(R.raw.qr_code_lottie)
                }
                BottomSheetTypeEnum.GEOLOCATION.name -> {
                    binding.btnQrCode.setText(R.string.geolocation_button_text)
                    binding.animationView.setAnimation(R.raw.location_lottie)
                }
            }
        }


    }

    companion object {

        private const val DESCRIPTION = "DESCRIPTION"
        private const val LOTTIE_KEY = "LOTTIE_KEY"

        fun newInstance(descriptionId: Int, lottieKey: String): BaseBottomSheetDialog {
            return BaseBottomSheetDialog().apply {
                arguments = Bundle().apply {
                    putInt(DESCRIPTION, descriptionId)
                    putString(LOTTIE_KEY, lottieKey)
                }
            }
        }
    }
}