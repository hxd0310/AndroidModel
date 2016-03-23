package com.bry.ankle.androidmodel.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.bry.ankle.androidmodel.R;
import com.bry.ankle.androidmodel.Widget.NavigationViews;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addNavigationViews();
    }

    private void addNavigationViews() {
        NavigationViews navigationViews = (NavigationViews) findViewById(R.id.navigation_views);
        List<View> textViews = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            TextView textView = new TextView(this);
            textView.setText("tab" + i);
            textViews.add(textView);
            textView.setGravity(Gravity.CENTER);
            if(i == 0 || i == 2) {
                textView.setBackgroundResource(R.color.colorAccent);
            }
        }
        navigationViews.addViews(textViews);
        navigationViews.setOnNavigationClickListener(new NavigationViews.OnNavigationClickListener() {
            @Override
            public void onNavigationClick(View v, int position) {
                start();
            }
        });
    }

    private void start() {
        NavigationBarActivity.startActivity(this, null);
    }

}
