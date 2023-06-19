package com.vn.tcshop.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Animation_main extends AppCompatActivity {
    private TextView text_V;
    private ImageView img_V;
    private View v1, v2, v3, v4, v1a, v2a, v3a, v4a;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_main);

        text_V = findViewById(R.id.tv_animation_main);
        img_V = findViewById(R.id.v_img);
        v1 = findViewById(R.id.v1);
        v2 = findViewById(R.id.v2);
        v3 = findViewById(R.id.v3);
        v4 = findViewById(R.id.v4);
        v1a = findViewById(R.id.v1a);
        v2a = findViewById(R.id.v2a);
        v3a = findViewById(R.id.v3a);
        v4a = findViewById(R.id.v4a);

        img_V.setVisibility(View.INVISIBLE);
        text_V.setVisibility(View.INVISIBLE);
        v1.setVisibility(View.INVISIBLE);
        v2.setVisibility(View.INVISIBLE);
        v3.setVisibility(View.INVISIBLE);
        v4.setVisibility(View.INVISIBLE);
        v1a.setVisibility(View.INVISIBLE);
        v2a.setVisibility(View.INVISIBLE);
        v3a.setVisibility(View.INVISIBLE);
        v4a.setVisibility(View.INVISIBLE);

        Animation logo_img = AnimationUtils.loadAnimation(Animation_main.this, R.anim.zoom_animation);

        Animation a_v1 = AnimationUtils.loadAnimation(Animation_main.this, R.anim.top_animation);
        Animation a_v2 = AnimationUtils.loadAnimation(Animation_main.this, R.anim.top_animation);
        Animation a_v3 = AnimationUtils.loadAnimation(Animation_main.this, R.anim.top_animation);
        Animation a_v4 = AnimationUtils.loadAnimation(Animation_main.this, R.anim.top_animation);

        Animation a_v1a = AnimationUtils.loadAnimation(Animation_main.this, R.anim.bottom_animation);
        Animation a_v2a = AnimationUtils.loadAnimation(Animation_main.this, R.anim.bottom_animation);
        Animation a_v3a = AnimationUtils.loadAnimation(Animation_main.this, R.anim.bottom_animation);
        Animation a_v4a = AnimationUtils.loadAnimation(Animation_main.this, R.anim.bottom_animation);

        v1.startAnimation(a_v1);
        v1a.startAnimation(a_v1a);
        v1.setVisibility(View.VISIBLE);
        v1a.setVisibility(View.VISIBLE);

        a_v1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v2.setVisibility(View.VISIBLE);
                v2a.setVisibility(View.VISIBLE);
                v2.startAnimation(a_v2);
                v2a.startAnimation(a_v2a);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        a_v2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v3.setVisibility(View.VISIBLE);
                v3a.setVisibility(View.VISIBLE);
                v3.startAnimation(a_v3);
                v3a.startAnimation(a_v3a);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        a_v3.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v4.setVisibility(View.VISIBLE);
                v4a.setVisibility(View.VISIBLE);
                v4.startAnimation(a_v4);
                v4a.startAnimation(a_v4a);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        a_v4.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                img_V.setVisibility(View.VISIBLE);
                img_V.startAnimation(logo_img);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        logo_img.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                text_V.setVisibility(View.VISIBLE);
                final String animateTxt = text_V.getText().toString();
                text_V.setText("");
                count = 0;
                new CountDownTimer(animateTxt.length() * 30, 30) {

                    @Override
                    public void onTick(long l) {
                        text_V.setText(animateTxt.substring(0, count));
                        count++;
                    }

                    @Override
                    public void onFinish() {
                        text_V.setText(animateTxt); // Đảm bảo hiển thị đầy đủ chữ khi kết thúc
                    }
                }.start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Animation_main.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },8000);
    }
}