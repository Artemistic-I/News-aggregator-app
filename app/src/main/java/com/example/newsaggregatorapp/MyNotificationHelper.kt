package com.example.newsaggregatorapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

class MyNotificationHelper (base: Context) : ContextWrapper(base) {
    private val manager : NotificationManager? get() =
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    init {
        createChannels()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannels() {
        val channel1 = NotificationChannel(CHANNEL_ONE_ID, CHANNEL_ONE_NAME,
            NotificationManager.IMPORTANCE_HIGH)
        channel1.enableLights(true)
        channel1.lightColor = Color.RED
        channel1.setShowBadge(true)
        channel1.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        manager!!.createNotificationChannel(channel1)
    }
    fun getNotif(num:Int, title:String, body:String) : NotificationCompat.Builder {
        var channelID = CHANNEL_ONE_ID
        var icon = R.drawable.ic_main

        when (num) {
            101 -> {
                channelID = CHANNEL_ONE_ID
                icon = R.drawable.ic_main
            }
        }
        return NotificationCompat.Builder(applicationContext, channelID)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(icon)
            .setAutoCancel(true)
    }
    fun notify(id:Int, notification: NotificationCompat.Builder) {
        manager!!.notify(id, notification.build())
    }
    companion object {
        const val CHANNEL_ONE_ID = "com.example.newsaggregatorapp.ONE"
        const val CHANNEL_ONE_NAME = "Channel One"
    }
}