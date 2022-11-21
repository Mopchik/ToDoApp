package com.mopchik.planner.notifications_worker

import android.R
import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mopchik.planner.MainActivity


// class NotificationReceiver : BroadcastReceiver() {
//     override fun onReceive(context: Context, intent: Intent) {
//         val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//         val notification = Notification(R.drawable.icon, "Test", System.currentTimeMillis())
// //Интент для активити, которую мы хотим запускать при нажатии на уведомление
// //Интент для активити, которую мы хотим запускать при нажатии на уведомление
//         val intentTL = Intent(context, MainActivity::class.java)
//         notification.setLatestEventInfo(
//             context, "Test", "Do something!",
//             PendingIntent.getActivity(
//                 context, 0, intentTL,
//                 PendingIntent.FLAG_CANCEL_CURRENT
//             )
//         )
//         notification.flags = Notification.DEFAULT_LIGHTS or Notification.FLAG_AUTO_CANCEL
//         nm.notify(1, notification)
// // Установим следующее напоминание.
// // Установим следующее напоминание.
//         val am = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//         val pendingIntent = PendingIntent.getBroadcast(
//             context, 0,
//             intent!!, PendingIntent.FLAG_CANCEL_CURRENT
//         )
//         am[AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + AlarmManager.INTERVAL_DAY] =
//             pendingIntent
//     }
// }