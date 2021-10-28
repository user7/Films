package com.geekbrains.films.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.*
import com.geekbrains.films.d
import java.io.ByteArrayOutputStream
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class UrlFetchService : Service() {
    private var serviceLooper: Looper? = null
    private var serviceHandler: ServiceHandler? = null

    private inner class ServiceHandler(looper: Looper) : Handler(looper) {
        override fun handleMessage(msg: Message) {
            val urlString = msg.data.getString(KEY_URL)
            d("fetching $urlString")
            var conn: HttpsURLConnection? = null
            try {
                val bytes = ByteArrayOutputStream()
                conn = URL(urlString).openConnection() as HttpsURLConnection
                with(conn) {
                    requestMethod = "GET"
                    readTimeout = 3000
                    inputStream.copyTo(bytes, 1600)
                }
                val intent = Intent(ACTION_URL_FETCHED)
                intent.putExtra(KEY_URL, urlString)
                intent.putExtra(KEY_BYTES, bytes.toByteArray())
                d("encoding ${conn.contentEncoding}")
                sendBroadcast(intent)
            } catch (e: Exception) {
                d("connection error $e")
            } finally {
                conn?.disconnect()
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
        const val KEY_BYTES = "b"
        const val ACTION_URL_FETCHED = "com.geekbrains.films.url_fetched"
        var context: Context? = null

        fun start(url: String) {
            val intent = Intent(context, UrlFetchService::class.java)
            intent.putExtra(KEY_URL, url)
            context?.startService(intent)
        }

        fun stop() {
            context?.startService(Intent(context, UrlFetchService::class.java))
        }
    }
}