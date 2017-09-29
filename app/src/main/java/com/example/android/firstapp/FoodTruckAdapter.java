package com.example.android.firstapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by bighandsam on 9/24/17.
 */

public class FoodTruckAdapter extends ArrayAdapter<FoodTruck> {
    public FoodTruckAdapter(Context context, FoodTruck[] foodTrucks) {
        super(context, 0, foodTrucks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        FoodTruck foodTruck = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_foodtruck, parent, false);
        }
        // Lookup view for data population
        TextView ftname = (TextView) convertView.findViewById(R.id.ftname);
        TextView ftrating = (TextView) convertView.findViewById(R.id.ftrating);
        TextView ftcuisine = (TextView) convertView.findViewById(R.id.ftcuisine);
        // Populate the data into the template view using the data object
        ftname.setText(foodTruck.getName());
        ftrating.setText(String.valueOf(foodTruck.getRating()));
        ftcuisine.setText(foodTruck.getCuisine());
        // Return the completed view to render on screen
        return convertView;
    }
}