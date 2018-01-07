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

/**
 * Created by justi on 2017-12-29.
 */

public class GradePredictionFragment extends ListFragment implements AdapterView.OnItemClickListener {

    View myView;
    private Course[] courses;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.grade_prediction_layout, container, false);

        //If there is no semester, force the user to go to the semester screen to create one
        if(((MainActivity) getActivity()).semesters.size() == 0) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, new SemesterFragment()).commit();
        }
        else {

            //Change Header Title
            ((MainActivity) getActivity()).getSupportActionBar().setTitle("Grade Predictions");
            ((MainActivity) getActivity()).findViewById(R.id.toolbar).setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
        return myView;
    }

    //Creates the list of courses
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        generateList();
    }

    private void generateList() {
        courses = ((MainActivity)getActivity()).semesters.get(((MainActivity)getActivity()).currentSemester).getCoursesAsArray();
        GradePredictionArrayAdapter adapter = new GradePredictionArrayAdapter(getActivity(), courses);
        adapter.notifyDataSetChanged();
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    //Open the CourseDetailsFragment with the clicked course
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Course c = courses[position];
        CourseDetailsFragment frag = new CourseDetailsFragment();
        frag.setCourse(c);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
    }


}
