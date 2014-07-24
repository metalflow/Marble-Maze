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
import android.view.SurfaceView;
import android.opengl.GLSurfaceView;

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
	private FragmentTransaction transaction;
	private SurfaceView playfield;
	
//------------LIFE CYCLE EVENTS-------------------------------------------------
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.i("MazeFragment_OnCreate", "Sensor Device list");
		mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
		deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
		for (Sensor sensor : deviceSensors){
			Log.i("SensorList", sensor.getName() + " TYPE CODE: " + sensor.getType());
		}
		
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

		if (graphicsVersion == 3 ){
			drawFrag = new OpenGL3DrawFrag();
			//playfield = new GLSurfaceView(getActivity());
			playfield = (GLSurfaceView) getActivity().findViewById(R.id.playFieldView);
		}
		if (graphicsVersion == 2 ){
			drawFrag = new OpenGL2DrawFrag();
			//playfield = new GLSurfaceView(getActivity());
			playfield = (GLSurfaceView) getActivity().findViewById(R.id.playFieldView);
		}
		if (graphicsVersion == 1 ){
			drawFrag = new OpenGL1DrawFrag();
			//playfield = new GLSurfaceView(getActivity());
			playfield = (GLSurfaceView) getActivity().findViewById(R.id.playFieldView);
		}
		if (graphicsVersion == 0 ){
			drawFrag = new TwoDDrawFrag();
			//playfield = new SurfaceView(getActivity());
			playfield = (SurfaceView) getActivity().findViewById(R.id.playFieldView);
		}
			
		//playfield.findViewById(R.id.playField);
		transaction = getActivity().getSupportFragmentManager()
				.beginTransaction();
		
		//transaction.add(R.id.fragElement, drawFrag, "draw");
		//transaction.commit();
		transaction.hide(drawFrag);
		
		mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
		
	}

	@Override
	public void onPause() {
		super.onPause();

		mSensorManager.unregisterListener(this);
		transaction.remove(drawFrag);
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
