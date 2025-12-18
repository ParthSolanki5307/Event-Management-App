package com.example.event_management;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    ImageView mainProfileImg, av1, av2, av3, av4;
    DatabaseReference userRef;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // --- 1. ID BINDING ---
        mainProfileImg = findViewById(R.id.profile_image);
        av1 = findViewById(R.id.av1);
        av2 = findViewById(R.id.av2);
        av3 = findViewById(R.id.av3);
        av4 = findViewById(R.id.av4);

        EditText etEmail = findViewById(R.id.etProfileEmail);
        EditText etName = findViewById(R.id.etProfileName);

        Button btnUpdate = findViewById(R.id.btnUpdateProfile);
        Button btnResetPass = findViewById(R.id.btnResetPass);
        Button btnAdmin = findViewById(R.id.btnAdminPanel);
        Button btnLogout = findViewById(R.id.btnLogout);
        ImageButton btnBack = findViewById(R.id.btnBack);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        // --- 2. DATA LOADING ---
        if (user != null) {
            userRef = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());

            etEmail.setText(user.getEmail());
            etName.setText(user.getDisplayName());

            // Admin Button Visibility Check
            if (user.getEmail() != null && user.getEmail().equals("admin@gmail.com")) {
                btnAdmin.setVisibility(View.VISIBLE);
            }

            // Load Saved Avatar from Firebase
            userRef.child("avatar").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String avatar = snapshot.getValue(String.class);
                        updateMainImageUI(avatar);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        }

        // --- 3. AVATAR CLICK LOGIC ---
        av1.setOnClickListener(v -> saveAvatarToFirebase("av1", R.drawable.ic_user_1));
        av2.setOnClickListener(v -> saveAvatarToFirebase("av2", R.drawable.ic_user_2));
        av3.setOnClickListener(v -> saveAvatarToFirebase("av3", R.drawable.ic_user_3));
        av4.setOnClickListener(v -> saveAvatarToFirebase("av4", R.drawable.ic_user_4));

        // --- 4. UPDATE NAME LOGIC ---
        btnUpdate.setOnClickListener(v -> {
            String newName = etName.getText().toString().trim();
            if (newName.isEmpty()) {
                etName.setError("Name required");
                return;
            }
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(newName).build();
            user.updateProfile(profileUpdates).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    userRef.child("name").setValue(newName);
                    Toast.makeText(this, "Name Updated! ✅", Toast.LENGTH_SHORT).show();
                }
            });
        });

        // --- 5. RESET PASSWORD LOGIC ---
        btnResetPass.setOnClickListener(v -> {
            auth.sendPasswordResetEmail(user.getEmail()).addOnSuccessListener(aVoid ->
                    Toast.makeText(this, "Reset link sent to your email! 📩", Toast.LENGTH_LONG).show());
        });

        // --- 6. ADMIN PANEL ---
        btnAdmin.setOnClickListener(v -> startActivity(new Intent(this, AdminActivity.class)));

        // --- 7. LOGOUT ---
        btnLogout.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Logout")
                    .setMessage("Do you want to logout?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        auth.signOut();
                        Intent intent = new Intent(this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("No", null).show();
        });

        // Back Button & Bottom Nav
        btnBack.setOnClickListener(v -> finish());
        setupBottomNav();
    }

    private void saveAvatarToFirebase(String avatarKey, int resId) {
        if (userRef != null) {
            userRef.child("avatar").setValue(avatarKey);
            mainProfileImg.setImageResource(resId);
            Toast.makeText(this, "Avatar Changed!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateMainImageUI(String avatar) {
        if ("av1".equals(avatar)) mainProfileImg.setImageResource(R.drawable.ic_user_1);
        else if ("av2".equals(avatar)) mainProfileImg.setImageResource(R.drawable.ic_user_2);
        else if ("av3".equals(avatar)) mainProfileImg.setImageResource(R.drawable.ic_user_3);
        else if ("av4".equals(avatar)) mainProfileImg.setImageResource(R.drawable.ic_user_4);
    }

    private void setupBottomNav() {
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        bottomNav.setSelectedItemId(R.id.nav_profile);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
                return true;
            } else if (id == R.id.nav_categories) {
                startActivity(new Intent(this, CategoryActivity.class));
                finish();
                return true;
            }
            return id == R.id.nav_profile;
        });
    }
}