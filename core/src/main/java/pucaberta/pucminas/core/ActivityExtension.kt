package pucaberta.pucminas.core

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

inline fun <reified T> AppCompatActivity.observe(
    liveData: LiveData<T>,
    crossinline execution: (T) -> Unit
){
    liveData.observe(this, Observer{execution(it)})
}

fun Activity.startWithAnimation(intent: Intent){
    startActivity(intent)
    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
}