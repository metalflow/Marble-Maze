package edu.msudenver.CS390H.marblemaze;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends FragmentActivity {

	FragmentTransaction transaction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_container);

		Fragment titleFrag = new TitleFragment();
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.add(titleFrag, null);
		transaction.addToBackStack(null);
		transaction.commit();

		Log.i("MainActivity_onCreate", "onCreate finished");
	}

	public void executePlayGame(View view) {
		Fragment gameFrag = new MazeFragment();
		transaction = getSupportFragmentManager().beginTransaction();
		transaction.add(gameFrag, null);
		transaction.addToBackStack(null);
		transaction.commit();
		Log.i("MainActivity_executePlayGame", "executePlayGame finished");
	}

	public void executeSettings(View view) {
		Fragment settingsFrag = new SettingsFragment();
		transaction = getSupportFragmentManager().beginTransaction();
		transaction.add(settingsFrag, null);
		transaction.addToBackStack(null);
		transaction.commit();
		Log.i("MainActivity_executeSettings", "executeSettings finished");
	}

	public void executeHighScore(View view) {
		Fragment highFrag = new HighScoresFragment();
		transaction = getSupportFragmentManager().beginTransaction();
		transaction.add(highFrag, null);
		transaction.addToBackStack(null);
		transaction.commit();
		Log.i("MainActivity_executeHighSCore", "executeHighSCore finished");
	}
}
