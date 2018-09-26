package com.blogspot.blogsetyaaji.androidlanjutan;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
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

    private void changeUIButton(boolean rPlay, boolean rStop, boolean play, boolean stop) {
        btnPlay.setEnabled(play);
        btnSplay.setEnabled(stop);
        btnRecord.setEnabled(rPlay);
        btnRstop.setEnabled(rStop);
    }

    @OnClick(R.id.btnRstop)
    public void onBtnRstopClicked() {
    }

    @OnClick(R.id.btnSplay)
    public void onBtnSplayClicked() {
    }

    @OnClick(R.id.btnPlay)
    public void onViewClicked() {
    }
}
