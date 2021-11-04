package com.geekbrains.films.model.repository.transport

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.geekbrains.films.d
import com.geekbrains.films.model.repository.transport.broadcastservice.UrlFetchBroadcastService

class BroadcastServiceHttpTransport(val context: Context) : HttpTransport {
    private val textCallbacks = HashMap<String, (String) -> Unit>()
    private val bytesCallbacks = HashMap<String, (ByteArray) -> Unit>()

    init {
        context.registerReceiver(
            object : BroadcastReceiver() {
                override fun onReceive(ctx: Context?, intent: Intent?) =
                    onReceiveBroadcast(ctx, intent)
            },
            IntentFilter(UrlFetchBroadcastService.ACTION_URL_FETCHED)
        )
    }

    private fun onReceiveBroadcast(ctx: Context?, intent: Intent?) {
        intent?.let {
            val url = intent.getStringExtra(UrlFetchBroadcastService.KEY_URL)
            val bytes = intent.getByteArrayExtra(UrlFetchBroadcastService.KEY_BYTES)
            val text = intent.getStringExtra(UrlFetchBroadcastService.KEY_TEXT)
            url?.let {
                text?.let { textCallbacks.remove(url)?.let { it(text) } }
                bytes?.let { bytesCallbacks.remove(url)?.let { it(bytes) } }
            }
        }
    }

    override fun queryText(url: String, callback: (String) -> Unit) {
        textCallbacks.put(url, callback)
        val intent = Intent(context, UrlFetchBroadcastService::class.java)
        intent.putExtra(UrlFetchBroadcastService.KEY_URL, url)
        intent.putExtra(UrlFetchBroadcastService.KEY_IS_TEXT, true)
        context.startService(intent)
    }

    override fun queryBytes(url: String, callback: (ByteArray) -> Unit) {
        bytesCallbacks.put(url, callback)
        val intent = Intent(context, UrlFetchBroadcastService::class.java)
        intent.putExtra(UrlFetchBroadcastService.KEY_URL, url)
        intent.putExtra(UrlFetchBroadcastService.KEY_IS_TEXT, false)
        context.startService(intent)
    }
}