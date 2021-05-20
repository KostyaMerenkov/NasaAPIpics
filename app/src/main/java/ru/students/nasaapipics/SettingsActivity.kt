package ru.students.nasaapipics

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.*
import ru.students.nasaapipics.databinding.MainFragmentBinding
import ru.students.nasaapipics.databinding.SettingsActivityBinding

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadTheme()
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        //TODO: preference listener and activity update
//        val tempIntent = intent;
//        tempIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//        finish();
//        overridePendingTransition(0, 0);
//        startActivity(intent);
//        overridePendingTransition(0, 0);

    }

    private fun loadTheme() {
        val sp = PreferenceManager.getDefaultSharedPreferences(this)

        val darkTheme = sp.getBoolean("dark_theme", false)
        val themes = sp.getString("themes", resources.getString(R.string.standard))
        Toast.makeText(this, "themes=$themes", Toast.LENGTH_SHORT).show()
        if (darkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        when (themes) {
            "standard" -> setTheme(R.style.Theme_Main)
            "orange" -> setTheme(R.style.Theme_Main_Orange)
            "blue" -> setTheme(R.style.Theme_Main_Blue)
        }
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            //findPreference(dark_theme)
        }

    }
}