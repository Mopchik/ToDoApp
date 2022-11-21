package com.mopchik.planner.notifications_worker

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.mopchik.planner.AppScope
import com.mopchik.planner.ContextClass
import com.mopchik.planner.ContextOwner
import java.util.*
import javax.inject.Inject

@AppScope
class NotificationSender @Inject constructor(@ContextClass(ContextOwner.APP)
                                             val context:Context) {
    private val alarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    // private val alarmInfoIntent by lazy{
    //     val intent = Intent(context, MainActivity::class.java)
    //     intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    //     PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    // }
    private fun getAlarmActionPendingIntent(id: Int, text: String): PendingIntent{
        val intent = Intent(context, NotificationService::class.java)
        intent.putExtra("NotificationText", text)
        intent.putExtra("NotificationId", id)
        return PendingIntent.getService(context, id, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    }
    fun send(id:Int, timeToSend: Calendar, notificationText: String){
        // val alarmClockInfo = AlarmManager.AlarmClockInfo(timeToSend.timeInMillis, alarmInfoIntent)
        // Устанавливаем разовое напоминание
        // val nowC = Calendar.getInstance()
        // timeToSend.set(Calendar.SECOND, nowC.get(Calendar.SECOND) + 20)
        // timeToSend.set(Calendar.MINUTE, nowC.get(Calendar.MINUTE))
        // timeToSend.set(Calendar.HOUR_OF_DAY, nowC.get(Calendar.HOUR_OF_DAY))
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeToSend.timeInMillis,
            getAlarmActionPendingIntent(id, notificationText))
        Log.i("KOSTIK", "Notification was planned")
    }
    fun cancelNotification(id: Int){
        alarmManager.cancel(getAlarmActionPendingIntent(id, ""))
    }
}