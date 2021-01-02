package UN.Miage.covoit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class EcranAccueil extends AppCompatActivity {

    Button inscription;
    public void randomMessageAccueil(){
        Random random = new Random();
        int valeur = random.nextInt(3-1)+1; // on génère un nombre aléatoire entre 1 et 3 (inclus)
        TextView phraseAccueil = (TextView) findViewById(R.id.phrase_accueil);
        switch (valeur){
            case 1:
                phraseAccueil.setText("BON RETOUR PARMI NOUS!");
                break;
            case 2:
                phraseAccueil.setText("D'INNOMBRABLES TRAJETS VOUS ATTENDENT!");
                break;
            case 3:
                phraseAccueil.setText("HEY MATELOT!");
                break;
        }
        phraseAccueil.setTextSize(25);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecran_accueil);
        randomMessageAccueil();
        inscription = findViewById(R.id.inscription);
        inscription.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent inscription = new Intent(EcranAccueil.this, Inscription.class);
                startActivity(inscription);
            }
        });

    }
}