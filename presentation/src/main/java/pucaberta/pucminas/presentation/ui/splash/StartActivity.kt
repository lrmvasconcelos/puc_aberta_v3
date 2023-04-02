package pucaberta.pucminas.presentation.ui.splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import pucaberta.pucminas.core.observe
import pucaberta.pucminas.core.startWithAnimation
import pucaberta.pucminas.core.viewBinding
import pucaberta.pucminas.presentation.R
import pucaberta.pucminas.presentation.databinding.StartActivityBinding
import pucaberta.pucminas.presentation.ui.map.MapActivity
import pucaberta.pucminas.presentation.ui.registration.RegistrationActivity

class StartActivity : AppCompatActivity() {

    private val viewModel: StartViewModel by viewModel()

    private val binding: StartActivityBinding by viewBinding(
        StartActivityBinding::inflate
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupObservers()
        viewModel.starDelay()
    }

    private fun setupObservers() = with(viewModel) {
        observe(isLogged) {
            if (it) {
                startWithAnimation(MapActivity.newInstance(this@StartActivity))
            } else {
                startWithAnimation(RegistrationActivity.newInstance(this@StartActivity))
            }
        }
    }
}