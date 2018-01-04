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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import vanheek.justin.dev.studentcompanion.objects.Assignment;
import vanheek.justin.dev.studentcompanion.objects.Exam;
import vanheek.justin.dev.studentcompanion.objects.Milestone;

/**
 * Created by justi on 2017-12-30.
 */

public class ExamDetailsFragment extends Fragment {

    View myView;
    Exam exam;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.exam_details_layout, container, false);

        //Change Header Title
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Exam Details");

        //Change color
        ((MainActivity)getActivity()).findViewById(R.id.toolbar).setBackgroundColor(exam.getCourse().getColor());
        myView.findViewById(R.id.examName).setBackgroundColor(exam.getCourse().getColor());
        myView.findViewById(R.id.examCourse).setBackgroundColor(exam.getCourse().getColor());


        fillDetails();

        return myView;
    }

    private void fillDetails() {
        ((TextView) myView.findViewById(R.id.examName)).setText(exam.getName());
        ((TextView) myView.findViewById(R.id.examCourse)).setText(exam.getCourse().getName());
        if(exam.getDate() != null) {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            ((TextView) myView.findViewById(R.id.examDate)).setText(format.format(exam.getDate().getTime()));
        }
        else {
            ((TextView) myView.findViewById(R.id.examDate)).setText("");
        }
        if(exam.getGrade() > -1) {
            ((TextView) myView.findViewById(R.id.examGrade)).setText("Grade: " + exam.getGrade() + "%");
        }
        else {
            ((TextView) myView.findViewById(R.id.examGrade)).setText("Grade: --");
        }
        ((TextView) myView.findViewById(R.id.examWeight)).setText("Weight: " + exam.getPercentOfGrade() + "%");
        ((TextView) myView.findViewById(R.id.examRoom)).setText("Room: " + exam.getRoom());
        ((TextView) myView.findViewById(R.id.examSeat)).setText("Seat: " + exam.getSeat());

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
                EditExamFragment frag = new EditExamFragment();
                frag.setExam(exam);
                fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
                return true;
        }
        return false;
    }

    public void setExam(Exam course) {
        this.exam = course;
    }
}
