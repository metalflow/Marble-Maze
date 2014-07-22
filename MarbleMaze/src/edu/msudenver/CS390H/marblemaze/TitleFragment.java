package edu.msudenver.CS390H.marblemaze;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.*;

public class TitleFragment extends Fragment {



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		Log.i("TitleFragment_onCreate", "onCreate finished");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View fragView = inflater.inflate(R.layout.title_screen,container,false);
		Log.i("TitleFragment_onCreateView", "onCreateView finished");
		return fragView;
		//return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onStart(){
		super.onStart();
		
		//getActivity().setContentView(R.layout.title_screen);
		
		Log.i("TitleFragment_onStart", "onStart finished");		
	}

}
