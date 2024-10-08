package com.example.telefonchi.ui.home.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeViewAdapter extends FirestoreRecyclerAdapter<CityModel, HomeViewAdapter.LongHolder> {
    private final FirestoreRecyclerOptions<CityModel> optionsInt;
    Map<String,Object> putDelete = new HashMap<>();
    List<String> activityllist;
    int lll;
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

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull LongHolder holder, int position, @NonNull CityModel cityModel) {

        String firebaseDocId = getSnapshots().getSnapshot(position).getId();

        holder.TextName.setText(cityModel.getName());
        holder.TextnNick.setText(cityModel.getNick());
        holder.TextYear.setText(cityModel.getYear());
        holder.TextTotalSum.setText(Integer.toString(cityModel.getTotalSum()));
        holder.TextStartSum.setText(Integer.toString(cityModel.getStartSum()));
        holder.TextFinshSum.setText(Integer.toString(cityModel.getFinishSum()));
        holder.TextAmountSum.setText(Integer.toString(cityModel.getAmountMonth()));
        holder.TextSumMonth.setText(Integer.toString(cityModel.getSumMonth()));
        holder.TextTel.setText(Integer.toString(cityModel.getTel()));
        holder.TextComment.setText(cityModel.getComment());
        holder.TextPaymentId.setText(Integer.toString(cityModel.getPayment()));
//        holder.TextListId.setText(String.valueOf(cityModel.getRegions()));

        if(cityModel.getFinishSum() == 0) {
//            holder.TextFinshSum.setTextColor(Color.RED);
            holder.relativeLayout6.setBackgroundColor(Color.rgb(100, 187, 100));

//            for (int i = 0; i==cityModel.getFinishSum(); i++) {
//                lll = 1;
//          }
//            Log.d("demo10", String.valueOf(lll));
        }



//        Log.d("demo16", cityModel.getRegions().get(1));
        holder.editSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("demo22", "firebaseDocId " + activityllist.get(0));

//            Log.d("demo22", "firebaseDocId " + firebaseDocId);
//                Log.d("demo22", cityModel.getName() + " " + cityModel.getTotalSum());
//            Intent intent = new Intent(activity, EditHameViewActivity.class);
                Intent intent = new Intent(v.getContext(), EditHameViewActivity.class);
                Bundle data1 = new Bundle();
                intent.putExtra("nameEdit", cityModel.getName());
                intent.putExtra("nickEdit", cityModel.getNick());
                intent.putExtra("yearEdit", cityModel.getYear());
                intent.putExtra("totalSumEdit", cityModel.getTotalSum());
                intent.putExtra("startSumEdit", cityModel.getStartSum());
                intent.putExtra("finishSumEdit", cityModel.getFinishSum());
                intent.putExtra("amountMonthEdit", cityModel.getAmountMonth());
                intent.putExtra("sumMonthEdit", cityModel.getSumMonth());
                intent.putExtra("telEdit", cityModel.getTel());
                intent.putExtra("commentEdit", cityModel.getComment());
                intent.putExtra("paymentEdit", cityModel.getPayment());


                intent.putExtra("collegGetId", firebaseDocId);
                intent.putExtra("collection", (CharSequence) activityllist.get(0));
                intent.putExtra("add", "b");

                intent.putExtra("pathlinkCollection",  "users/" + (CharSequence) activityllist.get(0) + "/" + (CharSequence) activityllist.get(0) + "/" + firebaseDocId);

                Log.d("demo36", "HomeAdapter " + (CharSequence) activityllist.get(0));

                v.getContext().startActivity(intent);
            }
        });

        holder.deleteSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Matinni o'chirish");
                builder.setMessage("Matinni o'chirishni istaysizmi?");
                builder.setPositiveButton("Ha", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        FirebaseFirestore db = FirebaseFirestore.getInstance();
          //           Delete faylni saqlab qolish
                        putDelete.put("name", cityModel.getName());
                        putDelete.put("nick", cityModel.getNick());
                        putDelete.put("year", cityModel.getYear());
                        putDelete.put("totalSum", cityModel.getTotalSum());
                        putDelete.put("startSum", cityModel.getStartSum());
                        putDelete.put("finishSum", cityModel.getFinishSum());
                        putDelete.put("amountMonth", cityModel.getAmountMonth());
                        putDelete.put("sumMonth", cityModel.getSumMonth());
                        putDelete.put("tel", cityModel.getTel());
                        putDelete.put("comment", cityModel.getComment());
                        putDelete.put("addSum",   cityModel.getAddSum());
                        putDelete.put("payment", cityModel.getPayment());

                        db.collection("delete").add(putDelete);


          //              Delete fiuksiyasini qo'shish mumkun
                        db.collection("users").document(activityllist.get(0) + '/' + activityllist.get(0) + '/' + firebaseDocId)
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                    }
                                });
                        Toast.makeText(v.getContext(),  "Matin o'chirildi!", Toast.LENGTH_SHORT).show();
                        //Refresh Activity
//                        Intent intent = new Intent(v.getContext(), v.getContext().getClass());
//                        v.getContext(). startActivity(intent);

//                finish();
                    }
                });
                builder.setNegativeButton("Yo'q", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.create().show();
            }
        });
    }



    class LongHolder extends RecyclerView.ViewHolder {
    RelativeLayout  relativeLayout6;
    TextView TextName, TextnNick, TextYear, TextTotalSum, TextStartSum, TextFinshSum, TextAmountSum, TextSumMonth, TextTel, TextComment, TextListId, TextPaymentId;
    ImageView deleteSelect, editSelect;
        public LongHolder(View itemView) {
            super(itemView);
            TextName = itemView.findViewById(R.id.text_view_name);
            TextnNick = itemView.findViewById(R.id.text_view_nick);
            TextYear = itemView.findViewById(R.id.text_view_year);
            TextTotalSum = itemView.findViewById(R.id.text_view_totalSum);
            TextStartSum = itemView.findViewById(R.id.text_view_startSum);
            TextFinshSum = itemView.findViewById(R.id.text_view_finishSum);
            TextAmountSum = itemView.findViewById(R.id.text_view_amountMonth);
            TextSumMonth = itemView.findViewById(R.id.text_view_sumMonth);
            TextTel = itemView.findViewById(R.id.text_view_tel);
            TextComment = itemView.findViewById(R.id.text_comment);
//            TextListId = itemView.findViewById(R.id.text_list_id);
            TextPaymentId = itemView.findViewById(R.id.text_payment_id);
            relativeLayout6 = itemView.findViewById(R.id.relativeLayout6);

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