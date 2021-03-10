package UN.Miage.covoit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AjouterUnTrajet extends AppCompatActivity {

    TextInputLayout depart, destination, heure, passagers, date, prix;
    EditText commentaire;
    Button validerTrajet, deconnection;
    FirebaseDatabase BD = FirebaseDatabase.getInstance();
    DatabaseReference table;
    String key;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void updateUI(FirebaseUser account) {
        if(account != null){
            Toast.makeText(this,"Vous êtes connecté",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Vous êtes déconnecté",Toast.LENGTH_LONG).show();
            FirebaseAuth.getInstance().signOut();
            Intent ajout = new Intent(AjouterUnTrajet.this, EcranAccueil.class);
            startActivity(ajout);
            finish();
        }
    }

    //verifier la saisie
    private boolean validationDepart(){
        String nom = depart.getEditText().getText().toString();
        if (nom.isEmpty()){
            depart.setError("Veuillez tapper la ville de départ s'il vous plait ");
            return false;
        }else{
            depart.setError(null);
            return true;
        }
    }
    private boolean validationDestination(){
        String nom = destination.getEditText().getText().toString();
        if (nom.isEmpty()){
            destination.setError("Veuillez tapper la destination s'il vous plait");
            return false;
        }else{
            destination.setError(null);
            return true;
        }
    }/*
    private boolean validationDate(){
        String nom = date.getEditText().getText().toString();
        if (nom.isEmpty()){
            date.setError("Veuillez tapper la date de départ");
            return false;
        }else{
            date.setError(null);
            return true;
        }
    }*/
    private boolean validationHeure(){

        String nom = heure.getEditText().getText().toString();
        if (nom.isEmpty()){
            heure.setError("Veuillez tapper l'heure de départ");
            return false;
        }else{
            heure.setError(null);
            return true;
        }
    }
    private boolean validationPassagers(){
        String nom = passagers.getEditText().getText().toString();
        if (nom.isEmpty()){
            passagers.setError("Veuillez tapper le nombre de passagers que vous pouvez prendre");
            return false;
        }else{
            passagers.setError(null);
            return true;
        }
    }/*
    private boolean validationPrix(){
        String nom = prix.getEditText().getText().toString();
        if (nom.isEmpty()){
            prix.setError("Veuillez tapper le prix de votre trajet");
            return false;
        }else{
            prix.setError(null);
            return true;
        }
    }*/
    //le commentaire est facultatif pas besoin de vérifier la saisie
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_un_trajet);
        depart = findViewById(R.id.lieu_depart);
        destination = findViewById(R.id.lieu_arrivee);
        date = findViewById(R.id.date_depart);
        heure = findViewById(R.id.heure_depart);
        passagers = findViewById(R.id.nb_passagers);
        prix = findViewById(R.id.prix);
        commentaire = findViewById(R.id.commentaire);
        deconnection = findViewById(R.id.button_deconnection);
        validerTrajet = findViewById(R.id.button_ajouter_trajet);

        validerTrajet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validationDepart() || !validationDestination()  || !validationHeure() || !validationPassagers()){
                    return;
                }
                String villeDepart = depart.getEditText().getText().toString();
                String villeDestination = destination.getEditText().getText().toString();
                String dateDepart = date.getEditText().getText().toString();
                String heureDepart = heure.getEditText().getText().toString();
                String placesDisponibles = passagers.getEditText().getText().toString();
                String passagers = "";
                String prixPersonne = prix.getEditText().getText().toString();
                String comment = commentaire.getText().toString();
                currentUser = mAuth.getCurrentUser();
                String conducteur = currentUser.getUid();
                // A completer les verifs et modifier les champs (entiers , date !! ..)

                table = BD.getReference("Trajets");
                DatabaseReference postsRef = table;
                DatabaseReference newPostRef = postsRef.push();


                Trajet trajet = new Trajet(villeDepart,villeDestination,dateDepart,heureDepart,placesDisponibles, passagers, prixPersonne, comment, conducteur);
                newPostRef.setValue(trajet);

                Intent mesTrajets = new Intent(AjouterUnTrajet.this, MesTrajets.class);
                startActivity(mesTrajets);
                finish();
            }
        });
    }
}