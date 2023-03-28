package pucaberta.pucminas.core

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

inline fun <reified T> AppCompatActivity.observe(
    liveData: LiveData<T>,
    crossinline execution: (T) -> Unit
){
    liveData.observe(this, Observer{execution(it)})
}