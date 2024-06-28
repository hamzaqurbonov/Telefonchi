package com.example.telefonchi.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telefonchi.R;
import com.example.telefonchi.ui.home.view.HomeViewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeViewActivity extends AppCompatActivity {
    private HomeViewAdapter.RecyclerViewClickListner listner;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String month, docId;
    TextView nameTextView;
    private RecyclerView recyclerView;
    public List<String> activityllist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_view);

        nameTextView = findViewById(R.id.textView);
        recyclerView = findViewById(R.id.recycler_home_view_ID);
        month = getIntent().getExtras().getString("month");
        docId = getIntent().getExtras().getString("docId");

        db.collection("users").document(docId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        ArrayList<String> arrayMapList = (ArrayList<String>) document.get("tag");
                        for (Object transaction: arrayMapList) {
                            Map values = (Map)transaction;
                            activityllist.add((String) values.get("name"));


                        }
                        Log.d("demo2", activityllist.toString());
//                        initViews();
//                        setOnClickListner();
                        refreshAdapter(activityllist);

                    }
                }
            }

        });
        nameTextView.setText(month);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }



    private void refreshAdapter(List<String> activityllist) {

//        recyclerView = findViewById(R.id.recycler_home_view_ID);
        recyclerView.setLayoutManager(new GridLayoutManager(HomeViewActivity.this, 3));

        HomeViewAdapter adapter = new HomeViewAdapter(this, activityllist, listner);
        recyclerView.setAdapter(adapter);
    }



}