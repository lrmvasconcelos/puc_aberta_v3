package pucaberta.pucminas.core

import android.os.SystemClock
import android.view.View
import android.view.View.OnClickListener

private const val DEFAULT_DEBOUNCE_TIME = 500L

fun View.clickWithDebounce(debounce: Long = DEFAULT_DEBOUNCE_TIME, click: () -> Unit) {
    this.setOnClickListener(object : OnClickListener {
        private var lastTimeClicked: Long = 0

        override fun onClick(v: View?) {
            if (SystemClock.elapsedRealtime() - lastTimeClicked < debounce)
                return
            else
                click.invoke()

            lastTimeClicked = SystemClock.elapsedRealtime()
        }
    })
}