package com.geekbrains.films.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.*
import com.geekbrains.films.d
import java.util.*
import kotlin.collections.ArrayDeque
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

class UrlFetchService : Service() {
    private val queue = ArrayDeque<String>()
    private var serviceLooper: Looper? = null
    private var serviceHandler: ServiceHandler? = null

    private inner class ServiceHandler(looper: Looper) : Handler(looper) {
        var i = 0
        override fun handleMessage(msg: Message) {
            // TODO fetch url here
            stopSelf(msg.arg1)
        }
    }

    override fun onCreate() {
        HandlerThread("ServiceStart", Process.THREAD_PRIORITY_BACKGROUND).apply {
            start()
            serviceLooper = looper
            serviceHandler = ServiceHandler(looper)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.getStringExtra(KEY_URL)?.let { url ->
            serviceHandler?.obtainMessage()?.also { msg ->
                msg.arg1 = startId
                msg.data = Bundle().apply { putString(KEY_URL, url) }
                serviceHandler?.sendMessage(msg)
            }
        }
        return START_STICKY
    }

    companion object {
        const val KEY_URL = "u"

        fun start(context: Context, url: String) {
            val intent = Intent(context, UrlFetchService::class.java)
            intent.putExtra(KEY_URL, url)
            context.startService(intent)
        }

        fun stop(context: Context) {
            context.startService(Intent(context, UrlFetchService::class.java))
        }
    }
}