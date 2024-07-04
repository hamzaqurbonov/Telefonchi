package com.example.telefonchi.ui.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.telefonchi.R;
import com.example.telefonchi.ui.home.HomeViewActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditHameViewActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText editNameId, editSumId;
    private Button addContactId;
    String stringName, stringSum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_hame_view);


        editNameId = findViewById(R.id.edit_name_id);
        editSumId = findViewById(R.id.edit_sum_id);
        addContactId = findViewById(R.id.add_contact_id);

        stringName = getIntent().getStringExtra("name");
        stringSum = getIntent().getStringExtra("sum");

        editNameId.setText(stringName);
        editSumId.setText(stringSum);

        addContactId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> city = new HashMap<>();
                city.put("name", "Los Angeles");
                city.put("state", editSumId.getText().toString());
                city.put("country", editNameId.getText().toString());

                db.collection("cities").document("LA")
                        .set(city)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("demo3", "DocumentSnapshot successfully written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("demo3", "Error writing document", e);
                            }
                        });




                Toast.makeText(EditHameViewActivity.this, "Course Updated..", Toast.LENGTH_SHORT).show();


                Intent i = new Intent(EditHameViewActivity.this, HomeViewActivity.class);
                startActivity(i);
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}