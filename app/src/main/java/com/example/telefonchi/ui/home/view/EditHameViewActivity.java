package com.example.telefonchi.ui.home.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EditHameViewActivity extends AppCompatActivity {

    Map<String,Object> nestedData = new HashMap<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText nameEditId, nickEditId, yearEditId, totalSumEditId, startSumEditId, finishSumEditId, amountMonthEditId, sumMonthEditId, telEditId, commentEditId;
    Button addContactId;
    String docId, addTrue, collegGetId, collection;
    String nameEdit, nickEdit, yearEdit, totalSumEdit, startSumEdit, finishSumEdit, amountMonthEdit, sumMonthEdit, telEdit, commentEdit;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_hame_view);

        List<Map<String, Object>> modellist = prepareMemerList();


        //Edit R.id
        nameEditId = findViewById(R.id.edit_name_id);
        nickEditId = findViewById(R.id.edit_nick_id);
        yearEditId = findViewById(R.id.edit_year_id);
        totalSumEditId = findViewById(R.id.edit_totalSum_id);
        startSumEditId = findViewById(R.id.edit_startSum_id);
        finishSumEditId = findViewById(R.id.edit_finishSum_id);
        amountMonthEditId = findViewById(R.id.edit_amountMonth_id);
        sumMonthEditId = findViewById(R.id.edit_sumMonth_id);
        telEditId = findViewById(R.id.edit_tel_id);
        commentEditId = findViewById(R.id.edit_comment_id);
        //button
        addContactId = findViewById(R.id.add_contact_id);

        //collection Document ID
        docId = getIntent().getExtras().getString("docId");
        addTrue = getIntent().getExtras().getString("add");
        collegGetId = getIntent().getExtras().getString("collegGetId");
        collection = getIntent().getExtras().getString("collection");

        //HomeViewAdapter getEdit getIntent()
        Intent intentReceived = getIntent();
        Bundle extras = intentReceived.getExtras();

        if (extras != null){
            Log.d("demo15", "0 ");
            nameEdit = extras.getString("nameEdit");
            nickEdit = extras.getString("nickEdit");
            yearEdit = String.valueOf(extras.getInt("yearEdit"));
            totalSumEdit = String.valueOf(extras.getInt("totalSumEdit"));
            startSumEdit = String.valueOf(extras.getInt("startSumEdit"));
            finishSumEdit = String.valueOf(extras.getInt("finishSumEdit"));
            amountMonthEdit = String.valueOf(extras.getInt("amountMonthEdit"));
            sumMonthEdit = String.valueOf(extras.getInt("sumMonthEdit"));
            telEdit = String.valueOf(extras.getInt("telEdit"));
            commentEdit = extras.getString("commentEdit");
        }else {
            Log.d("demo15", "-1 ");
            nameEdit = "";
            nickEdit = "";
            yearEdit = "0";
            totalSumEdit = "0";
            startSumEdit = "0";
            finishSumEdit = "0";
            amountMonthEdit = "0";
            sumMonthEdit = "0";
            telEdit = "0";
            commentEdit = "";
        }


        //add Map text
        nameEditId.setText(nameEdit);
        nickEditId.setText(nickEdit);
        yearEditId.setText(yearEdit);
        totalSumEditId.setText(totalSumEdit);
        startSumEditId.setText(startSumEdit);
        finishSumEditId.setText(Integer.toString(Integer.parseInt(totalSumEdit)  - Integer.parseInt(startSumEdit)));
        amountMonthEditId.setText(amountMonthEdit);
        if(Objects.equals(addTrue, "a")) {
            sumMonthEditId.setText(sumMonthEdit);
            Log.d("demo15", "1 " + amountMonthEdit);
        } else if (Integer.parseInt(amountMonthEdit) == 0) {
            sumMonthEditId.setText(Integer.toString(Integer.parseInt("0")));
            Log.d("demo15", "2 " + amountMonthEdit);
        } else if (Integer.parseInt(amountMonthEdit) > 0) {
            sumMonthEditId.setText(Integer.toString(Integer.parseInt(finishSumEdit)  / Integer.parseInt(amountMonthEdit)));
            Log.d("demo15", "3 " + amountMonthEdit);
        }
        telEditId.setText(telEdit);
        commentEditId.setText(commentEdit);

        addContactId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nestedData.put("name", nameEditId.getText().toString());
                nestedData.put("nick", nickEditId.getText().toString());
                nestedData.put("year", Integer.parseInt(yearEditId.getText().toString()));
                nestedData.put("totalSum", Integer.parseInt(totalSumEditId.getText().toString()));
                nestedData.put("startSum", Integer.parseInt(startSumEditId.getText().toString()));
                nestedData.put("finishSum", Integer.parseInt(finishSumEditId.getText().toString()));
                nestedData.put("amountMonth", Integer.parseInt(amountMonthEditId.getText().toString()));
                nestedData.put("sumMonth", Integer.parseInt(sumMonthEditId.getText().toString()));
                nestedData.put("tel", Integer.parseInt(telEditId.getText().toString()));
                nestedData.put("comment", commentEditId.getText().toString());
                nestedData.put("regions",  FieldValue.arrayUnion("greater_virginia"));


//                Log.d("demo8",  addTrue);

                if(Objects.equals(addTrue, "b")) {
                    db.collection("users").document(collection + "/" + collection + "/" + collegGetId)
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


//                    DocumentReference arrayUnion = db.collection("users").document(collection + "/" + collection + "/" + collegGetId);
//                    arrayUnion.update("regions", FieldValue.serverTimestamp());

                    Log.d("demo8", "fasle" + " " + addTrue);
                    Intent i = new Intent(EditHameViewActivity.this, HomeViewActivity.class);
                    startActivity(i);
//                    nestedData.clear();

                } else if (Objects.equals(addTrue, "a"))
                {
                    CollectionReference citiesRef = db.collection("users");
                    citiesRef.document(docId).collection(docId).add(nestedData);
                    Log.d("demo8", "true");
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



    private List<Map<String, Object>> prepareMemerList() {

        List<Map<String, Object>> modellist = new ArrayList<>();
        Map<String,Object> nestedData = new HashMap<>();
        nestedData.put("sum", "sdsd");
        nestedData.put("name", "sssss");

        for (int i = 0; i<3; i++) {
            modellist.add(0,nestedData);

            Log.d("demo16", "asdd " + modellist);
        }
        return modellist;

    }

}