package com.example.telefonchi.ui.home.view;

import android.content.Context;
import android.content.Intent;
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



public class HomeViewAdapter extends FirestoreRecyclerAdapter<CityModel, HomeViewAdapter.LongHolder> {
    private final FirestoreRecyclerOptions<CityModel> optionsInt;
    List<String> activityllist;
    private OnItemClickListner listner;

    public HomeViewAdapter(FirestoreRecyclerOptions<CityModel> optionsInt, List<String> activityllist) {
        super(optionsInt);
        this.optionsInt = optionsInt;
        this.activityllist = activityllist;
    }

    @NonNull
    @Override
    public LongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_view_item,parent, false);
        return new LongHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull LongHolder holder, int position, @NonNull CityModel cityModel) {

        String firebaseDocId = getSnapshots().getSnapshot(position).getId();


        holder.TextViewName.setText(cityModel.getName());
        holder.TextViewSum.setText(cityModel.getSum());


        holder.editSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("demo22", "firebaseDocId " + activityllist.get(0));

//            Log.d("demo22", "firebaseDocId " + firebaseDocId);
                Log.d("demo22", cityModel.getName() + " " + cityModel.getSum());
//            Intent intent = new Intent(activity, EditHameViewActivity.class);
                Intent intent = new Intent(v.getContext(), EditHameViewActivity.class);
                intent.putExtra("nameEdit", cityModel.getName());
                intent.putExtra("sumEdit", cityModel.getSum());
                intent.putExtra("collegGetId", firebaseDocId);
                intent.putExtra("collection", (CharSequence) activityllist.get(0));
                intent.putExtra("add", "b");
                v.getContext().startActivity(intent);
            }
        });

        holder.deleteSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("users").document(activityllist.get(0) + '/' + activityllist.get(0) + '/' + firebaseDocId)
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
//            Refresh(homeViewActivity.createDb());
            }
        });
    }

//    void Refresh(List<CityModel>  events) {
//        activityllist.clear();
//        activityllist.addAll(events);
//        notifyDataSetChanged();
//    }

    class LongHolder extends RecyclerView.ViewHolder {

    TextView TextViewName, TextViewSum;
    ImageView deleteSelect, editSelect;
        public LongHolder(View itemView) {
            super(itemView);
        TextViewName = itemView.findViewById(R.id.text_view_name);
        TextViewSum = itemView.findViewById(R.id.text_view_sum);
        deleteSelect = itemView.findViewById(R.id.delete_select);
        editSelect = itemView.findViewById(R.id.edit_select);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listner != null) {
                        listner.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListner {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setItemClickListner(OnItemClickListner listner) {
        this.listner = listner;
    }
}