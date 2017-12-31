package vanheek.justin.dev.studentcompanion;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import vanheek.justin.dev.studentcompanion.objects.Assignment;
import vanheek.justin.dev.studentcompanion.objects.Course;

/**
 * Created by justi on 2017-12-30.
 */

public class EditAssignmentFragment extends Fragment {

    View myView;
    private Assignment assignment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.edit_assignment_layout, container, false);

        //Change Header Title
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Edit Assignment");

        //Button Listeners
        myView.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new AssignmentFragment()).commit();
            }

        });
        myView.findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                updateAssignment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new AssignmentFragment()).commit();
            }

        });

        //Fill form
        fillAssignmentForm();

        return myView;
    }

    private void updateAssignment() {
        if(assignment == null) {
            assignment = new Assignment();
            assignment.getCourse().addAssignment(assignment);
        }

        //TODO Course
        assignment.setName(((EditText) myView.findViewById(R.id.editName)).getText().toString());
        assignment.setPercentOfGrade(Double.parseDouble(((EditText) myView.findViewById(R.id.editPercent)).getText().toString()));
        //TODO Due date
        //TODO Milestones
        assignment.setComplete(((CheckBox) myView.findViewById(R.id.editComplete)).isChecked());
        try {
            assignment.setGrade(Integer.parseInt(((EditText) myView.findViewById(R.id.editGrade)).getText().toString()));
        }
        catch (NumberFormatException e){
            assignment.setGrade(-1);
        }
        assignment.setNotes(((EditText) myView.findViewById(R.id.editNotes)).getText().toString());
    }

    private void fillAssignmentForm() {
        if(assignment != null) {

            ((EditText) myView.findViewById(R.id.editCourse)).setText(assignment.getCourse().getName());
            ((EditText) myView.findViewById(R.id.editName)).setText(assignment.getName());
            ((EditText) myView.findViewById(R.id.editPercent)).setText(assignment.getPercentOfGrade()+"");
            //TODO Due date
            //TODO Milestones
            ((CheckBox) myView.findViewById(R.id.editComplete)).setChecked(assignment.isComplete());
            double grade = assignment.getGrade();
            if(grade < 0) {
                ((EditText) myView.findViewById(R.id.editGrade)).setText("");
            }
            else {
                ((EditText) myView.findViewById(R.id.editGrade)).setText(assignment.getGrade() + "");
            }
            ((EditText) myView.findViewById(R.id.editNotes)).setText(assignment.getNotes());



        }
        else {
            ((EditText) myView.findViewById(R.id.editCourse)).setText("TODO");
        }
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

}
