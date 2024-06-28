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

        Query query = hadRef.orderBy("month", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<HomeModel> options = new FirestoreRecyclerOptions.Builder<HomeModel>().setQuery(query, HomeModel.class).build();


        adapter = new HomeAdapter(options);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3  ));

        recyclerView.setAdapter(adapter);


        adapter.setItemClickListner(new HomeAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                HomeModel noteMode = documentSnapshot.toObject(HomeModel.class);
                String dokumentId = documentSnapshot.getId();
                String path = documentSnapshot.getReference().getPath();

                String getMonth = adapter.getItem(position).getMonth();

                Intent intent = new Intent(getContext(), HomeViewActivity.class);
                intent.putExtra("month", getMonth);
                intent.putExtra("docId", dokumentId);
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