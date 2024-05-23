package ru.students.nasaapipics

import android.animation.Animator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import ru.students.nasaapipics.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private var handler = Handler(Looper.getMainLooper())
    private lateinit var vb: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadTheme()
        vb = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(vb.root)

        vb.imageView.animate().setStartDelay(300).rotationBy(720f)
            .setInterpolator(DecelerateInterpolator())
            .setDuration(2700).setListener(object : Animator.AnimatorListener {
                override fun onAnimationEnd(animation: Animator) {
                    handler.removeCallbacksAndMessages(null)
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }

                override fun onAnimationRepeat(animation: Animator) {}
                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationStart(animation: Animator) {}
            })

        handler.postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 3000)
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }

    private fun loadTheme() {
        val sp = PreferenceManager.getDefaultSharedPreferences(this)

        val darkTheme = sp.getBoolean("dark_theme", false)
        val themes = sp.getString("themes", resources.getString(R.string.standard))
        //Toast.makeText(this, "darkTheme=$darkTheme", Toast.LENGTH_SHORT).show()
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