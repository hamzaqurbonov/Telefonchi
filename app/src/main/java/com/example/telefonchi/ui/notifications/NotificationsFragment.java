package com.example.telefonchi.ui.notifications;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

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
import com.example.telefonchi.databinding.FragmentNotificationsBinding;
import com.example.telefonchi.ui.dashboard.DashboardAdapter;
import com.example.telefonchi.ui.dashboard.DashboardModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NotificationsFragment extends Fragment {

//    private NotificationAdapter.RecyclerViewClickListner listner;
    private SwipeRefreshLayout swipeRefreshLayout;
    LocalDateTime DateObj = LocalDateTime.now();
    DateTimeFormatter Format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    DateTimeFormatter FormatObj = DateTimeFormatter.ofPattern("dd");
    String DateMMDD = DateObj.format(Format);
    String formattedDate = DateObj.format(FormatObj);
    public List<String> activityllist = new ArrayList<>();

    public List<NotificationModel> activityList = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
//    private CollectionReference hadRef = db.collection("users/Yanvar/Yanvar");

//    FirestoreRecyclerOptions<DashboardModel> options;
    private FragmentDashboardBinding binding;
    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private DashboardAdapter adapter2;
    MenuItem menuItem;
    SearchView searchView;
    Toolbar toolbar;
    TextView textData;

//    private FragmentNotificationsBinding binding;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =    inflater.inflate(R.layout.fragment_notifications, container, false);

        recyclerView = view.findViewById(R.id.recycler_notification_Id);
        textData = view.findViewById(R.id.text_data_id);
        textData.setText(DateMMDD + " yil");

        swipeRefreshLayout = view. findViewById(R.id.swipeRefreshLayout);
//        toolbar = view.findViewById( R.id.toolbar_search_notification);
//
//        AppCompatActivity activity = (AppCompatActivity) getActivity();
//        activity.setSupportActionBar(toolbar );
//        activity.getSupportActionBar().setTitle("");

        Log.d("demo32", "Error : " + formattedDate);

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

    public void Db() {
        db.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d("demo28", "Error : " + e.getMessage());
                }
                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        Log.d("demo28", "users doc "  + doc.getDocument().getId());
//                        activityList.add(doc.getDocument().getId());

                        doc.getDocument().getReference().collection(doc.getDocument().getId()).whereNotEqualTo("finishSum", 0)
                                .whereGreaterThanOrEqualTo("year", formattedDate)
                                .whereLessThanOrEqualTo("year",  formattedDate + "\uf8ff")
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
                                                    activityList.add(new NotificationModel(
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
                                                    Log.d("demo37", "collection2 "  + doc.getDocument().getReference().getPath() + " " +  "doc.getDocument().getData()");
//                                        Log.d("demo28", "collection3 "  +  doc.getDocument().getData().get("name"));

//                                            adapter = new NotificationAdapter(activityList);
//                                            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
//                                            recyclerView.setAdapter(adapter);
//                                            setOnClickListner();
                                                    recycler();

                                                }
                                            }
                                        }

                                    }
                                });
                            }
                        }

                 }});
    }

    public void recycler() {
        adapter = new NotificationAdapter(activityList);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recyclerView.setAdapter(adapter);
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

//    private void setOnClickListner() {
//
//        listner = new NotificationAdapter.RecyclerViewClickListner() {
//            @Override
//            public void onClick(View v, int position) {
////                Intent intent = new Intent();
////                cueVideoInitYouTubePlayer();
//
////                Toast.makeText(LongChildOne.this, "ID " + activityllist.get(position), Toast.LENGTH_SHORT).show();
//
////                Log.d("demo33", activityList.get(position));
////                intent.putExtra( "part",activityllist.get(position));
////                    Toast.makeText(this, "ID " + notificationModel.getName(), Toast.LENGTH_SHORT).show();
////                intent.putExtra("dokumentId", dokumentIdModel);
////                intent.putExtra("getName",  getName);
////                startActivity(intent);
////                finish();
//            }
//
//        };
//
//    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        setHasOptionsMenu(true);
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
////        getMenuInflater().inflate(R.menu.menu_search,menu);
//
//
//        inflater.inflate(R.menu.menu_search,menu);
//        menuItem = menu.findItem(R.id.menu_search_id);
//        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
//        searchView.setIconified(true);
//        searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
//        searchView.setQueryHint("Qidiruv");
////        searchView.setQuery("25", false);
//
//
//
////        inflater.inflate(R.menu.menu_search,menu);
////
////        MenuItem item = menu.findItem(R.id.menu_search_id);
////
////        SearchView searchView = (SearchView)item.getActionView();
//////        searchView.setIconified(true);
////        searchView.setQueryHint("Qidiruv");
//
//        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
////                processSearch(s);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                processSearch(s);
//                return false;
//            }
//        });
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    void processSearch(String s) {
//        activityList.clear();
//        db.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
//                if (e != null) {
//                    Log.d("demo28", "Error : " + e.getMessage());
//                }
//                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
//                    if (doc.getType() == DocumentChange.Type.ADDED) {
//                        Log.d("demo28", "users doc "  + doc.getDocument().getId());
//
//
//                        doc.getDocument().getReference().collection(doc.getDocument().getId()).whereNotEqualTo("finishSum", 0)
//                                .whereGreaterThanOrEqualTo("year", s)
//                                .whereLessThanOrEqualTo("year", s + "\uf8ff")
//                                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                                    @Override
//                                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
//                                        if (e != null) {
//                                            Log.d("demo28",  "collection1 "+ "Error : " + e.getMessage());
//                                        } else {
//
//                                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
//                                                if (doc.getType() == DocumentChange.Type.ADDED) {
////
//                                                    Map<String, Object> values = doc.getDocument().getData();
//////
//                                                    activityList.add(new NotificationModel(
//                                                            (String) values.get("name"),
//                                                            (String) values.get("nick"),
//                                                            (String) values.get("comment"),
//                                                            (String) values.get("year"),
//                                                            Integer.parseInt(values.get("totalSum").toString()),
//                                                            Integer.parseInt(values.get("startSum").toString()),
//                                                            Integer.parseInt(values.get("finishSum").toString()),
//                                                            Integer.parseInt(values.get("amountMonth").toString()),
//                                                            Integer.parseInt(values.get("sumMonth").toString()),
//                                                            Integer.parseInt(values.get("tel").toString()),
//                                                            Integer.parseInt(values.get("payment").toString())
//                                                    ));
//
////                                        DashboardModel dashboardModel = doc.getDocument().toObject(DashboardModel.class);
////                                        Log.d("demo28", "collection2 "  + doc.getDocument().getId() + " " +  doc.getDocument().getData());
////                                            Log.d("demo31", "collection3 " + doc.getDocument().getData().get("name"));
//
////                                            Log.d("demo31", "collection4 " + activityList);
//                                                    adapter = new NotificationAdapter(activityList);
//                                                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
//                                                    recyclerView.setAdapter(adapter);
//                                                }
//
//                                            }
//                                        }
//
//                                    }
//                                });
//                    }
//
//                }
//
//            }});
//    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}