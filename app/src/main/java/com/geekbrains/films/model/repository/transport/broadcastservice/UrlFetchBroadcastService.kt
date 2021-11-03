package com.geekbrains.films.model.repository.transport.broadcastservice

import android.app.Service
import android.content.Intent
import android.os.*
import com.geekbrains.films.model.repository.transport.util.BlockingHttpClient

class UrlFetchBroadcastService : Service() {
    private var serviceLooper: Looper? = null
    private var serviceHandler: ServiceHandler? = null

    private inner class ServiceHandler(looper: Looper) : Handler(looper) {
        override fun handleMessage(msg: Message) {
            msg.data.getString(KEY_URL)?.let { url ->
                val isText = msg.data.getBoolean(KEY_IS_TEXT, false)
                val intent = Intent(ACTION_URL_FETCHED)
                intent.putExtra(KEY_URL, url)
                if (isText) {
                    intent.putExtra(KEY_TEXT, BlockingHttpClient.queryText(url))
                } else {
                    intent.putExtra(KEY_BYTES, BlockingHttpClient.queryBytes(url))
                }
                sendBroadcast(intent)
            }
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
        intent?.let {
            serviceHandler?.obtainMessage()?.also { msg ->
                msg.arg1 = startId
                msg.data = intent.extras
                serviceHandler?.sendMessage(msg)
            }
        }
        return START_STICKY
    }

    companion object {
        const val PREFIX = "com.geekbrains.films"
        const val KEY_URL = "$PREFIX.url"
        const val KEY_BYTES = "$PREFIX.bytes"
        const val KEY_TEXT = "$PREFIX.text"
        const val KEY_IS_TEXT = "$PREFIX.is_text"
        const val ACTION_URL_FETCHED = "$PREFIX.url_fetched"
    }
}