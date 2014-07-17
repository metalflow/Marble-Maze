package edu.msudenver.CS390H.marblemaze;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

public class TitleFragment extends Fragment {



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setContentView(R.layout.title_screen);

		Log.i("TitleFragment_onCreate", "onCreate finished");
	}



}
