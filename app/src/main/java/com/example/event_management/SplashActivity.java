package com.example.event_management;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // --- OPTIONAL: LOGO ANIMATION ---
        // Agar logo ko thoda "Zoom-in" wala effect dena hai:
        ImageView logo = findViewById(R.id.splashLogo);
        Animation fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        logo.startAnimation(fadeIn);

        // --- 3 SECOND DELAY LOGIC ---
        new Handler().postDelayed(() -> {
            // Check if user is already logged in
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                // Seedha Home Screen (MainActivity) par jao
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            } else {
                // Pehle Login karwao
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }

            // Smooth transition effect
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish(); // Splash activity ko band kar do taaki user back na aa sake
        }, 3000);
    }
}