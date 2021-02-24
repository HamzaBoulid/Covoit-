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


public class MainActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {
    //Variables
    Animation animationOuvertureApplication;
    ImageView logoCovoit;
    private static final int POS_CLOSE=0;
    private static final int POS_DASHBOARD=1;
    private static final int POS_MY_PROFILE=2;
    private static final int POS_NEARBY_RES=3;
    private static final int POS_SETTINGS=4;
    private static final int POS_LOGOUT=5;

    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseAuth.getInstance().signOut();

        //Cacher les barres supérieurs
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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