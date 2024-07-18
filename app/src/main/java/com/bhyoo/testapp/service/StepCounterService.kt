package com.bhyoo.testapp.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.bhyoo.testapp.R
import com.bhyoo.testapp.database.StepDatabase
import com.bhyoo.testapp.database.StepEntity
import com.bhyoo.testapp.view.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@AndroidEntryPoint
class StepCounterService : Service(), SensorEventListener {

    private var sensorManager: SensorManager? = null
    private var stepDetectorSensor: Sensor? = null

    @Inject
    lateinit var db: StepDatabase

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(1, createChannel().build())
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        stepDetectorSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
        sensorManager?.registerListener(
            this@StepCounterService, stepDetectorSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
//        startTimerTest()
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_STEP_DETECTOR) {
            insertStepData()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // do nothing
    }

    private fun insertStepData() {

        db.stepDao().run {
            val entity = StepEntity(createdAt = System.currentTimeMillis())
            Log.i("ddd", entity.toString())
            insert(entity)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    // Timer test instead of real device
    private fun startTimerTest() {
        object : CountDownTimer(30000L, 1000L) {

            override fun onTick(millisUntilFinished: Long) {
                insertStepData()
            }

            override fun onFinish() {
            }
        }.start()
    }

    private fun createChannel(): NotificationCompat.Builder {
        val notificationIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getActivity(
                this,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
        val builder = if (Build.VERSION_CODES.O <= Build.VERSION.SDK_INT) {
            val channelId = "step_service_channel"
            val channel = NotificationChannel(
                channelId,
                "Step Service",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)
            NotificationCompat.Builder(this, channelId)
        } else {
            NotificationCompat.Builder(this)
        }

        builder.setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Step Counter")
            .setContentText("Counting...")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOngoing(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            builder.setForegroundServiceBehavior(Notification.FOREGROUND_SERVICE_IMMEDIATE)
        }
        return builder
    }

    override fun onDestroy() {
        super.onDestroy()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            stopForeground(STOP_FOREGROUND_REMOVE)
        } else{
            stopForeground(true)
        }

    }
}