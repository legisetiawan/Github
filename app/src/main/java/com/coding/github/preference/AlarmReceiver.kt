package com.coding.github.preference

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.coding.github.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    companion object{
        private const val NOTIFICATION_ID = 1
        private const val CHANEL_ID = "chanel_id"
        private const val CHANEL_NAME = "chanel_name"
        private const val TIME_FORMAT = "HH:mm"
        const val EXTRA_MESSAGE = "extra_message"
        const val EXTRA_TYPE = "extra_type"
        const val ID_REPEATING = 101
    }

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        sentNotification(context)
    }

    private fun sentNotification(context: Context) {
        val intent = context.packageManager.getLaunchIntentForPackage("com.coding.github")
        val pending  = PendingIntent.getActivity(context,0,intent,0)
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(context, CHANEL_ID)
            .setContentIntent(pending)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle(context.resources.getString((R.string.app_name)))
            .setContentText("Jangan Lupa sholat")
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val chanel = NotificationChannel(CHANEL_ID, CHANEL_NAME,NotificationManager.IMPORTANCE_DEFAULT)

            builder.setChannelId(CHANEL_ID)
            notificationManager.createNotificationChannel(chanel)
        }

        val notification = builder.build()
        notificationManager.notify(NOTIFICATION_ID,notification)
    }

    fun setRepeatAlarm(context: Context,type:String,time:String,message:String) {
        if (dateInvalid(time, TIME_FORMAT)) return
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context,AlarmReceiver::class.java)
        intent.putExtra(EXTRA_MESSAGE,message)
        intent.putExtra(EXTRA_TYPE,type)

        val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val calender = Calendar.getInstance()
        calender.set(Calendar.HOUR_OF_DAY,Integer.parseInt(timeArray[0]))
        calender.set(Calendar.MINUTE,Integer.parseInt(timeArray[1]))
        calender.set(Calendar.SECOND,0)

        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING,intent,0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calender.timeInMillis,AlarmManager.INTERVAL_DAY,pendingIntent)
        Toast.makeText(context, "Repeating Alarm Set Up", Toast.LENGTH_SHORT).show()
    }

    private fun dateInvalid(time: String, timeFormat: String): Boolean {
        return try {
            val df = SimpleDateFormat(timeFormat, Locale.getDefault())
            df.isLenient = false
            df.parse(time)
            false
        }catch (e: ParseException){
            true
        }
    }

    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context,AlarmReceiver::class.java)
        val requestCode = ID_REPEATING
        val pendingIntent = PendingIntent.getBroadcast(context,requestCode,intent,0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, "Repeating Alarm di batalkan", Toast.LENGTH_SHORT).show()
    }
}
