package com.geekbrains.films.model.repository.transport

interface HttpTransport {
    fun queryText(url: String, callback: (String) -> Unit)
    fun queryBytes(url: String, callback: (ByteArray) -> Unit)
}
