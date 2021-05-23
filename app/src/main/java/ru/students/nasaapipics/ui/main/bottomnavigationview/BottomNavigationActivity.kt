package ru.students.nasaapipics.ui.main.bottomnavigationview

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import ru.students.nasaapipics.R
import ru.students.nasaapipics.databinding.ActivityBottomNavigationBinding
import ru.students.nasaapipics.databinding.ActivityViewpagerBinding
import ru.students.nasaapipics.navigation.main.viewpager.ViewPagerAdapter
import ru.students.nasaapipics.ui.main.viewpager.EarthFragment
import ru.students.nasaapipics.ui.main.viewpager.MarsFragment
import ru.students.nasaapipics.ui.main.viewpager.WeatherFragment

class BottomNavigationActivity : AppCompatActivity() {
    private lateinit var vb: ActivityBottomNavigationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(vb.root)

        vb.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_view_earth -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_api_bottom_container, EarthFragment())
                        .commit()
                    true
                }
                R.id.bottom_view_mars -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_api_bottom_container, MarsFragment())
                        .commit()
                    true
                }
                R.id.bottom_view_weather -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_api_bottom_container, WeatherFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }
        vb.bottomNavigationView.selectedItemId = R.id.bottom_view_earth
        vb.bottomNavigationView.setOnNavigationItemReselectedListener { item ->
            when (item.itemId) {
                R.id.bottom_view_earth -> {
                    //Item retapped
                }
                R.id.bottom_view_mars -> {
                    //Item retapped
                }
                R.id.bottom_view_weather -> {
                    //Item retapped
                }
            }
        }
        val badge = vb.bottomNavigationView.getOrCreateBadge(R.id.bottom_view_earth)
        badge.maxCharacterCount = 2
        badge.number = 5

        //vb.bottomNavigationView.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_SELECTED
    }
}