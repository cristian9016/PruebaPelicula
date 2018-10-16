package com.example.personal.pruebapelicula.ui.main

import android.arch.lifecycle.ViewModel
import com.example.personal.pruebapelicula.repository.MainRepository

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    fun getDataOnline(option: Int) =
            repository.getDataOnline(option)

    fun getDataOffline(option: Int) =
            repository.getDataOffline(option)

    fun searchMovieOrSerie() =
            repository.searchMovieOrSerie()

}