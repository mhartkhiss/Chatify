package com.example.chatify.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.example.chatify.LoginActivity;
import com.example.chatify.ProgressDialog;
import com.example.chatify.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.redmatrix.chatify.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SignUpControl implements View.OnClickListener {

    private Boolean isSignUpValid;
    private ProgressDialog progressDialog;




    private Context context;
    private EditText emailEditText, passwordEditText, confirmPasswordEditText;

    public SignUpControl(Context context, EditText emailEditText, EditText passwordEditText, EditText confirmPasswordEditText) {
        this.context = context;
        this.emailEditText = emailEditText;
        this.passwordEditText = passwordEditText;
        this.confirmPasswordEditText = confirmPasswordEditText;
        this.progressDialog = new ProgressDialog((Activity) context);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSignUp) {
            signUpUser();
        } else if (v.getId() == R.id.txtLogin) {
            goToLoginActivity();
        }
    }

    private void signUpUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        isSignUpValid = validateSignUpFields(email, password, confirmPassword);

        if (isSignUpValid) {
            progressDialog.show();
            progressDialog.setMessage("Signing up...");
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user != null) {
                                String userId = user.getUid();
                                String username = email;
                                String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                                FirebaseDatabase.getInstance().getReference("users")
                                        .child(userId)
                                        .setValue(new User(userId, username, email, "none", "user", null, timestamp, timestamp))
                                        .addOnCompleteListener(databaseTask -> {
                                            if (databaseTask.isSuccessful()) {
                                                Toast.makeText(context, "Sign up successful", Toast.LENGTH_SHORT).show();
                                                FirebaseAuth.getInstance().signOut();
                                                goToLoginActivity();
                                            } else {
                                                Toast.makeText(context, "Failed to store user data in database", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(context, "Email address is already in use", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Sign up failed. Please try again later.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private boolean validateSignUpFields(String email, String password, String confirmPassword) {
        // Check if email is empty or invalid
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, "Invalid email address", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Check if password is empty or less than 6 characters
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            Toast.makeText(context, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        ((Activity) context).finish();
    }
}

