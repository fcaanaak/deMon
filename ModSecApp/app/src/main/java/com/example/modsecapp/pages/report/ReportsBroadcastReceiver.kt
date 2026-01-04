package com.example.modsecapp.pages.report

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.modsecapp.Constants
import com.example.modsecapp.R
import com.example.modsecapp.httpclient.HttpClient


class ReportsBroadcastReceiver : BroadcastReceiver() {

    val broadcastIntervalMillis = 15 * Constants.SECONDS_TO_MILLIS

    override fun onReceive(context: Context?, intent: Intent?) {

        // Idea: When this function is called, we need to do the following
        // 1. Make an HTTP GET request to the server to see if there are
        // any latest reports.
        // 1.a) If the returned list is empty (do nothing)
        // Look for a list saved to file and if there is none, make one
        // else, load the list from file, append latest entries to it, then save it back.
        //
        // This is important as later on, in reportFragment, we will simply read from the file
        val serverip = "10.0.0.114"


        val httpClient = HttpClient(context!!,serverip)
        val reportFetcher = ReportFetcher(context)

        httpClient.getReports {
            reportFetcher.updateSavedReports(it)

            if (it.isNotEmpty()){

                val channelId = "i.apps.notifications"

                Log.d("DEBUG",it.toString())

                val builder = NotificationCompat.Builder(context!!, channelId)
                    .setSmallIcon(R.drawable.checkmark) // Notification icon
                    .setContentTitle("ALERT") // Title displayed in the notification
                    .setContentText("Detection from ${it.first().deviceName}") // Text displayed in the notification
                    .setAutoCancel(true) // Dismiss notification when tapped
                    .setPriority(NotificationCompat.PRIORITY_HIGH) // Notification priority for better visibility

                // Display the notification
                with(NotificationManagerCompat.from(context)) {
                    notify(1234, builder.build())
                }

            }

        }



        broadcastAlarm(context,intent!!)

    }

    private fun broadcastAlarm(context:Context, intent: Intent){

        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager

        // Create a PendingIntent that will be triggered when the alarm goes off
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + broadcastIntervalMillis,
            pendingIntent
        )

    }





}