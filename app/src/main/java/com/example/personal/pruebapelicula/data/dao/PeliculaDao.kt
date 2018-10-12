package com.example.personal.pruebapelicula.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.personal.pruebapelicula.data.model.Pelicula
import io.reactivex.Flowable

@Dao
interface PeliculaDao {
    @Insert
    fun insert(pelicula: Pelicula)

    @Query("SELECT * FROM Pelicula WHERE id = :id")
    fun getMovie(id: Long): Pelicula

    @Query("SELECT * FROM Pelicula WHERE genero = :genero")
    fun all(genero: Int): Flowable<List<Pelicula>>

    @Query("SELECT * FROM Pelicula WHERE title like '%'+:title+'%'")
    fun searchMovie(title: String): Flowable<List<Pelicula>>
}
