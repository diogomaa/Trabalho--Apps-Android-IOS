package com.example.Rotacao;

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

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor rotacionSensor;

    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = findViewById(R.id.mensagem);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        rotacionSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        if (rotacionSensor == null){
            finish();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] rotationMatrix = new float[16];
        SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);

        float[] remappedRotationMatrix = new float[16];
        SensorManager.remapCoordinateSystem(rotationMatrix,SensorManager.AXIS_X,SensorManager.AXIS_Z,remappedRotationMatrix);

        float[] orientations = new float[3];
        SensorManager.getOrientation(remappedRotationMatrix, orientations);

        for(int i = 0;i < 3; i++){
            orientations[i] = (float)(Math.toDegrees(orientations[i]));
        }

        if (orientations[2] > -90){view.setBackgroundColor(Color.BLACK);}
        else if (orientations[2] < -90) {view.setBackgroundColor(Color.YELLOW);}
        else if (Math.abs(orientations[2]) < 90) {view.setBackgroundColor(Color.GRAY);}
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();

        mSensorManager.registerListener(this,rotacionSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mSensorManager.unregisterListener(this);
    }
}
