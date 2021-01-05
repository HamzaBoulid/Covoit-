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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EspaceUtilisateur extends AppCompatActivity {
    Button deconnection, ajouter_trajet, mes_trajets, mes_infos;
   // FirebaseDatabase BD = FirebaseDatabase.getInstance();
 //   DatabaseReference table;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    // Verif mdp avec currentUser à ajouter
    public void lancerSession() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        final String email = "hahakaka@gmail.com";
        final String username = "abcd";
        Query queryUtilisateur = reference.orderByChild("email").equalTo(email);
        queryUtilisateur.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    //Ajouter Verif mdp if equals ....
                    Utilisateur user = snapshot.child(username).getValue(Utilisateur.class);


                    Intent intent = new Intent(getApplicationContext(), InfosUtilisateur.class);

                    intent.putExtra("nom", user.nom);
                    intent.putExtra("prenom", user.prenom);
                    intent.putExtra("pseudo", user.pseudo);
                    intent.putExtra("age", user.email);
                    intent.putExtra("mail", user.age);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(EspaceUtilisateur.this, "hjj inconnu",
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
                finish();
            }
        });

        mes_trajets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent main = new Intent(EspaceUtilisateur.this, MesTrajets.class);
                startActivity(main);
                finish();
            }
        });

        mes_infos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lancerSession();
            }
        });






    }
}