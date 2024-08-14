package com.example.telefonchi.ui.home;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telefonchi.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class HomeAdapter extends FirestoreRecyclerAdapter<HomeModel, HomeAdapter.LongHolder> {
    private final FirestoreRecyclerOptions<HomeModel> options;
    private OnItemClickListner listner;

    public HomeAdapter(FirestoreRecyclerOptions<HomeModel> options) {
        super(options);
        this.options = options;
    }

    @NonNull
    @Override
    public LongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item,parent, false);
        return new LongHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull LongHolder holder, int position, @NonNull HomeModel noteModel) {

        LocalDateTime DateObj = LocalDateTime.now();
        DateTimeFormatter FormatObj = DateTimeFormatter.ofPattern("MM");
        String formattedDate = DateObj.format(FormatObj);



        holder.homeIdMonth.setText(noteModel.getMonth());
        if(Objects.equals(noteModel.getColour(), formattedDate)) {
            Log.d("demo40", noteModel.getColour() + " " + formattedDate);
//            holder.TextFinshSum.setTextColor(Color.RED);
            holder.linnerColour.setBackgroundColor(Color.rgb(110, 210, 15));

//            for (int i = 0; i==cityModel.getFinishSum(); i++) {
//                lll = 1;
//          }
//            Log.d("demo10", String.valueOf(lll));
        }

    }

//    public void  deleteItem(int position) {
//        getSnapshots().getSnapshot(position).getReference().delete();
//    }

    class LongHolder extends RecyclerView.ViewHolder {
        TextView homeIdMonth;
        ImageView imgChildItem;

        LinearLayout linnerColour;

        public LongHolder(View itemView) {
            super(itemView);
            homeIdMonth = itemView.findViewById(R.id.home_id_month);
            linnerColour = itemView.findViewById(R.id.linner_colour);


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