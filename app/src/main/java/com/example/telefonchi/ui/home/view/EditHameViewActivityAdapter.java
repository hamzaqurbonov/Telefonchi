package com.example.telefonchi.ui.home.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telefonchi.R;

import java.util.List;

public class EditHameViewActivityAdapter extends RecyclerView.Adapter< RecyclerView.ViewHolder>{

        private RecyclerViewClickListner listner;
        EditHameViewActivity homeViewActivity;
        List<String> activityllist ;


        public EditHameViewActivityAdapter(EditHameViewActivity homeViewActivity, List<String> activityllist) {
            this.activityllist = activityllist;
            this.homeViewActivity = homeViewActivity;
//            this.listner = listner;
        }


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false);

            return new EditHameViewActivityAdapter.HomeViewAdapterHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//            CityModel member = activityllist.get(position);

//            TextView TextName = ((HomeViewAdapterHolder) holder).TextViewName;
//            TextName.setText(member.getName());
//
//            TextView TextSum = ((HomeViewAdapterHolder) holder).TextViewSum;
//            TextSum.setText(member.getSum());

//        TextView Url= ((OneChildAdapterViewHolder) holder).TextViewName;
//        Url.setText(homeViewActivity.activityllist.get(position));
    ((HomeViewAdapterHolder) holder).TextViewName.setText(activityllist.get(position));
//    ((HomeViewAdapterHolder) holder).TextViewSum.setText(homeViewActivity.activityllist.get(position));




            Log.d("demo17", activityllist.get(0) );
        }

        @Override
        public int getItemCount() {
            int dd =  activityllist.size();
            return dd;
        }



        public class HomeViewAdapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            View view;
            TextView TextViewName, TextViewSum;

            public HomeViewAdapterHolder(View v) {
                super(v);
                view = v;

                TextViewName = view.findViewById(R.id.home_id_month);
//                TextViewSum = view.findViewById(R.id.text_view_sum);

//                view.setOnClickListener(this);
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
