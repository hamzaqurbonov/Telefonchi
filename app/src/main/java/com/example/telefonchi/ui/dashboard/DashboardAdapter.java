package com.example.telefonchi.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telefonchi.R;
import com.example.telefonchi.ui.home.HomeAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class DashboardAdapter extends FirestoreRecyclerAdapter<DashboardModel, DashboardAdapter.LongHolder> {
    private final FirestoreRecyclerOptions<DashboardModel> options;
    private HomeAdapter.OnItemClickListner listner;

    public DashboardAdapter(FirestoreRecyclerOptions<DashboardModel> options) {
        super(options);
        this.options = options;
    }

    @NonNull
    @Override
    public DashboardAdapter.LongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false);
        return new DashboardAdapter.LongHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull DashboardAdapter.LongHolder holder, int position, @NonNull DashboardModel noteModel) {

        holder.homeIdMonth.setText(noteModel.getName());

    }

//    public void  deleteItem(int position) {
//        getSnapshots().getSnapshot(position).getReference().delete();
//    }

    class LongHolder extends RecyclerView.ViewHolder {
        TextView homeIdMonth;
        ImageView imgChildItem;

        public LongHolder(View itemView) {
            super(itemView);
            homeIdMonth = itemView.findViewById(R.id.home_id_month);


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

    public void setItemClickListner(HomeAdapter.OnItemClickListner listner) {
        this.listner = listner;
    }
}