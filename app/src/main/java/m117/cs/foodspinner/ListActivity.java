package m117.cs.foodspinner;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Random;

public class ListActivity extends AppCompatActivity {

    public final static String SPIN_RESULT = "m117.cs.foodspinner.SPIN_RESULT";

    final Handler handler = new Handler();
    final int delay = 200;
    ListView itemList;

    int totalItems;
    int index;
    int spinCount;
    ArrayList<Integer> itemIcon;
    ArrayList<String> itemName;
    ArrayList<String> itemDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Intent intent = getIntent();
        String zipCode = intent.getStringExtra(MainActivity.ZIP_CODE);
        // TODO: GPS variable

        // TODO: Do something with zipCode or location or whatever

        // TODO: END

//        // Calculate total number of items
//        index = 0;
//        spinCount = 20;
//        for (int i = 0; i < itemName.size(); i++) {
//            totalItems += 1;
//        }

        itemIcon = new ArrayList<Integer>();
        itemName = new ArrayList<String>();
        itemDesc = new ArrayList<String>();

        for (int i = 0; i < 11; i++) {
            itemIcon.add(R.mipmap.test_icon);
        }

        itemName.add("Noodle World");
        itemName.add("Chipotle");
        itemName.add("In-N-Out");
        itemName.add("Five Guys");
        itemName.add("Shophouse");
        itemName.add("Bibigo");
        itemName.add("Dennys");
        itemName.add("800 Degrees");
        itemName.add("Taco Bell");
        itemName.add("Flame Broiler");
        itemName.add("Cava");

        itemDesc.add("The finest dining");
        itemDesc.add("Authentic Mexican food");
        itemDesc.add("3");
        itemDesc.add("3");
        itemDesc.add("3");
        itemDesc.add("3");
        itemDesc.add("3");
        itemDesc.add("3");
        itemDesc.add("3");
        itemDesc.add("3");
        itemDesc.add("3");

//        String[] itemDesc = {
//                "The finest dining.",
//                "Authentic Mexican food.",
//                "#value",
//                "five boys",
//                "is this even chinese food",
//                "turns u into a bibighost",
//                "best value in town",
//                "expensive pizza 800 dollars",
//                "taco hell",
//                "not very good",
//                "meditererananan chipotle",
//                "big sushi boat"
//        };

        final ListActivityAdapter adapter = new ListActivityAdapter(this, itemIcon, itemName, itemDesc);
        itemList = (ListView)findViewById(R.id.item_list);
        itemList.setAdapter(adapter);

        itemList.setClickable(true);
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Object o = itemList.getItemAtPosition(position);

                // Remove item from the list
                itemIcon.remove(position);
                itemName.remove(position);
                itemDesc.remove(position);

                // Notify adapter of change
                adapter.notifyDataSetChanged();
            }
        });

//        // Call function every 500 ms
//        handler.postDelayed(new Runnable() {
//            public void run() {
//                // Highlight a different entry
//                if (spinCount > 0) {
//                    spinCount -= 1;
//
//                    if (index < totalItems - 1) {
//                        // remove border
//                        itemList.getChildAt(index).setBackgroundResource(0);
//                        index += 1;
//                        // set border
//                        itemList.getChildAt(index).setBackgroundResource(R.drawable.activity_list_item_border);
//                    }
//                    else {
//                        // remove border
//                        itemList.getChildAt(index).setBackgroundResource(0);
//                        // set border
//                        index = 0;
//                        itemList.getChildAt(index).setBackgroundResource(R.drawable.activity_list_item_border);
//                    }
//                    handler.postDelayed(this, delay);
//                }
//            }
//        }, delay);

    }

    public void spinButton(View view) {
        // Choose random
        Random random = new Random();
        int resultIndex = random.nextInt(itemName.size());

        // Create array of result info
        ArrayList result = new ArrayList();
        result.add(itemIcon.get(resultIndex));
        result.add(itemName.get(resultIndex));
        result.add(itemDesc.get(resultIndex));

        /**
        // Open the result page
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(SPIN_RESULT, result);
        startActivity(intent);
         **/

        /** TOMMY */
        // Open the slot
        Intent intent = new Intent(this, SlotActivity.class);
        intent.putExtra(SPIN_RESULT, result);
        startActivity(intent);
    }
}