package pucaberta.pucminas.core.providers

import androidx.annotation.RawRes

interface ResourceRawProvider {
    fun getStringFromRaw(@RawRes id: Int): String
}