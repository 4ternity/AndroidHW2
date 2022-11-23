package com.example.myapplication

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat

class NotificationHandler (private val context: Context) {
    private var notificationManager: NotificationManager? = null

    fun createNotification(headerText: String?, messageBodyText: String?, messageBodyTextDetailed: String?, intent: PendingIntent){
        createNotificationChannel()
        val notificationBuilder = NotificationCompat.Builder (context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_stat_name)
            .setContentTitle(headerText)
            .setContentText(messageBodyText)
            .setSubText(messageBodyTextDetailed)
            .setContentIntent(intent)
            .setCategory(Notification.CATEGORY_MESSAGE)
            .build()
        notificationManager?.notify(103, notificationBuilder)
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager?.getNotificationChannel(CHANNEL_ID) == null) {
                val name: CharSequence = context.getString(R.string.channel_name)
                val description = context.getString(R.string.channel_description)
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
                mChannel.description = description
                notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager?.createNotificationChannel(mChannel)
            }
        }
    }
    companion object{
        private const val CHANNEL_ID = "NOTIFICATION_CHANNEL_ID"
    }
}