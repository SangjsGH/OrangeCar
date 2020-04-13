package com.example.orangecar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by xionghao on 15/10/30.
 */
public abstract class MyRecyAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final boolean on;
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> datalist;
    protected final int mItemLayoutId;
    protected int headerlayoutid;
    public static final int RECYLERITEMTYPE = 0;
    public static final int RECYLERHEADTYPE = 1;

    private MyViewHolder.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(MyViewHolder.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MyRecyAdapter(Context context, List<T> datalist, int layoutid) {
        this(context, datalist, layoutid, 0);
    }

    public MyRecyAdapter(Context context, List<T> datalist, int layoutid, int headerlayoutid) {
        this(context, datalist, layoutid, headerlayoutid, false);
    }

    public MyRecyAdapter(Context context, List<T> datalist, int layoutid, int headerlayoutid, boolean isneedM) {
        this.mContext = context;
        this.datalist = datalist;
        this.mItemLayoutId = layoutid;
        this.headerlayoutid = headerlayoutid;
        this.mInflater = LayoutInflater.from(context);
        this.on = isneedM;
    }

   /* public void refreshAdapter(List<T> mDataList){

        notifyDataSetChanged();
    }*/

    @Override
    public int getItemViewType(int position) {
        if (headerlayoutid != 0 && position == 0) {
            return RECYLERHEADTYPE;
        } else {
            return RECYLERITEMTYPE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder viewHolder = null;
        if (headerlayoutid != 0 && viewType == RECYLERHEADTYPE) {
            View view = mInflater.inflate(headerlayoutid, parent, false);
            viewHolder = setComViewHolder(view, viewType);

        } else {
            View view = mInflater.inflate(mItemLayoutId, parent, false);
            viewHolder = setComViewHolder(view, viewType);
            viewHolder.onItemClickListener(onItemClickListener);
        }

        return viewHolder;

    }

    protected abstract MyViewHolder setComViewHolder(View view, int viewType);


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (headerlayoutid != 0) {
            if (position == 0) {
                onBindHeader(holder);
            } else {
                onBindItem(holder, position - 1, datalist.get(position - 1));
            }
        } else {
            onBindItem(holder, position, datalist.get(position));
        }
    }

    public void onBindHeader(RecyclerView.ViewHolder holder) {
    }

    ;

    public void onBindItem(RecyclerView.ViewHolder holder, int position, T item) {
    }

    ;

    @Override
    public int getItemCount() {
        if (headerlayoutid != 0) {
            return (datalist == null ? 0 : datalist.size()) + 1;
        } else {
            return datalist == null ? 0 : datalist.size();
        }
    }

}
