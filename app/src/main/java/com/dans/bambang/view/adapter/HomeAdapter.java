package com.dans.bambang.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dans.bambang.R;
import com.dans.bambang.model.JobListResp;

import java.util.HashMap;
import java.util.List;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> implements View.OnClickListener{
    private List<JobListResp> itemList;
    private Context context;

    public HomeAdapter(List<JobListResp> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_joblist_home, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final HomeAdapter.ViewHolder holder, final int position) {
        final JobListResp object = itemList.get(position);
        if (object != null) {

            Glide.with(context).load(object.getCompanyUrl())
                    .error(context.getResources().getDrawable(R.drawable.logo_home))
                    .into(holder.logo);

            holder.title.setText(object.getTitle());
            holder.company.setText(object.getCompany());
            holder.location.setText(object.getLocation());
            holder.rlJob.setOnClickListener(this);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rlJob:
                break;
        }

    }

    public void addList(List<JobListResp> listLoad){
        for (JobListResp list : listLoad){
            itemList.add(list);
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView company;
        TextView location;
        ImageView logo;
        RelativeLayout rlJob;

        public ViewHolder(View itemView) {
            super(itemView);
            this.rlJob =  itemView.findViewById(R.id.rlJob);
            this.title =  itemView.findViewById(R.id.title);
            this.company =  itemView.findViewById(R.id.company);
            this.location =  itemView.findViewById(R.id.location);
            this.logo =  itemView.findViewById(R.id.logo);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
