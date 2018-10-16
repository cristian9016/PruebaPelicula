package com.example.personal.pruebapelicula

import com.example.personal.getgithubprofile.util.TestServer
import com.example.personal.getgithubprofile.util.TrampolineSchedulerRule
import com.example.personal.pruebapelicula.data.dao.PeliculaDao
import com.example.personal.pruebapelicula.data.dao.SerieDao
import com.example.personal.pruebapelicula.data.model.Pelicula
import com.example.personal.pruebapelicula.data.repository.MainRepository
import com.example.personal.pruebapelicula.net.PeliculaClient
import com.example.personal.pruebapelicula.net.SerieClient
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import org.mockito.Mockito.`when` as mWhen

@RunWith(MockitoJUnitRunner::class)
class MainTest {

    @Rule
    @JvmField
    val trampolineSchedulerRule: TrampolineSchedulerRule = TrampolineSchedulerRule()

    @Mock
    lateinit var peliculadao: PeliculaDao

    @Mock
    lateinit var seriedao: SerieDao

    lateinit var peliculaClient: PeliculaClient
    lateinit var serieClient: SerieClient
    lateinit var repository: MainRepository

    @Before
    fun setup() {
        val retrofit =
                Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                        .baseUrl(TestServer.httpUrl)
                        .build()
        peliculaClient = retrofit.create(PeliculaClient::class.java)
        serieClient = retrofit.create(SerieClient::class.java)
        repository = MainRepository(peliculaClient, peliculadao, serieClient, seriedao)
    }

    @Test
    fun testDataOffline() {
        val pelicula = listOf(Pelicula(123, "Pelicula Offline", "test pelicula offline", "", "", Date().toString(), 0f, 0))
        mWhen(peliculadao.all(0)).thenReturn(Flowable.just(pelicula))
        repository.getDataOffline(0)
                .test()
                .assertValue { it.size == 1 }
    }

    @Test
    fun testNoDataOffline() {
        val pelicula = listOf<Pelicula>()
        mWhen(peliculadao.all(0)).thenReturn(Flowable.just(pelicula))
        repository.getDataOffline(0)
                .test()
                .assertValue { it.size == 0 }
    }

    @Test
    fun testDataOnline() {
        repository.getDataOnline(0)
                .test()
                .assertValue { it.size == 1 }
    }
}