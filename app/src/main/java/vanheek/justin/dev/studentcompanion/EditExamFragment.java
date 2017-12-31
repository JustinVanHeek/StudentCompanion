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
import vanheek.justin.dev.studentcompanion.objects.Exam;

/**
 * Created by justi on 2017-12-30.
 */

public class EditExamFragment extends Fragment {

    View myView;
    private Exam exam;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.edit_exam_layout, container, false);

        //Change Header Title
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Edit Assignment");

        //Button Listeners
        myView.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new ExamFragment()).commit();
            }

        });
        myView.findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                updateAssignment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new ExamFragment()).commit();
            }

        });

        //Fill form
        fillAssignmentForm();

        return myView;
    }

    private void updateAssignment() {
        if(exam == null) {
            exam = new Exam();
            exam.getCourse().addExam(exam);
        }

        //TODO Course
        exam.setName(((EditText) myView.findViewById(R.id.editName)).getText().toString());
        exam.setPercentOfGrade(Double.parseDouble(((EditText) myView.findViewById(R.id.editPercent)).getText().toString()));
        try {
            exam.setGrade(Integer.parseInt(((EditText) myView.findViewById(R.id.editGrade)).getText().toString()));
        }
        catch (NumberFormatException e){
            exam.setGrade(-1);
        }
        exam.setNotes(((EditText) myView.findViewById(R.id.editNotes)).getText().toString());
    }

    private void fillAssignmentForm() {
        if(exam != null) {

            ((EditText) myView.findViewById(R.id.editCourse)).setText(exam.getCourse().getName());
            ((EditText) myView.findViewById(R.id.editName)).setText(exam.getName());
            ((EditText) myView.findViewById(R.id.editPercent)).setText(exam.getPercentOfGrade()+"");
            //TODO date
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
            ((EditText) myView.findViewById(R.id.editCourse)).setText("TODO");
        }
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

}
