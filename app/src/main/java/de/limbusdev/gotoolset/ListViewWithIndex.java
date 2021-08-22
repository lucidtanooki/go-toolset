package de.limbusdev.gotoolset;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;


public class ListViewWithIndex extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] names;
    private final Integer[] indices;


    public ListViewWithIndex(Activity context, int start, String[] names) {
        super(context, R.layout.index_list_item, names);

        this.context = context;
        this.names = names;
        this.indices = new Integer[names.length];
        for(int i=0; i<names.length; i++) {
            indices[i] = i+start;
        }
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.index_list_item, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.index_item_type_txt);
        txtTitle.setText(names[position]);

        TextView indexTitle = (TextView) rowView.findViewById(R.id.index_item_type_index);
        indexTitle.setText(Integer.toString(indices[position]));

        ImageView imageView = (ImageView) rowView.findViewById(R.id.index_item_type_img);
        imageView.setImageResource(R.drawable.monster_missing);

        return rowView;
    }
}