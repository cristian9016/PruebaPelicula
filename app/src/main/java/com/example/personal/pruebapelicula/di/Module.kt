package com.example.personal.pruebapelicula.di

import com.example.personal.pruebapelicula.net.PeliculaClient
import com.example.personal.pruebapelicula.net.SerieClient
import com.example.personal.pruebapelicula.ui.detail.DetailViewModel
import com.example.personal.pruebapelicula.ui.main.MainViewModel
import com.google.gson.GsonBuilder
import org.koin.android.viewmodel.experimental.builder.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single {
        Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    single<PeliculaClient> { get<Retrofit>().create(PeliculaClient::class.java) }
    single<SerieClient> { get<Retrofit>().create(SerieClient::class.java) }

    viewModel<MainViewModel>()
    viewModel<DetailViewModel>()
}