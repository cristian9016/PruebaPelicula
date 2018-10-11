package com.example.personal.pruebapelicula.util

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText

fun ViewGroup.inflate(layout: Int) = LayoutInflater.from(context).inflate(layout, this, false)

fun EditText.text(): String = text.toString()

fun AppCompatActivity.putFragment(container: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction()
            .replace(container, fragment)
            .commit()
}

fun Fragment.putFragment(container: Int, fragment: Fragment) {
    childFragmentManager.beginTransaction()
            .replace(container, fragment)
            .commit()
}