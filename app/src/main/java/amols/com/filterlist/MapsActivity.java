package amols.com.filterlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener
{

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        Bundle extras = getIntent().getExtras();
        double l1 = extras.getDouble("latitude");
        double l2 = extras.getDouble("longitude");

        mMap = googleMap;


        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(l1, l2);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Seattle"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setOnMarkerClickListener(this);

    }


    private Marker mPerth;
    @Override
    public boolean onMarkerClick(Marker marker)
    {
        Bundle extras = getIntent().getExtras();
        double l1 = extras.getDouble("latitude");
        double l2 = extras.getDouble("longitude");
        Intent intent = new Intent(MapsActivity.this, YelpSearchNearbyActivity.class);
        intent.putExtra("latitude", l1);
        intent.putExtra("longitude", l2);

        startActivity(intent);
        return false;
    }
}