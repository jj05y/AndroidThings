package cmwstudios.shakerapp;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class ShakerListener implements SensorEventListener {

    private Context context;
    private SensorManager mgr;
    private Sensor sensor;
    private double sensorReading;
    private static final double THRESHOLD = 9.81 * 2.5;
    private CallbackListener callbackListener;

    public interface CallbackListener {
        void thresholdReached();
    }

    public ShakerListener(Context context) {
        this.context = context;
        this.sensorReading = 0;
    }

    public void start() {
        mgr = (SensorManager) this.context.getSystemService(Context.SENSOR_SERVICE);
        sensor = mgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mgr.registerListener(this, sensor, 50);

    }

    public void stop() {
        mgr.unregisterListener(this);
    }

    public void addCallBackListener(CallbackListener l) {
        callbackListener = l;
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        double x = event.values[0];
        double y = event.values[1];
        double z = event.values[2];
        double accelmag = Math.sqrt(x*x + y*y + z*z);
        sensorReading = accelmag;
        Log.d("senReading", "Sensor Readding: " + sensorReading);
/*
        Log.d("senReading", "x: " + x);
        Log.d("senReading", "y: " + y);
        Log.d("senReading", "z: " + z);
*/

        if (sensorReading > THRESHOLD) {
            callbackListener.thresholdReached();
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
