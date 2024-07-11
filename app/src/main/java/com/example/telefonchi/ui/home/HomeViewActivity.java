package com.example.telefonchi.ui.home;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeViewActivity extends AppCompatActivity {
    private HomeViewAdapter adapterInt;

    private HomeViewAdapter listner;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String month, docId;
    TextView nameTextView;
    Button addButtonId;
    private RecyclerView recyclerViewInt;
    public List<CityModel> activityllist = new ArrayList<>();
    public List<CityModel> activityllistSearch = new ArrayList<>();

    MenuItem menuItem;
    SearchView searchView;
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

//        AppCompatActivity activity = (AppCompatActivity) HomeViewActivity.this;
//        setSupportActionBar(toolbar);
//        Objects.requireNonNull(getSupportActionBar()).setTitle("");


        nameTextView = findViewById(R.id.textView);
        recyclerViewInt = findViewById(R.id.recycler_home_view_ID);

        month = getIntent().getExtras().getString("month");
        docId = getIntent().getExtras().getString("docId");


        Query query = db.collection("users")
                .document(docId)
                .collection(docId).orderBy("name", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<CityModel> optionsInt = new FirestoreRecyclerOptions.Builder<CityModel>()
                .setQuery(query, CityModel.class).build();
//        Log.d("demo22", docId);

        adapterInt = new HomeViewAdapter(optionsInt);
//        recyclerViewInt.setHasFixedSize(true);
        recyclerViewInt.setLayoutManager(new GridLayoutManager(this,1  ));
        recyclerViewInt.setAdapter(adapterInt);


//        createDb();
        AddButton();
//        refreshAdapter();

        nameTextView.setText(month);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }



    private void AddButton() {
        addButtonId = findViewById(R.id.add_button_id);
        addButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeViewActivity.this, EditHameViewActivity.class);
                intent.putExtra("docId", docId);
                intent.putExtra("add", "a");
                startActivity(intent);
            }
        });
    }

    public String docId2 () {
    String docId2 = docId;
    return docId2;
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
                .document(docId)
                .collection(docId).orderBy("name", Query.Direction.DESCENDING)
                .whereGreaterThanOrEqualTo("name", s)
                .whereLessThanOrEqualTo("name", s + "\uf8ff");

        FirestoreRecyclerOptions<CityModel> optionsInt = new FirestoreRecyclerOptions.Builder<CityModel>()
                .setQuery(query, CityModel.class).build();

        Log.d("demo22", s);

        adapterInt = new HomeViewAdapter(optionsInt);
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