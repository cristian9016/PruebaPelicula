package com.example.personal.pruebapelicula.net

import com.example.personal.pruebapelicula.data.model.Pelicula
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface PeliculaClient {

    @GET("/3/movie/popular")
    fun getPopularPeliculas(@Header("Authorization") token: String, @Query("api_key") key: String, @Query("language") language: String): Observable<ResponseData<Pelicula>>

    @GET("/3/movie/top_rated")
    fun getTopRatedPeliculas(@Header("Authorization") token: String, @Query("api_key") key: String, @Query("language") language: String): Observable<ResponseData<Pelicula>>

    @GET("/3/movie/upcoming")
    fun getUpcomingPeliculas(@Header("Authorization") token: String, @Query("api_key") key: String, @Query("language") language: String): Observable<ResponseData<Pelicula>>

    @GET("/3/search/movie")
    fun searchMovie(@Header("Authorization") token: String, @Query("api_key") key: String, @Query("query") query: String, @Query("language") language: String): Observable<ResponseData<Pelicula>>

}