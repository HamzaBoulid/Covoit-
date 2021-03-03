package UN.Miage.covoit;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

public class EcranAccueil extends AppCompatActivity  implements DrawerAdapter.OnItemSelectedListener {
    private FirebaseAuth mAuth;
    TextInputLayout email_connection, mdp_connection;
    Button inscription, connection;
    private static final int POS_CLOSE=0;
    private static final int POS_DASHBOARD=1;
    private static final int POS_MY_PROFILE=2;
    private static final int POS_NEARBY_RES=3;
    private static final int POS_SETTINGS=4;
    private static final int POS_LOGOUT=5;

    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;
    /*
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }*/
    public void updateUI(FirebaseUser account) {
        if(account != null){
            Toast.makeText(this,"Vous êtes connecté",Toast.LENGTH_LONG).show();
            Intent main = new Intent(EcranAccueil.this, EspaceUtilisateur.class);
            startActivity(main);
            finish();
        }
    }
/*
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
*/
    private boolean validationEmail() {
        String email = email_connection.getEditText().getText().toString();
        String emailNorme = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.isEmpty()) {
            email_connection.setError("Email nécessaire");
            return false;
        } else if (!email.matches(emailNorme)) {
            email_connection.setError("Veuillez saisir un email valide");
            return false;
        }else {
            email_connection.setError(null);
            return true;
        }
    }

    private boolean validationMDP(){
        String mdp = mdp_connection.getEditText().getText().toString();
        if (mdp.isEmpty()) {
            mdp_connection.setError("Email nécessaire");
            return false;
        } else if (mdp.length()+1 <= 6  ) {
            mdp_connection.setError("Votre mot de passe doit contenir au moins 6 caractères");
            return false;
        }else {
            mdp_connection.setError(null);
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecran_accueil);
       // randomMessageAccueil();
        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        slidingRootNav= new SlidingRootNavBuilder(this)
                .withDragDistance(180)
                .withRootViewScale(0.75f)
                .withRootViewElevation(25)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.drawer_menu)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles= loadScreenTitles();

        DrawerAdapter adapter=new DrawerAdapter(Arrays.asList(
                createItemFor(POS_CLOSE),
                createItemFor(POS_DASHBOARD).setChecked(true),
                createItemFor(POS_MY_PROFILE),
                createItemFor(POS_NEARBY_RES),
                createItemFor(POS_SETTINGS),
                new SpaceItem(260),
                createItemFor(POS_LOGOUT)
        ));
        adapter.setListener(this);
        RecyclerView list= findViewById(R.id.drawer_list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_DASHBOARD);

        inscription = findViewById(R.id.inscription);
        connection = findViewById(R.id.connection);
        email_connection = findViewById(R.id.email_connection);
        mdp_connection = findViewById(R.id.mdp_connection);

        inscription.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent main = new Intent(EcranAccueil.this, Inscription.class);
                startActivity(main);
                //faut pas ajouter finish pour pouvoir revenir en arrière
            }
        });

        connection.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (!validationEmail() || !validationMDP())
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
    private DrawerItem createItemFor(int position){
        return new SimpleItem(screenIcons[position],screenTitles[position])
                .withIconTint(color(R.color.pink))
                .withTextTint(color(R.color.black))
                .withSelectedIconTint(color(R.color.black))
                .withSelectedTextTint(color(R.color.pink));
    }
    @ColorInt
    private int color(@ColorRes int res)
    {
        return ContextCompat.getColor(this,res);
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.id_activityScreenTitles);
    }
    private Drawable[] loadScreenIcons() {
        TypedArray ta= getResources().obtainTypedArray(R.array.id_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i=0; i < ta.length(); i++) {
            int id= ta.getResourceId(i, 0);
            if(id!=0){
                icons[i]=ContextCompat.getDrawable(this,id);
            }
        }
        ta.recycle();
        return icons;
    }

    @Override
    public void onBackPressed() {
        finish();
    }



    @Override
    public void onItemSelected(int position) {
        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
        if( position == POS_DASHBOARD){
            DashBoardFragment dashBoardFragment= new DashBoardFragment();
            transaction.replace(R.id.container, dashBoardFragment);
        }
        else if( position == POS_MY_PROFILE){
            MyprofileFragment myprofileFragment= new MyprofileFragment();
            transaction.replace(R.id.container, myprofileFragment);
        }
        else if( position == POS_NEARBY_RES){
            AjouterTrajetFragment ajouterTrajetFragment= new AjouterTrajetFragment();
            transaction.replace(R.id.container, ajouterTrajetFragment);
        }
        else if( position == POS_SETTINGS){
            MesTrajetFragment mesTrajetFragment= new MesTrajetFragment();
            transaction.replace(R.id.container, mesTrajetFragment);
        }
        else if( position == POS_LOGOUT){
            finish();
        }
        slidingRootNav.closeMenu();
        transaction.addToBackStack(null);
        transaction.commit();
    }

}