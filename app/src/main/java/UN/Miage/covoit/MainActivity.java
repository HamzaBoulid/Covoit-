package UN.Miage.covoit;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;


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
//setView à avancer


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