package com.geekbrains.films.model.repository.transport

import com.geekbrains.films.d
import okhttp3.*
import java.io.IOException

class OkHttpTransport : HttpTransport {
    private val client = OkHttpClient()

    override fun queryText(url: String, callback: (String) -> Unit) {
        queryAux(url, "queryText") { callback(it.string()) }
    }

    override fun queryBytes(url: String, callback: (ByteArray) -> Unit) {
        queryAux(url, "queryBytes") { callback(it.bytes()) }
    }

    private fun queryAux(url: String, ctx: String, callback: (ResponseBody) -> Unit) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                d("$ctx fetch error: $e")
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (response.isSuccessful) {
                        response.body()?.let { body -> callback(body) }
                    }
                }
            }
        })
    }
}