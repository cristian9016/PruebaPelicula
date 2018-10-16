package com.example.personal.pruebapelicula.repository

import com.example.personal.pruebapelicula.data.dao.PeliculaDao
import com.example.personal.pruebapelicula.data.dao.SerieDao
import com.example.personal.pruebapelicula.data.model.Pelicula
import com.example.personal.pruebapelicula.data.model.Serie
import com.example.personal.pruebapelicula.net.PeliculaClient
import com.example.personal.pruebapelicula.net.ResponseData
import com.example.personal.pruebapelicula.net.SerieClient
import com.example.personal.pruebapelicula.ui.SearchBarActivity
import com.example.personal.pruebapelicula.ui.main.MainActivity
import com.example.personal.pruebapelicula.util.Constants
import com.example.personal.pruebapelicula.util.applySchedulers
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class MainRepository(private val peliculaClient: PeliculaClient,
                     private val peliculaDao: PeliculaDao,
                     private val serieClient: SerieClient,
                     private val serieDao: SerieDao) {

    fun getDataOnline(option: Int): Observable<MutableList<MainActivity.ItemType>> = when (option) {
        Constants.GENRE_MOVIE_POPULAR -> peliculaClient.getPopularPeliculas(Constants.TOKEN, Constants.appKey, "es-ES")
        Constants.GENRE_MOVIE_TOP_RATED -> peliculaClient.getTopRatedPeliculas(Constants.TOKEN, Constants.appKey, "es-ES")
        Constants.GENRE_MOVIE_UPCOMING -> peliculaClient.getUpcomingPeliculas(Constants.TOKEN, Constants.appKey, "es-ES")
        Constants.GENRE_SERIE_POPULAR -> serieClient.getPopularSeries(Constants.TOKEN, Constants.appKey, "es-ES")
        else -> serieClient.getTopRatedSeries(Constants.TOKEN, Constants.appKey, "es-ES")
    }
            .map {
                for (item in it.results) {
                    if (item is Pelicula) {
                        item.genero = option
                        if (peliculaDao.getMovie(item.id) == null) peliculaDao.insert(item)
                    } else if (item is Serie) {
                        item.genero = option
                        if (serieDao.getSerie(item.id) == null) serieDao.insert(item)
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
            .toObservable()
            .applySchedulers()

    fun getDataOffline(option: Int): Observable<MutableList<MainActivity.ItemType>> = when (option) {
        Constants.GENRE_MOVIE_POPULAR, Constants.GENRE_MOVIE_TOP_RATED, Constants.GENRE_MOVIE_UPCOMING ->
            peliculaDao.all(option).toObservable()
        else -> serieDao.all(option).toObservable()
    }
            .map {
                val list = mutableListOf<MainActivity.ItemType>()
                for (item in it) {
                    if (item is Pelicula) list.add(MainActivity.ItemType(item, Constants.type_movie))
                    else list.add(MainActivity.ItemType(item as Serie, Constants.type_serie))
                }
                list
            }
            .applySchedulers()

    fun searchMovieOrSerie() = SearchBarActivity.query
            .startWith("")
            .flatMap {
                if (!it.equals("")) {
                    peliculaClient.searchMovie(Constants.TOKEN, Constants.appKey, it, "es-ES")
                            .zipWith(serieClient.searchSerie(Constants.TOKEN, Constants.appKey, it, "es-ES"),
                                    BiFunction { t1: ResponseData<Pelicula>, t2: ResponseData<Serie> ->
                                        val list = mutableListOf<MainActivity.ItemType>()
                                        for (pelicula in t1.results) {
                                            if (peliculaDao.getMovie(pelicula.id) == null) peliculaDao.insert(pelicula)
                                            list.add(MainActivity.ItemType(pelicula, Constants.type_movie))
                                        }
                                        for(serie in t2.results){
                                            if (serieDao.getSerie(serie.id) == null) serieDao.insert(serie)
                                            list.add(MainActivity.ItemType(serie, Constants.type_serie))
                                        }
                                        list
                                    }
                            ).applySchedulers()
                }else{
                    Observable.just(mutableListOf())
                }
            }
            .applySchedulers()

    fun searchMovieOrSerieForUnitTest() = Observable.just("prueba")
            .flatMap {
                if (!it.equals("")) {
                    peliculaClient.searchMovie(Constants.TOKEN, Constants.appKey, it, "es-ES")
                            .zipWith(serieClient.searchSerie(Constants.TOKEN, Constants.appKey, it, "es-ES"),
                                    BiFunction { t1: ResponseData<Pelicula>, t2: ResponseData<Serie> ->
                                        val list = mutableListOf<MainActivity.ItemType>()
                                        for (pelicula in t1.results) {
                                            if (peliculaDao.getMovie(pelicula.id) == null) peliculaDao.insert(pelicula)
                                            list.add(MainActivity.ItemType(pelicula, Constants.type_movie))
                                        }
                                        for(serie in t2.results){
                                            if (serieDao.getSerie(serie.id) == null) serieDao.insert(serie)
                                            list.add(MainActivity.ItemType(serie, Constants.type_serie))
                                        }
                                        list
                                    }
                            ).applySchedulers()
                }else{
                    Observable.just(mutableListOf())
                }
            }
            .applySchedulers()


}