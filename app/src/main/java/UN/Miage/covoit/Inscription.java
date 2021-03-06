package UN.Miage.covoit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class Inscription extends AppCompatActivity {
    TextInputLayout nomInscription, prenomInscription, pseudoInscription, ageInscription, emailInscription, telephoneInscritpion, mdpInscription;
    Button validerInscritpion, retourAccueil;
    private String TAG = "";
    FirebaseDatabase BD = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private boolean validationNom(){
        String nom = nomInscription.getEditText().getText().toString();
        if (nom.isEmpty()){
            nomInscription.setError("Veuillez tapper votre nom");
            return false;
        }else{
            nomInscription.setError(null);
            return true;
        }
    }
    private boolean validationPrenom(){
        String prenom = prenomInscription.getEditText().getText().toString();
        if (prenom.isEmpty()){
            prenomInscription.setError("Veuillez tapper votre nom");
            return false;
        }else{
            prenomInscription.setError(null);
            return true;
        }
    }
    private boolean validationPseudo() { //NORME PSEUDO A REVOIR !
        String pseudo = pseudoInscription.getEditText().getText().toString();
        String normePseudo = "^([a-zA-Z0-9_\\-\\.]+)";
        if (pseudo.isEmpty()) {
            pseudoInscription.setError("Le pseudo ne peut pas être vide");
            return false;
        } else if(!pseudo.matches(normePseudo)){
            pseudoInscription.setError("Un pseudo ne doit pas contenir d'espace");
            return false;
        }else if (pseudo.length() >= 15) {
            pseudoInscription.setError("Username too long");
            return false;
        }else {
            pseudoInscription.setError(null);
            return true;
        }
    }
    private boolean validationAge(){
        String age = ageInscription.getEditText().getText().toString();
        if (age.isEmpty()){
            ageInscription.setError("Veuillez saisir votre age");
            return false;
            // AJOUTER VERIFIER AGE MAX ET MIN
        }
        else{
            ageInscription.setError(null);
            return true;
        }
    }
    private boolean validationEmail() {
        String email = emailInscription.getEditText().getText().toString();
        String emailNorme = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.isEmpty()) {
            emailInscription.setError("Email nécessaire");
            return false;
        } else if (!email.matches(emailNorme)) {
            emailInscription.setError("Veuillez saisir un email valide");
            return false;
        }else if(email.length()+1 <= 10){
            emailInscription.setError("Veuillez saisir un email valide");
            return false;
        }
        else {
            emailInscription.setError(null);
            return true;
        }
    }

    /*
    AJOUTER VERIF TELEPHONE
    NORME PSEUDO A REVOIR !
     */
    private boolean validationMDP(){
        String mdp = mdpInscription.getEditText().getText().toString();
        if (mdp.isEmpty()) {
            mdpInscription.setError("Email nécessaire");
            return false;
        } else if (mdp.length()+1 <= 6  ) {
            mdpInscription.setError("Votre mot de passe doit contenir au moins 6 caractères");
            return false;
        }else {
            mdpInscription.setError(null);
            return true;
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void updateUI(FirebaseUser account){
        if(account != null){
            startActivity(new Intent(Inscription.this,EspaceUtilisateur.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        nomInscription = findViewById(R.id.nom_inscription);
        prenomInscription = findViewById(R.id.prenom_inscription);
        pseudoInscription = findViewById(R.id.pseudo_inscription);
        ageInscription = findViewById(R.id.age_inscription);
        emailInscription = findViewById(R.id.email_insciption);
        telephoneInscritpion = findViewById(R.id.telephone_inscription);
        mdpInscription = findViewById(R.id.MDP_inscription);
        validerInscritpion = findViewById(R.id.button_inscription);
        retourAccueil = findViewById(R.id.button_accueil);

        validerInscritpion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(!validationPrenom() || !validationNom() || !validationPseudo() || !validationAge() || !validationEmail() || !validationMDP()){
                    return;
                }
                String nom = nomInscription.getEditText().getText().toString();
                String prenom = prenomInscription.getEditText().getText().toString();
                String pseudo = pseudoInscription.getEditText().getText().toString();
                String age = ageInscription.getEditText().getText().toString();
                String email = emailInscription.getEditText().getText().toString();
                String telephone = emailInscription.getEditText().getText().toString();
                String mdp = mdpInscription.getEditText().getText().toString();

                Utilisateur utilisateur = new Utilisateur(nom, prenom, pseudo, age, email, telephone);


                //register user
                mAuth.createUserWithEmailAndPassword(email,mdp)
                        .addOnCompleteListener(Inscription.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "New user registration: " + task.isSuccessful());
                                if (task.isSuccessful()) {

                                    BD.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(utilisateur).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(Inscription.this, "Inscription réussie." ,
                                                        Toast.LENGTH_SHORT).show();
                                                Intent espaceUtilisateur = new Intent(Inscription.this, EspaceUtilisateur.class);
                                                startActivity(espaceUtilisateur);
                                                finish();

                                            }else{
                                                Toast.makeText(Inscription.this, "Inscription échouée. Vérifier votre connection.",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(Inscription.this, "Inscription échouée. Vérifier votre connection.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });


        retourAccueil.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // AJOUTER UN SERVICE POUR MAINTENIR LACTIVITE INSCRIPTION EN VIE !
                Intent accueil = new Intent(Inscription.this, EcranAccueil.class);
                startActivity(accueil);
                finish();
            }
        });


    }
}