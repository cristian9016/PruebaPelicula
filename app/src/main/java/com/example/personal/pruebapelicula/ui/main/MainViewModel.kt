package com.example.personal.pruebapelicula.ui.main

import android.arch.lifecycle.ViewModel
import com.example.personal.pruebapelicula.data.repository.MainRepository

class MainViewModel(val repository: MainRepository) : ViewModel() {

    fun getDataOnline(option: Int) =
            repository.getDataOnline(option)

    fun getDataOffline(option: Int) =
            repository.getDataOffline(option)

    fun searchMovieOrSerieOffline() =
            repository.searchMovieOrSerieOffline()

    fun searchMovieOrSerie() =
            repository.searchMovieOrSerie()

}