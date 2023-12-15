package com.project;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder{
    public TextView Flight, Date;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}
