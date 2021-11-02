package com.geekbrains.films.model.repository.transport

import com.geekbrains.films.d
import com.geekbrains.films.model.repository.transport.util.BlockingHttpClient

class ThreadedHttpTransport : HttpTransport {
    override fun queryText(url: String, callback: (String) -> Unit) {
        runThread("queryText") { callback(BlockingHttpClient.queryText(url)) }
    }

    override fun queryBytes(url: String, callback: (ByteArray) -> Unit) {
        runThread("queryBytes") { callback(BlockingHttpClient.queryBytes(url)) }
    }

    private fun runThread(errorContext: String, run: () -> Unit) {
        Thread {
            try {
                run()
            } catch (e : Exception) {
                d("$errorContext callback error: $e")
            }
        }.start()
    }
}