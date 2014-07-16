package edu.msudenver.CS390H.marblemaze;

import java.util.List;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;

public class SettingsFragment extends Fragment {
	
	private SensorManager mSensorManager;
	private List<Sensor> deviceSensors;
	private Sensor mSensor;
	private SharedPreferences prefs;
	private SeekBar volumeBar;
	private SeekBar	sensitivityBar;
	private RadioButton videoQuality;
	private Activity parentActivity;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setContentView(R.layout.settings);
		
		parentActivity = super.getActivity();

		mSensorManager = (SensorManager) parentActivity.getSystemService(Context.SENSOR_SERVICE);
		deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
		prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
		
		volumeBar = (SeekBar) parentActivity.findViewById(R.id.volumeSeek);
		videoQuality = (RadioButton) parentActivity.findViewById(R.id.graphics_Radio_2D);
		volumeBar = (SeekBar) parentActivity.findViewById(R.id.sensorSensitivitySeek);
		
		Log.i("SettingsFragment_OnCreate", "On create finished");
	}
	
	@Override
	public void onStart(){
		super.onStart();
		
		
		
		Log.i("SettingsFragment_onStart", "On start finished");
	}
	
}
