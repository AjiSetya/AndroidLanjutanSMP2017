package com.blogspot.blogsetyaaji.androidlanjutan;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends MyFunctions {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        MyFunctions myFunrevenctions = new MyFunctions();
//        myFunctions.bukaActivity();
    }

    public void bukaEmail(View view) {
        bukaActivity(EmailActivity.class);
    }

    public void bukaSms(View view) {
        bukaActivity(SmsActivity.class);
    }

    public void bukaRecord(View view) {
        bukaActivity(RecordActivity.class);
    }

    public void bukaTts(View view) {
        bukaActivity(TtsActivity.class);
    }

    public void bukaStt(View view) {
        bukaActivity(SttActivity.class);
    }

    public void bukaCamera(View view) {
        bukaActivity(CameraActivity.class);
    }
}
