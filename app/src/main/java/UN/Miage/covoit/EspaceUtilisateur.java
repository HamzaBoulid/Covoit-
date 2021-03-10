package UN.Miage.covoit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EspaceUtilisateur extends AppCompatActivity {
    Button deconnection, ajouter_trajet, mes_trajets, mes_infos, rechercherTrajet, mes_reservations;
    String id;
    // FirebaseDatabase BD = FirebaseDatabase.getInstance();
    //   DatabaseReference table;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private FirebaseUser currentUser;



    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    // Verif mdp avec currentUser à ajouter
    public void infosUtilisateur() {
        currentUser = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
        id = currentUser.getUid();
        reference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Utilisateur user = snapshot.getValue(Utilisateur.class);
                if (user != null){
                    Intent intent = new Intent(getApplicationContext(), InfosUtilisateur.class);
                    intent.putExtra("nom", user.nom);
                    intent.putExtra("prenom", user.prenom);
                    intent.putExtra("pseudo", user.pseudo);
                    intent.putExtra("age", user.age);
                    intent.putExtra("mail", user.email);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(EspaceUtilisateur.this, "inconnu",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateUI(FirebaseUser account) {
        if(account != null){
            Toast.makeText(this,"Vous êtes connecté",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Vous êtes déconnecté",Toast.LENGTH_LONG).show();
            FirebaseAuth.getInstance().signOut();
            Intent ajout = new Intent(EspaceUtilisateur.this, EcranAccueil.class);
            startActivity(ajout);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espace_utilisateur);
        deconnection = findViewById(R.id.deconnection);
        ajouter_trajet = findViewById((R.id.ajouter_trajet));
        mes_trajets=findViewById(R.id.mes_trajets);
        mes_infos=findViewById(R.id.infos_utilisateur);
        mes_reservations=findViewById(R.id.mes_reservations);
        rechercherTrajet=findViewById(R.id.rechercherTrajet);


        deconnection.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                FirebaseAuth.getInstance().signOut();
                Intent inscription = new Intent(EspaceUtilisateur.this, EcranAccueil.class);
                startActivity(inscription);
                finish();
            }
        });

        ajouter_trajet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ajout = new Intent(EspaceUtilisateur.this, AjouterUnTrajet.class);
                startActivity(ajout);
                //faut pas ajouter finish pour pouvoir revenir en arrière
            }
        });

        mes_reservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mes_resa = new Intent(EspaceUtilisateur.this, MesReservations.class);
                startActivity(mes_resa);
                //faut pas ajouter finish pour pouvoir revenir en arrière
            }
        });

        mes_trajets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mesTrajets = new Intent(EspaceUtilisateur.this, MesTrajets.class);
                startActivity(mesTrajets);
                //faut pas ajouter finish pour pouvoir revenir en arrière
            }
        });

        mes_infos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infosUtilisateur();
            }
        });

        rechercherTrajet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rechercherTrajet = new Intent(EspaceUtilisateur.this, ResultatRecherche.class);
                startActivity(rechercherTrajet);
            }
        });    }
}