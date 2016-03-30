package com.vilyever.logger;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * LoggerDisplayItemViewHolder
 * AndroidLogger <com.vilyever.logger>
 * Created by vilyever on 2016/3/29.
 * Feature:
 */
public class LoggerDisplayItemViewHolder extends RecyclerView.ViewHolder {
    final LoggerDisplayItemViewHolder self = this;


    /* Constructors */
    public LoggerDisplayItemViewHolder(ViewGroup parent) {
        super(new LinearLayout(parent.getContext()));

        LinearLayout rootLayout = (LinearLayout) this.itemView;
        rootLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        rootLayout.addView(getMessageLabel());
    }
    
    /* Public Methods */
    public void reload(LoggerModel model) {
        switch (model.getLogLevel()) {
            case Info:
                this.itemView.setBackgroundColor(LoggerDisplayConfigure.InfoBackgroundColor);
                getMessageLabel().setTextColor(LoggerDisplayConfigure.InfoTextColor);
                break;
            case Warn:
                this.itemView.setBackgroundColor(LoggerDisplayConfigure.WarnBackgroundColor);
                getMessageLabel().setTextColor(LoggerDisplayConfigure.WarnTextColor);
                break;
            case Error:
                this.itemView.setBackgroundColor(LoggerDisplayConfigure.ErrorBackgroundColor);
                getMessageLabel().setTextColor(LoggerDisplayConfigure.ErrorTextColor);
                break;
            case Assert:
                this.itemView.setBackgroundColor(LoggerDisplayConfigure.AssertBackgroundColor);
                getMessageLabel().setTextColor(LoggerDisplayConfigure.AssertTextColor);
                break;
            case Debug:
                default:
                this.itemView.setBackgroundColor(LoggerDisplayConfigure.DebugBackgroundColor);
                getMessageLabel().setTextColor(LoggerDisplayConfigure.DebugTextColor);
                break;
        }

        getMessageLabel().setText(model.getLogcatMessage());
    }

    /* Properties */
    private TextView messageLabel;
    protected TextView getMessageLabel() {
        if (this.messageLabel == null) {
            this.messageLabel = new TextView(this.itemView.getContext());
            this.messageLabel.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            this.messageLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            this.messageLabel.setTypeface(Typeface.DEFAULT_BOLD);
        }
        return this.messageLabel;
    }
    
    /* Overrides */
     
     
    /* Delegates */
     
     
    /* Private Methods */
    
}