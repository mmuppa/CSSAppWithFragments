package edu.uw.tacoma.mmuppa.cssappwithfragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;

import edu.uw.tacoma.mmuppa.cssappwithfragments.model.Instructor;


public class InstructorFragment extends Fragment {

    private Instructor mInstructor;

    private EditText mNameEditText;
    private EditText mTitleEditText;
    private EditText mOfficeEditText;
    private EditText mEmailEditText;
    private ImageView mImageView;

    private static final int IO_BUFFER_SIZE = 8 * 1024;

    private Button mAddButton;

    public final static String INSTRUCTOR_DATA = "instructor";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_instructor, container, false);

        mNameEditText = (EditText) v.findViewById(R.id.instructor_name);
        mTitleEditText = (EditText) v.findViewById(R.id.instructor_title);
        mOfficeEditText = (EditText) v.findViewById(R.id.instructor_office);
        mEmailEditText = (EditText) v.findViewById(R.id.instructor_email);
        mImageView = (ImageView) v.findViewById(R.id.instructor_image);

        Bundle args = getArguments();
        if (args != null) {
            Instructor instructor = (Instructor) args.getSerializable(INSTRUCTOR_DATA);
            if (instructor != null) {
                mNameEditText.setText(instructor.getFullName());
                mTitleEditText.setText(instructor.getTitle());
                mOfficeEditText.setText(instructor.getOffice());
                mEmailEditText.setText(instructor.getEmail());
                String photoUrl = instructor.getPhotoUrl();
                if (photoUrl != null) {
                        String urlStr = Instructor.IMAGE_URL+photoUrl;
                        DownloadImageTask task = new DownloadImageTask();
                        task.execute(new String[]{urlStr});
                }
            }

        }
        return v;
    }

    /**
     * Running the loading of the JSON in a separate thread.
     * Code adapted from http://www.vogella.com/tutorials/AndroidBackgroundProcessing/article.html
     */
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap bitmap = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    InputStream is = new BufferedInputStream(urlObject.openStream());
                     bitmap = BitmapFactory.decodeStream(is);

                } catch (Exception e) {
                    String response = "Unable to download the image, Reason: "
                            + e.getMessage();
                }
            }
            return bitmap;
        }


        /**
         * It checks to see if there was a problem with the URL(Network) which is when an
         * exception is caught. If there was an exception, it is displayed in red using the text
         * view widget. It tries to call the parse Method and checks to see if it was successful.
         * If not, it displays the exception.
         * @param bitmap
         */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
           mImageView.setImageBitmap(bitmap);
        }
    }

}
