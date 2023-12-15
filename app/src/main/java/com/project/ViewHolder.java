package com.project;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder{
    public TextView ticket_depart, ticket_arrival, ticket_date_depart, ticket_date_arrival, ticket_duration;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        ticket_depart = itemView.findViewById(R.id.ticket_depart);
        ticket_arrival = itemView.findViewById(R.id.ticket_arrival);
        ticket_date_depart = itemView.findViewById(R.id.ticket_date_depart);
        ticket_date_arrival = itemView.findViewById(R.id.ticket_date_arrive);
        ticket_duration = itemView.findViewById(R.id.ticket_duration);
    }
}
