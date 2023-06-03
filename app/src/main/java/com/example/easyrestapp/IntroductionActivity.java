package com.example.easyrestapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;;
import android.widget.ImageView;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;

public class IntroductionActivity extends AppCompatActivity {

    ImageView logo, splash;
    LottieAnimationView lottieAnimationView;
    TextView easy, rest;
    Intent i;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        splash = findViewById(R.id.img_back);
        lottieAnimationView = findViewById(R.id.lottie_animation);
        easy = findViewById(R.id.easy_tv);
        rest = findViewById(R.id.rest_tv);
        i= new Intent(this,StartActivity.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        splash.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        easy.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        rest.animate().translationY(1400).setDuration(1000).setStartDelay(4000).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                startActivity(i);
            }
            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }

}