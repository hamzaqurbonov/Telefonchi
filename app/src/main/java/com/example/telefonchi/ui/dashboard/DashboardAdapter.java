package com.example.telefonchi.ui.dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.example.telefonchi.ui.home.view.EditHameViewActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class DashboardAdapter  extends RecyclerView.Adapter<DashboardAdapter .ViewHolder>  {


    private List<DashboardModel> мodalArrayList;


    public DashboardAdapter (List<DashboardModel> мodalArrayList) {
        this.мodalArrayList = мodalArrayList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DashboardModel modal = мodalArrayList.get(position);
        holder.text_view_name.setText(modal.getName());
        holder.text_view_year.setText(modal.getYear());
        holder.text_view_totalSum.setText(String.valueOf(modal.getTotalSum()));
        holder.text_view_startSum.setText(String.valueOf(modal.getStartSum()));
        holder.text_view_payment.setText(String.valueOf(modal.getPayment()));
        holder.text_view_finishSum.setText(String.valueOf(modal.getFinishSum()));
        holder.text_view_sumMonth.setText(String.valueOf(modal.getSumMonth()));
        holder.text_view_tel.setText(String.valueOf(modal.getTel()));

//        holder.idTebel.setText(Integer.toString(modal.getId()));
        holder.relativeLayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditHameViewActivity.class);

                intent.putExtra("nameEdit", modal.getName());
                intent.putExtra("nickEdit", modal.getNick());
                intent.putExtra("yearEdit", modal.getYear());
                intent.putExtra("totalSumEdit", modal.getTotalSum());
                intent.putExtra("startSumEdit", modal.getStartSum());
                intent.putExtra("finishSumEdit", modal.getFinishSum());
                intent.putExtra("amountMonthEdit", modal.getAmountMonth());
                intent.putExtra("sumMonthEdit", modal.getSumMonth());
                intent.putExtra("telEdit", modal.getTel());
                intent.putExtra("commentEdit", modal.getComment());
                intent.putExtra("paymentEdit", modal.getPayment());


                intent.putExtra("pathlink", modal.getPathlink());
//                intent.putExtra("collection", (CharSequence) activityllist.get(0));
                intent.putExtra("add", "b");



                v.getContext(). startActivity(intent);
            }
        });


    }

//    void Refresh(ArrayList<DashboardModel> events) {
//        мodalArrayList.clear();
//        мodalArrayList.addAll(events);
//        notifyDataSetChanged();
//    }

    @Override
    public int getItemCount() {
        return мodalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView  text_view_name, text_view_year, text_view_totalSum, text_view_startSum, text_view_payment, text_view_finishSum, text_view_sumMonth, text_view_tel;
        ImageView deleteSelect;
        RelativeLayout relativeLayoutId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//        idTebel = itemView.findViewById(R.id.id_tebel);
            text_view_name = itemView.findViewById(R.id.text_view_name);
            text_view_year = itemView.findViewById(R.id.text_view_year);
            text_view_totalSum = itemView.findViewById(R.id.text_view_totalSum);
            text_view_startSum = itemView.findViewById(R.id.text_view_startSum);
            text_view_payment = itemView.findViewById(R.id.text_view_payment);
            text_view_finishSum = itemView.findViewById(R.id.text_view_finishSum);
            text_view_sumMonth = itemView.findViewById(R.id.text_view_sumMonth);
            text_view_tel = itemView.findViewById(R.id.text_view_tel);

            relativeLayoutId = itemView.findViewById(R.id.relative_layout_id);
        }
    }

}











//public class DashboardAdapter extends FirestoreRecyclerAdapter<DashboardModel, DashboardAdapter.LongHolder> {
//    private final FirestoreRecyclerOptions<DashboardModel> options;
//    private HomeAdapter.OnItemClickListner listner;
//
//    public DashboardAdapter(FirestoreRecyclerOptions<DashboardModel> options) {
//        super(options);
//        this.options = options;
//    }
//
//    @NonNull
//    @Override
//    public DashboardAdapter.LongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false);
//        return new DashboardAdapter.LongHolder(v);
//    }
//
//    @Override
//    protected void onBindViewHolder(@NonNull DashboardAdapter.LongHolder holder, int position, @NonNull DashboardModel noteModel) {
//
//        holder.homeIdMonth.setText(noteModel.getName());
//
//    }
//
////    public void  deleteItem(int position) {
////        getSnapshots().getSnapshot(position).getReference().delete();
////    }
//
//    class LongHolder extends RecyclerView.ViewHolder {
//        TextView homeIdMonth;
//        ImageView imgChildItem;
//
//        public LongHolder(View itemView) {
//            super(itemView);
//            homeIdMonth = itemView.findViewById(R.id.home_id_month);
//
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getAdapterPosition();
//                    if (position != RecyclerView.NO_POSITION && listner != null) {
//                        listner.onItemClick(getSnapshots().getSnapshot(position), position);
//                    }
//                }
//            });
//        }
//    }
//
//    public interface OnItemClickListner {
//        void onItemClick(DocumentSnapshot documentSnapshot, int position);
//    }
//
//    public void setItemClickListner(HomeAdapter.OnItemClickListner listner) {
//        this.listner = listner;
//    }
//}