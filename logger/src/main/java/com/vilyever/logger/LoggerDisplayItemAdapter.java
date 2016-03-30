package com.vilyever.logger;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * LoggerDisplayItemAdapter
 * AndroidLogger <com.vilyever.logger>
 * Created by vilyever on 2016/3/29.
 * Feature:
 */
public class LoggerDisplayItemAdapter extends RecyclerView.Adapter {
    final LoggerDisplayItemAdapter self = this;


    /* Constructors */
    
    
    /* Public Methods */
    
    
    /* Properties */
    
    
    /* Overrides */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LoggerDisplayItemViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LoggerDisplayItemViewHolder viewHolder = (LoggerDisplayItemViewHolder) holder;
        viewHolder.reload(LoggerModel.getLoggerModels().get(position));
    }

    @Override
    public int getItemCount() {
        return LoggerModel.getLoggerModels().size();
    }
     
     
    /* Delegates */
     
     
    /* Private Methods */
    
}