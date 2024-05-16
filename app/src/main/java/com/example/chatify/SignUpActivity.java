package com.example.chatify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chatify.controller.SignUpControl;
import com.redmatrix.chatify.R;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        TextView txtLogin = findViewById(R.id.txtLogin);
        Button btnSignUp = findViewById(R.id.btnSignUp);
        EditText emailEditText = findViewById(R.id.email);
        EditText passwordEditText = findViewById(R.id.password);
        EditText confirmPasswordEditText = findViewById(R.id.confirmPassword);

        SignUpControl signUpControl = new SignUpControl(this, emailEditText, passwordEditText, confirmPasswordEditText);

        txtLogin.setOnClickListener(signUpControl);
        btnSignUp.setOnClickListener(signUpControl);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
