package com.blogspot.blogsetyaaji.androidlanjutan;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SmsActivity extends AppCompatActivity {

    @BindView(R.id.edSmsTo)
    EditText edSmsTo;
    @BindView(R.id.edSmsPesan)
    EditText edSmsPesan;
    @BindView(R.id.btnSmsIntent)
    Button btnSmsIntent;
    @BindView(R.id.btnSmsKirim)
    Button btnSmsKirim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnSmsIntent)
    public void onBtnSmsIntentClicked() {
        // cek apakah data input kosong
        if (TextUtils.isEmpty(edSmsTo.getText().toString())) {
            edSmsTo.setError(getString(R.string.pilih_nomor));
            edSmsTo.requestFocus();
        } else if (TextUtils.isEmpty(edSmsPesan.getText().toString())) {
            edSmsPesan.setError(getString(R.string.masukkan_pesan));
            edSmsPesan.requestFocus();
        } else {
            // membuka aplikasi sms menggunakan intent
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra("sms_body", edSmsPesan.getText().toString());
            intent.putExtra("address", edSmsTo.getText().toString());
            intent.setType("vnd.android-dir/mms-sms");
            startActivity(intent);
        }
    }

    @OnClick(R.id.btnSmsKirim)
    public void onBtnSmsKirimClicked() {
        // kirim langsung pesan dari aplikasi
        // cek permission
        // jika permission pada pengaturan untuk aplikasi ini belum enable
        if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission(Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_DENIED) {
            // tampilkan message minta izin
            requestPermissions(new String[]{Manifest.permission.SEND_SMS}
                    , 120);
        } else {
            // kirim sms langsung
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(edSmsTo.getText().toString(),
                        null, edSmsPesan.getText().toString(),
                        null, null);
                clearForm();
                Toast.makeText(this, getString(R.string.mengirim), Toast.LENGTH_SHORT).show();
            } catch (Exception e){
                Toast.makeText(this, getString(R.string.gagal_mengirim), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void clearForm() {
        edSmsTo.setText("");
        edSmsPesan.setText("");
    }
}
