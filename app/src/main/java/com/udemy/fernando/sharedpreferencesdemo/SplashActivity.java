package com.udemy.fernando.sharedpreferencesdemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getSharedPreferences(LoginActivity.PREFERENCES_NAME, MODE_PRIVATE);

        if(isRemembered())
            goToMain();
        else
            goToLogin();
    }

    private boolean isRemembered(){
        return preferences.getBoolean(LoginActivity.PREFERENCES_NAME_REMEMBER, false);
    }

    private void goToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
