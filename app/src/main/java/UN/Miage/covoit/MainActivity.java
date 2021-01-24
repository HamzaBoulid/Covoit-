package UN.Miage.covoit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    //Variables
    Animation animationOuvertureApplication;
    ImageView logoCovoit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseAuth.getInstance().signOut();

        //Cacher les barres supérieurs
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Animation d'ouverture de l'application
        animationOuvertureApplication = AnimationUtils.loadAnimation(this, R.anim.animation_ouverture_application);
        logoCovoit = findViewById(R.id.imageAccueil);
        logoCovoit.setAnimation(animationOuvertureApplication);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent ecranAccueil = new Intent(MainActivity.this, EcranAccueil.class);
                startActivity(ecranAccueil);
                finish();
            }
        }, 2000);
    }
}