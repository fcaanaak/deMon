package com.example.modsecapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import com.example.modsecapp.httpclient.HttpClient
import com.example.modsecapp.httpclient.HttpRequestManager
import com.example.modsecapp.pages.dashboard.DashboardFragment
import com.example.modsecapp.pages.devices.DevicesFragment
import com.example.modsecapp.pages.report.ReportFragment
import com.example.modsecapp.pages.report.ReportEntry
import com.example.modsecapp.pages.report.ReportsBroadcastReceiver
import java.util.Calendar


class MainActivity : AppCompatActivity() {


    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var dashboardFragment: DashboardFragment
    private lateinit var reportFragment: ReportFragment
    private lateinit var devicesFragment: DevicesFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel()

        if (!checkNotificationPermissions()){
            requestNotificationPermissions()
        }

	// Will need to remove the bottom lines as we transition to firebase cloud messaging instead of polling
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        // Create an intent to trigger the alarm
        val intent = Intent(this@MainActivity, ReportsBroadcastReceiver::class.java)

        // Create a PendingIntent that will be triggered when the alarm goes off
        val pendingIntent = PendingIntent.getBroadcast(this@MainActivity, 0, intent, PendingIntent.FLAG_IMMUTABLE)


        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + (10*1000),
            pendingIntent

        )

	// Stop removing stuff here
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        dashboardFragment = DashboardFragment()
        reportFragment = ReportFragment()
        devicesFragment = DevicesFragment()

        setCurrentFragment(dashboardFragment)

        bottomNavigationView.setOnItemSelectedListener {

            when (it.itemId){
                R.id.dashboard -> setCurrentFragment(dashboardFragment)
                R.id.report -> setCurrentFragment(reportFragment)
                R.id.devices -> setCurrentFragment(devicesFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment){

        val reportBundle = Bundle()

        reportBundle.putSerializable("reports",reports)

        supportFragmentManager.beginTransaction().apply {
            reportFragment.arguments = reportBundle
            replace(R.id.flFragment,fragment)
            commit()
        }
    }

    private fun createNotificationChannel() {

        val channelId = "i.apps.notifications"
        val description = "HELLO"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                description,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableLights(true) // Turn on notification light
                lightColor = Color.GREEN
                enableVibration(true) // Allow vibration for notifications
            }

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun checkNotificationPermissions(): Boolean{

        return (ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestNotificationPermissions(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                101
            )
        }
    }

    private fun sendNotification(){

        val channelId = "i.apps.notifications"

        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.checkmark) // Notification icon
            .setContentTitle("Hello") // Title displayed in the notification
            .setContentText("Welcome to GeeksforGeeks!!") // Text displayed in the notification
            .setAutoCancel(true) // Dismiss notification when tapped
            .setPriority(NotificationCompat.PRIORITY_HIGH) // Notification priority for better visibility

        // Display the notification
        with(NotificationManagerCompat.from(this)) {
            notify(1234, builder.build())
        }

    }



}
