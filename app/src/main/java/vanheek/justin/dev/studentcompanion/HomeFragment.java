package vanheek.justin.dev.studentcompanion;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

        //If there is no semester, force the user to go to the semester screen to create one
        if(((MainActivity) getActivity()).semesters.size() == 0) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, new SemesterFragment()).commit();
        }
        else {

            //Change Header Title
            ((MainActivity) getActivity()).getSupportActionBar().setTitle("Student Companion");
            ((MainActivity) getActivity()).findViewById(R.id.toolbar).setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            createTodaysLists();
            createTomorrowsLists();
            createNextWeeksLists();
        }
        return myView;
    }

    private void createTodaysLists() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY,22);
        Log.d("Home","Getting today's courses...");

        Course[] courses = getCoursesWithDate(todayStart,todayEnd);
        Assignment[] assignments = getAssignmentWithDate(todayStart,todayEnd);
        Exam[] exams = getExamsWithDate(todayStart,todayEnd);

        //Courses
        HomeCourseArrayAdapter adapterC = new HomeCourseArrayAdapter(getActivity(), courses,this);
        ((ListView)myView.findViewById(R.id.homeClassesToday)).setAdapter(adapterC);

        //Assignments
        HomeAssignmentArrayAdapter adapterA = new HomeAssignmentArrayAdapter(getActivity(), assignments,this);
        ((ListView)myView.findViewById(R.id.homeAssignmentsToday)).setAdapter(adapterA);

        //Exams
        HomeExamArrayAdapter adapterE = new HomeExamArrayAdapter(getActivity(), exams,this);
        ((ListView)myView.findViewById(R.id.homeExamsToday)).setAdapter(adapterE);

        if(courses.length == 0 && assignments.length == 0 && exams.length == 0)  {
            myView.findViewById(R.id.homeTextToday).setVisibility(View.GONE);
            myView.findViewById(R.id.homeTodayLists).setVisibility(View.GONE);
        }
    }

    private void createTomorrowsLists() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.add(Calendar.DAY_OF_YEAR,1);
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY,22);
        todayEnd.add(Calendar.DAY_OF_YEAR,1);
        Log.d("Home","Getting today's courses...");

        Course[] courses = getCoursesWithDate(todayStart,todayEnd);
        Assignment[] assignments = getAssignmentWithDate(todayStart,todayEnd);
        Exam[] exams = getExamsWithDate(todayStart,todayEnd);

        //Courses
        HomeCourseArrayAdapter adapterC = new HomeCourseArrayAdapter(getActivity(), courses,this);
        ((ListView)myView.findViewById(R.id.homeClassesTomorrow)).setAdapter(adapterC);

        //Assignments
        HomeAssignmentArrayAdapter adapterA = new HomeAssignmentArrayAdapter(getActivity(), assignments,this);
        ((ListView)myView.findViewById(R.id.homeAssignmentsTomorrow)).setAdapter(adapterA);

        //Exams
        HomeExamArrayAdapter adapterE = new HomeExamArrayAdapter(getActivity(), exams,this);
        ((ListView)myView.findViewById(R.id.homeExamsTomorrow)).setAdapter(adapterE);

        if(courses.length == 0 && assignments.length == 0 && exams.length == 0)  {
            myView.findViewById(R.id.homeTextTomorrow).setVisibility(View.GONE);
            myView.findViewById(R.id.homeTomorrowLists).setVisibility(View.GONE);
        }
    }

    private void createNextWeeksLists() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.add(Calendar.DAY_OF_YEAR,2);
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        Log.d("Home","HOUR"+todayStart.get(Calendar.HOUR_OF_DAY));
        Log.d("Home","HOUR"+todayStart.get(Calendar.HOUR));

        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY,22);
        todayEnd.add(Calendar.DAY_OF_YEAR,7);
        Log.d("Home","Getting week's courses...");

        Course[] courses = getCoursesWithDate(todayStart,todayEnd);
        Assignment[] assignments = getAssignmentWithDate(todayStart,todayEnd);
        Exam[] exams = getExamsWithDate(todayStart,todayEnd);

        //Courses
        HomeCourseArrayAdapter adapterC = new HomeCourseArrayAdapter(getActivity(), courses,this);
        ((ListView)myView.findViewById(R.id.homeClassesWeek)).setAdapter(adapterC);

        //Assignments
        HomeAssignmentArrayAdapter adapterA = new HomeAssignmentArrayAdapter(getActivity(), assignments,this);
        ((ListView)myView.findViewById(R.id.homeAssignmentsWeek)).setAdapter(adapterA);

        //Exams
        HomeExamArrayAdapter adapterE = new HomeExamArrayAdapter(getActivity(), exams,this);
        ((ListView)myView.findViewById(R.id.homeExamsWeek)).setAdapter(adapterE);

        if(courses.length == 0 && assignments.length == 0 && exams.length == 0)  {
            myView.findViewById(R.id.homeTextWeek).setVisibility(View.GONE);
            myView.findViewById(R.id.homeWeekLists).setVisibility(View.GONE);
        }
    }


    private Course[] getCoursesWithDate(Calendar start, Calendar end) {
        ArrayList<Course> courses = ((MainActivity)getActivity()).semesters.get(((MainActivity)getActivity()).currentSemester).getCourses();
        ArrayList<Course> allCourses = new ArrayList<Course>();
        Calendar tempCal = Calendar.getInstance();
        tempCal.set(start.get(Calendar.YEAR),start.get(Calendar.MONTH),start.get(Calendar.DAY_OF_MONTH));
        tempCal.set(Calendar.HOUR_OF_DAY,0);
        Log.d("Home", "Check " + tempCal.compareTo(end));
        while(tempCal.compareTo(end) <= 0) {
            for (Course c : courses) {
                Log.d("Home","Checking " + c.getName() + " for day " + tempCal.get(Calendar.DAY_OF_WEEK) + " on hour " + tempCal.get(Calendar.HOUR_OF_DAY));
                if(hasCourse(c,tempCal) && !allCourses.contains(c)) {
                    Log.d("Home", "Found " + c.getName());
                    allCourses.add(c);
                }
            }
            tempCal.add(Calendar.HOUR_OF_DAY,1);
        }
        Course[] results = new Course[allCourses.size()];
        for(int i = 0; i < results.length; i++) {
            results[i] = allCourses.get(i);
        }
        Log.d("Home","L:"+results.length);
        return results;
    }

    private boolean hasCourse(Course c, Calendar day) {
        if(hasCourseOnDay(c,day)) {
            return hasCourseWithinHour(c,day);
        }
        else {
            return false;
        }
    }

    private boolean hasCourseWithinHour(Course c, Calendar day) {
        int hour = c.getCourseHours().start/100;
        int checkHour = day.get(Calendar.HOUR_OF_DAY);
        return hour == checkHour;
    }

    private boolean hasCourseOnDay(Course c, Calendar day) {
        switch (day.get(Calendar.DAY_OF_WEEK)) {
            case 1: return c.getCourseHours().sun;
            case 2: return c.getCourseHours().mon;
            case 3: return c.getCourseHours().tue;
            case 4: return c.getCourseHours().wed;
            case 5: return c.getCourseHours().thu;
            case 6: return c.getCourseHours().fri;
            case 7: return c.getCourseHours().sat;
        }
        return false;
    }

    private Exam[] getExamsWithDate(Calendar start, Calendar end) {
        Exam[] allExams = null;
        ArrayList<Exam> exams = new ArrayList<Exam>();
        for(Course c : ((MainActivity) getActivity()).semesters.get(((MainActivity) getActivity()).currentSemester).getCourses()) {
            for(Exam e : c.getExams()) {
                if(e.getDate() !=null) {
                    if (e.getDate().compareTo(start) >= 0 && e.getDate().compareTo(end) <= 0) {
                        exams.add(e);
                    }
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
        for(Course c : ((MainActivity) getActivity()).semesters.get(((MainActivity) getActivity()).currentSemester).getCourses()) {
            for(Assignment a : c.getAssignments()) {
                if(a.getDue() != null) {
                    Log.d("Home", a.getDue().getTime().toString() + start.getTime().toString());
                    if (a.getDue().after(start) && a.getDue().before(end)) {
                        assignments.add(a);
                    }
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
