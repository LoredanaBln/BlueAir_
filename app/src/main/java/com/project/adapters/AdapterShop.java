package com.project.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.R;
import com.project.views.RecyclerViewInterface;
import com.project.viewmodel.Ticket;

import java.util.List;

public class AdapterShop extends RecyclerView.Adapter<AdapterShop.ViewHolderShop> {

    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    List<Ticket> listOfTickets;
    public AdapterShop(Context context, List<Ticket> listOfTickets,  RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.listOfTickets = listOfTickets;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public AdapterShop.ViewHolderShop onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ticket_recycler, parent, false);

        return new AdapterShop.ViewHolderShop(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterShop.ViewHolderShop holder, int position) {
        holder.ticket_depart.setText(listOfTickets.get(position).getTicket_depart());
        holder.ticket_arrival.setText(listOfTickets.get(position).getTicket_arrival());
        holder.ticket_date_arrival.setText(listOfTickets.get(position).getTicket_date_arrival().replaceAll("\\s.*", ""));
        holder.ticket_date_depart.setText(listOfTickets.get(position).getTicket_date_depart().replaceAll("\\s.*", ""));
        holder.company.setText(listOfTickets.get(position).getTicket_company());
        holder.price.setText(listOfTickets.get(position).getTicket_price());

    }

    @Override
    public int getItemCount() {
        return listOfTickets.size();
    }

    public static class ViewHolderShop extends RecyclerView.ViewHolder{

        TextView ticket_depart, ticket_arrival, ticket_date_depart, ticket_date_arrival, company, price;
        public ViewHolderShop(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            ticket_depart = itemView.findViewById(R.id.ticket_depart_admin);
            ticket_arrival = itemView.findViewById(R.id.ticket_arrival_admin);
            ticket_date_depart =itemView.findViewById(R.id.ticketAdminFlightID);
            ticket_date_arrival = itemView.findViewById(R.id.ticket_date_arrive_admin);
            company = itemView.findViewById(R.id.ticket_company);
            price = itemView.findViewById(R.id.ticket_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
