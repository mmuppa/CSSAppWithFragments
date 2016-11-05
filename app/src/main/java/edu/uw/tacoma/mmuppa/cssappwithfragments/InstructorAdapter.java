package edu.uw.tacoma.mmuppa.cssappwithfragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.uw.tacoma.mmuppa.cssappwithfragments.model.Instructor;


/**
 * Created by mmuppa on 4/14/15.
 */
public class InstructorAdapter extends ArrayAdapter<Instructor> {

    public InstructorAdapter(Context context, ArrayList<Instructor> objects) {
        super(context, 0, objects);
    }


    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public Instructor getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Instructor instructor = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_instructor_item
                    , parent, false);
        }
        // Lookup view for data population
        TextView name = (TextView) convertView.findViewById(R.id.item_instructor_name);
        TextView title = (TextView) convertView.findViewById(R.id.item_instructor_title);
        TextView office = (TextView) convertView.findViewById(R.id.item_instructor_office);

        // Populate the data into the template view using the data object
        name.setText(instructor.getFullName());
        title.setText(instructor.getTitle());
        office.setText(instructor.getOffice());

        // Return the completed view to render on screen
        return convertView;
    }
}
