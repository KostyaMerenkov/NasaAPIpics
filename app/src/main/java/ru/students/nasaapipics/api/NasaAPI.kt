package ru.students.nasaapipics.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaAPI {
    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String): Call<NasaServerResponseData>

    @GET("planetary/apod")
    fun getPictureOfTheOtherDay(@Query("api_key") apiKey: String,
                                @Query("date") date: String): Call<NasaServerResponseData>
}