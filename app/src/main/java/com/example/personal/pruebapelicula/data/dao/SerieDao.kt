package com.example.personal.pruebapelicula.data.dao

import android.arch.persistence.room.*
import com.example.personal.pruebapelicula.data.model.Serie
import io.reactivex.Flowable

@Dao
interface SerieDao{
    @Insert
    fun insert(serie: Serie)

    @Query("SELECT * FROM Serie WHERE id = :id")
    fun getSerie(id: Long): Serie

    @Query("SELECT * FROM Serie WHERE genero = :genero")
    fun all(genero:Int): Flowable<List<Serie>>

    @Query("SELECT * FROM Serie WHERE name like '%'+:name+'%'")
    fun searchSerie(name: String): Flowable<List<Serie>>
}