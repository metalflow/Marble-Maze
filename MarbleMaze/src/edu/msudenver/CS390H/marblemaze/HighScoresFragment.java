package edu.msudenver.CS390H.marblemaze;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.os.Bundle;

public class HighScoresFragment extends Fragment {

	File highscoreFile; 
	String names[] = { "Chester Cheaterson", "Bradley Betah",
			"Terry Thurdston", "Forrest Forton", "Barry Bottomsworth" };
	int times[] = { 0, 50, 150, 350, 750 };
	TextView highscoreList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setContentView(R.layout.highscores);

		highscoreFile = new File(getActivity().getFilesDir(), "highscores.dat");
		if (highscoreFile.exists()) {
			try {
				ObjectInputStream objectReader = new ObjectInputStream(
						new BufferedInputStream(getActivity().openFileInput(
								"highscores.dat")));
				names = (String[]) objectReader.readObject();
				times = (int[]) objectReader.readObject();
				objectReader.close();
			} catch (IOException | ClassNotFoundException e) {
				Toast.makeText(
						getActivity(),
						"Failed to get highscores values from highscores file! Application will now exit",
						Toast.LENGTH_LONG).show();
				Log.e("HighScoresFragment_onCreate_fileaccess",
						"Failed to get highscore values from file", e);
			}
		} else {
			try {
				ObjectOutputStream objectWriter = new ObjectOutputStream(
						new BufferedOutputStream(getActivity().openFileOutput(
								"highscores.dat", Context.MODE_PRIVATE)));
				objectWriter.writeObject(names);
				objectWriter.writeObject(times);
				objectWriter.close();
			} catch (IOException e1) {
				Toast.makeText(
						getActivity(),
						"Failed to write default values to highscores file! Application will now exit",
						Toast.LENGTH_LONG).show();
				Log.e("HighScoresFragment_onCreate_fileaccess",
						"Failed to write default values to file", e1);
			}
		}
		Log.i("HighScoresFragment_onCreate", "onCreate finished");
	}

	@Override
	public void onStart() {
		super.onStart();
		highscoreList = (TextView) getActivity().findViewById(
				R.id.highscore_box);
		highscoreList.append("name		: 	time");
		for (int i = 0; i < 5; i++) {
			highscoreList.append(names[i] + ": " + times[i] + '\n');
		}

		Log.i("HighScoresFragment_onCreate", "onCreate finished");
	}
}
