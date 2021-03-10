package UN.Miage.covoit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    //Variables
    Animation topAnim, bottomAnim;
    ImageView imageMain, imageAccueil;
    TextView slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseAuth.getInstance().signOut();

        //Cacher les barres supérieurs
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Animation d'ouverture de l'application
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        imageMain = findViewById(R.id.imageMain);
        imageAccueil = findViewById(R.id.imageAccueil);
        slogan = findViewById(R.id.slogan);

        imageMain.setAnimation(topAnim);
        imageAccueil.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent ecranAccueil = new Intent(MainActivity.this, EcranAccueil.class);
                startActivity(ecranAccueil);
                finish();

                //Pair[] pairs = new Pair[2];
                //pairs[0] = new Pair<View, String>(imageAccueil, "main_accueil");

                //if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                 //   @SuppressLint({"NewApi", "LocalSuppress"}) ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                //    startActivity(ecranAccueil, options.toBundle());
                //}

            }
        }, 2000);
    }
}