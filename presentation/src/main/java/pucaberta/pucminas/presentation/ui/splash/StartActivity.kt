package pucaberta.pucminas.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import pucaberta.pucminas.core.observe
import pucaberta.pucminas.core.startWithAnimation
import pucaberta.pucminas.core.viewBinding
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
            val intent = if (it) {
                MapActivity.newInstance(this@StartActivity)
            } else {
                RegistrationActivity.newInstance(this@StartActivity)
            }
            intent.apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startWithAnimation(intent)
        }
    }
}