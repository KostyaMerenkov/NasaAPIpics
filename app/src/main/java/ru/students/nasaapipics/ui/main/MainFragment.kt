package ru.students.nasaapipics.ui.main

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import coil.api.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.students.nasaapipics.MainActivity
import ru.students.nasaapipics.R
import ru.students.nasaapipics.api.NasaServerResponseData
import ru.students.nasaapipics.api.NasaServerResults
import ru.students.nasaapipics.databinding.MainFragmentBinding
import ru.students.nasaapipics.navigation.BottomNavigationDrawerFragment
import ru.students.nasaapipics.ui.main.viewpager.ViewPagerActivity
import ru.students.nasaapipics.ui.recyclerview.RecyclerViewActivity
import java.time.LocalDate

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
        private var isMain = true
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var vb: MainFragmentBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        MainFragmentBinding.inflate(inflater, container, false).also {
            vb = it
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomAppBar(view)
        setBottomSheet()
        setChips()
        vb.inputLayout.setEndIconOnClickListener {
            if (!vb.inputEditText.text.isNullOrBlank()) {
                startActivity(Intent(Intent.ACTION_VIEW).apply {
                    data =
                        Uri.parse("https://en.wikipedia.org/wiki/${vb.inputEditText.text.toString()}")
                })
            } else {
                Toast.makeText(context, "Вы ничего не ввели!", Toast.LENGTH_SHORT).show()
            }
        }
        vb.imageView.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        }
    }

    private fun setChips() {
        vb.today.isChecked = true
        vb.chipHd.setOnCheckedChangeListener { _, _ ->
            checkedDayChip(vb.chipGroup.checkedChipId)
        }
        vb.chipGroup.setOnCheckedChangeListener { _, checkedId ->
            checkedDayChip(checkedId)
        }
    }

    private fun checkedDayChip(checkedId: Int) {
        when (checkedId) {
            vb.today.id -> {
                viewModel.getData(null)
            }
            vb.yesterday.id -> {
                viewModel.getData(LocalDate.now().minusDays(1)).observe(viewLifecycleOwner, Observer<NasaServerResults> {
                    renderData(it)
                })
                Toast.makeText(context, "Yesterday!",Toast.LENGTH_SHORT).show()
            }
            vb.twoDaysAgo.id -> {
                viewModel.getData(LocalDate.now().minusDays(2)).observe(viewLifecycleOwner, Observer<NasaServerResults> {
                    renderData(it)
                })
                Toast.makeText(context, "Two days ago!",Toast.LENGTH_SHORT).show()
            }
            //https://api.nasa.gov/planetary/apod?date=2020-02-01&api_key=YUOR_KEY
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> {
                Toast.makeText(context, "Favourite", Toast.LENGTH_SHORT).show()
                startActivity(Intent(context, ViewPagerActivity::class.java))
            }
            R.id.app_bar_settings -> {
                Toast.makeText(context, "Settings", Toast.LENGTH_SHORT).show()
                startActivity(Intent(context, RecyclerViewActivity::class.java))
            }
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(parentFragmentManager, "tag")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

//    private fun startSettings() {
//        Toast.makeText(context, "Settings", Toast.LENGTH_SHORT).show()
//        vb.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_settings)
//        vb.fab.hide()
//        //vb.fab.visibility = View.GONE
//    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getData(null).observe(viewLifecycleOwner, Observer<NasaServerResults> {
            renderData(it)
        })
    }

    private fun renderData(data: NasaServerResults) {
        when (data) {
            is NasaServerResults.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                val hdurl = serverResponseData.hdurl
                if (url.isNullOrEmpty()) {
                    //showError("Сообщение, что ссылка пустая")
                    showError("Пустая ссылка.")
                } else {
                    //Отображение фото и информации
                    if (vb.chipHd.isChecked) {
                        hdurl?.let { showSuccess(serverResponseData, it) } ?: run {
                            showError("На данный день не hd изображения.")
                        }
                    } else showSuccess(serverResponseData, url)
                }
            }
            is NasaServerResults.Loading -> {
                showLoading()
            }
            is NasaServerResults.Error -> {
                showError("Нет соединения с сервером.")
            }
        }
    }

    private fun showError(errorMsg: String) {
        context?.let {
            AlertDialog.Builder(it).setTitle("Ошибка!")
                .setMessage("Не удалось загрузить изображение. $errorMsg")
                .setPositiveButton("Повторить попытку", DialogInterface.OnClickListener { _, _ ->
                    viewModel.getData(null)
                })
                .setNegativeButton("Отменить", DialogInterface.OnClickListener { _, _ ->
                    vb.progressMain.visibility = View.GONE
                    vb.imageView.visibility = View.VISIBLE
                    vb.bottomSheetInclude.bottomSheetContainer.visibility = View.GONE
                })
                .create().show()
        }
    }

    private fun showLoading() {
        vb.progressMain.visibility = View.VISIBLE
    }

    private fun showSuccess(serverResponseData: NasaServerResponseData, url: String) {

        //Coil в работе: достаточно вызвать у нашего ImageView
        //нужную extension-функцию и передать ссылку и заглушки для placeholder
        vb.imageView.load(url) {
            lifecycle(this@MainFragment)
            error(R.drawable.no_image)
            placeholder(R.drawable.no_image)
            listener { _, _ ->
                vb.progressMain.visibility = View.GONE
                vb.imageView.visibility = View.VISIBLE
            }
        }

        vb.bottomSheetInclude.bottomSheetDescriptionHeader.text = serverResponseData.title
        vb.bottomSheetInclude.bottomSheetDescription.text = serverResponseData.explanation
        vb.bottomSheetInclude.bottomSheetContainer.visibility = View.VISIBLE
    }

    private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)
        vb.fab.setOnClickListener {
            if (isMain) {
                isMain = false
                vb.bottomAppBar.navigationIcon = null
                vb.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                vb.fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_back_fab))
                vb.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_second)
            } else {
                isMain = true
                vb.bottomAppBar.navigationIcon =
                    ContextCompat.getDrawable(context, R.drawable.ic_hamburger_menu_bottom_bar)
                vb.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                vb.fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_plus_fab))
                vb.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
            }
        }
    }

    private fun setBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(vb.bottomSheetInclude.bottomSheetContainer)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    //BottomSheetBehavior.STATE_DRAGGING -> TODO("not implemented")
                    BottomSheetBehavior.STATE_COLLAPSED -> bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                    //BottomSheetBehavior.STATE_EXPANDED -> TODO("not implemented")
                    //BottomSheetBehavior.STATE_HALF_EXPANDED -> TODO("not implemented")
                    //BottomSheetBehavior.STATE_HIDDEN -> TODO("not implemented")
                    //BottomSheetBehavior.STATE_SETTLING -> TODO("not implemented")
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                //DoSmth
            }
        })
    }
}