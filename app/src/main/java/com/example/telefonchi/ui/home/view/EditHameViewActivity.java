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
import java.util.Objects;

public class EditHameViewActivity extends AppCompatActivity {

    Map<String,Object> nestedData = new HashMap<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText editNameId, editSumId;
    private Button addContactId;
    String stringName, stringSum, docId, addTrue, collegGetId, collection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_hame_view);

        docId = getIntent().getExtras().getString("docId");
        addTrue = getIntent().getExtras().getString("add");
        collegGetId = getIntent().getExtras().getString("collegGetId");
        collection = getIntent().getExtras().getString("collection");

        Log.d("demo22", "collection " + collection);


        editNameId = findViewById(R.id.edit_name_id);
        editSumId = findViewById(R.id.edit_sum_id);
        addContactId = findViewById(R.id.add_contact_id);

        stringName = getIntent().getStringExtra("nameEdit");
        stringSum = getIntent().getStringExtra("sumEdit");

        editNameId.setText(stringName);
        editSumId.setText(stringSum);


        addContactId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Map<String, Object> city = new HashMap<>();
//                city.put("month", "Yanvar");

                nestedData.put("sum", editSumId.getText().toString());
                nestedData.put("name", editNameId.getText().toString());

                Log.d("demo8",  addTrue);

                if(Objects.equals(addTrue, "a")) {
                    CollectionReference citiesRef = db.collection("users");
                    citiesRef.document(docId).collection(docId).add(nestedData);
                    Log.d("demo8", "true");
                    Intent i = new Intent(EditHameViewActivity.this, HomeViewActivity.class);
                    startActivity(i);

                } else if (Objects.equals(addTrue, "b"))
                {

                    db.collection("users").document(collection + "/" + collection +"/" + collegGetId)
                            .set(nestedData)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
//                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
//                                Log.w(TAG, "Error writing document", e);
                                }
                            });

                    Log.d("demo8", "fasle" + " " + addTrue);
                    Intent i = new Intent(EditHameViewActivity.this, HomeViewActivity.class);
                    startActivity(i);
                }
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