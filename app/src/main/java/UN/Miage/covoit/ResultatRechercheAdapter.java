package UN.Miage.covoit;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class ResultatRechercheAdapter extends RecyclerView.Adapter<ResultatRechercheAdapter.TrajetViewHolder> {

    private Context mCtx;
    private List<Trajet> trajetsList;
    private List<String> trajetsKeys;

    private String key;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private FirebaseUser currentUser;


    public ResultatRechercheAdapter(Context mCtx, List<Trajet> trajetList, List<String> trajetsKeys) {
        this.mCtx = mCtx;
        this.trajetsList = trajetList;
        this.trajetsKeys = trajetsKeys;
    }

    @NonNull
    @Override
    public TrajetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_resultat_trajets, parent, false);
        return new TrajetViewHolder(view);
    }

    public void chargerTrajet(String keyTrajet) {
        reference = FirebaseDatabase.getInstance().getReference("Trajets");
        reference.child(keyTrajet).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Trajet trajet = snapshot.getValue(Trajet.class);
                if (trajet != null){
                    Intent intent = new Intent(mCtx.getApplicationContext(), AfficherTrajetSeul.class);
                    intent.putExtra("depart", trajet.depart);
                    intent.putExtra("destination", trajet.destination);
                    intent.putExtra("date", trajet.date);
                    intent.putExtra("prix", trajet.prix);
                    intent.putExtra("key", keyTrajet);
                    mCtx.startActivity(intent);
                }
                else {
                    Toast.makeText(mCtx.getApplicationContext(), "inconnu",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onBindViewHolder(@NonNull TrajetViewHolder holder, int position) {
        Trajet trajet = trajetsList.get(position);
        String departDestination = trajet.getDepart().concat(" -> "+trajet.getDestination());
        holder.textViewTrajet.setText(departDestination);
        String datePrix = trajet.getDate().concat(" / "+trajet.getPrix());
        holder.textViewDatePrix.setText(datePrix);
        Integer placesDisponibles =  Integer.parseInt(trajet.getPassagersMax()) - Integer.parseInt(trajet.getPassagersReserves());
        holder.textViewPlacesDisponibles.setText(placesDisponibles.toString());
        if (placesDisponibles == 0)
            holder.afficherTrajet.setEnabled(false);
        holder.afficherTrajet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chargerTrajet(trajetsKeys.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return trajetsList.size();
    }

    class TrajetViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTrajet, textViewDatePrix, textViewPlacesDisponibles;
        Button afficherTrajet;

        public TrajetViewHolder(@NonNull View itemView) {
            super(itemView);
            afficherTrajet = itemView.findViewById(R.id.afficher_trajet);
            textViewTrajet = itemView.findViewById(R.id.text_view_trajet);
            textViewDatePrix = itemView.findViewById(R.id.text_view_dateEtPrix);
            textViewPlacesDisponibles = itemView.findViewById(R.id.text_view_places_disponibles);
            afficherTrajet = itemView.findViewById(R.id.afficher_trajet);
        }

    }
}
