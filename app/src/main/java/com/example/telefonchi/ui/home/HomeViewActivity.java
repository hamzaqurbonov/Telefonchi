package com.example.telefonchi.ui.home;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.MenuItemCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telefonchi.R;
import com.example.telefonchi.ui.home.view.CityModel;
import com.example.telefonchi.ui.home.view.EditHameViewActivity;
import com.example.telefonchi.ui.home.view.HomeViewAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class HomeViewActivity extends AppCompatActivity {
    CityModel cityModel;
    LocalDateTime DateObj = LocalDateTime.now();
    DateTimeFormatter FormatObj = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    String formattedDate = DateObj.format(FormatObj);

    private HomeViewAdapter adapterInt;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String month, docId1, pathlink, monthData;
    TextView nameTextView, textViewAmountId, textViewTotolId;
    Button addButtonId;
    private RecyclerView recyclerViewInt;
    public List<String> activityllist ;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_view);

        toolbar = findViewById( R.id.toolbar_search);
        AppCompatActivity activity = (AppCompatActivity) HomeViewActivity.this;
        activity.setSupportActionBar( toolbar );
        activity.getSupportActionBar().setTitle("");


        nameTextView = findViewById(R.id.textView_id);
        textViewAmountId = findViewById(R.id.textViewAmount_id);
        textViewTotolId = findViewById(R.id.textViewTotol_id);

        addButtonId = findViewById(R.id.add_button_id);
        recyclerViewInt = findViewById(R.id.recycler_home_view_ID);

        month = getIntent().getExtras().getString("month");
        docId1 = getIntent().getExtras().getString("docId1");
        pathlink = getIntent().getExtras().getString("pathlink");
        monthData = getIntent().getExtras().getString("monthData");




        DateTimeFormatter FormatObj = DateTimeFormatter.ofPattern("MM");
        String monthFormat = DateObj.format(FormatObj);
//        Log.d("demo40", "FormatObj " + monthFormat + " " + monthData);
        if(monthData.equals(monthFormat)) {
            Log.d("demo40", "if " + monthFormat + " " + monthData);
            addButtonId.setEnabled(true);
        }  else {
            Log.d("demo40", "else " + monthFormat + " " + monthData);
            addButtonId.setEnabled(false);
        }

        activityllist = Collections.singletonList(getIntent().getExtras().getString("docId1"));

        createDb();
        AddButton();
        totolFilter();
//        refreshAdapter();

        nameTextView.setText(month);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    private void totolFilter() {
        ArrayList<String> size = new ArrayList<>();
        db.collection("users" + "/" + docId1 + "/" + docId1)
                .whereEqualTo("finishSum", 0)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {


                            for (QueryDocumentSnapshot document : task.getResult()) {

                                size.add(document.getId());
                                Log.d("demo30", "true " + document.getId() + " => " + document.getData());
                            }

                            Log.d("demo30", "true size" + size.size());
                            textViewAmountId.setText(" Yopilgan " + size.size());

                        } else {
                            Log.d("demo1", "Error getting documents: ", task.getException());
                        }
                    }
                });

        db.collection("users" + "/" + docId1 + "/" + docId1)
                .whereNotEqualTo("finishSum", 0)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<String> totolSize = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                totolSize.add(document.getId());
                                Log.d("demo30", "total " + document.getId() + " => " + document.getData());
                            }

                            Log.d("demo30", "true size" + totolSize.size());

                            textViewTotolId.setText(" Qolgan " +totolSize.size());
                        } else {
                            Log.d("demo1", "Error getting documents: ", task.getException());
                        }
                    }
                });


    }



    private void createDb() {

        Query query = db.collection("users")
                .document(docId1)
                .collection(docId1).orderBy("name", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<CityModel> optionsInt = new FirestoreRecyclerOptions.Builder<CityModel>()
                .setQuery(query, CityModel.class).build();
//        Log.d("demo22", docId);

        adapterInt = new HomeViewAdapter(optionsInt , activityllist);
//        recyclerViewInt.setHasFixedSize(true);
        recyclerViewInt.setLayoutManager(new GridLayoutManager(this,1  ));
        recyclerViewInt.setAdapter(adapterInt);
    }

    private void AddButton() {

        addButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeViewActivity.this, EditHameViewActivity.class);
                Bundle data1 = new Bundle();

                data1.putString("docId2", docId1);
                data1.putString("add", "a");
                data1.putString("yearEdit", formattedDate);

                data1.putString("pathlink", pathlink + "/" + docId1);
                intent.putExtras(data1);
//                addButtonId.setEnabled(false); cityModel
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);

        MenuItem item = menu.findItem(R.id.menu_search_id);

        SearchView searchView = (SearchView)item.getActionView();
        searchView.setIconified(true);
        searchView.setQueryHint("Qidiruv");

        SearchManager searchManager = (SearchManager) HomeViewActivity.this.getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(HomeViewActivity.this.getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
//                processSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processSearch(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void processSearch(String s) {
        Query query = db.collection("users")
                .document(docId1)
                .collection(docId1).orderBy("name", Query.Direction.DESCENDING)
                .whereGreaterThanOrEqualTo("name", s)
                .whereLessThanOrEqualTo("name", s + "\uf8ff");

        FirestoreRecyclerOptions<CityModel> optionsInt = new FirestoreRecyclerOptions.Builder<CityModel>()
                .setQuery(query, CityModel.class).build();

        Log.d("demo22", s);

        adapterInt = new HomeViewAdapter(optionsInt, activityllist);
        if(adapterInt  != null) adapterInt.startListening();
//        if(adapterInt  != null) adapterInt.stopListening();
        recyclerViewInt.setAdapter(adapterInt);

//        if(optionsInt == null) {
//            adapterInt = new HomeViewAdapter(optionsInt);
//            recyclerViewInt.setAdapter(adapterInt);
//        } else {
//            adapterInt.updateOptions(optionsInt);
//        }
    }





    @Override
    public void onStart() {
        super.onStart();
//        recyclerViewInt.getRecycledViewPool().clear();
//        adapterInt.notifyDataSetChanged();
//        adapterInt.startListening();
        if(adapterInt  != null) adapterInt.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
//        adapterInt.stopListening();
        if(adapterInt  != null) adapterInt.stopListening();
    }
}