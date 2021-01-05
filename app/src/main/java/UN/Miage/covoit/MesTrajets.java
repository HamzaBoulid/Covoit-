package UN.Miage.covoit;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MesTrajets extends AppCompatActivity {
    ListView listView;
    ArrayList<String> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_trajets);

        listView = (ListView)findViewById(R.id.listView);

        arrayList = new ArrayList<>();


        arrayList.add("android");
        arrayList.add("is");
        arrayList.add("great");
        arrayList.add("for");
        arrayList.add("me");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

        listView.setAdapter(arrayAdapter);


    }
}