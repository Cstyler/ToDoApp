package bmstu.ru.todoapp


import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class NotificationPublisher : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = intent.getParcelableExtra<Notification>(NOTIFICATION)
        val id = intent.getIntExtra(NOTIFICATION_ID, 0)
        Log.i(TAG, "onReceive. $id. $notification")
        notificationManager.notify(id, notification)
        Log.i(TAG, "Notified")
    }

    companion object {
        const val NOTIFICATION_ID = "notification-id"
        const val NOTIFICATION = "notification"
        private const val TAG = "NotificationPublisher"
    }
}