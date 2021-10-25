package com.geekbrains.films.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import com.geekbrains.films.d


class ConnectivityBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            val networkInfo: NetworkInfo? =
                intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO)
            Toast.makeText(
                context!!,
                "Сетевое событие: $networkInfo",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}