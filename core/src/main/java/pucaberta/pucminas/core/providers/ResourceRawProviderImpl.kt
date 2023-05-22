package pucaberta.pucminas.core.providers

import android.content.Context
import java.lang.ref.WeakReference

class ResourceRawProviderImpl(
    context: Context
) : ResourceRawProvider {

    private val mContext: WeakReference<Context> = WeakReference(context)

    override fun getStringFromRaw(id: Int): String =
        mContext.get()?.resources?.openRawResource(id)?.reader()?.readText() ?: ""

}