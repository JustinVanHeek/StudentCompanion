package vanheek.justin.dev.studentcompanion;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

import vanheek.justin.dev.studentcompanion.objects.Assignment;
import vanheek.justin.dev.studentcompanion.objects.Course;
import vanheek.justin.dev.studentcompanion.objects.Exam;

/**
 * Created by justi on 2018-01-04.
 */

public class HomeFragment extends Fragment {

    private View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.home_layout, container, false);

        //Change Header Title
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Student Companion");

        return myView;
    }


    //Creates the lists //TODO - Current Todo
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Today Lists
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR,0);
        Calendar todayEnd = Calendar.getInstance();
        todayStart.set(Calendar.HOUR,22);
        HomeCourseArrayAdapter adapter = new HomeCourseArrayAdapter(getActivity(), getCoursesWithDate(todayStart,todayEnd));
        ((ListView)myView.findViewById(R.id.homeClassesToday)).setAdapter(adapter);

    }

    private Course[] getCoursesWithDate(Calendar start, Calendar end) {
        ArrayList<Course> courses = ((MainActivity)getActivity()).semesters[((MainActivity)getActivity()).currentSemester].getCourses();
        ArrayList<Course> allCourses = new ArrayList<Course>();
        Calendar tempCal = start;
        while(tempCal.compareTo(end) <= 0) {
            for (Course c : courses) {
                if(hasCourse(c,tempCal)) {
                    allCourses.add(c);
                }
            }
            tempCal.add(Calendar.DAY_OF_YEAR,1);
        }
        Course[] results = new Course[allCourses.size()];
        for(int i = 0; i < results.length; i++) {
            results[i] = allCourses.get(i);
        }
        return results;
    }

    private boolean hasCourse(Course c, Calendar day) {
        switch (day.get(Calendar.DAY_OF_WEEK)) {
            case 0: return c.getCourseHours().sun;
            case 1: return c.getCourseHours().mon;
            case 2: return c.getCourseHours().tue;
            case 3: return c.getCourseHours().wed;
            case 4: return c.getCourseHours().thu;
            case 5: return c.getCourseHours().fri;
            case 6: return c.getCourseHours().sat;
        }
        return false;
    }

    private Exam[] getExamsWithDate(Calendar start, Calendar end) {
        Exam[] allExams = null;
        ArrayList<Exam> exams = new ArrayList<Exam>();
        for(Course c : ((MainActivity) getActivity()).semesters[((MainActivity) getActivity()).currentSemester].getCourses()) {
            for(Exam e : c.getExams()) {
                if(e.getDate().compareTo(start) >= 0 && e.getDate().compareTo(end) <= 0) {
                    exams.add(e);
                }
            }
        }
        allExams = new Exam[exams.size()];
        int i = 0;
        for(Exam e : exams) {
            allExams[i] = e;
            i++;
        }
        return allExams;
    }

    private Assignment[] getAssignmentWithDate(Calendar start, Calendar end) {
        Assignment[] allAssignments = null;
        ArrayList<Assignment> assignments = new ArrayList<Assignment>();
        for(Course c : ((MainActivity) getActivity()).semesters[((MainActivity) getActivity()).currentSemester].getCourses()) {
            for(Assignment a : c.getAssignments()) {
                if(a.getDue().compareTo(start) >= 0 && a.getDue().compareTo(end) <= 0) {
                    assignments.add(a);
                }
            }
        }
        allAssignments = new Assignment[assignments.size()];
        int i = 0;
        for(Assignment a : assignments) {
            allAssignments[i] = a;
            i++;
        }
        return allAssignments;
    }

}
