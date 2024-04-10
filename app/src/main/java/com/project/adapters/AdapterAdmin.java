package com.project.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.R;
import com.project.views.RecyclerViewInterface;
import com.project.viewmodel.Ticket;

import java.util.List;

public class AdapterAdmin extends RecyclerView.Adapter<AdapterAdmin.ViewHolderAdmin>{
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;

    public List<Ticket> getListOfTickets() {
        return listOfTickets;
    }

    public void setListOfTickets(List<Ticket> listOfTickets) {
        this.listOfTickets = listOfTickets;
    }

    List<Ticket> listOfTickets;
    public AdapterAdmin(Context context, List<Ticket> listOfTickets,  RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.listOfTickets = listOfTickets;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public AdapterAdmin.ViewHolderAdmin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ticket_recycler_admin, parent, false);

        return new AdapterAdmin.ViewHolderAdmin(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAdmin.ViewHolderAdmin holder, int position) {
        holder.ticket_depart.setText(listOfTickets.get(position).getTicket_depart());
        holder.ticket_arrival.setText(listOfTickets.get(position).getTicket_arrival());
        holder.ticket_date_arrival.setText(listOfTickets.get(position).getTicket_date_arrival().replaceAll("\\s.*", ""));
        holder.ticket_date_depart.setText(listOfTickets.get(position).getTicket_date_depart().replaceAll("\\s.*", ""));
        holder.checkBox.setChecked(listOfTickets.get(position).getSelected());
        holder.flightNo.setText(listOfTickets.get(position).getFlightNumber());
       // holder.company.setText(listOfTickets.get(position).getTicket_company());
        //holder.price.setText(listOfTickets.get(position).getTicket_price());


    }

    @Override
    public int getItemCount() {
        return listOfTickets.size();
    }

    public class ViewHolderAdmin extends RecyclerView.ViewHolder{

        TextView ticket_depart, ticket_arrival, ticket_date_depart, ticket_date_arrival, flightNo;
        CheckBox checkBox;
        public void setCheckBox(Boolean b) {
            this.checkBox.setChecked(b);
        }

        public ViewHolderAdmin(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            ticket_depart = itemView.findViewById(R.id.ticket_depart_admin);
            ticket_arrival = itemView.findViewById(R.id.ticket_arrival_admin);
            ticket_date_depart = itemView.findViewById(R.id.ticketAdminFlightID);
            ticket_date_arrival = itemView.findViewById(R.id.ticket_date_arrive_admin);
            checkBox = itemView.findViewById(R.id.isSelected);
            flightNo = itemView.findViewById(R.id.ticketAdminFlightID);

            //company = itemView.findViewById(R.id.ticket_company);
            // price = itemView.findViewById(R.id.ticket_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });

        }
    }
}
