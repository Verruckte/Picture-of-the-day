package com.example.pictureoftheday.ui.collapsing_toolbar

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pictureoftheday.R
import com.example.pictureoftheday.ui.picture.BOTTOM_SHEET_CONTENT
import com.example.pictureoftheday.ui.picture.BOTTOM_SHEET_HEADER
import com.example.pictureoftheday.ui.settings.SETTINGS_SHARED_PREFERENCES
import com.example.pictureoftheday.ui.settings.THEME_RES_ID
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class CollapsingToolbarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(
                getSharedPreferences(SETTINGS_SHARED_PREFERENCES, MODE_PRIVATE)
                        .getInt(THEME_RES_ID, R.style.DefaultTheme)
        )
        setContentView(R.layout.collapsing_toolbar_activity)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title =
                intent.getStringExtra(BOTTOM_SHEET_HEADER)
        findViewById<TextView>(R.id.collapsing_toolbar_content_textview).text =
                intent.getStringExtra(BOTTOM_SHEET_CONTENT)
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }
}