package vanheek.justin.dev.studentcompanion;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import vanheek.justin.dev.studentcompanion.objects.Assignment;
import vanheek.justin.dev.studentcompanion.objects.Course;
import vanheek.justin.dev.studentcompanion.objects.Milestone;
import vanheek.justin.dev.studentcompanion.objects.Semester;

/**
 * Created by justi on 2017-12-30.
 */

public class EditAssignmentFragment extends Fragment {

    View myView;
    private Assignment assignment;
    private Calendar date;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.edit_assignment_layout, container, false);
        if(assignment == null) {
            assignment = new Assignment();
        }
        date=assignment.getDue();

        //Change Header Title
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Edit Assignment");

        //Button Listeners
        myView.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                AssignmentDetailsFragment frag = new AssignmentDetailsFragment();
                frag.setAssignment(assignment);
                fragmentManager.beginTransaction().replace(R.id.content_frame,frag).commit();
            }

        });
        myView.findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                updateAssignment();
                FragmentManager fragmentManager = getFragmentManager();
                AssignmentDetailsFragment frag = new AssignmentDetailsFragment();
                frag.setAssignment(assignment);
                fragmentManager.beginTransaction().replace(R.id.content_frame,frag).commit();
            }

        });
        myView.findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                delete();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new AssignmentFragment()).commit();
            }

        });

        //Set up Course Spinner
        Spinner spinner = (Spinner) myView.findViewById(R.id.editCourse);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item, getStringArrayOfCourses()); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        //Add Milestones
        myView.findViewById(R.id.editAddMilestone).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                EditMilestoneFragment frag = new EditMilestoneFragment();
                frag.setMilestone(new Milestone(),assignment);
                fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
            }

        });

        //Fill form
        fillAssignmentForm();

        //Date picker
        myView.findViewById(R.id.editDueDate).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        date = Calendar.getInstance();
                        date.set(selectedyear,selectedmonth,selectedday);
                    selectedmonth = selectedmonth + 1;
                        ((EditText)myView.findViewById(R.id.editDueDate)).setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);

                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });

        return myView;
    }

    private void delete() {
        assignment.getCourse().removeAssignment(assignment);

        //Save changes to storage
        DataManager.saveData((MainActivity) getActivity());
    }

    private String[] getStringArrayOfCourses() {
        String[] result = new String[((MainActivity)getActivity()).semesters.get(((MainActivity) getActivity()).currentSemester).getCourses().size()];
        for(int i = 0; i < result.length; i++) {
            result[i] = ((MainActivity)getActivity()).semesters.get(((MainActivity) getActivity()).currentSemester).getCourses().get(i).getName();
        }
        return result;
    }

    private void updateAssignment() {
        int courseIndex = ((Spinner) myView.findViewById(R.id.editCourse)).getSelectedItemPosition();

        if(assignment == null) {
            assignment = new Assignment();
        }

        if(assignment.getCourse() != null) {
            assignment.getCourse().removeAssignment(assignment);
        }
        assignment.setCourse(((MainActivity) getActivity()).semesters.get(((MainActivity) getActivity()).currentSemester).getCourses().get(courseIndex));
        assignment.setName(((EditText) myView.findViewById(R.id.editName)).getText().toString());
        try {
            assignment.setPercentOfGrade(Double.parseDouble(((EditText) myView.findViewById(R.id.editPercent)).getText().toString()));
        }
        catch (NumberFormatException e) {

        }
        assignment.setDue(date);
        assignment.setComplete(((CheckBox) myView.findViewById(R.id.editComplete)).isChecked());
        try {
            assignment.setGrade(Integer.parseInt(((EditText) myView.findViewById(R.id.editGrade)).getText().toString()));
        }
        catch (NumberFormatException e){
            assignment.setGrade(-1);
        }
        assignment.setNotes(((EditText) myView.findViewById(R.id.editNotes)).getText().toString());

        //Put assignment in date order
        assignment.getCourse().addAssignment(assignment);

        //Save changes to storage
        DataManager.saveData((MainActivity) getActivity());
    }

    private void fillAssignmentForm() {
        if(assignment != null) {

            ((Spinner) myView.findViewById(R.id.editCourse)).setSelection(new Util<Course>().find(assignment.getCourse(), ((MainActivity) getActivity()).semesters.get(((MainActivity) getActivity()).currentSemester).getCoursesAsArray()));
            ((EditText) myView.findViewById(R.id.editName)).setText(assignment.getName());
            ((EditText) myView.findViewById(R.id.editPercent)).setText(assignment.getPercentOfGrade()+"");
            if(assignment.getDue() != null) {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                ((EditText) myView.findViewById(R.id.editDueDate)).setText(format.format(assignment.getDue().getTime()));
            }
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
            ((Spinner) myView.findViewById(R.id.editCourse)).setSelection(0);
        }
        fillMilestones();
    }

    private void fillMilestones() {
        if(assignment != null) {
            MilestoneEditArrayAdapter adapter = new MilestoneEditArrayAdapter(getActivity(), assignment.getMilestonesAsArray(), this, assignment);
            //MilestoneArrayAdapter adapter = new MilestoneArrayAdapter(getActivity(), assignment.getMilestonesAsArray());
            ListView list = ((ListView) myView.findViewById(R.id.editMilestones));
            list.setAdapter(adapter);
        }
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

}
