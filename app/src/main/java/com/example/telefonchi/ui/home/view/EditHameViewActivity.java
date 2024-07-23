package com.example.telefonchi.ui.home.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telefonchi.R;
import com.example.telefonchi.ui.home.HomeViewActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EditHameViewActivity extends AppCompatActivity {
    EditHameViewActivityAdapter adapter;

    LocalDateTime DateObj = LocalDateTime.now();
    DateTimeFormatter FormatObj = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    String formattedDate = DateObj.format(FormatObj);


    Map<String,Object> nestedData = new HashMap<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText nameEditId, nickEditId, yearEditId, totalSumEditId, startSumEditId, finishSumEditId, amountMonthEditId, sumMonthEditId, telEditId, commentEditId, editPaymentId;
    Button addContactId;
    String docId, addTrue, collegGetId, collection;
    String nameEdit, nickEdit, yearEdit, totalSumEdit, startSumEdit, finishSumEdit, amountMonthEdit, sumMonthEdit, telEdit, commentEdit, paymentEdit;
    private RecyclerView recyclerView;
    public List<String> activityllist = new ArrayList<>();


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_hame_view);


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
        editPaymentId = findViewById(R.id.edit_payment_id);

        //button
        addContactId = findViewById(R.id.add_contact_id);

//        editPaymentId.setHint(Html.fromHtml("Qolgan summa <font color =\"#cc0029\" >0</font>"));
        editPaymentId.setTextColor(Color.RED);



        //HomeViewAdapter getEdit getIntent()
        Intent intentReceived = getIntent();
        Bundle extras = intentReceived.getExtras();
//        getIntent().getExtras().getString("paymentEdit");
        if (extras != null){
            //collection Document ID
            docId = getIntent().getExtras().getString("docId");
            addTrue = getIntent().getExtras().getString("add");
            collegGetId = getIntent().getExtras().getString("collegGetId");
            collection = getIntent().getExtras().getString("collection");


            nameEdit = extras.getString("nameEdit");
            nickEdit = extras.getString("nickEdit");
            yearEdit = extras.getString("yearEdit");
            totalSumEdit = String.valueOf(extras.getInt("totalSumEdit"));
            startSumEdit = String.valueOf(extras.getInt("startSumEdit"));
            finishSumEdit = String.valueOf(extras.getInt("finishSumEdit"));
            amountMonthEdit = String.valueOf(extras.getInt("amountMonthEdit"));
            sumMonthEdit = String.valueOf(extras.getInt("sumMonthEdit"));
            telEdit = String.valueOf(extras.getInt("telEdit"));
            commentEdit = extras.getString("commentEdit");
            paymentEdit = String.valueOf(extras.getInt("paymentEdit"));
        }


        //add Map text
        nameEditId.setText(nameEdit);
        nickEditId.setText(nickEdit);
        yearEditId.setText(yearEdit);
        totalSumEditId.setText(totalSumEdit);
        startSumEditId.setText(startSumEdit);
        finishSumEditId.setText(finishSumEdit);
        amountMonthEditId.setText(amountMonthEdit);
        if(Objects.equals(addTrue, "a")) {
            sumMonthEditId.setText(sumMonthEdit);
        } else if (Integer.parseInt(amountMonthEdit) == 0) {
            sumMonthEditId.setText(Integer.toString(Integer.parseInt("0")));
        } else if (Integer.parseInt(amountMonthEdit) > 0) {
            sumMonthEditId.setText(Integer.toString((Integer.parseInt(totalSumEdit) - Integer.parseInt(startSumEdit)) / Integer.parseInt(amountMonthEdit)));
        }
        telEditId.setText(telEdit);
        commentEditId.setText(commentEdit);
        editPaymentId.setText("0");

