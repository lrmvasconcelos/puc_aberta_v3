package pucaberta.pucminas.presentation.ui.bottomsheet

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import pucaberta.pucminas.presentation.databinding.ProgrammingBottomSheetBinding

class ProgrammingBottomSheetDialog : BottomSheetDialogFragment() {

    private lateinit var binding: ProgrammingBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = ProgrammingBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnPrimary.setOnClickListener {
            dismissAllowingStateLoss()
        }
        binding.btnSecundary.setOnClickListener {
            val uri = Uri.parse("https://www.pucminas.br/pucaberta/Paginas/default.aspx#programacao")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            if (intent.resolveActivity(requireActivity().packageManager) == null) {
                intent.data =
                    Uri.parse("https://www.pucminas.br/pucaberta/Paginas/default.aspx#programacao")
            }
            this.startActivity(intent)
        }
    }

    companion object {

        fun newInstance(): ProgrammingBottomSheetDialog {
            return ProgrammingBottomSheetDialog()
        }
    }
}