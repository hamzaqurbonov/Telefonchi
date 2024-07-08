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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditHameViewActivity extends AppCompatActivity {
//    List<Map<String, Object>> modellist = new ArrayList<Map<String, Object>>();
    Map<String,Object> nestedData = new HashMap<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText editNameId, editSumId;
    private Button addContactId;
    String stringName, stringSum, docId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_hame_view);

        docId = getIntent().getExtras().getString("docId");

        editNameId = findViewById(R.id.edit_name_id);
        editSumId = findViewById(R.id.edit_sum_id);
        addContactId = findViewById(R.id.add_contact_id);

        stringName = getIntent().getStringExtra("name");
        stringSum = getIntent().getStringExtra("sum");

        editNameId.setText("name");
        editSumId.setText(stringSum);

        addContactId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> city = new HashMap<>();
                city.put("month", "Yanvar");

//                List<Map<String, Object>> modellist = prepareMemerList();

//                city.put("tag", modellist);
//
//                Log.d("demo4", "bos " + modellist.get(2));

                nestedData.put("sum", editSumId.getText().toString());
                nestedData.put("name", editNameId.getText().toString());

                CollectionReference citiesRef = db.collection("users");
                citiesRef.document(docId).collection(docId).add(nestedData);


//                db.collection("users").document("Yanvar")
//                        .set(city, SetOptions.merge())
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Log.d("demo3", "DocumentSnapshot successfully written!");
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.w("demo3", "Error writing document", e);
//                            }
//                        });

//

//                db.collection("users")
//                        .add(city)
//                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                            @Override
//                            public void onSuccess(DocumentReference documentReference) {
//                                Log.d("demo3", "DocumentSnapshot written with ID: " + documentReference.getId());
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.w("demo3", "Error adding document", e);
//                            }
//                        });



//                db.collection("users").document("Yanvar")
//
//// Set the "isCapital" field of the city 'DC'
//
//                        .update(map)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Log.d("demo3", "DocumentSnapshot successfully updated!");
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.w("demo3", "Error updating document", e);
//                            }
//                        });







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

//    private List<Map<String, Object>> prepareMemerList() {
//
//        List<Map<String, Object>> modellist = new ArrayList<>();
//        Map<String,Object> nestedData = new HashMap<>();
//        nestedData.put("sum", editSumId.getText().toString());
//        nestedData.put("name", editNameId.getText().toString());
//
//        for (int i = 0; i<3; i++) {
//            modellist.add(0,nestedData);
//
//            Log.d("demo4", "asdd " + modellist);
//        }
//        return modellist;
//
//    }

}