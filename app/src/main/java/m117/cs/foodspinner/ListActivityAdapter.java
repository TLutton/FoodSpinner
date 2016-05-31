package m117.cs.foodspinner;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListActivityAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final ArrayList<Integer> itemIcon;
    private final ArrayList<String> itemName;
    private final ArrayList<String> itemDesc;

    public ListActivityAdapter(Activity context, ArrayList<Integer> itemIcon, ArrayList<String> itemName, ArrayList<String> itemDesc) {
        super(context, R.layout.activity_list_item_list, itemName);

        this.context = context;
        this.itemIcon = itemIcon;
        this.itemName = itemName;
        this.itemDesc = itemDesc;
    }

    public View getView(int index, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View row = inflater.inflate(R.layout.activity_list_item_list, null,true);

        ImageView itemIconView = (ImageView) row.findViewById(R.id.list_item_icon);
        TextView itemNameView = (TextView) row.findViewById(R.id.list_item_name);
        TextView itemDescView = (TextView) row.findViewById(R.id.list_item_desc);

        itemIconView.setImageResource((int) itemIcon.get(index));
        itemNameView.setText((String) itemName.get(index));
        itemDescView.setText((String) itemDesc.get(index));
        return row;
    }
}
