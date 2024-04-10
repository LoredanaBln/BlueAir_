package com.project.views;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.R;

public class ViewHolder extends RecyclerView.ViewHolder{
    public TextView ticket_depart, ticket_arrival, ticket_date_depart, ticket_date_arrival, ticket_duration;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        ticket_depart = itemView.findViewById(R.id.ticket_depart_admin);
        ticket_arrival = itemView.findViewById(R.id.ticket_arrival_admin);
        ticket_date_depart = itemView.findViewById(R.id.ticketAdminFlightID);
        ticket_date_arrival = itemView.findViewById(R.id.ticket_date_arrive_admin);
        ticket_duration = itemView.findViewById(R.id.ticket_duration);
    }
}
