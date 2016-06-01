package m117.cs.foodspinner;

import android.content.Intent;
import android.graphics.Bitmap;
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

        // Set Image
        ImageView resultIcon = (ImageView) findViewById(R.id.result_icon);
        if (-1 == (int) result.get(0)) {
            int icon = R.mipmap.test_icon;
            resultIcon.setImageResource(icon);
        } else {
            Bitmap iconBmp = (Bitmap) result.get(1);
            resultIcon.setImageBitmap(iconBmp);
        }

        // Set Name
        String name = (String) result.get(2);
        TextView resultName = (TextView) findViewById(R.id.result_name);
        resultName.setText(name);

        // Set Description
        String desc = (String) result.get(3);
        TextView resultDesc = (TextView) findViewById(R.id.result_desc);
        resultDesc.setText(desc);
    }
}
