package edu.msudenver.CS390H.marblemaze;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends FragmentActivity {

	FragmentTransaction transaction;
	private SharedPreferences prefs;
	private SharedPreferences.Editor prefsEditor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i("MainActivity_onCreate", "onCreate started");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_container);

		Fragment titleFrag = new TitleFragment();
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.add(R.id.fragElement, titleFrag, "title");
		// transaction.addToBackStack(null);
		transaction.commit();

		prefs = getPreferences(Context.MODE_PRIVATE);
		prefsEditor = prefs.edit();

		if (prefs.getInt("volume", 999) == 999
				|| prefs.getInt("sensitivity", 999) == 999
				|| prefs.getInt("graphicsVersion", 999) == 999) {
			prefsEditor.putInt("volume", 100);
			prefsEditor.putInt("sensitivity", 100);
			prefsEditor.putInt("graphicsVersion", 0);
			prefsEditor.apply();
			Log.i("MainActivity_onCreate",
					"SharedPreferences invalid, setting to defaults");
		} else {
			for (String key : prefs.getAll().keySet()){
				Log.i("MainActivity_onCreate", "SharedPreferences key: " + key + " and value is: " + prefs.getInt(key, 999) );
			}
		}
		
		getActionBar().hide();

		Log.i("MainActivity_onCreate", "onCreate finished");
	}

	@Override
	protected void onStart() {
		super.onStart();

		Log.i("MainActivity_onStart", "onStart finished");
	}

	@Override
	public void onStop() {
		super.onStop();

		boolean success = prefsEditor.commit();

		if (success)
			Log.i("MainActivity_onStop",
					"SharedPreferences successfully committed");
		else
			Log.i("MainActivity_onStop", "SharedPreferences failed to commit");

		Log.i("MainActivity_onStop", "onStop finished");
	}

	// -------- Button Methods ------------------------------------
	public void executePlayGame(View view) {
		Fragment gameFrag = new MazeFragment();
		transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.fragElement, gameFrag, "game");
		transaction.addToBackStack("game");
		transaction.commit();
		Log.i("MainActivity_executePlayGame", "executePlayGame finished");
	}

	public void executeSettings(View view) {
		Fragment settingsFrag = new SettingsFragment();
		transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.fragElement, settingsFrag, "settings");
		transaction.addToBackStack("settings");
		transaction.commit();
		Log.i("MainActivity_executeSettings", "executeSettings finished");
	}

	public void executeHighScore(View view) {
		Fragment highFrag = new HighScoresFragment();
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.fragElement, highFrag, "highscores");
		transaction.addToBackStack("highscores");
		transaction.commit();
		Log.i("MainActivity_executeHighSCore", "executeHighSCore finished");
	}
}
