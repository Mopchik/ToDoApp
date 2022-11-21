package com.mopchik.planner.notifications_worker

import android.app.*
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.mopchik.planner.CHANNEL_ID
import com.mopchik.planner.MainActivity
import com.mopchik.planner.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class NotificationService: Service() {
    private val largeIcon by lazy{
        val options = BitmapFactory.Options()
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_baseline_bookmark_24, options)
        bitmap
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("KOSTIK", "Notification service started.")
        intent!!
        CoroutineScope(Dispatchers.Default).launch {
            val id = intent.getIntExtra("NotificationId", -1)
            val text = intent.getStringExtra("NotificationText")
            sendNotification(id, text)
            Log.i("KOSTIK", "Notification was sent.")
            stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun sendNotification(id:Int, text: String?){
        createNotificationChannel()
        val startApp = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent
            .getActivity(this, 0, startApp,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_bookmark_24)
            // .setLargeIcon(largeIcon)
            .setContentTitle("Дедлайн завтра!")
            .setContentText(text)
            .setStyle(NotificationCompat.BigTextStyle().bigText(text))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setSound(Uri.parse(
                ("android.resource://"
                        + packageName) + "/" + R.raw.erzhan
            ))
            .setLights(getColor(R.color.red), 500, 500)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        NotificationManagerCompat.from(this).notify(id, builder.build())
        val r = RingtoneManager.getRingtone(this, Uri.parse(
            ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + applicationContext.packageName + "/" + R.raw.erzhan))
        r.play()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        Log.i("KOSTIK", "Notification destroyed.")
        super.onDestroy()
    }
}