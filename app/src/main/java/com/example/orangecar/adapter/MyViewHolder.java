package com.example.orangecar.adapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by xionghao on 15/10/30.
 */
public  abstract class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public MyViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        init(itemView);
    }
    public  void init(View view){

    }
    OnItemClickListener onItemClickListener;
    @Override
    public void onClick(View v) {
        if (onItemClickListener!=null){
            onItemClickListener.onItemClick(getLayoutPosition(),v);
        }
    }

    public void onItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface  OnItemClickListener{
        void onItemClick(int position, View view);
    }

}
