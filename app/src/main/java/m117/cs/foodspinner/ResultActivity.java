package m117.cs.foodspinner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        ArrayList result = intent.getParcelableArrayListExtra(ListActivity.SPIN_RESULT);

        int icon = (int) result.get(0);
        String name = (String) result.get(1);
        String desc = (String) result.get(2);

        //ImageView resultIcon = (ImageView) findViewById(R.id.result_icon);
        //resultIcon.setImageResource(icon);

        TextView resultName = (TextView) findViewById(R.id.result_name);
        resultName.setText(name);

        TextView resultDesc = (TextView) findViewById(R.id.result_desc);
        resultDesc.setText(desc);
    }
}
