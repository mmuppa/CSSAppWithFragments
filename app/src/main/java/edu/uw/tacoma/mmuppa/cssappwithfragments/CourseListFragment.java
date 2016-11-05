package edu.uw.tacoma.mmuppa.cssappwithfragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import edu.uw.tacoma.mmuppa.cssappwithfragments.model.Course;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnCourseInteractionListener}
 * interface.
 */
public class CourseListFragment extends Fragment implements AbsListView.OnItemClickListener {

    private OnCourseInteractionListener mCallback;

    private ArrayList<Course> mCourseList;
    private static final String COURSE_URL
            = "http://cssgate.insttech.washington.edu/~mmuppa/Android/test.php?cmd=courses";
    private ProgressDialog mProgressDialog;
    private String mCourses;
    private TextView mTextView;
    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mCoursesListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CourseListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_courselist_list, container, false);


        mTextView = (TextView) view.findViewById(R.id.course_error);

        mCoursesListView = (ListView) view.findViewById(R.id.course_list_view);
        // Set OnItemClickListener so we can be notified on item clicks
        mCoursesListView.setOnItemClickListener(this);
       /* mCoursesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                FragmentManager fragMgr = getFragmentManager();
                FragmentTransaction ft = fragMgr.beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.fragment_course_list_containter, new CourseFragment());
                ft.commit();
            }
        });*/

        mCourseList = new ArrayList<>();
        mAdapter = new CourseAdapter(view.getContext(), mCourseList);



        DownloadWebPageTask task = new DownloadWebPageTask();
        task.execute(new String[]{COURSE_URL});

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnCourseInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnCourseInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mCallback) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            //Toast.makeText(view.getContext(), "Got here", Toast.LENGTH_LONG).show();
            mCallback.onCourseSelected(mCourseList.get(position));
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnCourseInteractionListener {
        public void onCourseSelected(Course course);
    }

    /**
     * Running the loading of the JSON in a separate thread.
     * Code adapted from http://www.vogella.com/tutorials/AndroidBackgroundProcessing/article.html
     */
    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to download the list of courses, Reason: "
                            + e.getMessage();
                }
                finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }


        /**
         * It checks to see if there was a problem with the URL(Network) which is when an
         * exception is caught. If there was an exception, it is displayed in red using the text
         * view widget. It tries to call the parse Method and checks to see if it was successful.
         * If not, it displays the exception.
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            if (result.startsWith("Unable to")) {
                mTextView.setText(result);
                mTextView.setTextColor(Color.RED);
                return;
            }

            mCourses = result;
            result = Course.parseCourseJSON(mCourses, mCourseList);
            // Something wrong with the JSON returned.
            if (result != null) {
                mTextView.setTextColor(Color.RED);
                mTextView.setText(result);
                return;
            }

            // Everything is good, show the list of courses.
            if (!mCourseList.isEmpty()) {
                mCoursesListView.setAdapter(mAdapter);
            }
        }
    }
}
