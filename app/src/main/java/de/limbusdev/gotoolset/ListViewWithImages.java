package de.limbusdev.gotoolset;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class ListViewWithImages extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] web;
    private final Integer[] imageId;


    public ListViewWithImages(Activity context, String[] effs, Integer[] imgIds) {
        super(context, R.layout.type_list_item, effs);

        this.context = context;
        this.web = effs;
        this.imageId = imgIds;
        for(int i=0; i<imgIds.length; i++)System.out.println(imgIds[i]);

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.type_list_item, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.list_item_type_txt);
        txtTitle.setText(web[position]);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.list_item_type_img);
        imageView.setImageResource(imageId[position]);

        return rowView;
    }
}