package edu.uw.tacoma.mmuppa.cssappwithfragments.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by mmuppa on 4/14/15.
 */
public class Instructor implements Serializable {
    private String mFullName;
    private String mTitle;
    private String mEmail;
    private String mOffice;

    public static final String FULL_NAME = "fullName", TITLE = "title"
            , OFFICE = "office", PHOTO_URL = "photoUrl", EMAIL="email";



    public static final String IMAGE_URL = "http://cssgate.insttech.washington.edu/~mmuppa/Android/";
    private String mPhotoUrl;
    private Set<String> mCourses;

    public Instructor(String fullName, String title, String email, String office, String photoUrl) {
        mFullName = fullName;
        mTitle = title;
        mEmail = email;
        mOffice = office;
        mPhotoUrl = photoUrl;
    }

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String fullName) {
        mFullName = fullName;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getOffice() {
        return mOffice;
    }

    public void setOffice(String office) {
        mOffice = office;
    }

    @Override
    public String toString() {
        return "edu.uw.tacoma.mmuppa.cssappwithwebservices.model.Instructor{" +
                ", mFullName='" + mFullName + '\'' +
                ", mTitle='" + mTitle + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mOffice='" + mOffice + '\'' +
                '}';
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        mPhotoUrl = photoUrl;
    }

    /**
     * Parses the json string, returns an error message if unsuccessful.
     * Returns instructors list if success.
     * @param instructorJSON
     * @return
     */
    public static String parseInstructorJSON(String instructorJSON, List<Instructor> instructorList) {
        String reason = null;
        if (instructorJSON != null) {
            try {
                JSONArray arr = new JSONArray(instructorJSON);

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    Instructor instructor = new Instructor(obj.getString(Instructor.FULL_NAME)
                            , obj.getString(Instructor.TITLE)
                            , obj.getString(Instructor.EMAIL)
                            , obj.getString(Instructor.OFFICE)
                            , obj.getString(Instructor.PHOTO_URL));
                    instructorList.add(instructor);
                }
            } catch (JSONException e) {
                reason =  "Unable to parse data, Reason: " + e.getMessage();
            }

        }
        return reason;
    }
}
