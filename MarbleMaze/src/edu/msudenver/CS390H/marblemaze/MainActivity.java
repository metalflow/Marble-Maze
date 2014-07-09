package edu.msudenver.CS390H.marblemaze;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity {

	Fragment gameFrag = new MazeFragment();
	Fragment settingsFrag = new SettingsFragment();
	Fragment highFrag = new HighScoresFragment();
	FragmentTransaction transaction;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen_marble_maze);
		
		
	}

	public void executePlayGame() {
		transaction = getSupportFragmentManager().beginTransaction();
		transaction.add(gameFrag, null);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	public void executeSettings() {
		transaction = getSupportFragmentManager().beginTransaction();
		transaction.add(settingsFrag, null);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	public void executeHighSCore() {
		transaction = getSupportFragmentManager().beginTransaction();
		transaction.add(highFrag, null);
		transaction.addToBackStack(null);
		transaction.commit();
	}
}
