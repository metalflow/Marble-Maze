package edu.msudenver.CS390H.marblemaze;

import java.util.List;
import java.util.zip.Inflater;

import org.xmlpull.v1.XmlPullParser;

import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.SurfaceView;
import android.opengl.GLSurfaceView;

public class MazeFragment extends Fragment implements SensorEventListener,
		OnClickListener {
	private SensorManager mSensorManager;
	private List<Sensor> deviceSensors;
	private Sensor mSensor;
	private SharedPreferences prefs;
	private int volume;
	private int sensitivity;
	private int graphicsVersion;
	private Fragment drawFrag;
	private FragmentTransaction transaction;
	private TwoDDrawFrag playfield;
	private ActionBar actions;

	// ------------LIFE CYCLE
	// EVENTS-------------------------------------------------
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.i("MazeFragment_OnCreate", "Sensor Device list");
		mSensorManager = (SensorManager) getActivity().getSystemService(
				Context.SENSOR_SERVICE);
		deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
		for (Sensor sensor : deviceSensors) {
			Log.i("SensorList",
					sensor.getName() + " TYPE CODE: " + sensor.getType());
		}

		actions = getActivity().getActionBar();
		Log.i("MazeFragment_OnCreate", "On create finished");

		prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
		volume = prefs.getInt("volume", 100);
		sensitivity = prefs.getInt("sensitivity", 100);
		graphicsVersion = prefs.getInt("graphicsVersion", 0);
	}

	protected void onPostCreate(Bundle savedInstanceState) {
		// super.onPostCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("MazeFragment_onCreateView", "graphicsVersion = " + graphicsVersion );

		if (graphicsVersion > 0) {
			
			playfield = new OpenGLDrawFrag(getActivity(),graphicsVersion);
		} else {
			playfield = new TwoDDrawFrag(getActivity());
		}
		Log.i("MazeFragment_onCreateView", "onCreateView finished");
		return playfield;
		// return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();

	}

	@Override
	public void onResume() {
		super.onResume();

		for (Sensor sensor : deviceSensors) {
			mSensorManager.registerListener(this, sensor,
					SensorManager.SENSOR_DELAY_NORMAL);
		}

		playfield.setOnClickListener(this);
		// mSensorManager.registerListener(this, mSensor,
		// SensorManager.SENSOR_DELAY_NORMAL);

		Log.i("MazeFragment_onResume", "onResume finished");
	}

	@Override
	public void onPause() {
		super.onPause();

		mSensorManager.unregisterListener(this);
		// transaction.remove(drawFrag);

		Log.i("MazeFragment_onPause", "onPause finished");
	}

	// --------------------SENSOR
	// EVENTS--------------------------------------------------

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
			playfield.updateGravity(event.values[0], event.values[1],
					event.values[2]);
			// Log.i("MazeFragment_onSensorChanged", "updateGravity x:" +
			// event.values[0] +" y:" +event.values[1] + " z:"
			// +event.values[2]);
		}

		// Log.i("MazeFragment_onSensorChanged", "onSensorChanged finished");
	}

	@Override
	public void onClick(View v) {

		if (actions.isShowing()) {
			actions.hide();
			playfield.surfaceCreated(playfield.holder);
			onResume();
		} else {
			actions.show();
			playfield.surfaceDestroyed(playfield.holder);
			onPause();
		}
	}
}
