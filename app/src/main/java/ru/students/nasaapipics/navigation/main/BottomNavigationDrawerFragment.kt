package ru.students.nasaapipics.navigation.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.students.nasaapipics.ActivityConstraintSetAnimation
import ru.students.nasaapipics.AnimationsActivity
import ru.students.nasaapipics.R
import ru.students.nasaapipics.SettingsActivity
import ru.students.nasaapipics.databinding.DrawerBottomBinding
import ru.students.nasaapipics.ui.main.bottomnavigationview.BottomNavigationActivity
import ru.students.nasaapipics.ui.main.viewpager.ViewPagerActivity

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private lateinit var vb: DrawerBottomBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        DrawerBottomBinding.inflate(inflater, container, false).also {
            vb = it
        }.root

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        vb.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_one -> {
                    Toast.makeText(context, "1", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(context, BottomNavigationActivity()::class.java))
                    this.dismiss()
                }
                R.id.navigation_two -> {
                    Toast.makeText(context, "2", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(context, ViewPagerActivity()::class.java))
                    this.dismiss()
                }
                R.id.navigation_settings -> {
                    Toast.makeText(context, "Settings", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(context, SettingsActivity()::class.java))
                    this.dismiss()
                }
                R.id.navigation_animations-> {
                    Toast.makeText(context, "Animations", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(context, AnimationsActivity()::class.java))
                    this.dismiss()
                }
                R.id.navigation_animations_bonus-> {
                    Toast.makeText(context, "Animations (Bonus)", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(context, ActivityConstraintSetAnimation()::class.java))
                    this.dismiss()
                }
            }
            true
        }
    }
}

