package com.geekbrains.films.view

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.geekbrains.films.R
import com.geekbrains.films.d
import com.geekbrains.films.model.db.FilmDatabaseHolder
import com.geekbrains.films.model.db.FilmEntity
import com.geekbrains.films.services.ConnectivityBroadcastReceiver

import com.geekbrains.films.viewmodel.FilmsViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val KEY_ADULT = "a"

class MainActivity : AppCompatActivity() {
    private val model by viewModel<FilmsViewModel>()
    private val dbh : FilmDatabaseHolder by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerReceiver(
            ConnectivityBroadcastReceiver(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        val item = menu.findItem(R.id.menu_item_adult)
        this.getPreferences(Context.MODE_PRIVATE)?.let {
            val adult = it.getBoolean(KEY_ADULT, false)
            item.setChecked(adult)
            model.setAdultContent(adult)
        }
        model.appSettings.observe(this) {
            val editor = this.getPreferences(Context.MODE_PRIVATE)?.edit()
            editor?.putBoolean(KEY_ADULT, it.adultContent)
            editor?.apply()
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_adult) {
            val checked = !item.isChecked
            model.setAdultContent(checked)
            item.setChecked(checked)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}