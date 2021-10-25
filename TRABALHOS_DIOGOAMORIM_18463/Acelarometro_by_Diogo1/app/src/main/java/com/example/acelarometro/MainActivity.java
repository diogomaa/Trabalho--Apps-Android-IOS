package com.example.acelarometro;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.acelarometro.R;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager sensorManager;
    private boolean color = false;
    private View view;
    private long updateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = findViewById(R.id.textView);
        view.setBackgroundColor(Color.BLACK);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);

        updateTime = System.currentTimeMillis();
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            calculations(event);
        }
    }

    private void calculations(SensorEvent event) {
        float[] values = event.values;
        float x = values[0];
        float y = values[1];
        float z = values[2];

        long time = System.currentTimeMillis();

        float acelaracion = (x * x + y * y + z * z) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);

        if (acelaracion >= 2) {
            if (time - updateTime < 200) {
                return;
            }

            updateTime = time;
            Toast.makeText(this, "Movimento Detetado!", Toast.LENGTH_SHORT).show();

            if (color) { view.setBackgroundColor(Color.BLACK);
            } else { view.setBackgroundColor(Color.YELLOW); }
            color = !color;
        }
    }
    @Override
    public void onAccuracyChanged (Sensor sensor,int accuracy){

    }
    @Override
    protected void onResume () {
        super.onResume();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause () {
        super.onPause();

        sensorManager.unregisterListener(this);
    }
}
