package vanheek.justin.dev.studentcompanion;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import vanheek.justin.dev.studentcompanion.objects.Assignment;
import vanheek.justin.dev.studentcompanion.objects.Milestone;

/**
 * Created by justi on 2017-12-30.
 */

public class EditMilestoneFragment extends Fragment {

    View myView;
    private Milestone milestone;
    private Assignment assignment;
    private Calendar date;
    private boolean cameFromAssignmentDetails = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.edit_milestone_layout, container, false);
        if(milestone == null) {
            milestone = new Milestone();
        }
        date=milestone.getDate();

        //Change Header Title
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Edit Milestone");

        //Button Listeners
        myView.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                if(cameFromAssignmentDetails) {
                    AssignmentDetailsFragment frag = new AssignmentDetailsFragment();
                    frag.setAssignment(assignment);
                    fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
                }
                else {
                    EditAssignmentFragment frag = new EditAssignmentFragment();
                    frag.setAssignment(assignment);
                    fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
                }
            }

        });
        myView.findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                updateAssignment();
                FragmentManager fragmentManager = getFragmentManager();
                if(cameFromAssignmentDetails) {
                    AssignmentDetailsFragment frag = new AssignmentDetailsFragment();
                    frag.setAssignment(assignment);
                    fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
                }
                else {
                    EditAssignmentFragment frag = new EditAssignmentFragment();
                    frag.setAssignment(assignment);
                    fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
                }
            }

        });
        myView.findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                delete();
                FragmentManager fragmentManager = getFragmentManager();
                if(cameFromAssignmentDetails) {
                    AssignmentDetailsFragment frag = new AssignmentDetailsFragment();
                    frag.setAssignment(assignment);
                    fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
                }
                else {
                    EditAssignmentFragment frag = new EditAssignmentFragment();
                    frag.setAssignment(assignment);
                    fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
                }
            }

        });

        //Fill form
        fillAssignmentForm();

        //Date picker
        myView.findViewById(R.id.editDate).setOnClickListener(new View.OnClickListener() {

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
                        ((EditText)myView.findViewById(R.id.editDate)).setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);

                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });

        return myView;
    }

    private void delete() {
        assignment.removeMilestone(milestone);

        //Save changes to storage
        DataManager.saveData((MainActivity) getActivity());
    }

    private void updateAssignment() {
        if(milestone == null) {
            milestone = new Milestone();
        }
        assignment.getMilestones().remove(milestone);
        milestone.setName(((EditText) myView.findViewById(R.id.editName)).getText().toString());
        milestone.setDate(date);
        milestone.setReminder(((CheckBox) myView.findViewById(R.id.editReminder)).isChecked());
        assignment.addMilestone(milestone);
        //Save changes to storage
        DataManager.saveData((MainActivity) getActivity());

    }

    private void fillAssignmentForm() {
        if(milestone != null) {

            ((EditText) myView.findViewById(R.id.editName)).setText(milestone.getName());
            if(milestone.getDate() != null) {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                ((EditText) myView.findViewById(R.id.editDate)).setText(format.format(milestone.getDate().getTime()));
            }
            ((CheckBox) myView.findViewById(R.id.editReminder)).setChecked(milestone.isReminder());


        }
    }

    public void setMilestone(Milestone exam, Assignment a) {
        this.milestone = exam;
        assignment = a;
    }

    public void setCameFromAssignmentDetails(Boolean b) {
        cameFromAssignmentDetails = b;
    }

}
