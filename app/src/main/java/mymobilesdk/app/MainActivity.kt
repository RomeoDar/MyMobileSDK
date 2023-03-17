package mymobilesdk.app

import TouchAndAccelerometerSDK
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mymobilesdk.TouchEvent
import java.sql.DriverManager.println


class MainActivity : AppCompatActivity() {

    private var touchAndAccelerometerSDK: TouchAndAccelerometerSDK? = null
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase)
        touchAndAccelerometerSDK = TouchAndAccelerometerSDK(newBase)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.TextDes)
        textView.text = "White Area for touch events"

        val startButton = findViewById<Button>(R.id.collectButton)
        val stopButton = findViewById<Button>(R.id.stopButton)
        val myView = findViewById<View>(R.id.my_view)
        myView.setOnTouchListener(touchAndAccelerometerSDK?.getOnTouchListener())
        startButton.setOnClickListener {
                startCollection()
                myView.setOnTouchListener { v, event ->
                    touchAndAccelerometerSDK?.addTouchEvent(event.actionMasked, event.x, event.y, event.eventTime)
                    true
                }
            myView.setOnTouchListener(touchAndAccelerometerSDK?.getOnTouchListener())


        }
        stopButton.setOnClickListener {
            stopCollection()
        }
    }
    private fun startCollection() {
        touchAndAccelerometerSDK?.startCollection()
    }
    private fun stopCollection() {
        touchAndAccelerometerSDK?.stopCollection()
        displayData()
    }
    private fun displayData() {
        val data = touchAndAccelerometerSDK?.getCollectedData()
        val textView = findViewById<TextView>(R.id.dataTextView)
        textView.text = data.toString()
    }
}


