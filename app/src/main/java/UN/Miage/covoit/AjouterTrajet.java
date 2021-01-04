package UN.Miage.covoit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AjouterTrajet extends AppCompatActivity {
    TextInputLayout depart, destination, date, heure, passagers, prix, commentaire;
    Button validerTrajet;
    FirebaseDatabase BD = FirebaseDatabase.getInstance();
    DatabaseReference table;
    private FirebaseAuth mAuth;

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
    }
    private boolean validationDate(){
        String nom = date.getEditText().getText().toString();
        if (nom.isEmpty()){
            date.setError("Veuillez tapper la date de départ");
            return false;
        }else{
            date.setError(null);
            return true;
        }
    }
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
    }
    private boolean validationPrix(){
        String nom = prix.getEditText().getText().toString();
        if (nom.isEmpty()){
            prix.setError("Veuillez tapper le prix de votre trajet");
            return false;
        }else{
            prix.setError(null);
            return true;
        }
    }
    //le commentaire est facultatif pas besoin de vérifier la saisie
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        depart.findViewById(R.id.depart);
        destination.findViewById(R.id.arrivee);
        date.findViewById(R.id.date);
        heure.findViewById(R.id.heure);
        passagers.findViewById(R.id.nb_passagers);
        prix.findViewById(R.id.prix);
        commentaire.findViewById(R.id.commentaire);
        validerTrajet.findViewById(R.id.button_ajouter);
        setContentView(R.layout.activity_ajouter_trajet);

        validerTrajet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //vérifier que tout les champs sont valides
                if(!validationDepart() || !validationDestination() || !validationDate() || !validationHeure() || !validationPassagers() || !validationPrix()){
                    return;
                }
                String villeDepart = depart.getEditText().getText().toString();
                String villeDestination = destination.getEditText().getText().toString();
                String dateDepart = date.getEditText().getText().toString();
                String heureDepart = heure.getEditText().getText().toString();
                String nbPassager = passagers.getEditText().getText().toString();
                String prixPersonne = prix.getEditText().getText().toString();
                String comment = commentaire.getEditText().getText().toString();

                Trajet trajet = new Trajet(villeDepart, villeDestination, dateDepart, heureDepart, nbPassager, prixPersonne, comment);

                //la partie enregistrement dans la database
            }
        });
    }


}