package com.example.telefonchi.ui.home;

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
import com.example.telefonchi.databinding.FragmentHomeBinding;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference hadRef = db.collection("users");
    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private HomeAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = root.findViewById(R.id.recycler_Id);

        setUpRecyclerView();
        return root;
    }


    private void setUpRecyclerView() {

        Query query = hadRef.orderBy("number", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<HomeModel> options = new FirestoreRecyclerOptions.Builder<HomeModel>().setQuery(query, HomeModel.class).build();


        adapter = new HomeAdapter(options);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2  ));

        recyclerView.setAdapter(adapter);


        adapter.setItemClickListner(new HomeAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                HomeModel noteMode = documentSnapshot.toObject(HomeModel.class);
                String dokumentId = documentSnapshot.getId();
                String pathlink = documentSnapshot.getReference().getPath();

                String getMonth = adapter.getItem(position).getMonth();
                String monthData = adapter.getItem(position).getColour();

                Intent intent = new Intent(getContext(), HomeViewActivity.class);
                intent.putExtra("month", getMonth);
                intent.putExtra("docId1", dokumentId);
                intent.putExtra("monthData", monthData);


//                if (dokumentId.equals("January")) intent.putExtra("monthData", "01");
//                if (dokumentId.equals("February")) intent.putExtra("monthData", "02");
//                if (dokumentId.equals("March")) intent.putExtra("monthData", "03");
//                if (dokumentId.equals("April")) intent.putExtra("monthData", "04");
//                if (dokumentId.equals("May")) intent.putExtra("monthData", "05");
//                if (dokumentId.equals("June")) intent.putExtra("monthData", "06");
//                if (dokumentId.equals("July")) intent.putExtra("monthData", "07");
//                if (dokumentId.equals("Avgust")) intent.putExtra("monthData", "08");
//                if (dokumentId.equals("September")) intent.putExtra("monthData", "09");
//                if (dokumentId.equals("October")) intent.putExtra("monthData", "10");
//                if (dokumentId.equals("November")) intent.putExtra("monthData", "11");
//                if (dokumentId.equals("December")) intent.putExtra("monthData", "12");
//                Log.d("demo40", "HomeFragment " + dokumentId );
//                intent.putExtra("pathlink", pathlink);
//                Log.d("demo35", "true " + pathlink);
                startActivity(intent);

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}