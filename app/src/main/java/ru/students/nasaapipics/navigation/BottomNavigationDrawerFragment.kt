package ru.students.nasaapipics.navigation

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.students.nasaapipics.*
import ru.students.nasaapipics.databinding.DrawerBottomBinding
import ru.students.nasaapipics.ui.main.MainFragment

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private lateinit var vb: DrawerBottomBinding
    private lateinit var startForResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                // Handle the Intent
                //do stuff here
                Log.d("TEST", "activity recreate")
                activity?.recreate()
            }
        }
    }

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
                R.id.navigation_animations -> {
                    Toast.makeText(context, "Анимации", Toast.LENGTH_SHORT).show()
                    dialog?.dismiss()
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.container, AnimationsFragment())
                        .commitNow()
                }
                R.id.navigation_one -> Toast.makeText(context, "1", Toast.LENGTH_SHORT).show()
                R.id.navigation_two -> Toast.makeText(context, "2", Toast.LENGTH_SHORT).show()
                R.id.navigation_settings -> {
                    Toast.makeText(context, "Настройки", Toast.LENGTH_SHORT).show()
                    dialog?.dismiss()
                    startForResult.launch(Intent(context, SettingsActivity::class.java))
                }
            }
            true
        }

    }



}

