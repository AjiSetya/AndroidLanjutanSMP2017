package com.blogspot.blogsetyaaji.androidlanjutan;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecordActivity extends AppCompatActivity {

    @BindView(R.id.btnRecord)
    Button btnRecord;
    @BindView(R.id.btnRstop)
    Button btnRstop;
    @BindView(R.id.btnSplay)
    Button btnSplay;
    @BindView(R.id.btnPlay)
    Button btnPlay;

    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;

    private String outputFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        ButterKnife.bind(this);

        // tampilan default ketika aplikasi di buka
        changeUIButton(true, false, false, false);

        // mengatur tempat penyimpanan file
        outputFile = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/rekaman_suara.3gp";

        // inisialisasi komponen
        mediaRecorder = new MediaRecorder();
        mediaPlayer = new MediaPlayer();
    }

    @OnClick(R.id.btnRecord)
    public void onBtnRecordClicked() {
        if (Build.VERSION.SDK_INT >= 23
                && checkSelfPermission(Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_DENIED
                && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED
                && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED) {

            requestPermissions(new String[]{
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);
        } else {
            // mengatur sumber suara
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            // mengatur tipe file output
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            // mengatur encoder file
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            // masukkan file ke dalam folder
            mediaRecorder.setOutputFile(outputFile);

            // mulai merekam
            try {
                mediaRecorder.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaRecorder.start();

            // rubah tampilan tombol
            changeUIButton(false, true, false, false);
        }
    }

    @OnClick(R.id.btnRstop)
    public void onBtnRstopClicked() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
        mediaRecorder = new MediaRecorder();

        changeUIButton(true, false, true, false);
    }

    @OnClick(R.id.btnSplay)
    public void onBtnSplayClicked() {
        // jika audio masih diputar dan media player tidak kosong
        if (mediaPlayer != null && mediaPlayer.isPlaying()){
            try {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (IllegalStateException e){
                e.printStackTrace();
            }
        } else {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        changeUIButton(true, false, true, false);
    }

    @OnClick(R.id.btnPlay)
    public void onViewClicked() {
        // jika media player kosong, maka buat instance baru
        if (mediaPlayer == null){
            mediaPlayer = new MediaPlayer();
        }

        try {
            mediaPlayer.setDataSource(outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();

        changeUIButton(false, false, false, true);
    }

    private void changeUIButton(boolean rPlay, boolean rStop, boolean play, boolean stop) {
        btnPlay.setEnabled(play);
        btnSplay.setEnabled(stop);
        btnRecord.setEnabled(rPlay);
        btnRstop.setEnabled(rStop);
    }
}
