package com.shubham.tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout pp, pc;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pp = findViewById(R.id.pp);
        pc = findViewById(R.id.pc);

        pp.setOnClickListener(this);
        pc.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
        switch (view.getId()){
            case R.id.pp:
                intent = new Intent(this, PlayerVsPlayer.class);
                break;
            case R.id.pc:
                intent = new Intent(this, PlayerVsComp.class);
                break;
        }
        startActivity(intent,options.toBundle());

    }
}