package ru.students.nasaapipics.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.students.nasaapipics.BuildConfig
import ru.students.nasaapipics.api.NasaRetrofit
import ru.students.nasaapipics.api.NasaServerResponseData
import ru.students.nasaapipics.api.NasaServerResults
import java.time.LocalDate

class MainViewModel(
    private val liveDataForViewToObserve: MutableLiveData<NasaServerResults> = MutableLiveData(),
    private val retrofitImpl: NasaRetrofit = NasaRetrofit()) : ViewModel() {

    fun getData(date: LocalDate?): LiveData<NasaServerResults> {
        if (date == null) sendServerRequest()
        else sendServerRequestDate(date)
        return liveDataForViewToObserve
    }

    private fun sendServerRequest() {
        liveDataForViewToObserve.value = NasaServerResults.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            NasaServerResults.Error(Throwable("You need an API key to use the app"))
        } else {
            retrofitImpl.getNasaRetrofit().getPictureOfTheDay(apiKey).enqueue(object :
                Callback<NasaServerResponseData> {
                override fun onResponse(
                    call: Call<NasaServerResponseData>,
                    response: Response<NasaServerResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataForViewToObserve.value =
                            NasaServerResults.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataForViewToObserve.value =
                                NasaServerResults.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataForViewToObserve.value =
                                NasaServerResults.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<NasaServerResponseData>, t: Throwable) {
                    liveDataForViewToObserve.value = NasaServerResults.Error(t)
                }
            })
        }
    }

    private fun sendServerRequestDate(date: LocalDate) {
        liveDataForViewToObserve.value = NasaServerResults.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            NasaServerResults.Error(Throwable("You need an API key to use the app"))
        } else {
            retrofitImpl.getNasaRetrofit().getPictureOfTheOtherDay(apiKey, date.toString()).enqueue(object :
                Callback<NasaServerResponseData> {
                override fun onResponse(
                    call: Call<NasaServerResponseData>,
                    response: Response<NasaServerResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataForViewToObserve.value =
                            NasaServerResults.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataForViewToObserve.value =
                                NasaServerResults.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataForViewToObserve.value =
                                NasaServerResults.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<NasaServerResponseData>, t: Throwable) {
                    liveDataForViewToObserve.value = NasaServerResults.Error(t)
                }
            })
        }
    }

}