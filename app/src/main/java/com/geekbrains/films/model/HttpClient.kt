package com.geekbrains.films.model

import com.geekbrains.films.d
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.net.URL
import java.nio.charset.StandardCharsets
import javax.net.ssl.HttpsURLConnection

object HttpClient {
    fun queryBytes(
        url: String,
        onSuccess: (ByteArray) -> Unit,
        onError: ((String) -> Unit)?
    ) {
        Thread {
            var urlConnection: HttpsURLConnection? = null
            try {
                urlConnection = URL(url).openConnection() as HttpsURLConnection
                urlConnection.requestMethod = "GET"
                urlConnection.readTimeout = 3000
                val bytes = ByteArrayOutputStream()
                urlConnection.inputStream.copyTo(bytes, 1600)
                try {
                    onSuccess(bytes.toByteArray())
                } catch (e: Exception) {
                    d("callback error: $e")
                }
            } catch (e: Exception) {
                d("connection error: $e")
                onError?.let { it(e.toString()) }
            } finally {
                urlConnection?.disconnect()
            }
        }.start()
    }

    fun queryText(
        url: String,
        onSuccess: (String) -> Unit,
        onError: ((String) -> Unit)?
    ) {
        queryBytes(url, { bytes -> onSuccess(bytes.toString(StandardCharsets.UTF_8)) }, onError)
    }
}