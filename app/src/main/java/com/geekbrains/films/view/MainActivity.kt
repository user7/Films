package com.geekbrains.films.view

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.geekbrains.films.R
import com.geekbrains.films.services.ConnectivityBroadcastReceiver
import com.geekbrains.films.viewmodel.FilmsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val model by viewModel<FilmsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerReceiver(ConnectivityBroadcastReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }
}