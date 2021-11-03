package com.coding.github.ui.setting

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.coding.github.R
import com.coding.github.data.model.Reminder
import com.coding.github.preference.AlarmReceiver
import com.coding.github.preference.ReminderPreference
import com.coding.github.preference.SettingPreferences
import com.google.android.material.switchmaterial.SwitchMaterial

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingActivity : AppCompatActivity() {

    private lateinit var reminder: Reminder
    private lateinit var alarmReceiver: AlarmReceiver

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)


        val switch1 = findViewById<SwitchMaterial>(R.id.switch1)
        val reminderPreference = ReminderPreference(this)
        if (reminderPreference.getReminder().isReminder) {
            switch1.isChecked = true

        } else {
            switch1.isChecked = false
        }



        alarmReceiver = AlarmReceiver()

        switch1.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                saveReminder(true)
                alarmReceiver.setRepeatAlarm(this, "Repeating Alarm", "04:25", "sholat magrib")
            } else {
                saveReminder(false)
                alarmReceiver.cancelAlarm(this)
            }
        }

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        val pref = SettingPreferences.getInstance(dataStore)
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )
        mainViewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    switchTheme.isChecked = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    switchTheme.isChecked = false
                }
            })

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            mainViewModel.saveThemeSetting(isChecked)
        }
    }

    private fun saveReminder(state: Boolean) {
        val reminderPreference = ReminderPreference(this)
        reminder = Reminder()
        reminder.isReminder = state
        reminderPreference.setReminder(reminder)

    }
}
