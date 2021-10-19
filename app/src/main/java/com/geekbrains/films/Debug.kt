package com.geekbrains.films

import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar

fun d(msg: String) {
    Log.d("++", msg)
}

fun View.snackLong(view: View, text: String) {
    Snackbar.make(view,text, Snackbar.LENGTH_LONG).show()
}

fun View.snackLong(view: View, resource: Int) {
    Snackbar.make(view, resource, Snackbar.LENGTH_LONG).show()
}

fun View.snackInd(view: View, text: String) {
    Snackbar.make(view, text, Snackbar.LENGTH_INDEFINITE).show()
}

fun View.snackInd(view: View, resource: Int) {
    Snackbar.make(view, resource, Snackbar.LENGTH_INDEFINITE).show()
}