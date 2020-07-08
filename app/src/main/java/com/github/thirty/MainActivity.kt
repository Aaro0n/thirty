package com.github.thirty

import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Bundle
import android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private var count = 30 * 60
    private var job: Job? = null
    private val scope = MainScope()
    private val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    private lateinit var r: Ringtone


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.activity_main)
        initListener()
        initRingtone()
    }

    private fun initRingtone() {
        r = RingtoneManager.getRingtone(this, notification)
    }

    private fun initListener() {
        time.setOnClickListener {
            job?.cancel()
            job = countDown()
        }
        time.setOnLongClickListener {
            job?.cancel()
            time.text = getString(R.string.thirty)
            setTextSize(60)
            true
        }
    }

    private fun countDown(): Job {
        return scope.launch {
            for (i in 0..count) {
                delay(1000)
                setTextSize(80)
                time.text = "${count - i}"
                if (i != 0 && i % 60 == 0) {
                    r.play()
                }
            }
        }
    }

    private fun setTextSize(spSize:Int) {
        time.textSize = spSize.spToPx(this@MainActivity).toFloat()
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }
}