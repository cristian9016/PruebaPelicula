package com.example.personal.pruebapelicula.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
class Serie (@PrimaryKey var id_serie:Long?,
             var id:Long,
             var name:String,
             var overview:String,
             var poster_path:String,
             var backdrop_path:String,
             var first_air_date:String,
             var vote_average:Float,
             var genero:Int): Parcelable{
    @Ignore
    constructor(id:Long,name: String,overview: String,poster_path: String,backdrop_path:String,first_air_date: String,vote_average: Float,genero: Int):
            this(null,id,name,overview,poster_path,backdrop_path,first_air_date,vote_average,genero)
}