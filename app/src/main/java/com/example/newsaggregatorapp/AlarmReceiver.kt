package com.example.newsaggregatorapp

import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log


class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, p1: Intent) {
        Log.d("AAAAAAAAAAAA", "restarting service")
        context.startService(Intent(context, NewsNotifierService::class.java))
    }

    /*lateinit var context:Context
    override fun onReceive(context: Context, p1: Intent) {
        this.context = context

        if (isMyServiceRunning(this.context, NewsNotifierService::class.java)) {
            Log.d("AAAAAAAAAAAA", "alredy running no need to start again")
        } else {
            val background = Intent(context, NewsNotifierService::class.java)
            Log.d("AAAAAAAAAAAA", "restarting service")
            context.startService(background)
        }
    }
    fun isMyServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val services = activityManager.getRunningServices(Int.MAX_VALUE)
        if (services != null) {
            for (i in services.indices) {
                if (serviceClass.name == services[i].service.className && services[i].pid != 0) {
                    return true
                }
            }
        }
        return false
    }*/
}