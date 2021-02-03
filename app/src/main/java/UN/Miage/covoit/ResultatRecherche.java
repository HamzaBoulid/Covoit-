package UN.Miage.covoit;


import android.os.Bundle;
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


public class ResultatRecherche extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ResultatRechercheAdapter adapter;
    private List<Trajet> trajetList;
    private List<String> trajetsKeys;

    public int countPassagers(String passagers) {
        int itemCount = passagers.split(",").length;
        return itemCount;
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            trajetList.clear();
            trajetsKeys.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Toast.makeText(ResultatRecherche.this, "trajet(s) récupéré(s)." ,
                            Toast.LENGTH_SHORT).show();
                    Trajet trajet = new Trajet();
                    trajet.depart = snapshot.child("depart").getValue(String.class);
                    trajet.destination = snapshot.child("destination").getValue(String.class);
                    trajet.date = snapshot.child("date").getValue(String.class);
                    trajet.prix = snapshot.child("prix").getValue(String.class);
                    trajet.passagersMax = snapshot.child("placesMax").getValue(String.class);
                    String passagers = snapshot.child("passagers").getValue(String.class);
                    trajet.passagersReserves = Integer.toString(countPassagers(passagers)) ;
                    trajetsKeys.add(snapshot.getKey());
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
        setContentView(R.layout.activity_resultat_recherche);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        trajetList = new ArrayList<>();
        trajetsKeys = new ArrayList<>();
        adapter = new ResultatRechercheAdapter(this, trajetList, trajetsKeys);
        recyclerView.setAdapter(adapter);

        Query query = FirebaseDatabase.getInstance().getReference("Trajets").orderByChild("conducteur")
                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());

        query.addListenerForSingleValueEvent(valueEventListener);

    }
}
