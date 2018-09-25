package com.blogspot.blogsetyaaji.androidlanjutan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

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
    }

    @OnClick(R.id.btnSmsKirim)
    public void onBtnSmsKirimClicked() {
    }
}
