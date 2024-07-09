package com.example.telefonchi.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.example.telefonchi.ui.home.view.CityModel;
import com.example.telefonchi.ui.home.view.EditHameViewActivity;
import com.example.telefonchi.ui.home.view.HomeViewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeViewActivity extends AppCompatActivity {

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


        createDb();
        AddButton();


        nameTextView.setText(month);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    public List<CityModel> createDb () {
        db.collection("users")
                .document(docId)
                .collection(docId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ArrayList<String> arrayMapList = new ArrayList<>();
                                activityllist.add(new CityModel((String) document.get("name"), (String) document.get("sum"), document.getId()));
//                                Log.d("demo5", document.getId() + " => " + document.getData());
//                                Log.d("demo5", document.getId() + " => " + document.get("name"));

                            }
                        } else {
                            Log.d("demo5", "Error getting documents: ", task.getException());
                        }
                        refreshAdapter();
                    }
                });
        return activityllist;
    }



    public  void  refreshAdapter() {
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
                intent.putExtra("docId", docId);
                startActivity(intent);
            }
        });
    }

    public String docId2 () {
    String docId2 = docId;
    return docId2;
    }


}