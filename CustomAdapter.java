package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Cavid on 5/29/2017.
 */

public class CustomAdapter extends ArrayAdapter<EarthQuake> {

    public CustomAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, 0, objects);
    }
    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
    private String formatMagnitude(double magnitude){
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }
    private int getMagnitudeColor(double magnitude){
        int magnitudeColorResourceId;
        int floor = (int) Math.floor(magnitude);

        switch (floor){
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(),magnitudeColorResourceId);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rootView = convertView;
        if(rootView == null) {

            rootView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        EarthQuake earthQuake = getItem(position);



        TextView magnitude = (TextView) rootView.findViewById(R.id.magnitude);
        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitude.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(earthQuake.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);
        String formattedMagnitude = formatMagnitude(earthQuake.getMagnitude());
        magnitude.setText(formattedMagnitude);


        //get the Address that needs to be splited
        String place = earthQuake.getCity();
        //if String contains of split it two halves
        String distanceLocation = "";
        String location ="";
        if(place.contains("of")){
            String[] parts = place.split("(?<=of)");
            distanceLocation = parts[0];
            location = parts[1];

        }else {
            distanceLocation = "Near the ";
            location = earthQuake.getCity();

        }

        TextView distance = (TextView) rootView.findViewById(R.id.distance);
        distance.setText(distanceLocation);

        TextView city = (TextView) rootView.findViewById(R.id.city);
        city.setText(location);




        Date dateObject = new Date(earthQuake.getTimeInMilliSeconds());


        TextView date = (TextView) rootView.findViewById(R.id.date);
        String formattedDate = formatDate(dateObject);
        date.setText(formattedDate);


        TextView timeView = (TextView) rootView.findViewById(R.id.time);
        // Format the time string (i.e. "4:30PM")
        String formattedTime = formatTime(dateObject);
        // Display the time of the current earthquake in that TextView
        timeView.setText(formattedTime);


        return rootView;
    }
}
