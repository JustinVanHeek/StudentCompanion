package vanheek.justin.dev.studentcompanion;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;

import vanheek.justin.dev.studentcompanion.objects.Course;
import vanheek.justin.dev.studentcompanion.objects.Exam;
import vanheek.justin.dev.studentcompanion.objects.Milestone;
import vanheek.justin.dev.studentcompanion.objects.Semester;

/**
 * Created by justi on 2017-12-30.
 */

public class SemesterFragment extends ListFragment implements AdapterView.OnItemClickListener {

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.assignments_layout, container, false);

        //Change Header Title
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Semesters");

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
        SemesterArrayAdapter adapter = new SemesterArrayAdapter(getActivity(), ((MainActivity) getActivity()).semesters,this);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    //Set this semester as the current semester and return to course list
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ((MainActivity) getActivity()).currentSemester=position;

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new CoursesFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_item_add_course:
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new EditSemesterFragment()).commit();
                return true;
        }
        return false;
    }

}
