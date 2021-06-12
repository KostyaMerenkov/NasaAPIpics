package ru.students.nasaapipics.ui.recyclerview

import androidx.lifecycle.MutableLiveData

class NotesRepository {
    private var dataset = arrayListOf<Notes>()
    private var data: MutableLiveData<List<Notes>> = MutableLiveData()

    fun getNotes(): MutableLiveData<List<Notes>> {
        setNotes()
        data.value = dataset
        return data
    }

    fun setNotes() {
        dataset.add(Notes("Пустая", "заметка"))
    }
}