package com.codingchallenge.services

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.qonversion.android.sdk.Qonversion

class AppFirebaseMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        message.notification?.let {
            Log.d("Notification", "${it.title}")
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Qonversion.setNotificationsToken(token)
    }
}