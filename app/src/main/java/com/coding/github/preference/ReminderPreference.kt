package com.coding.github.preference

import android.content.Context
import com.coding.github.data.model.Reminder

class ReminderPreference(context: Context) {
    companion object {
        const val PREF_NAME = "pref_name"
        private const val REMINDER = "reminder"
    }

    private val preference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setReminder(value: Reminder) {
        val editor = preference.edit()
        editor.putBoolean(REMINDER, value.isReminder)
        editor.apply()
    }

    fun getReminder(): Reminder {
        val model = Reminder()
        model.isReminder = preference.getBoolean(REMINDER, false)
        return model
    }

}