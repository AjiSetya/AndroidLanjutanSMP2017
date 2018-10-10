package com.blogspot.blogsetyaaji.androidlanjutan;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TtsActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    @BindView(R.id.edTts)
    EditText edTts;
    @BindView(R.id.btnTts)
    Button btnTts;

    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts);
        ButterKnife.bind(this);

        // inisialisasi tts, memasang komponen ke class ini
        textToSpeech = new TextToSpeech(this, this);
    }

    @OnClick(R.id.btnTts)
    public void onViewClicked() {
        startSpeak();
    }

    @Override
    public void onInit(int status) {
        // apakah komponen dapat diakses
        if (status == TextToSpeech.SUCCESS) {
            // atur bahasa ke default (INA)
            Locale b_indo = new Locale("in", "INA");
            // masukkan bahasa ke komponen
            int hasil_bahasa = textToSpeech.setLanguage(b_indo);

            // jika bahasa tidak tersedia di perangkat
            if (hasil_bahasa == TextToSpeech.LANG_MISSING_DATA
                    || hasil_bahasa == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, getString(R.string.lang_not_found), Toast.LENGTH_SHORT).show();
            } else {
                // mengambil data pada edittext dan mengeluarkan sebagai suara
                startSpeak();
            }
        } else {
            Toast.makeText(this, getString(R.string.tts_error),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void startSpeak() {
        // ambil teks dari editteks
        String teks = edTts.getText().toString();
        // proses data dan keluarkan dengan suara
        textToSpeech.speak(teks, TextToSpeech.QUEUE_FLUSH,
                null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // jika komponen masih aktif
        if (textToSpeech != null) {
            // hentikan komponen
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
