package com.example.proximidade;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.proximidade.R;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager mSensorManager;
    private Sensor mSensor;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if (mSensor == null){
            finish();
        }

        image = (ImageView) findViewById(R.id.image);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if ( event.values[0] < mSensor.getMaximumRange() ){
            image.setImageResource(R.drawable.img1);
        } else {
            image.setImageResource(R.drawable.img2);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}


    @Override
    protected void onResume(){
        super.onResume();

        mSensorManager.registerListener(this, mSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
    }


}