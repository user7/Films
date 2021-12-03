package com.geekbrains.films.services

import com.geekbrains.films.d
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

private const val CUSTOM_FIELD = "custom"

class MessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        d("token: $token")
        // отправляем токен на сервер
        // FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
        //    // снова получили токен???
        // }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val custom = message.data[CUSTOM_FIELD]
        d("message.custom: $custom")
    }
}