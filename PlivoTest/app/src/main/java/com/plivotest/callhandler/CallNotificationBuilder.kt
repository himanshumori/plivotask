package com.plivotest.callhandler

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.widget.RemoteViews
import com.plivotest.R
import com.plivotest.util.Utils

class CallNotificationBuilder {

    fun build(context: Context , number : String) {

        val acceptIntent = Intent(context, CallHandlerService::class.java)
        acceptIntent.action = Utils.ACCEPT_ACTION

        val pAcceptIntent = PendingIntent.getService(context, 0,
                acceptIntent, 0)

        val rejectIntent = Intent(context, CallHandlerService::class.java)
        rejectIntent.action = Utils.REJECT_ACTION

        val pRejectIntent = PendingIntent.getService(context, 0,
                rejectIntent, 0)

        val notificationLayout = RemoteViews(context.packageName, R.layout.call_notification_layout)
        val notificationLayoutExpanded = RemoteViews(notificationLayout)

        notificationLayoutExpanded.setTextViewText(R.id.call_details, number);

        notificationLayoutExpanded.setOnClickPendingIntent(R.id.button_accept,pAcceptIntent)

        notificationLayoutExpanded.setOnClickPendingIntent(R.id.button_reject,pRejectIntent)

        // Apply the layouts to the notification
        val uriSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)

        val customNotification = NotificationCompat.Builder(context, "1000")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(notificationLayout)
                .setCustomBigContentView(notificationLayoutExpanded)
                .setAutoCancel(false)
                .setSound(uriSound)
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                .build()
        val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

        mNotificationManager!!.notify(1000, customNotification)
    }
}