package com.example.personal.pruebapelicula.ui.detail

import android.animation.ObjectAnimator
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.AnimationUtils
import com.example.personal.pruebapelicula.R
import com.example.personal.pruebapelicula.data.model.Pelicula
import com.example.personal.pruebapelicula.data.model.Serie
import com.example.personal.pruebapelicula.databinding.ActivityDetailBinding
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    val pelicula: Pelicula? by lazy { intent.getParcelableExtra<Pelicula>(PELICULA) }
    val serie: Serie? by lazy { intent.getParcelableExtra<Serie>(SERIE) }
    lateinit var binding: ActivityDetailBinding
    var ratingValue = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if(pelicula!=null) {
            binding.pelicula = pelicula
            ratingValue = pelicula!!.vote_average
        }
        else {
            binding.serie = serie
            ratingValue = serie!!.vote_average
        }
        imgPoster.startAnimation(AnimationUtils.loadAnimation(this,R.anim.img_detail_anim))
        ObjectAnimator.ofFloat(ratingBar,"rating",0f,ratingValue)
                .setDuration(2000)
                .start()
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
