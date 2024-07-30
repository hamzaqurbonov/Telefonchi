package com.example.telefonchi.ui.notifications;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telefonchi.R;
import com.example.telefonchi.ui.dashboard.DashboardAdapter;
import com.example.telefonchi.ui.dashboard.DashboardModel;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>  {


    private List<NotificationModel> мodalArrayList;


    public NotificationAdapter (List<NotificationModel> мodalArrayList) {
        this.мodalArrayList = мodalArrayList;

    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_item, parent, false);
        return new NotificationAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        NotificationModel modal = мodalArrayList.get(position);
        holder.text_view_name.setText(modal.getName());
        holder.text_view_year.setText(modal.getYear());
        holder.text_view_totalSum.setText(String.valueOf(modal.getTotalSum()));
        holder.text_view_startSum.setText(String.valueOf(modal.getStartSum()));
        holder.text_view_payment.setText(String.valueOf(modal.getPayment()));
        holder.text_view_finishSum.setText(String.valueOf(modal.getFinishSum()));
        holder.text_view_sumMonth.setText(String.valueOf(modal.getSumMonth()));
        holder.text_view_tel.setText(String.valueOf(modal.getTel()));

//        holder.idTebel.setText(Integer.toString(modal.getId()));



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
        private TextView text_view_name, text_view_year, text_view_totalSum, text_view_startSum, text_view_payment, text_view_finishSum, text_view_sumMonth, text_view_tel;
        ImageView deleteSelect;


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
        }
    }

}