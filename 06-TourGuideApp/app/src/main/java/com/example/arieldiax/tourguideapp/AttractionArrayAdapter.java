package com.example.arieldiax.tourguideapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AttractionArrayAdapter extends ArrayAdapter<Attraction> {

    /**
     * Creates a new AttractionArrayAdapter object.
     *
     * @param context     Instance of the Context class.
     * @param attractions List of the Attraction objects.
     */
    public AttractionArrayAdapter(Context context, ArrayList<Attraction> attractions) {
        super(context, 0, attractions);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Attraction attraction = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_attraction, parent, false);
        }
        ImageView attractionImageImageView = (ImageView) convertView.findViewById(R.id.attraction_image_image_view);
        attractionImageImageView.setImageResource(attraction.getAttractionImageResourceId());
        TextView attractionNameTextView = (TextView) convertView.findViewById(R.id.attraction_name_text_view);
        attractionNameTextView.setText(attraction.getAttractionName());
        TextView attractionDescriptionTextView = (TextView) convertView.findViewById(R.id.attraction_description_text_view);
        attractionDescriptionTextView.setText(attraction.getAttractionDescription());
        return convertView;
    }
}
