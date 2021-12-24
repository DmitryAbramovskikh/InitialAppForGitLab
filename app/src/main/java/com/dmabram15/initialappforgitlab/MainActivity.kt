package com.dmabram15.initialappforgitlab

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    val notificationReceiver = NotificationReceiver()
    lateinit var startBtn: Button

    lateinit var alarmManager : AlarmManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        startBtn = findViewById<Button>(R.id.start_btn)
        startBtn.setOnClickListener { onStartBtnClick() }
    }



    fun onStartBtnClick() {
        val intent = Intent(this, NotificationReceiver::class.java).apply {
            putExtra(NotificationReceiver.EXTRA_KEY_MESSAGE, "Some message")
        }
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, 101, intent, PendingIntent.FLAG_ONE_SHOT)

        registerReceiver(notificationReceiver, IntentFilter(Intent.ACTION_USER_PRESENT))

        alarmManager
            .setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, pendingIntent)
    }
}