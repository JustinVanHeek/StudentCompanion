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
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import vanheek.justin.dev.studentcompanion.objects.Assignment;
import vanheek.justin.dev.studentcompanion.objects.Course;
import vanheek.justin.dev.studentcompanion.objects.Exam;

/**
 * Created by justi on 2017-12-30.
 */

public class EditExamFragment extends Fragment {

    View myView;
    private Exam exam;
    private Calendar date;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.edit_exam_layout, container, false);
        if(exam == null) {
            exam = new Exam();
        }
        date=exam.getDate();

        //Change Header Title
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Edit Exam");

        //Button Listeners
        myView.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                ExamDetailsFragment frag = new ExamDetailsFragment();
                frag.setExam(exam);
                fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
            }

        });
        myView.findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                updateAssignment();
                FragmentManager fragmentManager = getFragmentManager();
                ExamDetailsFragment frag = new ExamDetailsFragment();
                frag.setExam(exam);
                fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();            }

        });
        myView.findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                delete();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new ExamFragment()).commit();
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
        exam.getCourse().removeExam(exam);

        //Save changes to storage
        DataManager.saveData((MainActivity) getActivity());
    }

    private String[] getStringArrayOfCourses() {
        String[] result = new String[((MainActivity)getActivity()).semesters.get(((MainActivity)getActivity()).currentSemester).getCourses().size()];
        for(int i = 0; i < result.length; i++) {
            result[i] = ((MainActivity)getActivity()).semesters.get(((MainActivity)getActivity()).currentSemester).getCourses().get(i).getName();
        }
        return result;
    }

    private void updateAssignment() {
        int courseIndex = ((Spinner) myView.findViewById(R.id.editCourse)).getSelectedItemPosition();

        if(exam == null) {
            exam = new Exam();
        }
        if(exam.getCourse() != null) {
            exam.getCourse().getExams().remove(exam);
        }
        exam.setCourse(((MainActivity) getActivity()).semesters.get(((MainActivity)getActivity()).currentSemester).getCourses().get(courseIndex));

        exam.setDate(date);
        exam.setName(((EditText) myView.findViewById(R.id.editName)).getText().toString());
        exam.setPercentOfGrade(Double.parseDouble(((EditText) myView.findViewById(R.id.editPercent)).getText().toString()));
        try {
            exam.setGrade(Integer.parseInt(((EditText) myView.findViewById(R.id.editGrade)).getText().toString()));
        }
        catch (NumberFormatException e){
            exam.setGrade(-1);
        }
        exam.setNotes(((EditText) myView.findViewById(R.id.editNotes)).getText().toString());
        exam.getCourse().addExam(exam);

        //Save changes to storage
        DataManager.saveData((MainActivity) getActivity());
    }

    private void fillAssignmentForm() {
        if(exam != null) {

            ((Spinner) myView.findViewById(R.id.editCourse)).setSelection(new Util<Course>().find(exam.getCourse(), ((MainActivity) getActivity()).semesters.get(((MainActivity)getActivity()).currentSemester).getCoursesAsArray()));
            ((EditText) myView.findViewById(R.id.editName)).setText(exam.getName());
            ((EditText) myView.findViewById(R.id.editPercent)).setText(exam.getPercentOfGrade()+"");
            if(exam.getDate() != null) {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                ((EditText) myView.findViewById(R.id.editDate)).setText(format.format(exam.getDate().getTime()));
            }
            ((EditText) myView.findViewById(R.id.editRoom)).setText(exam.getRoom());
            ((EditText) myView.findViewById(R.id.editSeat)).setText(exam.getSeat());
            double grade = exam.getGrade();
            if(grade < 0) {
                ((EditText) myView.findViewById(R.id.editGrade)).setText("");
            }
            else {
                ((EditText) myView.findViewById(R.id.editGrade)).setText(exam.getGrade() + "");
            }
            ((EditText) myView.findViewById(R.id.editNotes)).setText(exam.getNotes());



        }
        else {
            ((Spinner) myView.findViewById(R.id.editCourse)).setSelection(0);
        }
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

}
