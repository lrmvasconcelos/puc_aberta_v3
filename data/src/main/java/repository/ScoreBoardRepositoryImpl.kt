package repository

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import models.MarkLocation
import pucaberta.pucminas.core.getPreferences
import repoInterfaces.ScoreBoardRepository

class ScoreBoardRepositoryImpl(
    private val context: Context
) : ScoreBoardRepository {

    private val sharedPreferences: SharedPreferences by lazy { context.getPreferences() }

    override fun saveNewMark(marksChecked: List<String>) {
        val json = GsonBuilder().serializeNulls().create().toJson(marksChecked)
        sharedPreferences.edit().putString(USER_LEVEL_CACHE_KEY, json).apply()
    }

    override fun getQrReaderTypes(): List<String> {

        val json = sharedPreferences.getString(USER_LEVEL_CACHE_KEY, null)

        if (json.isNullOrBlank()) {
            return emptyList()
        }

        return try {
            Gson().fromJson(
                json,
                TypeToken.getParameterized(List::class.java, String::class.java).type
            )
        } catch (e: Exception) {
            emptyList()
        }
    }

    companion object {
        private const val USER_LEVEL_CACHE_KEY = "puc_aberta_user_level_cache_key"
    }
}