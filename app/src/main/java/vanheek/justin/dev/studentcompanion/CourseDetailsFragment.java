package vanheek.justin.dev.studentcompanion;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import vanheek.justin.dev.studentcompanion.objects.Course;
import vanheek.justin.dev.studentcompanion.objects.WeeklySchedule;

/**
 * Created by justi on 2017-12-30.
 */

public class CourseDetailsFragment extends Fragment {

    View myView;
    Course course;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.course_details_layout, container, false);

        //Change Header Title
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Course Details");

        fillDetails();

        return myView;
    }

    private void fillDetails() {
        ((TextView) myView.findViewById(R.id.courseName)).setText(course.getName());
        ((TextView) myView.findViewById(R.id.courseCode)).setText(course.getCourseCode());
        ((TextView) myView.findViewById(R.id.courseCredits)).setText(course.getCredits()+"");

        String details;
        WeeklySchedule schedule = course.getCourseHours();
        if(schedule == null) {
            details = course.getRoom();
        }
        else {
            details = schedule.toString() + " " + course.getRoom();
        }
        ((TextView) myView.findViewById(R.id.courseDetails)).setText(details);
        ((TextView) myView.findViewById(R.id.courseProf)).setText(course.getProf());

        String officeDetails;
        schedule = course.getOfficeHours();
        if(schedule == null) {
            officeDetails = "Office: " + course.getOffice();
        }
        else {
            officeDetails = "Office: " + course.getOffice() + " " + schedule.toString();
        }
        ((TextView) myView.findViewById(R.id.courseOfficeDetails)).setText(officeDetails);
        ((TextView) myView.findViewById(R.id.courseNotes)).setText("Notes: " + course.getNotes());
        createCourseEvalList(course);

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
                EditCourseFragment frag = new EditCourseFragment();
                frag.setCourse(course);
                fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
                return true;
        }
        return false;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    private void createCourseEvalList(Course c) {
        CourseEvalArrayAdapter adapter = new CourseEvalArrayAdapter(getActivity(), c.getAssignmentsAsArray());
        ListView list = ((ListView) myView.findViewById(R.id.courseAssignments));
        list.setAdapter(adapter);
        CourseEvalExamArrayAdapter adapter2 = new CourseEvalExamArrayAdapter(getActivity(), c.getExamsAsArray());
        ListView list2 = ((ListView) myView.findViewById(R.id.courseExams));
        list2.setAdapter(adapter2);
    }
}
