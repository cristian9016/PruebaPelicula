package com.example.personal.pruebapelicula.ui.detail

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.personal.pruebapelicula.R
import com.example.personal.pruebapelicula.data.model.Pelicula
import com.example.personal.pruebapelicula.data.model.Serie
import com.example.personal.pruebapelicula.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    val pelicula: Pelicula? by lazy { intent.getParcelableExtra<Pelicula>(PELICULA) }
    val serie: Serie? by lazy { intent.getParcelableExtra<Serie>(SERIE) }
    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if(pelicula!=null) binding.pelicula = pelicula
        else binding.serie = serie
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val PELICULA = "pelicula"
        const val SERIE = "serie"
    }
}
