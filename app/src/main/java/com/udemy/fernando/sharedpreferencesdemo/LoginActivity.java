package com.udemy.fernando.sharedpreferencesdemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private EditText txtEmail, txtPassword;
    private Button btnLogin;
    private Switch switchRemember;
    private CoordinatorLayout coordinatorLayout;

    public final static String PREFERENCES_NAME = "preferences";
    public final static String PREFERENCES_NAME_EMAIL = "email";
    public final static String PREFERENCES_NAME_PASSWORD = "password";
    public final static String PREFERENCES_NAME_REMEMBER = "remember";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);

        bindComponents();
        initComponents();

        setCredentialsIfExists();
    }

    private void bindComponents(){
        txtEmail = findViewById(R.id.editTextEmail);
        txtPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.buttonLogin);
        switchRemember = findViewById(R.id.switchRemember);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
    }

    private void initComponents(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();
                if(login(email, password)) {
                    savePreferences(email, password);
                    goToMain();
                }
            }
        });
    }

    private boolean isValidEmail(String email){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password){
        return password.length() >= 4;
    }

    private boolean login(String email, String password){
        if(!isValidEmail(email))
            Snackbar.make(coordinatorLayout,"Email is not valid, please try again", Snackbar.LENGTH_SHORT).show();
        else if(!isValidPassword(password))
            Snackbar.make(coordinatorLayout, "Password is not valid, 4 characters or more, please try again", Snackbar.LENGTH_SHORT).show();
        else
            return true;

        return false;
    }

    private void goToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void savePreferences(String email, String password){
        SharedPreferences.Editor editor = preferences.edit();

        if(switchRemember.isChecked()){
            editor.putString(PREFERENCES_NAME_EMAIL, email);
            editor.putString(PREFERENCES_NAME_PASSWORD, password);
            editor.putBoolean(PREFERENCES_NAME_REMEMBER, true);
            editor.apply();
            /*
            apply() y commit() hacen lo mismo, guardan las preferencias, pero apply() es asincrono y commit() es sincrono
             */
            //editor.commit();
        }else{
            editor.clear();
            editor.putBoolean(PREFERENCES_NAME_REMEMBER, false);
            editor.apply();
        }
    }

    private void setCredentialsIfExists(){
        String mail = preferences.getString(PREFERENCES_NAME_EMAIL, "");
        String password = preferences.getString(PREFERENCES_NAME_PASSWORD, "");
        boolean remember = preferences.getBoolean(PREFERENCES_NAME_REMEMBER, false);
        txtEmail.setText(mail);
        txtPassword.setText(password);
        switchRemember.setChecked(remember);
    }
}
