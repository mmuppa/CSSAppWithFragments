package edu.uw.tacoma.mmuppa.cssappwithfragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class NonTabActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_tab);

        // Display the Back Navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = null;
        Intent intent = getIntent();
        if (intent.getStringExtra(MainActivity.SHOW).equals(MainActivity.SHOW_ABOUT)) {
            fragment = new AboutFragment();
        } else if (intent.getStringExtra(MainActivity.SHOW).equals(MainActivity.SHOW_ADD_COURSE)) {
            fragment = new CourseFragment();
            if (intent.getSerializableExtra(CourseFragment.COURSE_DATA) != null) {
                Bundle data = new Bundle();
                data.putSerializable(CourseFragment.COURSE_DATA
                        , intent.getSerializableExtra(CourseFragment.COURSE_DATA));
                fragment.setArguments(data);
            }
        } else if (intent.getStringExtra(MainActivity.SHOW).equals(MainActivity.SHOW_INSTRUCTOR)) {
            Bundle data = new Bundle();
            data.putSerializable(InstructorFragment.INSTRUCTOR_DATA
                    , intent.getSerializableExtra(InstructorFragment.INSTRUCTOR_DATA));
            fragment = new InstructorFragment();
            fragment.setArguments(data);
        }
        fm.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_non_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home)  {
            if (NavUtils.getParentActivityName(this) != null) {
                NavUtils.navigateUpFromSameTask(this);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
