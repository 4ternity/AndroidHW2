package com.example.myapplication

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.example.myapplication.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

   private lateinit var binding: ActivityMainBinding

   private var headerText: String? = null
   private var messageBodyText: String? = null
   private var messageBodyTextDetailed: String? = null
   private var timeUntilShownInSeconds: Int = 0
   private var isCheckBoxChecked = false
   private var isHeaderEmpty = true
   private var isMessageBodyEmpty = true
   private var isMessageBodyDetailedEmpty = true
   private var totalTime: Long = 0
   private var alarmManger: AlarmManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initButtonListeners()
        initEditTextListeners()
    }
    private fun initButtonListeners(){
        with(binding){
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                etNotificationBodyDetailed.isEnabled = isChecked
                isCheckBoxChecked = isChecked
            }
            btnShowAlarm.setOnClickListener{
                headerText = etNotificationHeader.text.toString()
                messageBodyText = etNotificationBody.text.toString()
                messageBodyTextDetailed = if (isCheckBoxChecked) {
                    etNotificationBodyDetailed.text.toString()
                } else { null}
                timeUntilShownInSeconds = Integer.valueOf(etNotificationTime.text.toString())
                setAlarm(
                    timeUntilShownInSeconds,
                    headerText,
                    messageBodyText,
                    messageBodyTextDetailed
                )
            }
            btnDisableAlarm.setOnClickListener {
                val timeDiff = if(totalTime != 0L) { (totalTime - SystemClock.elapsedRealtime())/1000} else {0}
                if(timeDiff > 0L) {
                    alarmManger?.cancel(getPendingIntent(
                        headerText,
                        messageBodyText,
                        messageBodyTextDetailed
                    ))
                    Toast.makeText(baseContext, "notif time remaining: $timeDiff was cancel", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(baseContext, "can not cancel notif", Toast.LENGTH_SHORT).show()
                }
            }
            btnLastDeviceRebootTime.setOnClickListener {
                val timeInMillis = SystemClock.uptimeMillis()
                val time = String.format("%02d min, %02d sec",
                    TimeUnit.MILLISECONDS.toMinutes(timeInMillis),
                    TimeUnit.MILLISECONDS.toSeconds(timeInMillis) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeInMillis))
                )
                Toast.makeText(baseContext, time, Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun initEditTextListeners() {
        with(binding) {
            etNotificationHeader.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    isHeaderEmpty = s?.length == 0
                }

                override fun afterTextChanged(s: Editable?) {
                    checkIfNotEmpty()
                }
            })
            etNotificationBody.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    isMessageBodyEmpty = s?.length == 0
                }

                override fun afterTextChanged(s: Editable?) {
                    checkIfNotEmpty()
                }
            })
            etNotificationTime.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    isMessageBodyDetailedEmpty = s?.length == 0
                }

                override fun afterTextChanged(s: Editable?) {
                    checkIfNotEmpty()
                }
            })
        }
    }
    private fun checkIfNotEmpty() {
        binding.btnShowAlarm.isEnabled =
            !isHeaderEmpty && !isMessageBodyEmpty && !isMessageBodyDetailedEmpty
    }
    private fun setAlarm(
        timeUntilShown: Int,
        headerText: String?,
        messageBodyText: String?,
        messageBodyTextDetailed: String?
    ) {
        alarmManger = getSystemService(ALARM_SERVICE) as AlarmManager
        val pendingIntent = getPendingIntent(headerText, messageBodyText, messageBodyTextDetailed)
        totalTime = SystemClock.elapsedRealtime() + timeUntilShown * 1000
        alarmManger?.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            totalTime,
            pendingIntent
        )
    }
    private fun getPendingIntent(
        headerText: String?,
        messageBodyText: String?,
        messageBodyTextDetailed: String?): PendingIntent = Intent(this, AlarmReceiver::class.java).apply{
        putExtra(AlarmReceiver.BODY_TEXT, messageBodyText)
        putExtra(AlarmReceiver.HEADER_TEXT, headerText)
        putExtra(AlarmReceiver.BODY_TEXT_DETAILED, messageBodyTextDetailed)
    }.let {intent ->
        PendingIntent.getBroadcast(baseContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }


}