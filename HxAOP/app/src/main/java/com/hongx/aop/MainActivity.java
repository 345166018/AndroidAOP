package com.hongx.aop;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hongx.aop.annotation.BehaviorTrace;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @BehaviorTrace("分享")
    public void share(View view) {
        long begin = System.currentTimeMillis();
        SystemClock.sleep(new Random().nextInt(2000));
        long duration = System.currentTimeMillis() - begin;
        Log.d("hongxue", duration + "");
    }
}
