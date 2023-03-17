import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.view.MotionEvent
import com.example.mymobilesdk.AccelerometerData
import com.example.mymobilesdk.TouchEvent
import java.util.*

import android.view.View


class TouchAndAccelerometerSDK(context: Context) :
   SensorEventListener {

   private val sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
   private val touchEvents: ArrayList<TouchEvent> = ArrayList()
   private val accelerometerData: ArrayList<AccelerometerData> = ArrayList()

   fun startCollection() {
       val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
       sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
   }

   fun stopCollection() {
       sensorManager.unregisterListener(this)
   }


   fun getCollectedData(): List<Any> {
       val data = ArrayList<Any>()
       data.addAll(touchEvents)
       data.addAll(accelerometerData)
       //data.sortBy { it.timestamp }
       return data
   }
    fun getOnTouchListener(): View.OnTouchListener {
        return onTouchListener
    }

   override fun onSensorChanged(event: SensorEvent) {
       if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
           val data = AccelerometerData(event.values[0], event.values[1], event.values[2], event.timestamp)
           accelerometerData.add(data)
       }
   }

   override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    fun addTouchEvent(action: Int, x: Float, y: Float, timestamp: Long) {
        val data = TouchEvent(action, x, y, timestamp)
        touchEvents.add(data)
    }
   private val onTouchListener = object : android.view.View.OnTouchListener {
       override fun onTouch(v: android.view.View, event: MotionEvent): Boolean {
           val data = TouchEvent(
               event.actionMasked,
               event.x,
               event.y,
               event.eventTime
           )
           touchEvents.add(data)
           return true
       }
   }
}

