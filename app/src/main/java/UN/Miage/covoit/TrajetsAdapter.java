package UN.Miage.covoit;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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


public class TrajetsAdapter extends RecyclerView.Adapter<TrajetsAdapter.TrajetViewHolder> {

    private Context mCtx;
    private List<Trajet> trajetList;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;



    public TrajetsAdapter(Context mCtx, List<Trajet> trajetList) {
        this.mCtx = mCtx;
        this.trajetList = trajetList;
    }

    @NonNull
    @Override
    public TrajetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_mes_trajets, parent, false);
        return new TrajetViewHolder(view);
    }

    public void afficherInfosUtilisateur(String idPassager){
        reference = FirebaseDatabase.getInstance().getReference("users");
        reference.child(idPassager).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Utilisateur user = snapshot.getValue(Utilisateur.class);
                if (user != null){
                    Intent intent = new Intent(mCtx.getApplicationContext(), InfosUtilisateur.class);
                    intent.putExtra("nom", user.nom);
                    intent.putExtra("prenom", user.prenom);
                    intent.putExtra("pseudo", user.pseudo);
                    intent.putExtra("age", user.age);
                    intent.putExtra("mail", user.email);
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

        Trajet trajet = trajetList.get(position);
        if (trajet.getConducteur() == currentUser.getUid()){
            holder.textViewConducteur.setText("Moi");
        }else{
            holder.textViewConducteur.setText("Cliquez ici pour consulter la fiche");
            holder.textViewConducteur.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    afficherInfosUtilisateur(trajet.getConducteur());
                }
            });
        }
        holder.textViewArrivee.setText(trajet.getDestination());
        holder.textViewDate.setText(trajet.getDate());
        holder.textViewPrix.setText(trajet.getPrix());
        holder.textViewDepart.setText(trajet.getDepart());
        if (trajet.passagers.length()!=0){
        String[] passagersRes = trajet.passagers.split(",");
        int itemCount = passagersRes.length;
        for(int i=0;i<itemCount;i++) {
            Button button1 = new Button(mCtx.getApplicationContext());
            if(passagersRes[i] == currentUser.getUid()){
                button1.setText("Moi");
            }else{
                button1.setText("Passager " + i);
            }
            button1.setId(i);
            button1.setWidth(100);
            button1.setHeight(100);
            holder.buttonContainer.addView(button1);
            String passager = passagersRes[i];
            holder.buttonContainer.findViewById(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    afficherInfosUtilisateur(passager);
                }
            });
        }
        }else{
            TextView pasDePassagers = new TextView(mCtx.getApplicationContext());
            pasDePassagers.setText("Pas de passagers");
            holder.buttonContainer.addView(pasDePassagers);
        }
    }

    @Override
    public int getItemCount() {
        return trajetList.size();
    }

    class TrajetViewHolder extends RecyclerView.ViewHolder {

        TextView textViewDepart, textViewArrivee, textViewDate, textViewPrix, textViewConducteur;
        LinearLayout buttonContainer;

        public TrajetViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewConducteur = itemView.findViewById(R.id.text_view_conducteur);
            textViewDepart = itemView.findViewById(R.id.text_view_depart);
            textViewArrivee = itemView.findViewById(R.id.text_view_arrivee);
            textViewDate = itemView.findViewById(R.id.text_view_date);
            textViewPrix = itemView.findViewById(R.id.text_view_prix);
            buttonContainer = itemView.findViewById(R.id.buttons_container);

        }
    }
}
