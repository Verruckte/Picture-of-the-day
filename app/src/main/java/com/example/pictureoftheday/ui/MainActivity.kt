package com.example.pictureoftheday.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pictureoftheday.R
import com.example.pictureoftheday.ui.picture.PictureOfTheDayFragment
import com.example.pictureoftheday.ui.settings.SETTINGS_SHARED_PREFERENCES
import com.example.pictureoftheday.ui.settings.THEME_RES_ID

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(
            getSharedPreferences(SETTINGS_SHARED_PREFERENCES, MODE_PRIVATE)
                .getInt(THEME_RES_ID, R.style.DefaultTheme)
        )
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNowAllowingStateLoss()
        }
    }
}