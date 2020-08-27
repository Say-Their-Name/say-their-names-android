package io.saytheirnames

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager

object ThemeManager {

    fun applyTheme(mode: Int) {
        if (AppCompatDelegate.getDefaultNightMode() == mode) return
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    fun setTheme(mode: Int, context: Context) {
        applyTheme(mode)

        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        preferences.edit().putInt(R.string.preference_key_theme.toString(), mode)
                .apply()
    }

    fun isNightMode(): Boolean {
        return getCurrentTheme() == AppCompatDelegate.MODE_NIGHT_YES
    }

    private fun getCurrentTheme(): Int {
        return AppCompatDelegate.getDefaultNightMode()
    }

    fun toggleTheme(context: Context) {
        if (isNightMode()) setTheme(AppCompatDelegate.MODE_NIGHT_NO, context)
        else setTheme(AppCompatDelegate.MODE_NIGHT_YES, context)
    }
}
