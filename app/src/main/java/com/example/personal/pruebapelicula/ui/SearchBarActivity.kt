package com.example.personal.pruebapelicula.ui

import android.annotation.SuppressLint
import android.app.SearchManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import com.example.personal.pruebapelicula.R
import com.jakewharton.rxbinding2.support.v7.widget.queryTextChanges
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

@SuppressLint("Registered")
open class SearchBarActivity: AppCompatActivity(){

    private lateinit var searchView:SearchView
    private var queryDis:Disposable? = null
    private lateinit var searchMenu:Menu

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        searchMenu = menu
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menu.clear()
        menuInflater.inflate(R.menu.menu_search, menu)
        setupSearchBar(menu)
        return super.onPrepareOptionsMenu(menu)
    }

    private fun setupSearchBar(menu:Menu){
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.search_toolbar).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setIconifiedByDefault(true)
        searchView.setOnSearchClickListener { supportActionBar?.setDisplayHomeAsUpEnabled(false) }
        searchView.setOnCloseListener {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            onSearchCloseListener.onNext(true)
            false
        }
        queryDis = searchView.queryTextChanges()
                .subscribe { query.onNext(it.toString()) }
    }

    override fun onDestroy() {
        super.onDestroy()
        queryDis?.dispose()
    }

    override fun onBackPressed() {
        if(searchView.isIconified) super.onBackPressed()
        else searchView.isIconified = true
    }

    companion object {
        val query: PublishSubject<String> = PublishSubject.create()
        val onSearchCloseListener:PublishSubject<Boolean> = PublishSubject.create()
    }

}