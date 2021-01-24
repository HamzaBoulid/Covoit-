package UN.Miage.covoit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class InfosUtilisateur extends AppCompatActivity {

    TextView nom, prenom, age, mail, pseudo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_infos_utilisateur);

        nom = findViewById(R.id.info_nom);
        prenom = findViewById(R.id.info_prenom);
        age = findViewById(R.id.info_age);
        mail = findViewById(R.id.info_mail);
        pseudo = findViewById(R.id.info_pseudo);

        afficherInfosUtilisateur();


    }

    private void afficherInfosUtilisateur(){
        Intent intent = getIntent();
        String nomUtilisateur = intent.getStringExtra("nom");
        String prenomUtilisateur = intent.getStringExtra("prenom");
        String ageUtilisateur = intent.getStringExtra("age").concat(" ans");
        String pseudoUtilisateur = intent.getStringExtra("pseudo");
        String mailUtilisateur = intent.getStringExtra("mail");


        nom.setText(nomUtilisateur);
        prenom.setText(prenomUtilisateur);
        pseudo.setText(pseudoUtilisateur);
        age.setText(ageUtilisateur);
        mail.setText(mailUtilisateur);



    }
}