package edu.msudenver.CS390H.marblemaze;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MazeFragment extends Fragment implements SensorEventListener
{
	private SensorManager mSensorManager;
	private List<Sensor> deviceSensors;
	private Sensor mSensor;
	private SharedPreferences prefs;
	private int volume;
	private int sensitivity;
	private int graphicsVersion;
	private Fragment drawFrag;
	private Fragment TwoDDrawFrag;
	private Fragment OpenGL1DrawFrag;
	private Fragment OpenGL2DrawFrag;
	private Fragment OpenGL3DrawFrag;
	
//------------LIFE CYCLE EVENTS-------------------------------------------------
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.i("MazeFragment_OnCreate", "Sensor Device list");
		mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
		deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
		for (Sensor sensor : deviceSensors){
			Log.i("SensorList", sensor.getName() + " TYPE CODE: " + sensor.getType());
		}
		
		TwoDDrawFrag = new TwoDDrawFrag();
		OpenGL1DrawFrag= new OpenGL1DrawFrag();
		OpenGL2DrawFrag= new OpenGL2DrawFrag();
		OpenGL3DrawFrag= new OpenGL3DrawFrag();

		Log.i("MarbleMazeActivity_OnCreate", "On create finished");

	}

	protected void onPostCreate(Bundle savedInstanceState) {
		//super.onPostCreate(savedInstanceState);


		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View fragView = inflater.inflate(R.layout.game,container,false);
		Log.i("HihgScoresFragment_onCreateView", "onCreateView finished");
		return fragView;
		//return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();

		
		
	}

	@Override
	public void onResume() {
		super.onResume();

		prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
		volume = prefs.getInt("volume", 100);
		sensitivity = prefs.getInt("sensitivity", 100);
		graphicsVersion = prefs.getInt("graphicsVersion", 0);
		/*
		private Fragment TwoDDrawFrag;
		private Fragment OpenGL1DrawFrag;
		private Fragment OpenGL2DrawFrag;
		private Fragment OpenGL3DrawFrag;
		
		
		if (graphicsVersion == 3 && OpenGL3DrawFrag.gets)
		drawFrag = new TitleFragment();
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.add(R.id.fragElement, titleFrag, "title");
		// transaction.addToBackStack(null);
		transaction.commit();
		*/
		mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
		
	}

	@Override
	public void onPause() {
		super.onPause();

		mSensorManager.unregisterListener(this);
		
	}



//--------------------SENSOR EVENTS--------------------------------------------------
	

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		
	}
	
}
