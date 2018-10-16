package com.example.personal.pruebapelicula.di

import android.arch.persistence.room.Room
import com.example.personal.pruebapelicula.data.AppDatabase
import com.example.personal.pruebapelicula.data.dao.PeliculaDao
import com.example.personal.pruebapelicula.data.dao.SerieDao
import com.example.personal.pruebapelicula.net.PeliculaClient
import com.example.personal.pruebapelicula.net.SerieClient
import com.example.personal.pruebapelicula.ui.detail.DetailViewModel
import com.example.personal.pruebapelicula.ui.main.MainViewModel
import com.example.personal.pruebapelicula.data.repository.MainRepository
import com.example.personal.pruebapelicula.util.Constants
import com.google.gson.GsonBuilder
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.experimental.builder.viewModel
import org.koin.dsl.module.module
import org.koin.experimental.builder.single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single {
        Retrofit.Builder()
                .baseUrl(Constants.URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    single<PeliculaClient> { get<Retrofit>().create(PeliculaClient::class.java) }
    single<SerieClient> { get<Retrofit>().create(SerieClient::class.java) }

    single {
        Room.databaseBuilder(androidApplication(),AppDatabase::class.java,"app.db")
                .fallbackToDestructiveMigration()
                .build()
    }

    single<PeliculaDao> { get<AppDatabase>().peliculaDao() }
    single<SerieDao> { get<AppDatabase>().serieDao()}

    viewModel<MainViewModel>()
    viewModel<DetailViewModel>()

    single<MainRepository>()
}