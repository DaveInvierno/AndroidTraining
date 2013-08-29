package com.bignerdranch.android.training;

import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class HelloMoonFragment extends Fragment {

	private AudioPlayer mPlayer = new AudioPlayer();
	private Button mPlayButton;
	private Button mPauseButton;
	private Button mStopButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstances) {
		View v = inflater.inflate(R.layout.fragment_hello_moon, parent, false);
		
		mPlayButton = (Button)v.findViewById(R.id.hellomoon_playButton);
		mPlayButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mPlayer.play(getActivity());
			}
		});
		
		mPauseButton = (Button)v.findViewById(R.id.hellomoon_pauseButton);
		mPauseButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int state = mPlayer.pause();
				if (state == 1) 
					mPauseButton.setText(R.string.hellomoon_resume);
				else if (state == 2)
					mPauseButton.setText(R.string.hellomoon_pause);
			}
		});
		
		mStopButton = (Button)v.findViewById(R.id.hellomoon_stopButton);
		mStopButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mPlayer.stop();
			}
		});
		
		showVideo(v);
		
		return v;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		mPlayer.stop();
	}
	
	 private void showVideo(View v)
	 {
	        VideoView vd = (VideoView)v.findViewById(R.id.videoView);
	        AssetManager am = getActivity().getAssets();
	        Uri uri = Uri.parse("android.resource://" +	R.raw.apollo_17_stroll);
	        MediaController mc = new MediaController(getActivity());
	        vd.setMediaController(mc);
	        vd.setVideoURI(uri);
	        vd.start();
	 }
}
