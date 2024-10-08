package com.example.telefonchi.ui.dashboard;

import static java.util.Collections.*;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.telefonchi.R;
import com.example.telefonchi.databinding.FragmentDashboardBinding;
import com.example.telefonchi.databinding.FragmentHomeBinding;
import com.example.telefonchi.ui.home.HomeAdapter;
import com.example.telefonchi.ui.home.HomeModel;
import com.example.telefonchi.ui.home.HomeViewActivity;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Flow;

public class DashboardFragment extends Fragment {
    DashboardModel dashboardModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    public List<DashboardModel> activityList = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference hadRef = db.collection("users/Yanvar/Yanvar");

    FirestoreRecyclerOptions<DashboardModel> options;
    private FragmentDashboardBinding binding;
    private RecyclerView recyclerView;
    private DashboardAdapter adapter;
    private DashboardAdapter adapter2;
    MenuItem menuItem;
    SearchView searchView;
    Toolbar toolbar;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//       binding = FragmentDashboardBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
        View view =    inflater.inflate(R.layout.fragment_dashboard, container, false);

        swipeRefreshLayout = view. findViewById(R.id.swipeRefreshLayout);

        recyclerView = view.findViewById(R.id.recycler_dashboard_Id);

        toolbar = view.findViewById( R.id.toolbar_search_dash);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar );
        activity.getSupportActionBar().setTitle("");



        Db();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                activityList.clear();
                Db();

            }
        });


        return view;
    }


    public void Db () {
        db.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d("demo28", "Error : " + e.getMessage());
                }
                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        Log.d("demo28", "users doc "  + doc.getDocument().getId());


                        doc.getDocument().getReference().collection(doc.getDocument().getId()).whereNotEqualTo("finishSum", 0).addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                if (e != null) {
                                    Log.d("demo28",  "collection1 "+ "Error : " + e.getMessage());
                                }

                                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                    if (doc.getType() == DocumentChange.Type.ADDED) {
//
                                        Map<String, Object> values = doc.getDocument().getData();
////
                                        activityList.add(new DashboardModel(
                                                (String) values.get("name"),
                                                (String) values.get("nick"),
                                                (String) values.get("comment"),
                                                (String) values.get("year"),
                                                Integer.parseInt(values.get("totalSum").toString()),
                                                Integer.parseInt(values.get("startSum").toString()),
                                                Integer.parseInt(values.get("finishSum").toString()),
                                                Integer.parseInt(values.get("amountMonth").toString()),
                                                Integer.parseInt(values.get("sumMonth").toString()),
                                                Integer.parseInt(values.get("tel").toString()),
                                                Integer.parseInt(values.get("payment").toString()),
                                                doc.getDocument().getReference().getPath()
                                        ));

//                                        DashboardModel dashboardModel = doc.getDocument().toObject(DashboardModel.class);
//                                        Log.d("demo28", "collection2 "  + doc.getDocument().getId() + " " +  doc.getDocument().getData());
//                                        Log.d("demo28", "collection3 "  +  doc.getDocument().getData().get("name"));

                                        adapter = new DashboardAdapter(activityList);
                                        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1  ));
                                        recyclerView.setAdapter(adapter);
                                    }
                                }

                            }
                        });
                    }

                }

            }});
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu,@NonNull MenuInflater inflater) {
//        getMenuInflater().inflate(R.menu.menu_search,menu);


        inflater.inflate(R.menu.menu_search,menu);
        menuItem = menu.findItem(R.id.menu_search_id);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setIconified(true);
        searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
        searchView.setQueryHint("Qidiruv");


//        inflater.inflate(R.menu.menu_search,menu);
//
//        MenuItem item = menu.findItem(R.id.menu_search_id);
//
//        SearchView searchView = (SearchView)item.getActionView();
////        searchView.setIconified(true);
//        searchView.setQueryHint("Qidiruv");

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
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
        super.onCreateOptionsMenu(menu, inflater);
    }

    void processSearch(String s) {
        activityList.clear();
        db.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d("demo28", "Error : " + e.getMessage());
                }
                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        Log.d("demo28", "users doc "  + doc.getDocument().getId());


                        doc.getDocument().getReference().collection(doc.getDocument().getId()).whereNotEqualTo("finishSum", 0)
                                .whereGreaterThanOrEqualTo("year", s)
                                .whereLessThanOrEqualTo("year", s + "\uf8ff")
                                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                if (e != null) {
                                    Log.d("demo28",  "collection1 "+ "Error : " + e.getMessage());
                                } else {

                                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                        if (doc.getType() == DocumentChange.Type.ADDED) {
//
                                            Map<String, Object> values = doc.getDocument().getData();
////
                                            activityList.add(new DashboardModel(
                                                    (String) values.get("name"),
                                                    (String) values.get("nick"),
                                                    (String) values.get("comment"),
                                                    (String) values.get("year"),
                                                    Integer.parseInt(values.get("totalSum").toString()),
                                                    Integer.parseInt(values.get("startSum").toString()),
                                                    Integer.parseInt(values.get("finishSum").toString()),
                                                    Integer.parseInt(values.get("amountMonth").toString()),
                                                    Integer.parseInt(values.get("sumMonth").toString()),
                                                    Integer.parseInt(values.get("tel").toString()),
                                                    Integer.parseInt(values.get("payment").toString()),
                                                    doc.getDocument().getReference().getPath()
                                            ));

//                                        DashboardModel dashboardModel = doc.getDocument().toObject(DashboardModel.class);
//                                        Log.d("demo28", "collection2 "  + doc.getDocument().getId() + " " +  doc.getDocument().getData());
//                                            Log.d("demo31", "collection3 " + doc.getDocument().getData().get("name"));

//                                            Log.d("demo31", "collection4 " + activityList);
                                            adapter = new DashboardAdapter(activityList);
                                            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
                                            recyclerView.setAdapter(adapter);
                                        }

                                    }
                                }

                            }
                        });
                    }

                }

            }});
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}