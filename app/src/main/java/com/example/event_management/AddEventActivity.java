package com.example.event_management;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEventActivity extends AppCompatActivity {

    EditText etName, etDate, etTime, etDesc, etPrice;
    Button btnSave;
    DatabaseReference eventDb;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user == null) {
            finish();
            return;
        }

        // Firebase path
        eventDb = FirebaseDatabase.getInstance().getReference("Events");

        etName = findViewById(R.id.etEventName);
        etDate = findViewById(R.id.etEventDate);
        etTime = findViewById(R.id.etEventTime);
        etDesc = findViewById(R.id.etEventDesc);
        // etPrice = findViewById(R.id.etEventPrice); // Agar XML mein price field hai

        btnSave = findViewById(R.id.btnSaveEvent);

        btnSave.setOnClickListener(v -> saveEvent());
    }

    private void saveEvent() {
        String name = etName.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String time = etTime.getText().toString().trim();
        String desc = etDesc.getText().toString().trim();
        String price = "500"; // Filhaal static, baad mein etPrice.getText() se le sakte ho

        if (name.isEmpty() || date.isEmpty() || time.isEmpty()) {
            Toast.makeText(this, "Please fill required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String eventId = eventDb.push().getKey();

        // 🔥 Ab constructor match karega kyunki humne Event.java fix kar di hai
        Event event = new Event(eventId, name, date, time, desc, R.drawable.ic_launcher_background, price);

        if (eventId != null) {
            eventDb.child(eventId).setValue(event)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Event Added!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(this, "Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}