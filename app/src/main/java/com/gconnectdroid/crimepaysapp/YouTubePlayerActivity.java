package com.gconnectdroid.crimepaysapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YouTubePlayerActivity extends YouTubeBaseActivity
        implements YouTubePlayer.OnInitializedListener {
    private static final String TAG = "YouTubePlayerActivity";
    static final String GOOGLE_API_KEY ="AIzaSyCH58d5B2d4cYonLt834B8170TWE8Yeu4U";
    static final String YOUTUBE_VIDEO_ID ="phPZYTGgMj0";
    static final String YOUTUBE_PLAYLIST ="phPZYTGgMj0";
    SharedPreferences shared;
    AlertDialog alertdialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_you_tube_player);
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.youtubeactivity);*/

        ConstraintLayout layout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_you_tube_player, null);
        setContentView(layout);

    Button button1 = new Button(this);
        button1.setLayoutParams (new ConstraintLayout.LayoutParams(300, 80));
        button1.setText("Logout");
        layout.addView(button1);


        YouTubePlayerView playerView = new YouTubePlayerView(this);
        playerView.setLayoutParams (new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT ));
        layout.addView(playerView);

        playerView.initialize(GOOGLE_API_KEY, this);


    }
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored){
    Log.d(TAG,"onInitializationSuccess: Provider is " + provider.getClass().toString());
        Toast.makeText(this, "Initialized youtube player successfully", Toast.LENGTH_LONG).show();

        youTubePlayer.setPlaybackEventListener(playBackEventListener);
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);

        if(!wasRestored){
            youTubePlayer.loadVideo(YOUTUBE_PLAYLIST);
        }

    }
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        final int REQUEST_CODE = 1;
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, REQUEST_CODE).show();
        } else {
            String errorMessage = String.format("There was an error initializing the youtube player (%1$s)", youTubeInitializationResult.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    private YouTubePlayer.PlaybackEventListener playBackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {
            Toast.makeText(YouTubePlayerActivity.this, "Good, Video is Playing, Ok", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onPaused() {
            Toast.makeText(YouTubePlayerActivity.this, " Video is Paused", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onStopped() {
            Toast.makeText(YouTubePlayerActivity.this, "Video is Stopped", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {
            Toast.makeText(YouTubePlayerActivity.this, "Click, AD, amake the Ad creator rich", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onVideoStarted() {
            Toast.makeText(YouTubePlayerActivity.this, "Good, Video started", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onVideoEnded() {
            Toast.makeText(YouTubePlayerActivity.this, "Congratulation you have completed the video", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch( item.getItemId()){
            case R.id.menuLogout:
                alertdialog = new AlertDialog.Builder(YouTubePlayerActivity.this).create();

                alertdialog.setTitle("Logout");
                alertdialog.setMessage("Are you sure ! logout ?");
                alertdialog.setCancelable(false);
                alertdialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        alertdialog.dismiss();

                    }
                });

                alertdialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = shared.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent = new Intent(YouTubePlayerActivity.this, Signin.class);
                        startActivity(intent);
                    }
                });
                alertdialog.show();

        break;
            case R.id.settings:
                Toast.makeText(this,"You clicked Settings", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }
}
