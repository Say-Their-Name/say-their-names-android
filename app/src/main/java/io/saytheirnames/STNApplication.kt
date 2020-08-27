package io.saytheirnames

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager

class STNApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initTheme()
    }

    private fun initTheme() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        ThemeManager.applyTheme(preferences.getInt(R.string.preference_key_theme.toString(), AppCompatDelegate.MODE_NIGHT_NO))
    }
}