package com.example.personal.pruebapelicula.net

import com.example.personal.pruebapelicula.data.model.Serie
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SerieClient {
    @GET("/3/tv/popular")
    fun getPopularSeries(@Header("Authorization") token:String, @Query("api_key") key:String,@Query("language")language:String): Observable<ResponseData<Serie>>

    @GET("/3/tv/top_rated")
    fun getTopRatedSeries(@Header("Authorization") token:String, @Query("api_key") key:String,@Query("language")language:String): Observable<ResponseData<Serie>>

}