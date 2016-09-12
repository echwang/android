package com.echwang.audioapp;

import android.content.pm.PackageManager;
import android.media.MediaCodecInfo;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class AudioAppActivity extends AppCompatActivity {

    private static MediaRecorder mediaRecorder;
    private static MediaPlayer mediaPlayer;

    private static String audioFilePath;
    private static Button stopButton;
    private static Button playButton;
    private static Button recordButton;

    private boolean isRecording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_app);

        recordButton = (Button) findViewById(R.id.recordButton);
        playButton = (Button) findViewById(R.id.playButton);
        stopButton = (Button) findViewById(R.id.stopButton);

        if(!hasMicrophone())
        {
            stopButton.setEnabled(false);
            playButton.setEnabled(false);
            recordButton.setEnabled(false);
        }
        else
        {
            stopButton.setEnabled(false);
            playButton.setEnabled(false);
        }

//        audioFilePath = Environment.getDataDirectory().getAbsolutePath() + "/myAudio.3gp";
        audioFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myAudio.3gp";
    }

    protected boolean hasMicrophone(){
        PackageManager packageManager = this.getPackageManager();

        return packageManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE);
    }

    public void recordAudio(View view) throws IOException
    {
        isRecording = true;
        stopButton.setEnabled(true);
        playButton.setEnabled(false);
        recordButton.setEnabled(false);

        try{
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setOutputFile(audioFilePath);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (Exception e){
            e.printStackTrace();
        }


    }

    public void stopAudio(View view) throws IOException
    {
        stopButton.setEnabled(false);
        playButton.setEnabled(true);

        try {
            if (isRecording) {
                recordButton.setEnabled(false);
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;
                isRecording = false;
            } else {
                mediaPlayer.release();
                mediaPlayer = null;
                recordButton.setEnabled(true);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public void playAudio(View view) throws IOException
    {
        stopButton.setEnabled(true);
        playButton.setEnabled(false);
        recordButton.setEnabled(false);

        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(audioFilePath);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
