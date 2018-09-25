package com.blogspot.blogsetyaaji.androidlanjutan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MyFunctions extends AppCompatActivity {

    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
    }

    public void bukaActivity(Class activityTujuan){
        startActivity(new Intent(context, activityTujuan));
    }
}
