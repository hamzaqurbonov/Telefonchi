package com.example.telefonchi.ui.home.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telefonchi.R;
import com.example.telefonchi.ui.home.HomeAdapter;
import com.example.telefonchi.ui.home.HomeModel;
import com.example.telefonchi.ui.home.HomeViewActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class HomeViewAdapter extends RecyclerView.Adapter< RecyclerView.ViewHolder>{

private RecyclerViewClickListner listner;
        HomeViewActivity homeViewActivity;
        List<CityModel> activityllist ;


public HomeViewAdapter(HomeViewActivity homeViewActivity, List<CityModel> activityllist, RecyclerViewClickListner listner) {
        this.activityllist = activityllist;
        this.homeViewActivity = homeViewActivity;
        this.listner = listner;
        }


@NonNull
@Override
public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_view_item, parent, false);

        return new HomeViewAdapter.HomeViewAdapterHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    CityModel member = activityllist.get(position);

    TextView TextName = ((HomeViewAdapterHolder) holder).TextViewName;
    TextName.setText(member.getName());

    TextView TextSum = ((HomeViewAdapterHolder) holder).TextViewSum;
    TextSum.setText(member.getSum());

    ImageView delete = ((HomeViewAdapterHolder) holder).deleteSelect;


    delete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            db.collection("users").document(homeViewActivity.docId2() + '/' + homeViewActivity.docId2() + '/' + member.getId())
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
//                            Log.d("demo6", "DocumentSnapshot successfully deleted!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
//                            Log.w("demo6", "Error deleting document", e);
                        }
                    });

//            Log.d("demo7", member.getName() + " " + member.getSum() + " " + member.getId() + homeViewActivity.docId2());


            Toast.makeText(v.getContext(), "Matin o'chirildi", Toast.LENGTH_SHORT).show();
            Refresh(homeViewActivity.createDb());

        }
    });
}

    void Refresh(List<CityModel>  events) {
        activityllist.clear();
        activityllist.addAll(events);
        notifyDataSetChanged();
    }

@Override
public int getItemCount() {
        int dd =  homeViewActivity.activityllist.size();
        return dd;
        }



public class HomeViewAdapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    View view;
    TextView TextViewName, TextViewSum;
    ImageView deleteSelect;

    public HomeViewAdapterHolder(View v) {
        super(v);
        view = v;
        TextViewName = view.findViewById(R.id.text_view_name);
        TextViewSum = view.findViewById(R.id.text_view_sum);
        deleteSelect = view.findViewById(R.id.delete_select);
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        listner.onClick(view, getAdapterPosition());
    }

}

public interface RecyclerViewClickListner {
    void onClick(View v, int position);
}
}