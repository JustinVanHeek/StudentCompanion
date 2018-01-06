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

import vanheek.justin.dev.studentcompanion.objects.Assignment;
import vanheek.justin.dev.studentcompanion.objects.Course;
import vanheek.justin.dev.studentcompanion.objects.Exam;

/**
 * Created by justi on 2017-12-29.
 */

public class ExamFragment extends ListFragment implements AdapterView.OnItemClickListener {

    View myView;
    private Exam[] examsByDueDate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.exams_layout, container, false);

        //If there is no semester, force the user to go to the semester screen to create one
        if(((MainActivity) getActivity()).semesters.size() == 0) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, new SemesterFragment()).commit();
        }
        else {

            //Change Header Title
            ((MainActivity) getActivity()).getSupportActionBar().setTitle("Exams");
            ((MainActivity) getActivity()).findViewById(R.id.toolbar).setBackgroundColor(getResources().getColor(R.color.colorPrimary));
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

            listByDueDate();
        }
    }

    private ArrayList<Exam> getAllAssignments() {
        ArrayList<Exam> assignments = new ArrayList<Exam>();
        for(Course c : ((MainActivity) getActivity()).semesters.get(((MainActivity) getActivity()).currentSemester).getCourses()) {
            for(Exam a : c.getExams()) {
                assignments.add(a);
            }
        }
        return assignments;
    }

    //Open the EditCourseFragment with the clicked course
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Exam assignment = examsByDueDate[position];
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

    private void listByDueDate() {
        getByDueDate();
        ExamArrayAdapter adapter = new ExamArrayAdapter(getActivity(), examsByDueDate);
        adapter.notifyDataSetChanged();
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    private void getByDueDate() {
        ArrayList<Exam> assignments = getAllAssignments();
        ArrayList<Exam> sortedAssignments = new ArrayList<Exam>();
        while(assignments.size() > 0) {
            Exam earliest = null;
            for(Exam a : assignments) {
                if(earliest == null) {
                    earliest = a;
                }
                else {
                    if(a.compareTo(earliest) < 0) {
                        earliest = a;
                    }
                }
            }
            assignments.remove(earliest);
            sortedAssignments.add(earliest);
        }
        examsByDueDate = new Exam[sortedAssignments.size()];
        for(int i = 0; i < examsByDueDate.length; i++) {
            examsByDueDate[i] = sortedAssignments.get(i);
        }
    }
}
