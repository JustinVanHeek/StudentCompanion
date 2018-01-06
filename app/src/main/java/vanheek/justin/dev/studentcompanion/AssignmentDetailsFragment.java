package vanheek.justin.dev.studentcompanion;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import vanheek.justin.dev.studentcompanion.objects.Assignment;
import vanheek.justin.dev.studentcompanion.objects.Course;
import vanheek.justin.dev.studentcompanion.objects.Exam;
import vanheek.justin.dev.studentcompanion.objects.Milestone;
import vanheek.justin.dev.studentcompanion.objects.WeeklySchedule;

/**
 * Created by justi on 2017-12-30.
 */

public class AssignmentDetailsFragment extends Fragment {

    View myView;
    Assignment assignment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.assignment_details_layout, container, false);

        //Change Header Title
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Assignment Details");

        //Change color
        ((MainActivity)getActivity()).findViewById(R.id.toolbar).setBackgroundColor(assignment.getCourse().getColor());
        myView.findViewById(R.id.assignmentName).setBackgroundColor(assignment.getCourse().getColor());
        myView.findViewById(R.id.assignmentCourse).setBackgroundColor(assignment.getCourse().getColor());
        int[][] states = {new int[] { android.R.attr.state_enabled}, // enabled
                new int[] {-android.R.attr.state_enabled}, // disabled
                new int[] {-android.R.attr.state_checked}, // unchecked
                new int[] { android.R.attr.state_pressed}  // pressed
        };
        int[] colors = {assignment.getCourse().getColor(),assignment.getCourse().getColor(),assignment.getCourse().getColor(),assignment.getCourse().getColor()};
        ColorStateList csl = new ColorStateList(states,colors);
        ((ProgressBar) myView.findViewById(R.id.progressBar)).setProgressTintList(csl);

        //Update progress bar
        ((ListView) myView.findViewById(R.id.assignmentMilestones)).setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                updateProgressBar();
            }

        });

        //Completed Checkbox functionality
        ((CheckBox) myView.findViewById(R.id.assignmentCompleted)).setChecked(assignment.isComplete());
        ((CheckBox) myView.findViewById(R.id.assignmentCompleted)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                assignment.setComplete(((CheckBox) myView.findViewById(R.id.assignmentCompleted)).isChecked());
                updateProgressBar();
            }

        });

        //Add Milestone Button
        myView.findViewById(R.id.assignmentAddMilestone).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                EditMilestoneFragment frag = new EditMilestoneFragment();
                Milestone e = new Milestone();
                frag.setMilestone(e,assignment);
                frag.setCameFromAssignmentDetails(true);
                fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
            }

        });

        fillDetails();

        return myView;
    }

    public void updateProgressBar() {
        //Calculate progress
        int p = getProgress();
        if (assignment.isComplete()) {
            p = 100;
        }

        //Show progress
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ((ProgressBar) myView.findViewById(R.id.progressBar)).setProgress(p,true);
        }
        else {
            ((ProgressBar) myView.findViewById(R.id.progressBar)).setProgress(p);
        }
        //Save changes to storage
        DataManager.saveData((MainActivity) getActivity());
    }

    public int getProgress() {
        if(assignment.getMilestones().size() > 0) {
            double weight = 100 / assignment.getMilestones().size();
            int complete = 0;
            for (Milestone m : assignment.getMilestones()) {
                if (m.isComplete()) {
                    complete++;
                }
            }
            return (int) Math.ceil(complete * weight);
        }
        else {
            return 0;
        }
    }

    private void fillDetails() {
        ((TextView) myView.findViewById(R.id.assignmentName)).setText(assignment.getName());
        ((TextView) myView.findViewById(R.id.assignmentCourse)).setText(assignment.getCourse().getName());
        ((CheckBox) myView.findViewById(R.id.assignmentCompleted)).setChecked(assignment.isComplete());
        if(assignment.getDue() != null) {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            ((TextView) myView.findViewById(R.id.assignmentDueDate)).setText("Due: " + format.format(assignment.getDue().getTime()));
        }
        else {
            ((TextView) myView.findViewById(R.id.assignmentDueDate)).setText("");
        }
        if(assignment.getGrade() > -1) {
            ((TextView) myView.findViewById(R.id.assignmentGrade)).setText("Grade: " + assignment.getGrade() + "%");
        }
        else {
            ((TextView) myView.findViewById(R.id.assignmentGrade)).setText("Grade: --");
        }
        ((TextView) myView.findViewById(R.id.assignmentWeight)).setText("Weight: " + assignment.getPercentOfGrade() + "%");
        updateProgressBar();
        fillMilestones();

    }

    private void fillMilestones() {
        MilestoneArrayAdapter adapter = new MilestoneArrayAdapter(getActivity(), assignment.getMilestonesAsArray());
        adapter.setAssignmentViewer(this);
        ListView list = ((ListView) myView.findViewById(R.id.assignmentMilestones));
        list.setAdapter(adapter);
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
        inflater.inflate(R.menu.course_details_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        FragmentManager fragmentManager = getFragmentManager();

        switch (item.getItemId()) {

            case R.id.menu_item_edit_course:
                EditAssignmentFragment frag = new EditAssignmentFragment();
                frag.setAssignment(assignment);
                fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
                return true;
        }
        return false;
    }

    public void setAssignment(Assignment course) {
        this.assignment = course;
    }
}
