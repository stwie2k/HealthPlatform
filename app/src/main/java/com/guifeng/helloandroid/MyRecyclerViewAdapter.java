package com.guifeng.helloandroid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class MyRecyclerViewAdapter<T> extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<T> data;
    private int layoutId;
    public OnItemClickListener _OnItemClickListener=null;

    public MyRecyclerViewAdapter(Context _context, int _layoutId, List _data)
    {
        context=_context;
        layoutId=_layoutId;
        data=_data;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    MyViewHolder holder = MyViewHolder.get(context, parent, layoutId);
        return holder;
}

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        convert(holder, data.get(position)); // convert函数需要重写，下面会讲
        if (_OnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _OnItemClickListener.onClick(holder.getAdapterPosition());
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    _OnItemClickListener.onLongClick(holder.getAdapterPosition());
                    return true;
                }
            });
        }
    }
    public abstract void convert(MyViewHolder holder, T t);

    public interface OnItemClickListener{
        void onClick(int position);
        boolean onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener _onItemClickListener) {
        this._OnItemClickListener = _onItemClickListener;
    }
    @Override
    public int getItemCount(){
        return data.size();
    }
    public void removeItem(int position){
        data.remove(position);
        notifyItemRemoved(position);
    }



}
