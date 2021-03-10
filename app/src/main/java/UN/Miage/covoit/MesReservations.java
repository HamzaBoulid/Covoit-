package UN.Miage.covoit;


import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MesReservations extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TrajetsAdapter adapter;
    private List<Trajet> trajetList;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            trajetList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(snapshot.child("passagers").getValue(String.class).contains(currentUser.getUid())) {
                        Toast.makeText(MesReservations.this, "jjj(s) récupéré(s).",
                                Toast.LENGTH_SHORT).show();
                        Trajet trajet = new Trajet();
                        trajet.conducteur = snapshot.child("conducteur").getValue(String.class);
                        trajet.depart = snapshot.child("depart").getValue(String.class);
                        trajet.destination = snapshot.child("destination").getValue(String.class);
                        trajet.date = snapshot.child("date").getValue(String.class);
                        trajet.prix = snapshot.child("prix").getValue(String.class);
                        trajet.passagers = snapshot.child("passagers").getValue(String.class);
                        trajetList.add(trajet);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView titrePage;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_trajets);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        trajetList = new ArrayList<>();
        adapter = new TrajetsAdapter(this, trajetList);
        recyclerView.setAdapter(adapter);
        titrePage = findViewById(R.id.mes_trajets);
        titrePage.setText("Mes Reservations");
        Query query = FirebaseDatabase.getInstance().getReference("Trajets").orderByKey();


        query.addListenerForSingleValueEvent(valueEventListener);

    }
}
