package ru.students.nasaapipics.ui.main.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import ru.students.nasaapipics.R
import ru.students.nasaapipics.databinding.ActivityViewpagerBinding
import ru.students.nasaapipics.navigation.main.viewpager.ViewPagerAdapter

    private const val EARTH = 0
    private const val MARS = 1
    private const val WEATHER = 2

    class ViewPagerActivity : AppCompatActivity() {

        private lateinit var vb: ActivityViewpagerBinding

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            vb = ActivityViewpagerBinding.inflate(layoutInflater)
            setContentView(vb.root)
            vb.viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
            vb.tabLayout.setupWithViewPager(vb.viewPager)
            setHighlightedTab(EARTH)

            vb.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

                override fun onPageSelected(position: Int) {
                    setHighlightedTab(position)
                }

                override fun onPageScrollStateChanged(state: Int) {}
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }
            })
        }

        private fun setHighlightedTab(position: Int) {
            val layoutInflater = LayoutInflater.from(this@ViewPagerActivity)

            vb.tabLayout.getTabAt(EARTH)?.customView = null
            vb.tabLayout.getTabAt(MARS)?.customView = null
            vb.tabLayout.getTabAt(WEATHER)?.customView = null

            when (position) {
                EARTH -> {
                    setEarthTabHighlighted(layoutInflater)
                }
                MARS -> {
                    setMarsTabHighlighted(layoutInflater)
                }
                WEATHER -> {
                    setWeatherTabHighlighted(layoutInflater)
                }
                else -> {
                    setEarthTabHighlighted(layoutInflater)
                }
            }
        }

        private fun setEarthTabHighlighted(layoutInflater: LayoutInflater) {
            val earth =
                layoutInflater.inflate(R.layout.activity_viewpager_custom_tab_earth, null)
            earth.findViewById<AppCompatTextView>(R.id.tab_image_textview)
                .setTextColor(
                    ContextCompat.getColor(
                        this@ViewPagerActivity,
                        R.color.colorAccent
                    )
                )
            vb.tabLayout.getTabAt(EARTH)?.customView = earth
            vb.tabLayout.getTabAt(MARS)?.customView =
                layoutInflater.inflate(R.layout.activity_viewpager_custom_tab_mars, null)
            vb.tabLayout.getTabAt(WEATHER)?.customView =
                layoutInflater.inflate(R.layout.activity_viewpager_custom_tab_weather, null)
        }

        private fun setMarsTabHighlighted(layoutInflater: LayoutInflater) {
            val mars =
                layoutInflater.inflate(R.layout.activity_viewpager_custom_tab_mars, null)
            mars.findViewById<AppCompatTextView>(R.id.tab_image_textview)
                .setTextColor(
                    ContextCompat.getColor(
                        this@ViewPagerActivity,
                        R.color.colorAccent
                    )
                )
            vb.tabLayout.getTabAt(EARTH)?.customView =
                layoutInflater.inflate(R.layout.activity_viewpager_custom_tab_earth, null)
            vb.tabLayout.getTabAt(MARS)?.customView = mars
            vb.tabLayout.getTabAt(WEATHER)?.customView =
                layoutInflater.inflate(R.layout.activity_viewpager_custom_tab_weather, null)
        }

        private fun setWeatherTabHighlighted(layoutInflater: LayoutInflater) {
            val weather =
                layoutInflater.inflate(R.layout.activity_viewpager_custom_tab_weather, null)
            weather.findViewById<AppCompatTextView>(R.id.tab_image_textview)
                .setTextColor(
                    ContextCompat.getColor(
                        this@ViewPagerActivity,
                        R.color.colorAccent
                    )
                )
            vb.tabLayout.getTabAt(EARTH)?.customView =
                layoutInflater.inflate(R.layout.activity_viewpager_custom_tab_earth, null)
            vb.tabLayout.getTabAt(MARS)?.customView =
                layoutInflater.inflate(R.layout.activity_viewpager_custom_tab_mars, null)
            vb.tabLayout.getTabAt(WEATHER)?.customView = weather
        }
    }
