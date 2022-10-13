package com.example.myapplication.Trip;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    private List<Trip> trips;

    public interface IClickItemTrip{
        void updateTrip(Trip trip);

    }

    private IClickItemTrip iClickItemTrip;

    public TripAdapter(IClickItemTrip iClickItemTrip) {
        this.iClickItemTrip = iClickItemTrip;
    }

    public void setData(List<Trip> list){
        this.trips = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trip, parent, false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        Trip trip = trips.get(position);
        if(trip == null){
            return;
        }

        holder.tvTripId.setText(String.valueOf(position + 1));
        holder.tvTripName.setText(trip.getName());
        holder.tvTripDate.setText(trip.getDate());
        holder.tvTripDestination.setText(trip.getDestination());
        if(trip.getRiskAssessment()){
            holder.tvTripRequireAssessment.setText("Require Assessment: Yes");
        }
        else
        {
            holder.tvTripRequireAssessment.setText("Require Assessment: No");
        }


    }

    @Override
    public int getItemCount() {
        if(trips != null){
            return trips.size();
        }
        return 0;
    }

    public class TripViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTripId;
        private TextView tvTripName;
        private TextView tvTripDate;
        private TextView tvTripDestination;
        private TextView tvTripRequireAssessment;


        public TripViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Trip trip = trips.get(getAdapterPosition());
                    iClickItemTrip.updateTrip(trips.get(getAdapterPosition()));
                }
            });

            tvTripId = itemView.findViewById(R.id.tv_tripId);
            tvTripName = itemView.findViewById(R.id.tv_tripName);
            tvTripDate = itemView.findViewById(R.id.tv_tripDate);
            tvTripDestination = itemView.findViewById(R.id.tv_tripDestination);
            tvTripRequireAssessment = itemView.findViewById(R.id.tv_tripRequireAssessment);
        }
    }
}
