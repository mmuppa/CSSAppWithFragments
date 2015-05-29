package edu.uw.tacoma.mmuppa.cssappwithfragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.uw.tacoma.mmuppa.cssappwithfragments.model.Course;


/**
 * Created by mmuppa on 4/14/15.
 */
public class CourseAdapter extends ArrayAdapter<Course> {

    public CourseAdapter(Context context, ArrayList<Course> objects) {
        super(context, 0, objects);
    }


    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public Course getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Course course = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_course_item
                    , parent, false);
        }
        // Lookup view for data population
        TextView id = (TextView) convertView.findViewById(R.id.item_course_id);
        TextView shortDesc = (TextView) convertView.findViewById(R.id.item_course_short_desc);
        TextView longDesc = (TextView) convertView.findViewById(R.id.item_course_long_desc);

        // Populate the data into the template view using the data object
        id.setText(course.getCourseId());
        shortDesc.setText(course.getShortDescription());
        longDesc.setText(course.getLongDescription());

        // Return the completed view to render on screen
        return convertView;
    }
}
