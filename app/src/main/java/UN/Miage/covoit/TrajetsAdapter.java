package UN.Miage.covoit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class TrajetsAdapter extends RecyclerView.Adapter<TrajetsAdapter.TrajetViewHolder> {

    private Context mCtx;
    private List<Trajet> trajetList;

    public TrajetsAdapter(Context mCtx, List<Trajet> trajetList) {
        this.mCtx = mCtx;
        this.trajetList = trajetList;
    }

    @NonNull
    @Override
    public TrajetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_mes_trajets, parent, false);
        return new TrajetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrajetViewHolder holder, int position) {
        Trajet trajet = trajetList.get(position);
        holder.textViewArrivee.setText(trajet.getDestination());
        holder.textViewDate.setText(trajet.getDate());
        holder.textViewPrix.setText(trajet.getPrix());
        holder.textViewDepart.setText(trajet.getDepart());
    }

    @Override
    public int getItemCount() {
        return trajetList.size();
    }

    class TrajetViewHolder extends RecyclerView.ViewHolder {

        TextView textViewDepart, textViewArrivee, textViewDate, textViewPrix;

        public TrajetViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDepart = itemView.findViewById(R.id.text_view_depart);
            textViewArrivee = itemView.findViewById(R.id.text_view_arrivee);
            textViewDate = itemView.findViewById(R.id.text_view_date);
            textViewPrix = itemView.findViewById(R.id.text_view_prix);

        }
    }
}
