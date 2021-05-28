package ru.students.nasaapipics

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import ru.students.nasaapipics.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadTheme()
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }

    override fun onResume() {
        super.onResume()

    }

    private fun loadTheme() {
        val sp = PreferenceManager.getDefaultSharedPreferences(this)

        val darkTheme = sp.getBoolean("dark_theme", false)
        val themes = sp.getString("themes", resources.getString(R.string.standard))
        Toast.makeText(this, "darkTheme=$darkTheme", Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "theme=$themes", Toast.LENGTH_SHORT).show()
        if (darkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        when (themes) {
            "standard" -> setTheme(R.style.Theme_Main)
            "orange" -> setTheme(R.style.Theme_Main_Orange)
            "blue" -> setTheme(R.style.Theme_Main_Blue)
        }
        //Toast.makeText(this, "themes $themes\t string " + resources.getString(R.string.orange), Toast.LENGTH_SHORT).show()
    }


}