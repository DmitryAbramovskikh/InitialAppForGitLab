package com.dmabram15.initialappforgitlab

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {

        p0?.run{ pushNotification(p0) }

    }

    private fun pushNotification(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val pendingIntent = PendingIntent.getActivity(context, 42, intent, PendingIntent.FLAG_ONE_SHOT)
        var notification: Notification

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            nm.createNotificationChannel(
                NotificationChannel("some_notification_channel", "My_app", NotificationManager.IMPORTANCE_HIGH).apply {
                    description = "Some channel"
                    lockscreenVisibility = Notification.VISIBILITY_PRIVATE
                    setShowBadge(true)
                }
            )

            notification = Notification.Builder(context, "some_notification_channel")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setColor(context.getColor(R.color.cardview_light_background))
                .setContentIntent(pendingIntent)
                .setTicker("Some ticker")
                .setContentTitle("Some title")
                .setContentText("SomeText")
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher_foreground))
                .setDefaults(Notification.DEFAULT_ALL)
                .setCategory(Notification.CATEGORY_EVENT)
                .build()
        }
        else
        {
            notification = NotificationCompat.Builder(context, "some_notification_channel")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setColor(context.getColor(R.color.cardview_light_background))
                .setContentIntent(pendingIntent)
                .setContentTitle("Some title")
                .setContentText("SomeText")
                .setTicker("Some ticker")
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher_foreground))
                .setDefaults(Notification.DEFAULT_ALL)
                .setCategory(Notification.CATEGORY_EVENT)
                .build()
        }

        nm.notify(100, notification)
    }

    companion object {
        val EXTRA_KEY_MESSAGE = "message_key"
    }

}