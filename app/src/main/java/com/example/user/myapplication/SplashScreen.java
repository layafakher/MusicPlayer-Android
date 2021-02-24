package com.example.user.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import static java.lang.Thread.sleep;

/**
 * Created by user on 9/25/2020.
 */

public class SplashScreen extends Activity{

    MediaPlayer mediaPlayer;
    public static final int RequestPermissionCode = 7;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        if (ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") != 0 || ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, 7);
        }

        mediaPlayer = MediaPlayer.create(SplashScreen.this,R.raw.mus);
        mediaPlayer.start();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
               try {
                   sleep(4000);

               }catch (InterruptedException i){
                   i.printStackTrace();
               }finally {
                   Intent i = new Intent(SplashScreen.this,MainActivity.class);
                   startActivity(i);
               }
            }
        });
        thread.start();
    }
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        switch(requestCode) {
            case 7:
                if (grantResults.length > 0) {
                    boolean readStorage = grantResults[0] == 0;
                    boolean writeInStorage = grantResults[1] == 0;
                    if (!readStorage || !writeInStorage) {
                        Toast.makeText(this, "Permission Denied",Toast.LENGTH_SHORT).show();
                    }
                }
            default:
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
        mediaPlayer.pause();
    }
}


