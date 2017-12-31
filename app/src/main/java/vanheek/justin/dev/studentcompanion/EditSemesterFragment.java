package vanheek.justin.dev.studentcompanion;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

import vanheek.justin.dev.studentcompanion.objects.Exam;
import vanheek.justin.dev.studentcompanion.objects.Semester;

/**
 * Created by justi on 2017-12-30.
 */

public class EditSemesterFragment extends Fragment {

    View myView;
    private Semester semester;
    private Calendar start;
    private Calendar end;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.edit_semester_layout, container, false);

        //Change Header Title
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Edit Assignment");

        //Button Listeners
        myView.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new SemesterFragment()).commit();
            }

        });
        myView.findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                updateAssignment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new SemesterFragment()).commit();
            }

        });

        //Popups for date pickers
        myView.findViewById(R.id.editStart).setOnClickListener(new View.OnClickListener() {

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
                        selectedmonth = selectedmonth + 1;
                        ((EditText)myView.findViewById(R.id.editStart)).setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);

                        start = Calendar.getInstance();
                        start.set(selectedyear,selectedmonth,selectedday);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });
        myView.findViewById(R.id.editEnd).setOnClickListener(new View.OnClickListener() {

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
                        selectedmonth = selectedmonth + 1;
                        ((EditText)myView.findViewById(R.id.editEnd)).setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);

                        end = Calendar.getInstance();
                        end.set(selectedyear,selectedmonth,selectedday);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });

        //Fill form
        fillAssignmentForm();

        return myView;
    }

    private void updateAssignment() {
        if(semester == null) {
            semester = new Semester();
            ((MainActivity)getActivity()).addSemester(semester);
        }

        semester.setName(((EditText) myView.findViewById(R.id.editName)).getText().toString());
        semester.setStart(start);
        semester.setEnd(end);

    }

    private void fillAssignmentForm() {
        if(semester != null) {
            ((EditText) myView.findViewById(R.id.editName)).setText(semester.getName());
            //TODO Start
            //TODO End
        }
    }

    public void setSemester(Semester s) {
        this.semester = s;
    }

}
