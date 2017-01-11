package com.example.jinhua.whereisthetoilet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;


public class AboutActivity extends Activity implements View.OnTouchListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        videoPlayer();
    }

    public void videoPlayer() {

        VideoView videoview = new VideoView(this);
        setContentView(videoview);

        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.toiletworking);

        videoview.setVideoURI(uri);
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                    goMain();
                }

        });

        videoview.start();

        videoview.setOnTouchListener(new View.OnTouchListener(){

            @Override
        public boolean onTouch(View v, MotionEvent event) {
                ((VideoView) v).stopPlayback();
                goMain();
                        return true;

            }

        });

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }


    private synchronized void goMain()
    {
        Intent intent = new Intent(AboutActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
