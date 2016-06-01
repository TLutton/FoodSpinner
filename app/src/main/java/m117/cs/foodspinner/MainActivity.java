package m117.cs.foodspinner;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;


public class MainActivity extends AppCompatActivity{

    public final static String ZIP_CODE = "m117.cs.foodspinner.ZIP_CODE";
    public final static String GPS_COORD = "m117.cs.foodspinner.GPS_COORD";

    private LocationManager locationManager;
    private String provider;
    private Criteria criteria;

    private double mLatitude;
    private double mLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** Start Location Gathering Code **/
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setCostAllowed(false);

        provider = locationManager.getBestProvider(criteria,false);
        Location location = null;
        try {
            location = locationManager.getLastKnownLocation(provider);
        } catch (SecurityException se) {
            System.err.print("Security Exception: " + se.toString());
        }

        // if no location, go to settings to enable location gathering
        if(location == null) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        mLatitude  = location.getLatitude();
        mLongitude = location.getLongitude();
        /** End Location Gathering Code **/


    }

    /** Called when the user clicks the Enter ZIP button */
    public void enterZipCode(View view) {
        EditText editZipCode = (EditText) findViewById(R.id.edit_enter_zip_code);
        String zipCode = editZipCode.getText().toString();
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra(ZIP_CODE, zipCode);
        startActivity(intent);
    }

    /** OnClick handler of get user location button **/
    public void getUserLocation(View view) {
        // TODO: Get location from Network Provider


        // Boelter hall lat/long for the lols
        String gpsCoords = "34.0686200, -118.4428600"; // How to format this??

        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra(GPS_COORD, gpsCoords);
        startActivity(intent);

    }

}