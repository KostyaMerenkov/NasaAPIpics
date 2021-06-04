package ru.students.nasaapipics.ui.recyclerview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.students.nasaapipics.api.NasaServerResults
import ru.students.nasaapipics.databinding.ActivityRecyclerViewBinding

class RecyclerViewViewModel(private val repository: NotesRepository): ViewModel() {
    private var notesForViewToObserve: MutableLiveData<List<Notes>> = MutableLiveData()

    fun getNotes(): LiveData<List<Notes>> = notesForViewToObserve

    fun init() {
        if (!notesForViewToObserve.value.isNullOrEmpty()) return
        else notesForViewToObserve = repository.getNotes()
    }

    fun addNewNote(note: Notes) {
        val currentNotes: MutableList<Notes> = notesForViewToObserve.value as MutableList<Notes>
        currentNotes.add(note)
        notesForViewToObserve.postValue(currentNotes)
    }


}