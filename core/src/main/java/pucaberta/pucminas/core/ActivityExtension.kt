package pucaberta.pucminas.core

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

inline fun <reified T> AppCompatActivity.observe(
    liveData: LiveData<T>,
    crossinline execution: (T) -> Unit
) {
    liveData.observe(this, Observer { execution(it) })
}

inline fun <reified T> AppCompatActivity.observeEvent(
    event: SharedFlow<T>,
    crossinline onCollected: (T) -> Unit
) {
    lifecycleScope.launch {
        event.collect {
            onCollected(it)
        }
    }
}


fun Activity.startWithAnimation(intent: Intent) {
    startActivity(intent)
    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
}

fun Context.getPreferences() = getSharedPreferences("puc_aberta_prefences", Context.MODE_PRIVATE)