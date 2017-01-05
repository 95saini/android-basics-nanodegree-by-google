package com.example.arieldiax.tourguideapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class RomeFragment extends Fragment {

    /**
     * Creates a new RomeFragment object.
     */
    public RomeFragment() {
        // Required empty public constructor.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_attraction, container, false);
        final ArrayList<Attraction> attractions = new ArrayList<Attraction>();
        attractions.add(new Attraction(getString(R.string.rome_attraction_1_name), getString(R.string.rome_attraction_1_description), R.drawable.rome_colosseum, Double.parseDouble(getString(R.string.rome_attraction_1_latitude)), Double.parseDouble(getString(R.string.rome_attraction_1_longitude))));
        attractions.add(new Attraction(getString(R.string.rome_attraction_2_name), getString(R.string.rome_attraction_2_description), R.drawable.rome_pantheon, Double.parseDouble(getString(R.string.rome_attraction_2_latitude)), Double.parseDouble(getString(R.string.rome_attraction_2_longitude))));
        attractions.add(new Attraction(getString(R.string.rome_attraction_3_name), getString(R.string.rome_attraction_3_description), R.drawable.rome_roman_forum, Double.parseDouble(getString(R.string.rome_attraction_3_latitude)), Double.parseDouble(getString(R.string.rome_attraction_3_longitude))));
        attractions.add(new Attraction(getString(R.string.rome_attraction_4_name), getString(R.string.rome_attraction_4_description), R.drawable.rome_trevi_fountain, Double.parseDouble(getString(R.string.rome_attraction_4_latitude)), Double.parseDouble(getString(R.string.rome_attraction_4_longitude))));
        attractions.add(new Attraction(getString(R.string.rome_attraction_5_name), getString(R.string.rome_attraction_5_description), R.drawable.rome_spanish_steps, Double.parseDouble(getString(R.string.rome_attraction_5_latitude)), Double.parseDouble(getString(R.string.rome_attraction_5_longitude))));
        attractions.add(new Attraction(getString(R.string.rome_attraction_6_name), getString(R.string.rome_attraction_6_description), R.drawable.rome_st_peters_basilica, Double.parseDouble(getString(R.string.rome_attraction_6_latitude)), Double.parseDouble(getString(R.string.rome_attraction_6_longitude))));
        AttractionArrayAdapter attractionArrayAdapter = new AttractionArrayAdapter(getActivity(), attractions);
        ListView listAttractionListView = (ListView) rootView.findViewById(R.id.list_attraction_list_view);
        listAttractionListView.setAdapter(attractionArrayAdapter);
        listAttractionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Attraction attraction = attractions.get(position);
                Uri gmmIntentUri = Uri.parse("geo:" + attraction.getAttractionLatitude() + "," + attraction.getAttractionLongitude() + "?q=" + Uri.encode(attraction.getAttractionName()));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    Toast.makeText(getContext(), R.string.message_no_applications_found, Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rootView;
    }
}
