package UN.Miage.covoit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class EspaceUtilisateur extends AppCompatActivity {
    Button deconnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espace_utilisateur);
        deconnection = findViewById(R.id.button_deconnection);

        deconnection.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                FirebaseAuth.getInstance().signOut();
                Intent inscription = new Intent(EspaceUtilisateur.this, EcranAccueil.class);
                startActivity(inscription);
                finish();
            }
        });
    }
}