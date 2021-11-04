package com.geekbrains.films.model.repository.transport.util

import java.io.ByteArrayOutputStream
import java.net.URL
import java.nio.charset.StandardCharsets
import javax.net.ssl.HttpsURLConnection

object BlockingHttpClient {
    fun queryBytes(url: String) : ByteArray {
        with (URL(url).openConnection() as HttpsURLConnection) {
            requestMethod = "GET"
            readTimeout = 3000
            val bytes = ByteArrayOutputStream()
            inputStream.copyTo(bytes, 1600)
            disconnect()
            return bytes.toByteArray()
        }
    }

    fun queryText(url: String) : String {
        return queryBytes(url).toString(StandardCharsets.UTF_8)
    }
}