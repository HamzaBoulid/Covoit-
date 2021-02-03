package UN.Miage.covoit;


import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MesTrajets extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TrajetsAdapter adapter;
    private List<Trajet> trajetList;


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            trajetList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Toast.makeText(MesTrajets.this, "trajet(s) récupéré(s)." ,
                            Toast.LENGTH_SHORT).show();
                    Trajet trajet = new Trajet();
                    trajet.depart = snapshot.child("depart").getValue(String.class);
                    trajet.destination = snapshot.child("destination").getValue(String.class);
                    trajet.date = snapshot.child("date").getValue(String.class);
                    trajet.prix = snapshot.child("prix").getValue(String.class);
                    trajet.passagersReserves = snapshot.child("passagers").getValue(String.class);
                    trajetList.add(trajet);
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_trajets);
        TextView titrePage;
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        trajetList = new ArrayList<>();
        adapter = new TrajetsAdapter(this, trajetList);
        recyclerView.setAdapter(adapter);
        titrePage = findViewById(R.id.mes_trajets);
        titrePage.setText("Mes Trajets Proposées");
        Query query = FirebaseDatabase.getInstance().getReference("Trajets").orderByChild("conducteur")
                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());


        query.addListenerForSingleValueEvent(valueEventListener);

    }
}
