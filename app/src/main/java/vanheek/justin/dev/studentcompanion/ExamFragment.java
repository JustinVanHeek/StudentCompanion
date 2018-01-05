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

public class ExamFragment extends ListFragment implements AdapterView.OnItemClickListener {

    View myView;
    private Exam[] allAssignments;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.assignments_layout, container, false);

        //Change Header Title
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Exams");
        ((MainActivity) getActivity()).findViewById(R.id.toolbar).setBackgroundColor(getResources().getColor(R.color.colorPrimary));

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
        getAllAssignments();
        ExamArrayAdapter adapter = new ExamArrayAdapter(getActivity(), allAssignments);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    private void getAllAssignments() {
        ArrayList<Exam> assignments = new ArrayList<Exam>();
        for(Course c : ((MainActivity) getActivity()).semesters[((MainActivity) getActivity()).currentSemester].getCourses()) {
            for(Exam a : c.getExams()) {
                assignments.add(a);
            }
        }
        allAssignments = new Exam[assignments.size()];
        int i = 0;
        for(Exam a : assignments) {
            allAssignments[i] = a;
            i++;
        }
    }

    //Open the EditCourseFragment with the clicked course
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Exam assignment = allAssignments[position];
        ExamDetailsFragment frag = new ExamDetailsFragment();
        frag.setExam(assignment);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_item_add_course:
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new EditExamFragment()).commit();
                return true;
        }
        return false;
    }
}
