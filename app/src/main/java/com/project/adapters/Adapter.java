package com.project.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.R;
import com.project.viewmodel.Ticket;
import com.project.views.ViewHolder;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {

    private Context context;
    private List<Ticket> list;

    public Adapter(Context context, List<Ticket> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.ticket_recycler, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ticket_depart.setText(list.get(position).getTicket_depart());
        holder.ticket_arrival.setText(list.get(position).getTicket_arrival());
        holder.ticket_date_arrival.setText(list.get(position).getTicket_date_arrival());
        holder.ticket_date_depart.setText(list.get(position).getTicket_date_depart());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
