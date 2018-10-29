package bmstu.ru.todoapp.activities

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AppCompatActivity
import bmstu.ru.todoapp.NotificationPublisher
import bmstu.ru.todoapp.R
import java.util.*

object NotificationFunctions {
    private const val CHANNEL_ID = "channel-id"
    fun createNotificationChannel(context: AppCompatActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.notification_channel_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun scheduleNotification(notification: Notification, time: Long, notificationId: Int, context: AppCompatActivity) {
        val notificationIntent = Intent(context, NotificationPublisher::class.java)
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, notificationId)
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent)
    }

    fun buildNotification(context: AppCompatActivity, content: String): Notification {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        builder.setSmallIcon(R.drawable.ic_alarm)
        builder.setContentTitle(context.getString(R.string.notification_title))
        builder.setContentText(content)
        builder.priority = NotificationCompat.PRIORITY_DEFAULT
        return builder.build()
    }

    fun setNotification(
        year: Int,
        month: Int,
        day: Int,
        hour: Int,
        minute: Int,
        notificationContent: String,
        notificationId: Int,
        context: AppCompatActivity
    ) {
        createNotificationChannel(context)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(year, month, day, hour, minute)
        val time = calendar.timeInMillis
        scheduleNotification(
            buildNotification(context, notificationContent), time, notificationId, context
        )
    }
}