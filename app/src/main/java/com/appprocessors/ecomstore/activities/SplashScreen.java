package com.appprocessors.ecomstore.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.utils.UserSessionManager;

import androidx.appcompat.app.AppCompatActivity;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;


public class SplashScreen extends AppCompatActivity {

    // User Session Manager Class
    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        // Session class instance
        session = new UserSessionManager(getApplicationContext());

        // Check user login
        // If User is not logged in , This will redirect user to LoginActivity.


        SharedPreferences sp = getSharedPreferences("test", Context.MODE_PRIVATE);
        if (!sp.getBoolean("first", false)) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("first", true);
            editor.apply();
            Intent intent1 = new Intent(this, WelcomeActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            ;// Call the AppIntro java class
            finish();
            startActivity(intent1);
        } else {
            if (!session.isUserLoggedIn()) {
                // user is not logged in redirect him to Login Activity
                Intent i = new Intent(this, LoginSignUp.class);
                // Add new Flag to start new Activity
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                // Staring Login Activity
                startActivity(i);

            } else {
                Intent intent = new Intent(this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        }


    }
}
