package com.example.telefonchi.ui.notifications;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telefonchi.R;
import com.example.telefonchi.ui.dashboard.DashboardAdapter;
import com.example.telefonchi.ui.dashboard.DashboardModel;
import com.example.telefonchi.ui.home.HomeViewActivity;
import com.example.telefonchi.ui.home.view.EditHameViewActivity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>  {
//    private RecyclerViewClickListner listner;
    LocalDateTime DateObj = LocalDateTime.now();
    DateTimeFormatter Format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    String DateMMDD = DateObj.format(Format);
    NotificationsFragment notificationsFragment;
    private List<NotificationModel> мodalArrayList;
//    private List<String> activityllist;


    public NotificationAdapter (List<NotificationModel> мodalArrayList) {
        this.мodalArrayList = мodalArrayList;
//        this.activityllist = activityllist;

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

//                intent.putExtra("collection",  (CharSequence) activityllist.get(position));
                intent.putExtra("pathlink", modal.getPathlink());
//                intent.putExtra("collection", (CharSequence) activityllist.get(0));
                intent.putExtra("add", "d");


//                Log.d("demo37", "collection "  + (CharSequence) activityllist.get(position));


                v.getContext(). startActivity(intent);
            }
        });


        holder.send_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,
                        "Asslomu alaykum " + modal.getName() + "!\n" +
                        modal.getYear() + " кun xolatiga jami qarzdorlik " + modal.getTotalSum() + "so'm" +
                        "\nBoshlangich to'lov " + modal.getStartSum() + "so'm" +
                        "\nKeyingi to'lovlaringiz " + modal.getPayment() + "so'm" +
                        "\nHozirda " + DateMMDD + " kunga qolgan summa " + modal.getFinishSum() + "so'm" +
                        "\nOyma-oy to'lovingiz " + modal.getSumMonth() + "so'm"
                );
                intent.setType("text/plain");
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

    public class ViewHolder extends RecyclerView.ViewHolder  {
        private TextView text_view_name, text_view_year, text_view_totalSum, text_view_startSum, text_view_payment, text_view_finishSum, text_view_sumMonth, text_view_tel;
        ImageView send_select;
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
            send_select = itemView.findViewById(R.id.send_select);

//            itemView.setOnClickListener(this);

        }
//        @Override
//        public void onClick(View v) {
//            listner.onClick(itemView, getAdapterPosition());
//        }

    }

//    public interface RecyclerViewClickListner {
//        void onClick(View v, int position);
//    }

}