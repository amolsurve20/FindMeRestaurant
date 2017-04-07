package amols.com.filterlist;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class DisplayActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static final String TAG = "LOCATION";
    private ArrayAdapter<String> adapter;
    private static final int LOCATION_REQUEST_CODE = 1;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double lati;
    private double longi;
    private EditText searchField;
    private ImageButton b1,b2, b3, b4, mapbtn;
    Button searchButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        searchField = (EditText) findViewById(R.id.txtSearch);
        b2 = (ImageButton) findViewById(R.id.img2);
        b1 = (ImageButton) findViewById(R.id.img1);
        searchButton = (Button) findViewById(R.id.btnSearch);
        b3 = (ImageButton) findViewById(R.id.img3);
        b4 = (ImageButton) findViewById(R.id.img4);
        mapbtn = (ImageButton) findViewById(R.id.loc);

        searchButton=(Button) findViewById(R.id.btnSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String searchTerm=searchField.getText().toString();
                Intent intent = new Intent(DisplayActivity.this, DisplayListActivity.class);
                intent.putExtra("latitude",lati);
                intent.putExtra("longitude",longi);
                intent.putExtra("term",searchTerm);
                startActivity(intent);
            }
        });

        b1=(ImageButton) findViewById(R.id.img1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(DisplayActivity.this, DisplayMexicanActivity.class);
                intent.putExtra("latitude",lati);
                intent.putExtra("longitude",longi);
                startActivity(intent);
            }
        });

        b2=(ImageButton) findViewById(R.id.img2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(DisplayActivity.this, DisplayBurgerListActivity.class);
                intent.putExtra("latitude",lati);
                intent.putExtra("longitude",longi);
                startActivity(intent);
            }
        });

        b3=(ImageButton) findViewById(R.id.img3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(DisplayActivity.this, DisplayIndianActivity.class);
                intent.putExtra("latitude",lati);
                intent.putExtra("longitude",longi);
                startActivity(intent);
            }
        });

        b4=(ImageButton) findViewById(R.id.img4);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(DisplayActivity.this, DisplayThaiActivity.class);
                intent.putExtra("latitude",lati);
                intent.putExtra("longitude",longi);
                startActivity(intent);
            }
        });

        mapbtn=(ImageButton) findViewById(R.id.loc);
        mapbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(DisplayActivity.this, MapsActivity.class);
                intent.putExtra("latitude",lati);
                intent.putExtra("longitude",longi);
                startActivity(intent);
            }
        });



        if (mGoogleApiClient == null)
        {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }


    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.v(TAG, "GoogleApiClient connected");


        mLocationRequest = new LocationRequest();

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        } else {
            //request permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        if (location != null) {
            lati = location.getLatitude();
            longi = location.getLongitude();
            CharSequence text = "Current Location Retrieved!";
            int duration = Toast.LENGTH_LONG;
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else

        {
            Log.v(TAG, "Received null location");
            CharSequence text = "Received null location!";
            int duration = Toast.LENGTH_SHORT;
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: { //if asked for location
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onConnected(null); //do whatever we'd do when first connecting (try again)
                }
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }




}






