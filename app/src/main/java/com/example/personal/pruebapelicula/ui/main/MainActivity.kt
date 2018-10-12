package com.example.personal.pruebapelicula.ui.main

import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import com.example.personal.pruebapelicula.R
import com.example.personal.pruebapelicula.ui.SearchBarActivity
import com.example.personal.pruebapelicula.ui.adapter.GeneralAdapter
import com.example.personal.pruebapelicula.ui.detail.DetailActivity
import com.example.personal.pruebapelicula.util.Constants
import com.example.personal.pruebapelicula.util.LifeDisposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : SearchBarActivity(), DrawerLayout.DrawerListener {

    val toogle: ActionBarDrawerToggle by lazy {
        ActionBarDrawerToggle(this, drawer, R.string.opened_menu, R.string.closed_menu)
    }
    lateinit var adapter: GeneralAdapter
    val viewModel: MainViewModel by viewModel()
    val dis = LifeDisposable(this)
    var option = Constants.GENRE_MOVIE_POPULAR

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        if (savedInstanceState != null) option = savedInstanceState.getInt(OPTION)
        drawer.addDrawerListener(this)
        navigation.setNavigationItemSelectedListener { setContent(it) }
        adapter = GeneralAdapter()
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this)
        title = resources.getString(R.string.title_movie_popular)
        getDataOnline(option)
    }

    override fun onResume() {
        super.onResume()
        dis add adapter.onClickPelicula
                .subscribe { startActivity<DetailActivity>(PELICULA to it) }
        dis add adapter.onClickSerie
                .subscribe { startActivity<DetailActivity>(SERIE to it) }
        dis add viewModel.searchMovieOrSerie()
                .subscribeBy(
                        onNext = {
                            adapter.data = it
                        },
                        onError = {
                            toast(it.message!!)
                        }
                )
    }

    private fun setContent(item: MenuItem?): Boolean {
        drawer.closeDrawers()
        when (item?.itemId) {
            R.id.movie_popular -> {
                title = resources.getString(R.string.title_movie_popular)
                getDataOnline(Constants.GENRE_MOVIE_POPULAR)
            }
            R.id.movie_toprated -> {
                title = resources.getString(R.string.title_movie_TopRated)
                getDataOnline(Constants.GENRE_MOVIE_TOP_RATED)
            }
            R.id.movie_upcoming -> {
                title = resources.getString(R.string.title_movie_Upcoming)
                getDataOnline(Constants.GENRE_MOVIE_UPCOMING)
            }
            R.id.series_popular -> {
                title = resources.getString(R.string.title_serie_popular)
                getDataOnline(Constants.GENRE_SERIE_POPULAR)
            }
            R.id.series_top_rated -> {
                title = resources.getString(R.string.title_serie_topRated)
                getDataOnline(Constants.GENRE_SERIE_TOP_RATED)
            }
        }
        return true
    }

    private fun getDataOnline(option: Int) {
        this.option = option
        dis add viewModel.getDataOnline(option)
                .subscribeBy(
                        onNext = {
                            adapter.data = it
                        },
                        onError = { getDataOffline(option) }
                )
    }

    fun getDataOffline(option: Int) {
        dis add viewModel.getDataOffline(option)
                .subscribeBy(
                        onNext = {
                            if (it.isEmpty()) noInformation.visibility = View.VISIBLE
                            else noInformation.visibility = View.GONE
                            adapter.data = it
                        },
                        onError = {
                            noInformation.visibility = View.VISIBLE
                            it.printStackTrace()
                            toast(it.message!!)
                        },
                        onComplete = {
                            toast("no encontrado")
                        }
                )
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toogle.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (toogle.onOptionsItemSelected(item)) return true
        return super.onOptionsItemSelected(item)
    }

    override fun onDrawerStateChanged(p0: Int) = toogle.onDrawerStateChanged(p0)

    override fun onDrawerSlide(p0: View, p1: Float) = toogle.onDrawerSlide(p0, p1)

    override fun onDrawerClosed(p0: View) = toogle.onDrawerClosed(p0)

    override fun onDrawerOpened(p0: View) = toogle.onDrawerOpened(p0)

    override fun onSaveInstanceState(outState: Bundle?) {
        outState!!.putInt(OPTION, option)
        super.onSaveInstanceState(outState)
    }

    class ItemType(val item: Any, val type: Int)
    companion object {
        const val OPTION = "option"
        const val PELICULA = "pelicula"
        const val SERIE = "serie"
    }
}
