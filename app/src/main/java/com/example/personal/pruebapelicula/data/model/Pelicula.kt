package com.example.personal.pruebapelicula.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Entity
@Parcelize
class Pelicula (@PrimaryKey var id_pelicula:Long?,
                var id:Long,
                var title:String,
                var overview:String,
                var poster_path:String?,
                var backdrop_path:String?,
                var release_date:String,
                var vote_average:Float,
                var genero:Int):Parcelable{
    @Ignore
    constructor(id:Long,title: String,overview: String,poster_path: String,backdrop_path:String,release_date: String,vote_average: Float,genero: Int):
            this(null,id,title,overview,poster_path,backdrop_path,release_date,vote_average,genero)
}