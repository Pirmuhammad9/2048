package uz.gita.pirmuhammad.data.local.pref

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class SharedPref private constructor() {
    companion object {
        private var sharedPref: SharedPref? = null
        private var preferences: SharedPreferences? = null
        fun init(context: Context) {
            if (sharedPref == null) {
                sharedPref = SharedPref()
                preferences = context.getSharedPreferences("score", Context.MODE_PRIVATE)
            }
        }

        fun getInstance() = sharedPref!!
    }

    var score: Int
        get() = preferences!!.getInt("SCORE", 0)
        set(value) {
            if (value > score)
                preferences!!.edit().putInt("SCORE", value).apply()
        }

    var matrix: String?
        get() = preferences!!.getString("MATRIX", "")
        set(value) {
            preferences!!.edit().putString("MATRIX", value).apply()
        }
}