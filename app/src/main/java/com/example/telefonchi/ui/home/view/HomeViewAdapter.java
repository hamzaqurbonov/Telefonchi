package com.example.telefonchi.ui.home.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telefonchi.R;
import com.example.telefonchi.ui.home.HomeAdapter;
import com.example.telefonchi.ui.home.HomeModel;
import com.example.telefonchi.ui.home.HomeViewActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

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

//        TextView Url= ((OneChildAdapterViewHolder) holder).TextViewName;
//        Url.setText(homeViewActivity.activityllist.get(position));
//    ((HomeViewAdapterHolder) holder).TextViewName.setText(homeViewActivity.activityllist.get(position));
//    ((HomeViewAdapterHolder) holder).TextViewSum.setText(homeViewActivity.activityllist.get(position));




    Log.d("demo2", member.getName() + " " + member.getSum() );
        }

@Override
public int getItemCount() {
        int dd =  homeViewActivity.activityllist.size();
        return dd;
        }



public class HomeViewAdapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    View view;
    TextView TextViewName, TextViewSum;

    public HomeViewAdapterHolder(View v) {
        super(v);
        view = v;

        TextViewName = view.findViewById(R.id.text_view_name);
        TextViewSum = view.findViewById(R.id.text_view_sum);
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