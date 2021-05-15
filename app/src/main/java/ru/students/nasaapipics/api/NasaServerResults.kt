package ru.students.nasaapipics.api

sealed class NasaServerResults {
    data class Success(val serverResponseData: NasaServerResponseData) : NasaServerResults()
    data class Error(val error: Throwable) : NasaServerResults()
    data class Loading(val progress: Int?) : NasaServerResults()
}