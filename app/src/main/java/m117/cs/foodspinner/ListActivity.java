package m117.cs.foodspinner;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.SearchResponse;
import com.yelp.clientlib.entities.options.CoordinateOptions;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {

    public final static String SPIN_RESULT = "m117.cs.foodspinner.SPIN_RESULT";

    ListActivityAdapter adapter;

    private boolean doneLoading = false;
    private int lastIndex = -1;

    // Yelp Credentials
    private static String consumerKey = "CnhXH097tBBstvtvrHwCTg";
    private static String consumerSecret = "drf6gkPziGtZYIkNJHZKvtk69ig";
    private static String token = "HLmAR0Q8SXVhquyS7oyrzmXMiwbcJITI";
    private static String tokenSecret = "_8lW6PtMwLTM4ncfAVr6QeL35o0";

    ListView itemList;

    ArrayList<Bitmap> itemIcon;
    ArrayList<String> itemName;
    ArrayList<String> itemDesc;

    private double mLatitude;
    private double mLongitude;
    private Button mSpinButton;

    private class imageObject {
        public imageObject(URL u, int i) {
            myUrl = u;
            myIndex = i;
            myBmp = null;
        }

        public URL myUrl;
        public int myIndex;
        public Bitmap myBmp;
    }

    public class imageTask extends AsyncTask<imageObject, Integer, imageObject> {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected imageObject doInBackground(imageObject... params) {
            imageObject temp = params[0];
            try {
                temp.myBmp = BitmapFactory.decodeStream(temp.myUrl.openConnection().getInputStream());
            } catch (Exception e) {
                temp.myBmp = null;
            }
            return temp;
        }

        @Override
        protected void onPostExecute(imageObject result) {
            if (result.myBmp != null) {
                itemIcon.add(result.myBmp);
                if (result.myIndex == lastIndex) {
                    adapter.notifyDataSetChanged();
                    doneLoading = true;
                    mSpinButton.setClickable(true);
                }
            }
        }
    }

    public class webTask extends AsyncTask<String, Integer, SearchResponse> {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected SearchResponse doInBackground(String... params) {
            // Instantiate YelpAPI
            YelpAPIFactory apiFactory = new YelpAPIFactory(consumerKey, consumerSecret, token, tokenSecret);
            YelpAPI yelpAPI = apiFactory.createAPI();

            Map<String, String> y_params = new HashMap<>();

            // Search for restaurants
            y_params.put("term", "food");

            // Limit to 15 results
            y_params.put("limit", "15");

            //coordinates
            CoordinateOptions coordinate = CoordinateOptions.builder()
                    .latitude(mLatitude)
                    .longitude(mLongitude).build();
            Call<SearchResponse> call = yelpAPI.search(coordinate, y_params);

            Response<SearchResponse> response;

            try {
                response = call.execute();
                return response.body();

            }
            catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(SearchResponse result) {

            AlertDialog alertDialog = new AlertDialog.Builder(ListActivity.this).create();

            if (result != null) {
                SearchResponse res = result;

                int max = 10;

                ArrayList<Business> restaurants = res.businesses();

                if (restaurants.size() < max) {
                    max = restaurants.size();
                }
                lastIndex = max - 1;

                for (int i = 0; i < max; i++) {
                    try {
                        URL url = new URL(restaurants.get(i).imageUrl());
                        imageObject temp = new imageObject(url, i);
                        new imageTask().execute(temp);
                    }
                    catch (Exception e) {

                    }

                    itemName.add(restaurants.get(i).name());
                    itemDesc.add("Rating: " + restaurants.get(i).rating());
                }

                // Notify adapter of change
                adapter.notifyDataSetChanged();
            }
            else {
                alertDialog.setTitle("No results found!");
                alertDialog.show();
            }

            return;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Intent intent = getIntent();

        mSpinButton = (Button)findViewById(R.id.button_spin);
        mSpinButton.setClickable(false);

        mLatitude = Double.parseDouble(intent.getStringExtra(MainActivity.COORD_LAT));
        mLongitude = Double.parseDouble(intent.getStringExtra(MainActivity.COORD_LONG));

        itemIcon = new ArrayList<Bitmap>();
        itemName = new ArrayList<String>();
        itemDesc = new ArrayList<String>();

        adapter = new ListActivityAdapter(this, itemIcon, itemName, itemDesc);
        itemList = (ListView)findViewById(R.id.item_list);
        itemList.setAdapter(adapter);

        itemList.setClickable(true);
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                if (doneLoading) {
                    Object o = itemList.getItemAtPosition(position);

                    // Remove item from the list
                    if (itemIcon.size() > position) {
                        itemIcon.remove(position);

                    }
                    itemName.remove(position);
                    itemDesc.remove(position);

                    // Notify adapter of change
                    adapter.notifyDataSetChanged();
                }
            }
        });

        // Call the async function
        new webTask().execute("test");
    }

    public void spinButton(View view) {

        // If we have items to choose from
        if (doneLoading) {
            // Choose random
            Random random = new Random();
            int resultIndex = random.nextInt(itemName.size());

            // Create array of result info
            ArrayList result = new ArrayList();
            if (itemIcon.size() <= 0) {
                result.add(-1);
                result.add(-1);
            } else {
                result.add(1);
                result.add(itemIcon.get(resultIndex));
            }
            result.add(itemName.get(resultIndex));
            result.add(itemDesc.get(resultIndex));

            /** TOMMY */
            // Open the slot
            Intent intent = new Intent(this, SlotActivity.class);
            intent.putExtra(SPIN_RESULT, result);
            startActivity(intent);
        }
    }
}