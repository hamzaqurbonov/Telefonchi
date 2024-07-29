package com.example.telefonchi.ui.dashboard;

import static java.util.Collections.*;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.concurrent.Flow;

public class DashboardFragment extends Fragment {
    DashboardModel dashboardModel;
    public List<DashboardModel> activityList = new ArrayList<>();
//    List<DashboardModel> asList = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference hadRef = db.collection("users/Yanvar/Yanvar");

    FirestoreRecyclerOptions<DashboardModel> options;
    private FragmentDashboardBinding binding;
    private RecyclerView recyclerView;
    private DashboardAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        recyclerView = root.findViewById(R.id.recycler_dashboard_Id);

//        db.collection("grup").document("8FDD...").collection("anggota")
//                .whereEqualTo("iduser", idUser)
//                .orderBy("updatetime", Query.Direction.DESCENDING)

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
                                                    Integer.parseInt(values.get("payment").toString())
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



//        List<DashboardModel>   мodalArrayList =
//        List<DashboardModel> мodalArrayList =  prepareMemerList();

        Log.d("demo28", "activityList "  + activityList);


//        setUpRecyclerView();
        return root;
    }


//    private void setUpRecyclerView() {

//        for (int i = 0; i<3; i++) {
//            options = new FirestoreRecyclerOptions
//                .Builder<DashboardModel>()
//                .setQuery(db.collection("users/Yanvar/Yanvar").whereNotEqualTo("finishSum", 0), DashboardModel.class)
//                .build();
//            Log.d("demo29", "dashboardModel ");
//        }

//        options = new FirestoreRecyclerOptions
//                .Builder<DashboardModel>()
//                .setQuery(db.collection("users/Yanvar/Yanvar").whereNotEqualTo("finishSum", 0), DashboardModel.class)
//                .build();


//        Query query = hadRef.whereNotEqualTo("finishSum", 0);

//        FirestoreRecyclerOptions<DashboardModel> options = new FirestoreRecyclerOptions.Builder<DashboardModel>().setQuery(query, DashboardModel.class).build();


//        adapter = new DashboardAdapter(мodalArrayList);
//
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1  ));
//        recyclerView.setAdapter(adapter);
//    }

    private List<DashboardModel> prepareMemerList() {

        List<DashboardModel> modellist = new ArrayList<>();
//        Map<String,Object> nestedData = new HashMap<>();
//        nestedData.put("sum", "sdsd");
//        nestedData.put("name", "sssss");

        for (int i = 0; i<3; i++) {
//            modellist.add(new DashboardModel("name " + i, "nick", 1, "comment", "year",2, 3,4, 5,6,7));

//            Log.d("demo16", "asdd " + modellist);
        }
        return modellist;

    }




//    @Override
//    public void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        adapter.stopListening();
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}