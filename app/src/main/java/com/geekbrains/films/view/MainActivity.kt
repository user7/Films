package com.geekbrains.films.view

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.geekbrains.films.R
import com.geekbrains.films.services.ConnectivityBroadcastReceiver
import com.geekbrains.films.view.contacts.ContactsFragment

import com.geekbrains.films.viewmodel.FilmsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val KEY_ADULT = "a"

class MainActivity : AppCompatActivity() {
    private val model by viewModel<FilmsViewModel>()

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
        return when (item.itemId) {
            R.id.menu_item_adult -> {
                val checked = !item.isChecked
                model.setAdultContent(checked)
                item.setChecked(checked)
                return true
            }
            R.id.item_menu_contacts -> {
                val manager = supportFragmentManager
                manager.beginTransaction()
                    .add(R.id.film_list_fragment_container, ContactsFragment())
                    .hide(manager.findFragmentByTag("film_list_fragment_tag")!!)
                    .addToBackStack("")
                    .commit()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}