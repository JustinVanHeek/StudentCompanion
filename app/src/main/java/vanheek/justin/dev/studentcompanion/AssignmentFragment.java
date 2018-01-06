package vanheek.justin.dev.studentcompanion;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ToggleButton;

import java.util.ArrayList;

import vanheek.justin.dev.studentcompanion.objects.Assignment;
import vanheek.justin.dev.studentcompanion.objects.Course;
import vanheek.justin.dev.studentcompanion.objects.Milestone;

/**
 * Created by justi on 2017-12-29.
 */

public class AssignmentFragment extends ListFragment implements AdapterView.OnItemClickListener {

    View myView;
    private Assignment[] assignmentsByDueDate;
    private Assignment[] assignmentsByMilestone;
    private boolean byDueDate = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.assignments_layout, container, false);

        //If there is no semester, force the user to go to the semester screen to create one
        if(((MainActivity) getActivity()).semesters.size() == 0) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, new SemesterFragment()).commit();
        }
        else {

            //Change Header Title
            ((MainActivity) getActivity()).getSupportActionBar().setTitle("Assignments");
            ((MainActivity) getActivity()).findViewById(R.id.toolbar).setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            //Sorting button
            myView.findViewById(R.id.toggleButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (((ToggleButton) myView.findViewById(R.id.toggleButton)).isChecked()) {
                        Log.d("Assignment List", "Sort by Milestone");
                        listByMilestone();
                        byDueDate = false;
                    } else {
                        Log.d("Assignment List", "Sort by Due Date");
                        listByDueDate();
                        byDueDate = true;
                    }
                }
            });
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

    private void listByDueDate() {
        getByDueDate();
        AssignmentArrayAdapter adapter = new AssignmentArrayAdapter(getActivity(), assignmentsByDueDate);
        adapter.notifyDataSetChanged();
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    private void getByDueDate() {
        ArrayList<Assignment> assignments = getAllAssignments();
        ArrayList<Assignment> sortedAssignments = new ArrayList<Assignment>();
        while(assignments.size() > 0) {
            Assignment earliest = null;
            for(Assignment a : assignments) {
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
        assignmentsByDueDate = new Assignment[sortedAssignments.size()];
        for(int i = 0; i < assignmentsByDueDate.length; i++) {
            assignmentsByDueDate[i] = sortedAssignments.get(i);
        }
    }

    private void listByMilestone() {
        getByMilestone();
        AssignmentArrayAdapter adapter = new AssignmentArrayAdapter(getActivity(), assignmentsByMilestone);
        adapter.notifyDataSetChanged();
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    private void getByMilestone() {
        ArrayList<Assignment> assignments = getAllAssignments();
        ArrayList<Assignment> dateAssignments = new ArrayList<Assignment>();
        while(assignments.size() > 0) {
            Assignment earliest = null;
            for(Assignment a : assignments) {
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
            dateAssignments.add(earliest);
        }
        ArrayList<Assignment> sortedAssignments = new ArrayList<Assignment>();
        while(dateAssignments.size() > 0) {
            Assignment earliest = null;
            for(Assignment a : dateAssignments) {
                if(earliest == null) {
                    earliest = a;
                }
                else {
                    for(Milestone m : a.getMilestones()) {
                        if(m.getDate() != null && !m.isComplete()) {
                            if(earliest.getMilestones().size() == 0) {
                                earliest = a;
                            }
                            else {
                                for (Milestone m2 : earliest.getMilestones()) {
                                    if (m2.getDate() != null && !m2.isComplete()) {
                                        if (m.getDate().compareTo(m2.getDate()) < 0) {
                                            earliest = a;
                                        }
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
            }
            if(earliest == null) {
                earliest = dateAssignments.get(0);
            }
            dateAssignments.remove(earliest);
            sortedAssignments.add(earliest);
        }
        assignmentsByMilestone = new Assignment[sortedAssignments.size()];
        for(int i = 0; i < assignmentsByMilestone.length; i++) {
            assignmentsByMilestone[i] = sortedAssignments.get(i);
        }
    }

    private ArrayList<Assignment> getAllAssignments() {
        ArrayList<Assignment> assignments = new ArrayList<Assignment>();
        for(Course c : ((MainActivity) getActivity()).semesters.get(((MainActivity) getActivity()).currentSemester).getCourses()) {
            for(Assignment a : c.getAssignments()) {
                assignments.add(a);
            }
        }
        return assignments;
    }

    //Open the EditCourseFragment with the clicked course
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Assignment assignment;
        if(byDueDate) {
            assignment = assignmentsByDueDate[position];
        }
        else {
            assignment = assignmentsByMilestone[position];
        }
        AssignmentDetailsFragment frag = new AssignmentDetailsFragment();
        frag.setAssignment(assignment);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_item_add_course:
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new EditAssignmentFragment()).commit();
                return true;
        }
        return false;
    }
}
