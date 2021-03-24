package UN.Miage.covoit;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    public LatLng getLocationFromAddress( String strAddress) {

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 1);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return p1;
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Intent afficherCarte = getIntent();
        String depart = "";
        if (afficherCarte.hasExtra("depart")){ // vérifie qu'une valeur est associée à la clé “edittext”
            depart = afficherCarte.getStringExtra("depart"); // on récupère la valeur associée à la clé
        }
        String arrivee = "";
        if (afficherCarte.hasExtra("arrivée")){ // vérifie qu'une valeur est associée à la clé “edittext”
            arrivee = afficherCarte.getStringExtra("arrivée"); // on récupère la valeur associée à la clé
        }

        // Add a marker in Sydney and move the camera
        LatLng depart1 = getLocationFromAddress(depart);
        LatLng arrivee1 = getLocationFromAddress(arrivee);

        mMap.addMarker(new MarkerOptions().position(depart1).title("départ"));
        mMap.addMarker(new MarkerOptions().position(arrivee1).title("arrivée"));
        // Instantiates a new Polyline object and adds points to define a rectangle
        PolylineOptions polylineOptions = new PolylineOptions()
                .add(depart1)
                .add(arrivee1)
                .color(Color.BLUE);
               // .add(arret1)  pour l'ajout des arrets entre depart et arrivée

// Get back the mutable Polyline
        Polyline polyline = mMap.addPolyline(polylineOptions);

        mMap.moveCamera(CameraUpdateFactory.zoomIn());
    }
}