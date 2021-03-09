package com.example.user.myapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ImageButton playList;
    MediaPlayer mediaPlayer ;
    Utilities utils;
    SongManager songManager;
    int currentSongIndex = 0;
    boolean isShuffle = false;
    boolean isRepeat = false;

    ArrayList<HashMap<String,String>> songsList = new ArrayList<>();

    ImageButton btnPrevious;
    ImageButton btnBackward;
    ImageButton btnPlay;
    ImageButton btnForward;
    ImageButton btnNext;
    ImageButton btnShuffle;
    ImageButton btnRepeat;

    SeekBar songProgressBar;

    TextView songCurrentDurationLabel;
    TextView songTotalCurrentDurationLabel;
    TextView songTitle;

    Handler musicHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playList = (ImageButton) findViewById(R.id.btn_menubar);
        btnPrevious = (ImageButton) findViewById(R.id.btn_previouse);
        btnBackward = (ImageButton) findViewById(R.id.btn_backward);
        btnPlay = (ImageButton) findViewById(R.id.btn_play);
        btnForward = (ImageButton) findViewById(R.id.btn_forward);
        btnNext = (ImageButton) findViewById(R.id.btn_next);
        btnRepeat = (ImageButton) findViewById(R.id.btn_repeat);
        btnShuffle = (ImageButton) findViewById(R.id.btn_shuffle);

        songCurrentDurationLabel = (TextView)findViewById(R.id.songCurrentDurationLabel);
        songTotalCurrentDurationLabel = (TextView)findViewById(R.id.songTotalDurationLabel);
        songTitle = (TextView)findViewById(R.id.songTitle);

        songProgressBar = (SeekBar)findViewById(R.id.songProgressBar);

        mediaPlayer = new MediaPlayer();

        songManager = new SongManager();

        utils = new Utilities();

        songsList = songManager.getSongsList();

        songProgressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                musicHandler.removeCallbacks(musicUpdate);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                musicHandler.removeCallbacks(musicUpdate);
                int totalDuration = mediaPlayer.getDuration();
                int currentDuration = utils.progressToTime(seekBar.getProgress() ,totalDuration);
                mediaPlayer.seekTo(currentDuration);

                updateProgress();

            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (isRepeat){

                    playSong(currentSongIndex);

                }
                else if (isShuffle){

                    Random random = new Random();
                    currentSongIndex = random.nextInt(songsList.size()-1);
                    playSong(currentSongIndex);

                }
                else
                {
                    if (currentSongIndex<(songsList.size()-1)){
                        playSong(currentSongIndex+1);
                        currentSongIndex++;
                    }else
                    {
                        playSong(0);
                        currentSongIndex = 0;
                    }
                }

            }
        });

        playList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToPlayList = new Intent(MainActivity.this,PlayListActivity.class);
                startActivityForResult(goToPlayList,100);
            }
        });

        btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                    int currentPosition = mediaPlayer.getCurrentPosition();//return current position of music to milli seconds

                    if (currentPosition+10000 <= mediaPlayer.getDuration()){

                        mediaPlayer.seekTo(currentPosition+10000);

                    }else {
                        mediaPlayer.seekTo(mediaPlayer.getDuration());
                    }
                }
            }
        });

        btnBackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = mediaPlayer.getCurrentPosition();

                if (currentPosition-10000>=0){
                    mediaPlayer.seekTo(currentPosition-10000);
                }
                else {
                    mediaPlayer.seekTo(0);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentSongIndex<(songsList.size()-1)){
                    playSong(currentSongIndex+1);
                    currentSongIndex++;
                }
                else {
                    playSong(0);
                    currentSongIndex = 0;
                }
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentSongIndex-1>0){
                    playSong(currentSongIndex-1);
                    currentSongIndex--;
                }
                else {
                    playSong(songsList.size()-1);
                    currentSongIndex = songsList.size()-1;
                }
            }
        });
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                    if (mediaPlayer!=null){
                        mediaPlayer.pause();
                        btnPlay.setImageResource(R.drawable.btn_play);
                    }

                }else {
                    if (mediaPlayer!=null){
                        mediaPlayer.start();
                        btnPlay.setImageResource(R.drawable.btn_pause);
                    }
                }
            }
        });

        btnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRepeat){
                    isRepeat = false;
                    btnRepeat.setImageResource(R.drawable.repeatoff);
                    Toast.makeText(getApplicationContext(),"Repeat is Off",Toast.LENGTH_SHORT);
                }else {
                    isRepeat = true;
                    btnRepeat.setImageResource(R.drawable.repeaton);
                    Toast.makeText(getApplicationContext(),"Repeat is On",Toast.LENGTH_SHORT);
                    isShuffle = false;
                    btnShuffle.setImageResource(R.drawable.playaccidoff);
                }
            }
        });
        btnShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShuffle){
                    isShuffle = false;
                    btnShuffle.setImageResource(R.drawable.playaccidoff);
                    Toast.makeText(getApplicationContext(),"Shuffle is Off",Toast.LENGTH_SHORT);
                }else {
                    isShuffle = true;
                    btnShuffle.setImageResource(R.drawable.playaccidon);
                    Toast.makeText(getApplicationContext(),"Shuffle is On",Toast.LENGTH_SHORT);
                    isRepeat = false;
                    btnRepeat.setImageResource(R.drawable.repeatoff);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode ,int resultCode ,Intent data){
        super.onActivityResult(requestCode ,resultCode , data);
        if (resultCode == 100){
            currentSongIndex = data.getExtras().getInt("songIndex");
            playSong(currentSongIndex);
        }
    }

    public void playSong(int songIndex){
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(songsList.get(songIndex).get("path"));
            mediaPlayer.prepare();
            mediaPlayer.start();

            songTitle.setText(songsList.get(songIndex).get("title"));

            btnPlay.setImageResource(R.drawable.btn_pause);

            songProgressBar.setMax(100);
            songProgressBar.setProgress(0);
            updateProgress();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateProgress(){
        musicHandler.postDelayed(musicUpdate ,100);
    }

    Runnable musicUpdate = new Runnable() {
        @Override
        public void run() {
            long totalDuration = mediaPlayer.getDuration();
            long currentDuration = mediaPlayer.getCurrentPosition();
            songCurrentDurationLabel.setText(utils.changeMilliToTime(currentDuration));
            songTotalCurrentDurationLabel.setText(utils.changeMilliToTime(totalDuration));
            int progress = (int)utils.getProgressPercentage(currentDuration ,totalDuration);
            songProgressBar.setProgress(progress);
            musicHandler.postDelayed(this,100);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }
}


//        try{
//            mediaPlayer.setDataSource("/sdcard/Download/ash.mp3");
//            mediaPlayer.prepare();
//        }catch (IOException e){
//            e.printStackTrace();
//
//        }
//        mediaPlayer.start();