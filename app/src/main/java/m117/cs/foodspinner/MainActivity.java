package m117.cs.foodspinner;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.io.IOException;
import java.util.List;


public class MainActivity extends AppCompatActivity{

    public final static String ZIP_CODE = "m117.cs.foodspinner.ZIP_CODE";
    public final static String COORD_LAT = "m117.cs.foodspinner.LAT";
    public final static String COORD_LONG = "m117.cs.foodspinner.LONG";

    private LocationManager locationManager;
    private String provider;
    private Criteria criteria;

    private double mLatitude;
    private double mLongitude;
    private int mZipCode;

    private CheckBox mCheckBox;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // default lat/long
        mLatitude  = 0.0; // default to Boelter
        mLongitude = 0.0; // default to Boelter
        mZipCode = 90024; // default to WW

        mCheckBox = (CheckBox)findViewById(R.id.checkbox_use_current_location);
        mEditText = (EditText)findViewById(R.id.edittext_enter_location_name);

    }

    /** Called when the user clicks the Enter ZIP button */
    public void enterZipCode(View view) {
        //EditText editZipCode = (EditText) findViewById(R.id.edit_enter_zip_code);
        //String zipCode = editZipCode.getText().toString();
        //Intent intent = new Intent(this, ListActivity.class);
        //intent.putExtra(ZIP_CODE, zipCode);
        //startActivity(intent);
    }

    /** Onclick handler of use current location checkbox **/
    public void useCurrentLocationCheckbox(View view) {
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.layout_enter_location_name);
        if(!(mCheckBox.isChecked())) {
            // show linearLayout and gather ZIP Code
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            // hide the linearLayout
            linearLayout.setVisibility(View.GONE);
        }
    }

    // set lat and long
    private void getLocation() {
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

    // get coordinates from address/name
    private void getCoordsFromAddress() {
        Geocoder geocoder = new Geocoder(getBaseContext());
        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocationName(mEditText.getText().toString(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(addresses.size() > 0) {
            mLatitude = addresses.get(0).getLatitude();
            mLongitude = addresses.get(0).getLongitude();
        }
        if(mLatitude == 0.0 && mLongitude == 0.0) {
            // Location name wasn't found.
        }
    }

    /** OnClick handler of continue button. **/
    public void setCoordinatesAndContinue(View view) {

        //if using location info, getLocation
        if(mCheckBox.isChecked())
            getLocation();
        else // else set zip code to enter value
            getCoordsFromAddress();

        // pass zip code thru anyway. ??
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra(ZIP_CODE, Integer.toString(mZipCode));  // TODO: Remove zip code
        intent.putExtra(COORD_LAT, Double.toString(mLatitude));
        intent.putExtra(COORD_LONG, Double.toString(mLongitude));
        startActivity(intent);

    }

    /** Getters for latitude and longitude **/
    // TODO: Remove
    public double getmLatitude() {
        return mLatitude;
    }

    public double getmLongitude() {
        return mLongitude;
    }

}