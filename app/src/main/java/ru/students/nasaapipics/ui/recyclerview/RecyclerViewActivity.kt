package ru.students.nasaapipics.ui.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.DialogCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import ru.students.nasaapipics.databinding.ActivityRecyclerViewBinding
import java.util.*

class RecyclerViewActivity : AppCompatActivity() {

    private lateinit var vb: ActivityRecyclerViewBinding
    private val mRecyclerViewModel: RecyclerViewViewModel by lazy {
        RecyclerViewViewModel(NotesRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(vb.root)

        mRecyclerViewModel.init()
        mRecyclerViewModel.getNotes().observe(this, {
            vb.recyclerView.adapter?.notifyDataSetChanged()
        })
        initRecyclerView()
        vb.fab.setOnClickListener {

            mRecyclerViewModel.addNewNote(Notes("Новая", "Заметка"))
            //TODO("Экран создания новой заметки")
        }
    }

    private fun initRecyclerView() {
        val adapter = RecyclerViewAdapter(mRecyclerViewModel.getNotes().value!! as MutableList<Notes>)
        vb.recyclerView.adapter = adapter
        vb.recyclerView.layoutManager = GridLayoutManager(this,2)
        ItemTouchHelper(ItemTouchHelperCallback(adapter))
            .attachToRecyclerView(vb.recyclerView)

    }
}