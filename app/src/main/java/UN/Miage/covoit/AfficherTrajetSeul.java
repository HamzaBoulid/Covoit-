package UN.Miage.covoit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AfficherTrajetSeul extends AppCompatActivity {

    TextView depart, arrivee, date, prix, conducteur;
    String keyTrajet;
    Button reserver;
    Button afficher_carte;
    FirebaseDatabase BD = FirebaseDatabase.getInstance();
    DatabaseReference table;
    FirebaseUser currentUser;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_afficher_trajet_seul);
        mAuth = FirebaseAuth.getInstance();
        depart = findViewById(R.id.text_view_depart);
        arrivee = findViewById(R.id.text_view_arrivee);
        date = findViewById(R.id.text_view_date);
        prix = findViewById(R.id.text_view_prix);
        reserver = findViewById(R.id.button_reserver_trajet);
        afficher_carte = findViewById(R.id.button_afficher_carte);
        currentUser = mAuth.getCurrentUser();
        afficherInfosTrajet();

        reserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            table = BD.getReference("Trajets");
            table.child(keyTrajet).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String passagers = snapshot.child("passagers").getValue(String.class);
                    String[] passagersRes = passagers.split(",");
                    int itemCount = passagersRes.length;
                    String passagersAJour;
                    if (itemCount != 0) {
                        passagersAJour = passagers.concat("," + currentUser.getUid() );
                        table.child(keyTrajet).child("passagers").setValue(passagersAJour);
                    }else{
                        table.child(keyTrajet).child("passagers").setValue(currentUser.getUid());
                    }
                    Intent mesResa = new Intent(AfficherTrajetSeul.this, MesReservations.class);
                    startActivity(mesResa);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
        //ignore
                }
            });
            }
        });

        afficher_carte.setOnClickListener(afficher_carteOnClick);




    }

    public View.OnClickListener afficher_carteOnClick = new View.OnClickListener() {

        @Override
        public void onClick(View activity_maps) {
            Intent afficherCarte = new Intent(AfficherTrajetSeul.this, MapsActivity.class);
            afficherCarte.putExtra("dapart", depart.toString());
            afficherCarte.putExtra("arrivée", arrivee.toString());
            startActivity(afficherCarte);
        }

    };



    private void afficherInfosTrajet(){
        Intent intent = getIntent();
        String departTrajet = intent.getStringExtra("depart");
        String arriveeTrajet = intent.getStringExtra("destination");
        String prixTrajet = intent.getStringExtra("prix");
        String dateTrajet = intent.getStringExtra("date");
        keyTrajet = intent.getStringExtra("key");
        depart.setText(departTrajet);
        arrivee.setText(arriveeTrajet);
        prix.setText(prixTrajet);
        date.setText(dateTrajet);


    }
}