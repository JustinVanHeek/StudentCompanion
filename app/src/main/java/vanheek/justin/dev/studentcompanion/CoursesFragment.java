package vanheek.justin.dev.studentcompanion;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.lang.reflect.Field;

import vanheek.justin.dev.studentcompanion.objects.Course;

/**
 * Created by justi on 2017-12-29.
 */

public class CoursesFragment extends ListFragment implements AdapterView.OnItemClickListener {

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.courses_layout, container, false);

        //If there is no semester, force the user to go to the semester screen to create one
        if(((MainActivity) getActivity()).semesters.size() == 0) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, new SemesterFragment()).commit();
        }
        else {

            //Change Header Title
            ((MainActivity) getActivity()).getSupportActionBar().setTitle(((MainActivity) getActivity()).semesters.get(((MainActivity) getActivity()).currentSemester).getName());
            ((MainActivity) getActivity()).findViewById(R.id.toolbar).setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            //Prompt
            if(((MainActivity)getActivity()).semesters.get(((MainActivity) getActivity()).currentSemester).getCourses().size() == 0) {
                myView.findViewById(R.id.coursesPrompt).setVisibility(View.VISIBLE);
            }

            //Clickable Semester Title
            android.support.v7.app.ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                // Disable the default and enable the custom
                actionBar.setDisplayShowTitleEnabled(false);
                actionBar.setDisplayShowCustomEnabled(true);
                View customView = ((MainActivity) getActivity()).getLayoutInflater().inflate(R.layout.actionbar_title, null);
                // Get the textview of the title
                TextView customTitle = (TextView) customView.findViewById(R.id.actionbarTitle);


                // Change the font family (optional)
                customTitle.setTypeface(Typeface.DEFAULT_BOLD);
                customTitle.setTextSize(20);
                customTitle.setTextColor(Color.WHITE);
                customTitle.setText(((MainActivity) getActivity()).semesters.get(((MainActivity) getActivity()).currentSemester).getName());
                // Set the on click listener for the title
                customTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.w("MainActivity", "ActionBar's title clicked.");
                        Util.restoreActionBar((MainActivity) getActivity());
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.content_frame, new SemesterFragment()).commit();

                    }
                });
                // Apply the custom view
                actionBar.setCustomView(customView);
            }
        }

        return myView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Needed to add additional buttons to the top menu
        setHasOptionsMenu(true);
    }

    //Adds the (+) button to the top menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.courses_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //Creates the list of courses
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(((MainActivity) getActivity()).semesters.size() > 0) {
            CourseArrayAdapter adapter = new CourseArrayAdapter(getActivity(), ((MainActivity) getActivity()).semesters.get(((MainActivity) getActivity()).currentSemester).getCoursesAsArray());
            setListAdapter(adapter);
            getListView().setOnItemClickListener(this);
        }
    }

    //Open the EditCourseFragment with the clicked course
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Util.restoreActionBar((MainActivity)getActivity());
        Course course = ((MainActivity) getActivity()).semesters.get(((MainActivity) getActivity()).currentSemester).getCourses().get(position);
        CourseDetailsFragment frag = new CourseDetailsFragment();
        frag.setCourse(course);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        FragmentManager fragmentManager = getFragmentManager();
        Log.d("optionClick",item.getItemId()+" ID");

        switch (item.getItemId()) {

            case R.id.menu_item_add_course:
                Util.restoreActionBar((MainActivity)getActivity());
                fragmentManager.beginTransaction().replace(R.id.content_frame, new EditCourseFragment()).commit();
                return true;
            case android.R.id.home:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new SemesterFragment()).commit();
                return true;
        }
        return false;
    }
}
