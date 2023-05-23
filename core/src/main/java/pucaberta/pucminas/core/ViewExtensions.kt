package pucaberta.pucminas.core

import android.os.SystemClock
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

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

fun BottomSheetDialogFragment.showBottomSheet(fragmentManager: FragmentManager, className: String) {
    if (!fragmentManager.isDestroyed) {
        this.showOnce(fragmentManager, className)
    }
}

fun DialogFragment.showOnce(
    fragmentManager: FragmentManager,
    tag: String? = this::class.java.simpleName
) {
    try {
        if (fragmentManager.findFragmentByTag(tag) == null) {
            show(fragmentManager, tag)
        }
    } catch (error: Throwable) {
        Log.d(tag, "Dialog já existe")
    }
}