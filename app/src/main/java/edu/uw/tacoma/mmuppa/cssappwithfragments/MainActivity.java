package edu.uw.tacoma.mmuppa.cssappwithfragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import edu.uw.tacoma.mmuppa.cssappwithfragments.model.Course;
import edu.uw.tacoma.mmuppa.cssappwithfragments.model.Instructor;
import edu.uw.tacoma.mmuppa.cssappwithfragments.view.SlidingTabLayout;


public class MainActivity extends ActionBarActivity implements
        CourseListFragment.OnCourseInteractionListener,
        InstructorListFragment.OnInstructorInteractionListener {

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private SlidingTabLayout mSlidingTabLayout;
    private CharSequence mTitles[]={"Courses","Instructors"};
    private int mTabs =2;

    public static final String SHOW = "show";
    public static final String SHOW_ADD_COURSE = "AddCourse";
    public static final String SHOW_ABOUT = "About";
    public static final String SHOW_INSTRUCTOR = "Instructor";

;    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Creating The Toolbar and setting it as the Toolbar for the activity
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);


        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        mViewPagerAdapter =  new ViewPagerAdapter(getSupportFragmentManager(),mTitles, mTabs);

        // Assigning ViewPager View and setting the adapter
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mViewPagerAdapter);

        // Assigning the Sliding Tab Layout View
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.tabs);
        mSlidingTabLayout.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.ColorPrimaryDark));

        // Setting the ViewPager For the SlidingTabsLayout
        mSlidingTabLayout.setViewPager(mViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_new)  {
            Intent intent = new Intent(this, NonTabActivity.class);
            intent.putExtra(SHOW, SHOW_ADD_COURSE);
            startActivity(intent);
        } else if (id == R.id.action_about) {

           Intent intent = new Intent(this, NonTabActivity.class);
           intent.putExtra(SHOW, SHOW_ABOUT);
           startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCourseSelected(Course course) {
        /*CourseFragment fragment = new CourseFragment();
        Bundle data = new Bundle();
        data.putSerializable(CourseFragment.COURSE_DATA, course);
        fragment.setArguments(data);
        mViewPagerAdapter.replaceFragment(fragment, 0);*/
        Intent intent = new Intent(this, NonTabActivity.class);
        intent.putExtra(SHOW, SHOW_ADD_COURSE);
        intent.putExtra(CourseFragment.COURSE_DATA, course);
        startActivity(intent);
    }

    @Override
    public void onInstructorSelected(Instructor instructor) {
        Intent intent = new Intent(this, NonTabActivity.class);
        intent.putExtra(SHOW, SHOW_INSTRUCTOR);
        intent.putExtra(InstructorFragment.INSTRUCTOR_DATA, instructor);
        startActivity(intent);
        /*InstructorFragment fragment = new InstructorFragment();
        Bundle data = new Bundle();
        data.putSerializable(InstructorFragment.INSTRUCTOR_DATA, instructor);
        fragment.setArguments(data);
        mViewPagerAdapter.replaceFragment(fragment, 1);*/
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public Fragment[] getFragments() {
            return mFragments;
        }

        private Fragment[] mFragments;
        private CharSequence mTitles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
        private int mTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created

        public void replaceFragment(Fragment fragment, int index) {
            getSupportFragmentManager().beginTransaction().remove(mFragments[index]).commit();
            mFragments[index] = fragment;
           notifyDataSetChanged();
        }

        // Build a Constructor and assign the passed Values to appropriate values in the class
        public ViewPagerAdapter(android.support.v4.app.FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb) {
            super(fm);
            mFragments = new Fragment[2];
            mFragments[0] = new CourseListFragment();
            mFragments[1] = new InstructorListFragment();
            this.mTitles = mTitles;
            this.mTabs = mNumbOfTabsumb;

        }

        //This method return the fragment for the every position in the View Pager
        @Override
        public Fragment getItem(int position) {

            return mFragments[position];

        }


        // This method return the titles for the Tabs in the Tab Strip

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        // This method return the Number of tabs for the tabs Strip

        @Override
        public int getCount() {
            return mTabs;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

    }
}
