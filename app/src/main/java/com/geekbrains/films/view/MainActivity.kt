package com.geekbrains.films.view

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.geekbrains.films.R
import com.geekbrains.films.services.ConnectivityBroadcastReceiver
import com.geekbrains.films.services.UrlFetchService
import com.geekbrains.films.services.UrlFetchBroadcastReceiver
import com.geekbrains.films.viewmodel.FilmsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val model: FilmsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        UrlFetchService.context = this
        registerReceiver(ConnectivityBroadcastReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        registerReceiver(UrlFetchBroadcastReceiver(model), IntentFilter(UrlFetchService.ACTION_URL_FETCHED))
    }
}