package com.example.chatify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.chatify.models.Languages;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.redmatrix.chatify.R;

import java.util.List;

public class LanguageSetupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_setup);

        LinearLayout languageLayout = findViewById(R.id.linearLayout2); // Replace with your actual LinearLayout id

        List<String> languages = Languages.getLanguages();

        for (String language : languages) {
            Button languageButton = new Button(this);
            languageButton.setText(language);
            languageButton.setOnClickListener(v -> updateSourceLanguage(language));

            languageLayout.addView(languageButton);
        }
    }

    private void updateSourceLanguage(String language) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(uid);
            userRef.child("language").setValue(language)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(LanguageSetupActivity.this, "Language updated successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LanguageSetupActivity.this, MainActivity.class));
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LanguageSetupActivity.this, "Failed to update language", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}