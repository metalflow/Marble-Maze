package edu.msudenver.CS390H.marblemaze;

import java.util.List;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ConfigurationInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;

public class SettingsFragment extends Fragment implements
		OnSeekBarChangeListener, OnClickListener, OnCheckedChangeListener {

	private SharedPreferences prefs;
	private SharedPreferences.Editor prefsEditor;
	private SeekBar volumeBar;
	private SeekBar sensitivityBar;
	private RadioButton lowVideoQuality;
	private RadioButton low3DVideoQuality;
	private RadioButton med3DVideoQuality;
	private RadioButton high3DVideoQuality;
	private RadioGroup graphicsSettings;
	private Activity parentActivity;
	private int volume;
	private int sensitivity;
	private int graphicsVersion;
	private SensorManager mSensorManager;
	private List<Sensor> deviceSensors;
	private TextView sensorListView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		parentActivity = super.getActivity();
		prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
		prefsEditor = prefs.edit();

		Log.i("SettingsFragment_OnCreate", "On create finished");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View fragView = inflater.inflate(R.layout.settings, container, false);
		Log.i("HihgScoresFragment_onCreateView", "onCreateView finished");
		return fragView;
		// return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();
		
		parentActivity.getActionBar().hide();

		volumeBar = (SeekBar) parentActivity.findViewById(R.id.volumeSeek);
		sensitivityBar = (SeekBar) parentActivity
				.findViewById(R.id.sensorSensitivitySeek);
		lowVideoQuality = (RadioButton) parentActivity
				.findViewById(R.id.graphics_Radio_2D);
		low3DVideoQuality = (RadioButton) parentActivity
				.findViewById(R.id.graphics_Radio_OpenGL1);
		med3DVideoQuality = (RadioButton) parentActivity
				.findViewById(R.id.graphics_Radio_OpenGL2);
		high3DVideoQuality = (RadioButton) parentActivity
				.findViewById(R.id.graphics_Radio_OpenGL3);
		graphicsSettings = (RadioGroup) parentActivity
				.findViewById(R.id.settings_Graphics_Radio_Group);
		sensorListView = (TextView) parentActivity
			.findViewById(R.id.settings_SensorList);
		
		volumeBar.setOnSeekBarChangeListener(this);
		sensitivityBar.setOnSeekBarChangeListener(this);
		graphicsSettings.setOnCheckedChangeListener(this);
		
		if (prefs.getInt("volume", 999) == 999
			|| prefs.getInt("sensitivity", 999) == 999
			|| prefs.getInt("graphicsVersion", 999) == 999) {
			volumeBar.setProgress(prefs.getInt("volume", 999));
			sensitivityBar.setProgress(prefs.getInt("sensitivity", 999));
			switch (prefs.getInt("graphicsVersion", 999)) {
				case 1:
					low3DVideoQuality.setChecked(true);
					break;
				case 2:
					med3DVideoQuality.setChecked(true);
					break;
				case 3:
					high3DVideoQuality.setChecked(true);
					break;
				default:
					lowVideoQuality.setChecked(true);
					break;
			}
		} else {
			volumeBar.setProgress(volumeBar.getMax());
			sensitivityBar.setProgress(sensitivityBar.getMax());
			lowVideoQuality.setChecked(true);
		}
		
		
		mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
		deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
		//sensorListView.append();
		for (Sensor sensor : deviceSensors){
			sensorListView.append(sensor.getName() + " TYPE CODE: " + sensor.getType() +'\n');
		}


		lowVideoQuality.setEnabled(true);

		final ActivityManager activityManager = (ActivityManager) getActivity()
				.getSystemService(Context.ACTIVITY_SERVICE);
		final ConfigurationInfo configurationInfo = activityManager
				.getDeviceConfigurationInfo();
		int version;
		if (configurationInfo.reqGlEsVersion >= 0x30000) {
			version = 3;
			low3DVideoQuality.setEnabled(true);
			med3DVideoQuality.setEnabled(true);
			high3DVideoQuality.setEnabled(true);
		} else if (configurationInfo.reqGlEsVersion >= 0x20000) {
			version = 2;
			low3DVideoQuality.setEnabled(true);
			med3DVideoQuality.setEnabled(true);
			high3DVideoQuality.setEnabled(false);
		} else if (configurationInfo.reqGlEsVersion >= 0x10000) {
			version = 1;
			low3DVideoQuality.setEnabled(true);
			med3DVideoQuality.setEnabled(false);
			high3DVideoQuality.setEnabled(false);
		} else {
			version = 0;
			low3DVideoQuality.setEnabled(false);
			med3DVideoQuality.setEnabled(false);
			high3DVideoQuality.setEnabled(false);
		}

		Log.w("SettingsFragment_onStart", "OpenGL Version: " + version + " / "
				+ configurationInfo.reqGlEsVersion);

		Log.i("SettingsFragment_onStart", "On start finished");
	}

	@Override
	public void onPause() {
		super.onPause();

		prefsEditor.putInt("volume", volume);
		prefsEditor.putInt("sensitivity", sensitivity);
		prefsEditor.putInt("graphicsVersion", graphicsVersion);

		Log.i("SettingsFragment_onPause", "onPause finished");
	}

	@Override
	public void onStop() {
		super.onStop();

		prefsEditor.apply();

		Log.i("SettingsFragment_onStop", "onStop finished");
	}

	// --------------------Event Listeners----------------------------
	
	@Override
	public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
		String viewName = "none";

		if (arg0.getId() == volumeBar.getId()) {
			viewName = "volumeBar";
			volume = arg1;
		}

		if (arg0.getId() == sensitivityBar.getId()) {
			viewName = "sensitivityBar";
			sensitivity = arg1;
		}

		Log.i("SettingsFragment_onProgressChanged", "SeekBar: " + viewName
				+ "was cahnged to: " + arg1);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		String viewName = "none";
		int viewID = v.getId();
		if (viewID == volumeBar.getId())
			viewName = "volumeBar";
		else if (viewID == sensitivityBar.getId())
			viewName = "sensitivityBar";
		else if (viewID == lowVideoQuality.getId())
			viewName = "lowVideoQuality";
		else if (viewID == low3DVideoQuality.getId())
			viewName = "low3DVideoQuality";
		else if (viewID == med3DVideoQuality.getId())
			viewName = "med3DVideoQuality";
		else if (viewID == high3DVideoQuality.getId())
			viewName = "high3DVideoQuality";
		else if (viewID == graphicsSettings.getId())
			viewName = "graphicsSettings";
		else
			viewName = "not listed";

		Log.i("SettingsFragment_onClick", "View clicked was: " + viewName);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		String viewName = "none";
		
		if (lowVideoQuality.getId() == checkedId){
			graphicsVersion = 0;
			viewName = "lowVideoQuality";
		}
		if (low3DVideoQuality.getId() == checkedId){
			graphicsVersion = 1;
			viewName = "low3DVideoQuality";
		}
		if (med3DVideoQuality.getId() == checkedId){
			graphicsVersion = 2;
			viewName = "med3DVideoQuality";
		}
		if (high3DVideoQuality.getId() == checkedId){
			graphicsVersion = 3;
			viewName = "high3DVideoQuality";
		}
		
		Log.i("SettingsFragment_onCheckedChanged", "Radio button checked is ID: " + checkedId +": " + viewName);
	}

}
