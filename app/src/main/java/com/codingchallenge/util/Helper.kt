package com.codingchallenge.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import java.text.SimpleDateFormat
import java.util.*

fun parseDate(src: String): String? {
    return try {
        val parsedDate = DateTime(src, DateTimeZone.UTC)
        val dateFormat = SimpleDateFormat("MM/dd/yyyy hh:mm")
        dateFormat.format(Date(parsedDate.millis))
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun userImageUrl(email: String): String? {
    return "http://gravatar.com/avatar/$email.jpg?size=200&d=identicon"
}

fun networkAvailable(context: Context): Boolean {
    return isNetworkAvailable(context) ?: return false
}


/**
 * Has flaws
 * Shows only is phone connected but not actually is there internet connection
 * For that - one of the ways is to try to connect some url and wait for response code
 */
private fun isNetworkAvailable(application: Context): Boolean? {
    val connectivityManager =
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw)
        actNw != null && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || actNw.hasTransport(
            NetworkCapabilities.TRANSPORT_CELLULAR
        ) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) || actNw.hasTransport(
            NetworkCapabilities.TRANSPORT_BLUETOOTH
        ))
    } else {
        val nwInfo = connectivityManager.activeNetworkInfo
        nwInfo != null && nwInfo.isConnected
    }
}