// android:enabled="false"
        if(Objects.equals(addTrue, "b")) {
            yearEditId.setEnabled(false);
            amountMonthEditId.setEnabled(false);
            totalSumEditId.setEnabled(false);
            startSumEditId.setEnabled(false);
        } else if (Objects.equals(addTrue, "a")) {
            editPaymentId.setEnabled(false);
        }

        addContactId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nestedData.put("name", nameEditId.getText().toString());
                nestedData.put("nick", nickEditId.getText().toString());
                nestedData.put("year", yearEditId.getText().toString());
                nestedData.put("totalSum", Integer.parseInt(totalSumEditId.getText().toString()));
                nestedData.put("startSum", Integer.parseInt(startSumEditId.getText().toString()));
                nestedData.put("finishSum", Integer.parseInt(totalSumEditId.getText().toString()) - Integer.parseInt(startSumEditId.getText().toString())  - Integer.parseInt(paymentEdit) - Integer.parseInt(editPaymentId.getText().toString()));
                nestedData.put("amountMonth", Integer.parseInt(amountMonthEditId.getText().toString()));
                nestedData.put("sumMonth", Integer.parseInt(sumMonthEditId.getText().toString()));
                nestedData.put("tel", Integer.parseInt(telEditId.getText().toString()));
                nestedData.put("comment", commentEditId.getText().toString());
                nestedData.put("addSum",   activityllist);
                nestedData.put("payment", Integer.parseInt(editPaymentId.getText().toString()));

//                Log.d("demo28", "2a " + addTrue);
                if(Integer.parseInt(finishSumEditId.getText().toString()) - Integer.parseInt(editPaymentId.getText().toString())  < 0) {
//                    editPaymentId.setEnabled(false);
                    Toast.makeText(v.getContext(),  "Сумма тўловдан ошиб кетаяпти!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Objects.equals(addTrue, "b")) {
                    Intent i = new Intent(EditHameViewActivity.this, HomeViewActivity.class);
                    db.collection("users").document(collection + "/" + collection + "/" + collegGetId)
                            .set(nestedData)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                }
                            });
                    DocumentReference Data = db.collection("users").document(collection + "/" + collection + "/" + collegGetId);
//                    nestedData.update("timestamp", FieldValue.serverTimestamp());
                    Data.update("addSum", FieldValue.arrayUnion(formattedDate + " йил " + editPaymentId.getText().toString() + " сўм"));
                    Data.update("payment", FieldValue.increment(Long.parseLong(paymentEdit)));
                    Refresh();
                    nestedData.clear();
                    startActivity(i);
                    finish();

                } else if (Objects.equals(addTrue, "a"))
                {
                    Intent i = new Intent(EditHameViewActivity.this, HomeViewActivity.class);
                    CollectionReference citiesRef = db.collection("users");
                    citiesRef.document(docId).collection(docId).add(nestedData);
                    startActivity(i);
                    finish();
                }

            }

        });

        Db();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


private void Db() {
          db.collection("users").document(collection + "/" + collection + "/" + collegGetId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {

                    activityllist = (ArrayList<String>) document.get("addSum");
                    Log.d("demo17", "Map  " + activityllist.toString());
                    refreshAdapter(activityllist);

                }
            }
        }

    });

}

    private void refreshAdapter(List<String> activityllist) {

        recyclerView = findViewById(R.id.recycler_edit_id);
        recyclerView.setLayoutManager(new GridLayoutManager(EditHameViewActivity.this, 1));

        adapter = new EditHameViewActivityAdapter(this, activityllist);
        recyclerView.setAdapter(adapter);
    }

    private void Refresh() {
        activityllist.clear();
        Db();
        adapter.notifyDataSetChanged();
        refreshAdapter(activityllist);
    }

    private void refreshAdapter2(List<String> activityllist) {

        recyclerView.getRecycledViewPool().clear();
        adapter.notifyDataSetChanged();


        recyclerView = findViewById(R.id.recycler_edit_id);
        recyclerView.setLayoutManager(new GridLayoutManager(EditHameViewActivity.this, 1));

        adapter = new EditHameViewActivityAdapter(this, activityllist);
        recyclerView.setAdapter(adapter);
    }



//    @Override
//    public void onStart() {
//        super.onStart();
//        recyclerViewInt.getRecycledViewPool().clear();
//        adapterInt.notifyDataSetChanged();
//        adapterInt.startListening();
////        if(adapter  != null) adapter.startListening();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
////        adapterInt.stopListening();
//        if(adapter  != null) adapter.stopListening();
//    }




//    private List<Map<String, Object>> prepareMemerList() {
//
//        List<Map<String, Object>> modellist = new ArrayList<>();
//        Map<String,Object> nestedData = new HashMap<>();
//        nestedData.put("sum", "sdsd");
//        nestedData.put("name", "sssss");
//
//        for (int i = 0; i<3; i++) {
//            modellist.add(0,nestedData);
//
//            Log.d("demo16", "asdd " + modellist);
//        }
//        return modellist;
//
//    }

}