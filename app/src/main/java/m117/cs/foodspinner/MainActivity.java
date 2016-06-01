package m117.cs.foodspinner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    public final static String ZIP_CODE = "m117.cs.foodspinner.ZIP_CODE";
    public final static String GPS_COORD = "m117.cs.foodspinner.GPS_COORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        // TODO: Get location from GPS?

        // Boelter hall lat/long for the lols
        String gpsCoords = "34.0686200, -118.4428600"; // How to format this??

        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra(GPS_COORD, gpsCoords);
        startActivity(intent);

    }

}