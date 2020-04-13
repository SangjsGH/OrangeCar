package com.example.orangecar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orangecar.R;
import com.example.orangecar.mode.Selection;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecycleViewHolder>{

    private List<Selection> mlist;
    private Context context;
    private OnitemClick onitemClick;

    public  RecyclerAdapter( Context context,List<Selection> list){
        this.context=context;
        this.mlist=list;
    }
    //定义设置点击事件监听的方法
    public void setOnitemClickLintener (OnitemClick onitemClick) {
        this.onitemClick = onitemClick;
    }
    @NonNull
    @Override
    public RecyclerAdapter.RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_seletion,parent,false);
//        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT);
//        itemView.setLayoutParams(lp);

        return new RecycleViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.RecycleViewHolder holder, int position) {
        holder.text.setText(mlist.get(position).getSelection());
        if (onitemClick != null) {
            holder.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //在TextView的地方进行监听点击事件，并且实现接口
                    onitemClick.onItemClick(position);
                }
            });
        }
    }

    //定义一个点击事件的接口
    public interface OnitemClick {
        void onItemClick(int position);
    }
    @Override
    public int getItemCount() {

        return mlist.size();
    }

    class RecycleViewHolder extends RecyclerView.ViewHolder{
        private TextView text;

        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);
           text = itemView.findViewById(R.id.ctv_title);
        }
    }

}
