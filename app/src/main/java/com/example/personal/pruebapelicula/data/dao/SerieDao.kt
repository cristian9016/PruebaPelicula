package com.example.personal.pruebapelicula.data.dao

import android.arch.persistence.room.*
import com.example.personal.pruebapelicula.data.model.Serie
import io.reactivex.Flowable

@Dao
interface SerieDao{
    @Insert
    fun insert(serie: Serie)

    @Update
    fun update(serie: Serie)

    @Delete
    fun delete(serie: Serie)

    @Query("SELECT * FROM serie WHERE genero = :genero")
    fun all(genero:Int): Flowable<List<Serie>>

    @Query("DELETE FROM serie")
    fun deleteAll()
}