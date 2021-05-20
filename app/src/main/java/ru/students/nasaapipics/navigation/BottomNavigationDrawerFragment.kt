package ru.students.nasaapipics.navigation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.students.nasaapipics.MainActivity
import ru.students.nasaapipics.R
import ru.students.nasaapipics.SettingsActivity
import ru.students.nasaapipics.databinding.DrawerBottomBinding

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

    @SuppressLint("ResourceType")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        vb.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_one -> Toast.makeText(context, "1", Toast.LENGTH_SHORT).show()
                R.id.navigation_two -> Toast.makeText(context, "2", Toast.LENGTH_SHORT).show()
                R.id.navigation_settings -> {
                    Toast.makeText(context, "Настройки", Toast.LENGTH_SHORT).show()
                    dialog?.dismiss()
                    startActivity(Intent(context, SettingsActivity::class.java))
                }
            }
            true
        }
    }
}

