package com.huayuxun.whale.materialdesign;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.huayuxun.whale.materialdesign.widget.DoubanLoading;

public class MainActivity extends AppCompatActivity {

    private DoubanLoading mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        initProgressBar();
    }

    private void initProgressBar() {
        mProgressBar = (DoubanLoading)findViewById(R.id.douban_main_activity);
        mProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressBar.startAnimator();
            }
        });
    }
}
