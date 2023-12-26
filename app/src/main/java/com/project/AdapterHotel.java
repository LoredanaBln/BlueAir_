package com.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterHotel extends RecyclerView.Adapter<AdapterHotel.ViewHolderShop> {

    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    private List<Hotel> listOfHotels;
    public AdapterHotel(Context context, List<Hotel> listOfHotels,  RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.listOfHotels = listOfHotels;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public AdapterHotel.ViewHolderShop onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.hotel_recycler, parent, false);

        return new AdapterHotel.ViewHolderShop(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHotel.ViewHolderShop holder, int position) {
        holder.hotelName.setText(listOfHotels.get(position).getHotelName());
        holder.hotelLocation.setText(listOfHotels.get(position).getHotelCountry() + ", " + listOfHotels.get(position).getHotelCity());
        holder.hotelNumber.setText(listOfHotels.get(position).getHotelContact());

    }

    @Override
    public int getItemCount() {
        return listOfHotels.size();
    }

    public static class ViewHolderShop extends RecyclerView.ViewHolder{

        TextView hotelName, hotelLocation, hotelNumber;
        public ViewHolderShop(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            hotelName = itemView.findViewById(R.id.hotelRecyclerName);
            hotelLocation = itemView.findViewById(R.id.hotelRecyclerLocation);
            hotelNumber = itemView.findViewById(R.id.hotelRecyclerContact);
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
