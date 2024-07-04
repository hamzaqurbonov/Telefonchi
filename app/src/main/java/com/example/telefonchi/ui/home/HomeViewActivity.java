package com.example.telefonchi.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import com.example.telefonchi.ui.home.view.CityModel;
import com.example.telefonchi.ui.home.view.EditHameViewActivity;
import com.example.telefonchi.ui.home.view.HomeViewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeViewActivity extends AppCompatActivity {
    ArrayList<String> nextArrayList = new ArrayList<String>();
    CityModel cityModel;
    private HomeViewAdapter.RecyclerViewClickListner listner;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String month, docId;
    TextView nameTextView;
    Button addButtonId;
    private RecyclerView recyclerView;
    public List<CityModel> activityllist = new ArrayList<>();
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


                        for (Object transaction : arrayMapList) {
                            Map values = (Map) transaction;
//                            activityllist.add((String) values.get("name"));
//                            activityllist.add((String) values.get("sum"));

                            activityllist.add(new CityModel((String) values.get("name") , (String) values.get("sum")));
                        }
                          Log.d("demo2", "Map  " + arrayMapList.toString());
//                        Log.d("demo2", "activityllist " + activityllist.toString());

//                        initViews();

                        refreshAdapter(activityllist);

                    }
                }
            }

        });

        AddButton();



        nameTextView.setText(month);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }



    private void refreshAdapter(List<CityModel> activityllist) {

//        recyclerView = findViewById(R.id.recycler_home_view_ID);
        recyclerView.setLayoutManager(new GridLayoutManager(HomeViewActivity.this, 1));

        HomeViewAdapter adapter = new HomeViewAdapter(this, activityllist, listner);
        recyclerView.setAdapter(adapter);
    }

    private void AddButton() {
        addButtonId = findViewById(R.id.add_button_id);
        addButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeViewActivity.this, EditHameViewActivity.class);
                startActivity(intent);
            }
        });
    }

}