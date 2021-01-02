package UN.Miage.covoit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Random;

public class EcranAccueil extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextInputLayout email_connection, mdp_connection;
    Button inscription, connection;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    public void updateUI(FirebaseUser account) {
        if(account != null){
            Toast.makeText(this,"Vous êtes connecté",Toast.LENGTH_LONG).show();
            startActivity(new Intent(EcranAccueil.this,EspaceUtilisateur.class));

        }else {
            Toast.makeText(this,"Vous n'êtes pas connecté",Toast.LENGTH_LONG).show();
        }
    }
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
    private boolean validationSaisie(TextInputLayout layout){
        String saisie = layout.getEditText().getText().toString();
        if (saisie.isEmpty()){
            layout.setError("Veuillez bien saisir votre nom/prenom");
            return false;
        }else{
            layout.setError(null);
            return true;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecran_accueil);
        randomMessageAccueil();
        inscription = findViewById(R.id.inscription);
        connection = findViewById(R.id.connection);
        email_connection = findViewById(R.id.email_connection);
        mdp_connection = findViewById(R.id.mdp_connection);

        inscription.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent inscription = new Intent(EcranAccueil.this, Inscription.class);
                startActivity(inscription);
            }
        });

        connection.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (!validationSaisie(email_connection) || !validationSaisie(mdp_connection))
                    return;

                String email = email_connection.getEditText().getText().toString();
                String mdp = mdp_connection.getEditText().getText().toString();

                mAuth.signInWithEmailAndPassword(email, mdp)
                        .addOnCompleteListener(EcranAccueil.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(EcranAccueil.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }

                                // ...
                            }
                        });

            }
        });


    }
}