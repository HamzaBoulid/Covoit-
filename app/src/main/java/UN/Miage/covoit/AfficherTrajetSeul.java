package UN.Miage.covoit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AfficherTrajetSeul extends AppCompatActivity {

    TextView depart, arrivee, date, prix, conducteur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_afficher_trajet_seul);

        depart = findViewById(R.id.text_view_depart);
        arrivee = findViewById(R.id.text_view_arrivee);
        date = findViewById(R.id.text_view_date);
        prix = findViewById(R.id.text_view_prix);
        conducteur = findViewById(R.id.text_view_conducteur);

        afficherInfosUtilisateur();


    }

    private void afficherInfosUtilisateur(){
        Intent intent = getIntent();
        String departTrajet = intent.getStringExtra("depart");
        String arriveeTrajet = intent.getStringExtra("arrivee");
        String prixTrajet = intent.getStringExtra("prix").concat(" $");
        String dateTrajet = intent.getStringExtra("date");
        String conducteurTrajet = intent.getStringExtra("cnoducteur");


        depart.setText(departTrajet);
        arrivee.setText(arriveeTrajet);
        prix.setText(prixTrajet);
        date.setText(dateTrajet);
        conducteur.setText(conducteurTrajet);



    }
}