package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.content.SharedPreferences;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Patterns;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private EditText emailInput;
    private EditText passwordInput;
    private Button loginButton;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "inside onCreate");
        setContentView(R.layout.activity_login);

        emailInput    = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton   = findViewById(R.id.loginButton);

        prefs = getSharedPreferences(
                "com.example.androidassignments.PREFS",
                MODE_PRIVATE
        );

        String lastEmail = prefs.getString(
                "DefaultEmail",
                "email@domain.com"
        );
        emailInput.setText(lastEmail);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email    = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString();

                boolean valid = true;

                if (email.isEmpty() ||
                        !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailInput.setError("Enter a valid email");
                    valid = false;
                }

                if (password.isEmpty()) {
                    passwordInput.setError("Password cannot be empty");
                    valid = false;
                }

                if (!valid) {
                    Toast.makeText(
                            LoginActivity.this,
                            "Fix errors before continuing",
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }

                prefs.edit()
                        .putString("DefaultEmail", email)
                        .apply();

                Intent intent = new Intent(
                        LoginActivity.this,
                        MainActivity.class
                );
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.i(TAG, "inside onStart");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i(TAG, "inside onResume");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i(TAG, "inside onPause");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i(TAG, "inside onStop");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(TAG, "inside onDestroy");
    }
}