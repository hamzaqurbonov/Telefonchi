package com.example.telefonchi.ui.home.view;

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
    private OnItemClickListner listner;

    public HomeViewAdapter(FirestoreRecyclerOptions<CityModel> optionsInt) {
        super(optionsInt);
        this.optionsInt = optionsInt;
    }

    @NonNull
    @Override
    public LongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_view_item,parent, false);
        return new LongHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull LongHolder holder, int position, @NonNull CityModel cityModel) {

        holder.TextViewName.setText(cityModel.getName());
        holder.TextViewSum.setText(cityModel.getSum());
//        holder.imgChildItem.setImage(noteModel.getImageUrl());

//        Log.d("demo22", "a" + cityModel.getName());
    }

//    public void  deleteItem(int position) {
//        getSnapshots().getSnapshot(position).getReference().delete();
//    }

    class LongHolder extends RecyclerView.ViewHolder {
//    View view;
    TextView TextViewName, TextViewSum;
    ImageView deleteSelect, editSelect;
        public LongHolder(View itemView) {
            super(itemView);
//            videoName = itemView.findViewById(R.id.first_name);
//            Url = itemView.findViewById(R.id.first_name);
//            imgChildItem = itemView.findViewById(R.id.img_child_item);
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



//public class HomeViewAdapter extends RecyclerView.Adapter< RecyclerView.ViewHolder>{
//
//private RecyclerViewClickListner listner;
//        HomeViewActivity homeViewActivity;
//        List<CityModel> activityllist ;
//
//
//public HomeViewAdapter(HomeViewActivity homeViewActivity, List<CityModel> activityllist, RecyclerViewClickListner listner) {
//        this.activityllist = activityllist;
//        this.homeViewActivity = homeViewActivity;
//        this.listner = listner;
//        }
//
//
//@NonNull
//@Override
//public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_view_item, parent, false);
//
//        return new HomeViewAdapter.HomeViewAdapterHolder(view);
//        }
//
//@Override
//public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//    CityModel member = activityllist.get(position);
//
//    TextView TextName = ((HomeViewAdapterHolder) holder).TextViewName;
//    TextName.setText(member.getName());
//
//    TextView TextSum = ((HomeViewAdapterHolder) holder).TextViewSum;
//    TextSum.setText(member.getSum());
//
//    ImageView delete = ((HomeViewAdapterHolder) holder).deleteSelect;
//    ImageView edit = ((HomeViewAdapterHolder) holder).editSelect;
//
//    edit.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//            Intent intent = new Intent(homeViewActivity, EditHameViewActivity.class);
//            intent.putExtra("nameEdit", member.getName());
//            intent.putExtra("sumEdit", member.getSum());
//            intent.putExtra("collegGetId", member.getId());
//            intent.putExtra("collection", homeViewActivity.docId2() + '/' + homeViewActivity.docId2());
//            intent.putExtra("add", "b");
//
//
//            homeViewActivity.startActivity(intent);
//
//            Log.d("demo8", member.getName() + " " + member.getSum() + " " + member.getId());
//            Toast.makeText(v.getContext(), "Qayta tahrirlandi", Toast.LENGTH_SHORT).show();
//            Refresh(homeViewActivity.createDb());
//        }
//    });
//
//
//    delete.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            FirebaseFirestore db = FirebaseFirestore.getInstance();
//            db.collection("users").document(homeViewActivity.docId2() + '/' + homeViewActivity.docId2() + '/' + member.getId())
//                    .delete()
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
////                            Log.d("demo6", "DocumentSnapshot successfully deleted!");
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
////                            Log.w("demo6", "Error deleting document", e);
//                        }
//                    });
////            Log.d("demo7", member.getName() + " " + member.getSum() + " " + member.getId() + homeViewActivity.docId2());
//            Toast.makeText(v.getContext(), "Matin o'chirildi", Toast.LENGTH_SHORT).show();
//            Refresh(homeViewActivity.createDb());
//        }
//    });
//
//
//}
//
//    void Refresh(List<CityModel>  events) {
//        activityllist.clear();
//        activityllist.addAll(events);
//        notifyDataSetChanged();
//    }
//
//@Override
//public int getItemCount() {
//        int dd =  homeViewActivity.activityllist.size();
//        return dd;
//        }
//
//
//
//public class HomeViewAdapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//    View view;
//    TextView TextViewName, TextViewSum;
//    ImageView deleteSelect, editSelect ;
//
//    public HomeViewAdapterHolder(View v) {
//        super(v);
//        view = v;
//        TextViewName = view.findViewById(R.id.text_view_name);
//        TextViewSum = view.findViewById(R.id.text_view_sum);
//        deleteSelect = view.findViewById(R.id.delete_select);
//        editSelect = view.findViewById(R.id.edit_select);
//        view.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//        listner.onClick(view, getAdapterPosition());
//    }
//
//}
//
//public interface RecyclerViewClickListner {
//    void onClick(View v, int position);
//}
//}