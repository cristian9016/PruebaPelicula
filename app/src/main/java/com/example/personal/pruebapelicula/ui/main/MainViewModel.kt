package com.example.personal.pruebapelicula.ui.main

import android.arch.lifecycle.ViewModel
import com.example.personal.pruebapelicula.data.dao.PeliculaDao
import com.example.personal.pruebapelicula.data.dao.SerieDao
import com.example.personal.pruebapelicula.data.model.Pelicula
import com.example.personal.pruebapelicula.data.model.Serie
import com.example.personal.pruebapelicula.net.PeliculaClient
import com.example.personal.pruebapelicula.net.SerieClient
import com.example.personal.pruebapelicula.util.Constants
import com.example.personal.pruebapelicula.util.applySchedulers
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.Observable
import io.reactivex.Single

class MainViewModel(private val peliculaClient: PeliculaClient,
                    private val peliculaDao: PeliculaDao,
                    private val serieClient: SerieClient,
                    private val serieDao: SerieDao) : ViewModel() {

    fun getItems(option: Int): Observable<List<MainActivity.ItemType>> =
            ReactiveNetwork.checkInternetConnectivity()
                    .flatMapObservable { conn ->
                        if (conn) getDataOnline(option).toObservable()
                        else getDataOffline(option).toObservable()
                    }.applySchedulers()

    private fun getDataOnline(option: Int): Single<List<MainActivity.ItemType>> = when (option) {
        Constants.GENRE_MOVIE_POPULAR -> peliculaClient.getPopularPeliculas(Constants.TOKEN, Constants.appKey, "es-ES")
        Constants.GENRE_MOVIE_TOP_RATED -> peliculaClient.getTopRatedPeliculas(Constants.TOKEN, Constants.appKey, "es-ES")
        Constants.GENRE_MOVIE_UPCOMING -> peliculaClient.getUpcomingPeliculas(Constants.TOKEN, Constants.appKey, "es-ES")
        Constants.GENRE_SERIE_POPULAR -> serieClient.getPopularSeries(Constants.TOKEN, Constants.appKey, "es-ES")
        Constants.GENRE_SERIE_TOP_RATED -> serieClient.getTopRatedSeries(Constants.TOKEN, Constants.appKey, "es-ES")
        else -> serieClient.getPopularSeries(Constants.TOKEN, Constants.appKey, "es-ES")
    }
            .map {
                for (item in it.results) {
                    if (item is Pelicula) {
                        item.genero = option
                        peliculaDao.insert(item)
                    } else if (item is Serie) {
                        item.genero = option
                        serieDao.insert(item)
                    }
                }
                it.results
            }
            .flatMapIterable { it }
            .map {
                if (it is Pelicula) return@map MainActivity.ItemType(it, Constants.type_movie)
                else return@map MainActivity.ItemType(it as Serie, Constants.type_serie)
            }
            .toList()

    private fun getDataOffline(option: Int): Single<List<MainActivity.ItemType>> = when (option) {
        Constants.GENRE_MOVIE_POPULAR,
        Constants.GENRE_MOVIE_TOP_RATED,
        Constants.GENRE_MOVIE_UPCOMING -> peliculaDao.all(option).toObservable()
        else -> serieDao.all(option).toObservable()
    }
            .flatMapIterable { it }
            .map {
                if (it is Pelicula) return@map MainActivity.ItemType(it, Constants.type_movie)
                else return@map MainActivity.ItemType(it as Serie, Constants.type_serie)
            }
            .toList()


}