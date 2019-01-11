package com.appprocessors.ecomstore.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.utils.UserSessionManager;


public class SplashScreen extends AppCompatActivity {

    // User Session Manager Class
    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        // Session class instance
        session = new UserSessionManager(getApplicationContext());

        // Check user login
        // If User is not logged in , This will redirect user to LoginActivity.


        SharedPreferences sp = getSharedPreferences("test", Context.MODE_PRIVATE);
        if (!sp.getBoolean("first", false)) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("first", true);
            editor.apply();
            Intent intent1 = new Intent(this, WelcomeActivity.class); // Call the AppIntro java class
            startActivity(intent1);
        } else {
            if (session.checkLogin()) {
                finish();
            } else {
                Intent intent = new Intent(this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // Add new Flag to start new Activity
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        }


    }
}
