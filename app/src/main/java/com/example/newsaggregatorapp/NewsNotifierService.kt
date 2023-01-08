package com.example.newsaggregatorapp

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.koushikdutta.ion.Ion
import org.json.JSONArray
import java.util.*


class NewsNotifierService : Service() {

    private val updateInterval:Long = 30 * 1000
    private var notifHelp : MyNotificationHelper? = null
    private var category = "breaking-news"
    private var isRunning = false
    lateinit var notification: NotificationCompat.Builder
    private var serviceThread:Thread? = null

    @RequiresApi(Build.VERSION_CODES.O)
    fun postNotification(chan: Int, title:String, message:String) {
        var notificationBuilder: NotificationCompat.Builder? = null
        notificationBuilder = notifHelp!!.getNotif(chan, title, message)
        if (notificationBuilder != null) {
            notifHelp!!.notify(chan, notificationBuilder)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        var str = intent.getStringExtra("action")
        if (str.equals("start")) {
            isRunning = true
            getSharedPreferences("mypreference", Context.MODE_WORLD_READABLE)
                .edit()
                .putBoolean("isRunning", true)
                .commit()
            serviceThread = Thread {
                var needsRunning = getSharedPreferences("mypreference", Context.MODE_WORLD_READABLE)
                .getBoolean("isRunning", false)
                while (needsRunning) {
                    try {
                        Thread.sleep(2000)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    Log.d("Service", "Service is running...+++++++")
                    postNotification(101, "My notification", "Bla bla bla bla")


                    try {
                        Thread.sleep(updateInterval)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    needsRunning = getSharedPreferences("mypreference", Context.MODE_WORLD_READABLE)
                        .getBoolean("isRunning", false)
                }
            }
            serviceThread!!.start()
            val channelID = "Foreground Service ID"
            val channel = NotificationChannel(
                channelID,
                channelID,
                NotificationManager.IMPORTANCE_LOW
            )
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
            notification = NotificationCompat.Builder(this, channelID)
                .setContentText("Service is running")
                .setContentTitle("Service enabled")
                .setSmallIcon(R.drawable.sym_def_app_icon)
            notifHelp = MyNotificationHelper(this)
            startForeground(1001, notification.build())
        } else if (str.equals("stop")) {
            isRunning = false
            getSharedPreferences("mypreference", Context.MODE_PRIVATE)
                .edit()
                .putBoolean("isRunning", false)
                .commit()
            Log.d("AAAAAAAAAA", "trying to stop O.0")
            stopForeground(true)
            stopSelfResult(startId)
            serviceThread?.interrupt()
        }
        return START_STICKY
    }

    override fun onDestroy() {
        Log.d("AAAAAAAAAAAAA", "service destroyed :(")
        super.onDestroy()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        Log.d("AAAAAAAAAAAAA", "service task removed :(")
    }

    private fun populateNewsList(callback:(MutableList<MyModel>)->Unit) {
        var finalList = mutableListOf<MyModel>()
        val apikey = "f55d7b936c917b95aa82a248e6c303bb"
        if (category == null) {
            category = "science"
        }
        val url = "https://gnews.io/api/v4/top-headlines?topic=" + category + "&token=" + apikey + "&lang=en&max=10"
        Ion.with(this)
            .load(url)
            .setHeader("User_Agent", "CSC306A at Swansea University")
            .setHeader("Accept", "application/json")
            .asString()
            .setCallback{ex, result ->
                if (ex == null) {
                    finalList = extractData(result)
                    MainActivity.DebugUtilis.v("==============", result)
                    Log.d("=============", "finalList isEmpty = " + finalList.isEmpty().toString())
                    callback(finalList)
                }
            }
    }
    private fun extractData(result:String): MutableList<MyModel> {
        val finalList = mutableListOf<MyModel>()
        val jsonText = result.substring(result.indexOf('['), result.indexOf("}}]")+3)
        val myJSONArray = JSONArray(jsonText)
        for (i in 0..9) {
            val imageModel = MyModel()
            val myJSON = myJSONArray.getJSONObject(i)
            imageModel.setArticleTitle(myJSON.getString("title"))
            imageModel.setArticleImage(myJSON.getString("image"))
            imageModel.setArticleSummary(myJSON.getString("description"))
            imageModel.setArticleContent(myJSON.getString("content"))
            imageModel.setArticleSourceName(myJSON.getString("source"))
            imageModel.setArticlePublishedAt(myJSON.getString("publishedAt"))
            imageModel.setArticleURL(myJSON.getString("url"))
            finalList.add(imageModel)
        }
        return finalList
    }
}