package edu.uw.tacoma.mmuppa.cssappwithfragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.uw.tacoma.mmuppa.cssappwithfragments.data.CourseDB;
import edu.uw.tacoma.mmuppa.cssappwithfragments.model.Course;


public class CourseFragment extends Fragment {

    private Course mCourse;

    private EditText mCourseIdEditText;
    private EditText mCourseShortDescriptionEditText;
    private EditText mCourseLongDescriptionEditText;
    private EditText mCoursePrereqsEditText;
    private Button mAddButton;

    private CourseDB mCourseDB;

    public final static String COURSE_DATA = "course";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_course, container, false);

        mCourseIdEditText = (EditText) v.findViewById(R.id.course_id_edit_text);
        mCourseShortDescriptionEditText = (EditText) v.findViewById(R.id.course_short_desc_edit_text);
        mCourseLongDescriptionEditText = (EditText) v.findViewById(R.id.course_long_desc_edit_text);
        mCoursePrereqsEditText = (EditText) v.findViewById(R.id.course_prereqs_edit_text);
        mAddButton = (Button) v.findViewById(R.id.course_add_button);

        mCourseDB = new CourseDB(v.getContext());


        Bundle args = getArguments();
        if (args != null) {
            Course course = (Course) args.getSerializable(COURSE_DATA);
            if (course != null) {
                mCourseIdEditText.setText(course.getCourseId());
                mCourseShortDescriptionEditText.setText(course.getShortDescription());
                mCourseLongDescriptionEditText.setText(course.getLongDescription());
                mCoursePrereqsEditText.setText(course.getPrereqs());
            }

            //TODO -  Should update the data rather than insert here.

        }

            mAddButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String courseId = mCourseIdEditText.getText().toString();
                    String shortDesc = mCourseShortDescriptionEditText.getText().toString();
                    String longDesc = mCourseLongDescriptionEditText.getText().toString();
                    String prereqs = mCoursePrereqsEditText.getText().toString();

                    if (courseId.length() == 0 || shortDesc.length() == 0
                            || longDesc.length() == 0) {
                        Toast.makeText(v.getContext(), "Please enter all fields except for prereqs"
                                , Toast.LENGTH_SHORT)
                                .show();
                        return;
                    }
                    if (prereqs.length() == 0) prereqs = null;
                    mCourse = new Course(courseId, shortDesc, longDesc, prereqs);

                    try {
                        mCourseDB.insert(courseId, shortDesc, longDesc, prereqs);
                    } catch (Exception e) {
                        Toast.makeText(v.getContext(), e.toString(), Toast.LENGTH_LONG)
                                .show();

                        return;
                    }
                    Toast.makeText(v.getContext(), "Course added successfully!", Toast.LENGTH_SHORT)
                            .show();

                }
            });

        return v;
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mCourseDB != null)
            mCourseDB.close();
    }

}
