package com.example.myapplication

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.apply{
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntentFlag = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        } else {PendingIntent.FLAG_UPDATE_CURRENT}

        val pendingIntent = PendingIntent.getActivity(
            context, 0, Intent(context, MainActivity::class.java), pendingIntentFlag
        )
        NotificationHandler(context!!).createNotification(
            intent?.extras?.getString(HEADER_TEXT),
            intent?.extras?.getString(BODY_TEXT),
            intent?.extras?.getString(BODY_TEXT_DETAILED),
            pendingIntent
        )
    }
    companion object{
        const val HEADER_TEXT = "HEADER_TEXT"
        const val BODY_TEXT = "BODY_TEXT"
        const val BODY_TEXT_DETAILED = "BODY_TEXT_DETAILED"
    }
}