package com.example.pictureoftheday.ui.settings

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pictureoftheday.R
import com.example.pictureoftheday.databinding.SettingsFragmentBinding

const val SETTINGS_SHARED_PREFERENCES = "SettingsSharedPreferences"
const val THEME_RES_ID = "ThemeResID"
private const val THEME_NAME_SHARED_PREFERENCES = "ThemeNameSharedPreferences"
const val MY_DEFAULT_THEME = 0
const val MY_CUSTOM_THEME_BLUE = 1
const val My_CUSTOM_THEME_RED = 2

class SettingsFragment : Fragment() {

    private var _binding: SettingsFragmentBinding? = null
    private val binding get() = _binding!!

    private var currentTheme: Int? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = SettingsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        setSharedPreferencesSettings()
        binding.defaultTheme.setOnClickListener {
            if (currentTheme != MY_DEFAULT_THEME) {
                requireActivity().apply {
                    setTheme(R.style.DefaultTheme)
                    recreate()
                    saveThemeSettings(MY_DEFAULT_THEME, R.style.DefaultTheme)
                }
            }
        }
        binding.blueTheme.setOnClickListener {
            if (currentTheme != MY_CUSTOM_THEME_BLUE) {
                requireActivity().apply {
                    setTheme(R.style.MyCustomThemeBlue)
                    recreate()
                    saveThemeSettings(MY_CUSTOM_THEME_BLUE, R.style.MyCustomThemeBlue)
                }
            }
        }
        binding.redTheme.setOnClickListener {
            if (currentTheme != My_CUSTOM_THEME_RED) {
                requireActivity().apply {
                    setTheme(R.style.MyCustomThemeRed)
                    recreate()
                    saveThemeSettings(My_CUSTOM_THEME_RED, R.style.MyCustomThemeRed)
                }
            }
        }
    }

    private fun setSharedPreferencesSettings() {
        activity?.let {
            currentTheme =
                    it.getSharedPreferences(SETTINGS_SHARED_PREFERENCES, MODE_PRIVATE)
                            .getInt(THEME_NAME_SHARED_PREFERENCES, MY_DEFAULT_THEME)
            when (currentTheme) {
                MY_CUSTOM_THEME_BLUE -> {
                    binding.blueTheme.isChecked = true
                }
                My_CUSTOM_THEME_RED -> {
                    binding.redTheme.isChecked = true
                }
                else -> {
                    binding.defaultTheme.isChecked = true
                }
            }
        }
    }

    private fun saveThemeSettings(currentTheme: Int, style: Int) {
        this.currentTheme = currentTheme
        activity?.let {
            with(it.getSharedPreferences(SETTINGS_SHARED_PREFERENCES, MODE_PRIVATE).edit()) {
                putInt(THEME_NAME_SHARED_PREFERENCES, currentTheme).apply()
                putInt(THEME_RES_ID, style).apply()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}