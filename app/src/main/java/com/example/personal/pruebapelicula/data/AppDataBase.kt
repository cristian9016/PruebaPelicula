package com.example.personal.pruebapelicula.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverter
import android.arch.persistence.room.TypeConverters
import com.example.personal.pruebapelicula.data.dao.PeliculaDao
import com.example.personal.pruebapelicula.data.dao.SerieDao
import com.example.personal.pruebapelicula.data.model.Pelicula
import com.example.personal.pruebapelicula.data.model.Serie
import java.util.*

object DateConverter{

    @TypeConverter
    @JvmStatic
    fun dateToLong(date: Date):Long = date.time

    @TypeConverter
    @JvmStatic
    fun longToDate(timestap:Long) = Date(timestap)

}

@Database(entities = [Pelicula::class, Serie::class],version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase: RoomDatabase(){

    abstract fun peliculaDao(): PeliculaDao
    abstract fun serieDao(): SerieDao

}