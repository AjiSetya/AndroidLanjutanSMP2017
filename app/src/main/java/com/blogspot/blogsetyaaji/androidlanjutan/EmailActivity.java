package com.blogspot.blogsetyaaji.androidlanjutan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmailActivity extends AppCompatActivity {

    @BindView(R.id.edSubject)
    EditText edSubject;
    @BindView(R.id.edSendEmail)
    EditText edSendEmail;
    @BindView(R.id.edPesanMail)
    EditText edPesanMail;
    @BindView(R.id.btnSendEmail)
    Button btnSendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnSendEmail)
    public void onViewClicked() {
        // subject dan email tujuan tidak boleh kosong
        if (TextUtils.isEmpty(edSubject.getText().toString())){
            edSubject.setError(getString(R.string.subject_kosong));
            edSubject.requestFocus();
        } else if (TextUtils.isEmpty(edSendEmail.getText().toString())){
            edSendEmail.setError(getString(R.string.email_tujuan_kosong));
            edSendEmail.requestFocus ();
        } else {
            // kirim email menggunakan intent untuk membuka aplikasi email
            Intent intent = new Intent(Intent.ACTION_SEND);
            // buat intent meembawa data pada aplikasi
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{edSendEmail.getText().toString()});
            intent.putExtra(Intent.EXTRA_SUBJECT, edSubject.getText().toString());
            intent.putExtra(Intent.EXTRA_TEXT, edPesanMail.getText().toString());
            // mengubah tipe dari email
            intent.setType("message/rfc822");
            // mulai mengirim email ke aplikasi gmail
            startActivity(Intent.createChooser(intent, "Pilih Aplikasi"));
        }
    }
}
