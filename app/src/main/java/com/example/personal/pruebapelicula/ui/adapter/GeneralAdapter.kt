package com.example.personal.pruebapelicula.ui.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.personal.pruebapelicula.R
import com.example.personal.pruebapelicula.data.model.Pelicula
import com.example.personal.pruebapelicula.data.model.Serie
import com.example.personal.pruebapelicula.databinding.TemplatePeliculaBinding
import com.example.personal.pruebapelicula.databinding.TemplateSerieBinding
import com.example.personal.pruebapelicula.ui.main.MainActivity
import com.example.personal.pruebapelicula.util.Constants
import com.example.personal.pruebapelicula.util.inflate
import io.reactivex.subjects.PublishSubject

class GeneralAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data : MutableList<MainActivity.ItemType>  = mutableListOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }
    val onClickPelicula= PublishSubject.create<Pelicula>()
    val onClickSerie= PublishSubject.create<Serie>()

    override fun getItemViewType(position: Int): Int = when(data[position].type){
        Constants.type_movie -> {Constants.type_movie}
        else -> {Constants.type_serie}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when(viewType){
        Constants.type_movie -> { PeliculaHolder(parent.inflate(R.layout.template_pelicula))}
        else -> {SerieHolder(parent.inflate(R.layout.template_serie))}
    }
    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is PeliculaHolder -> holder.bind(data[position].item as Pelicula, onClickPelicula)
            is SerieHolder -> holder.bind(data[position].item as Serie, onClickSerie)
        }
    }

    class PeliculaHolder(view:View):RecyclerView.ViewHolder(view){
        val binding : TemplatePeliculaBinding = DataBindingUtil.bind(view)!!
        fun bind(pelicula: Pelicula,onClickItem:PublishSubject<Pelicula>){
            binding.pelicula = pelicula
            binding.clickPelicula = onClickItem
        }
    }

    class SerieHolder(view:View):RecyclerView.ViewHolder(view){
        val binding : TemplateSerieBinding = DataBindingUtil.bind(view)!!
        fun bind(serie: Serie,onClickItem: PublishSubject<Serie>){
            binding.serie = serie
            binding.clickSerie = onClickItem
        }
    }
}