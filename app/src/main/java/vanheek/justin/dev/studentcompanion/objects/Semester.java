package vanheek.justin.dev.studentcompanion.objects;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by justi on 2017-12-29.
 */

public class Semester implements Comparable, Serializable {

    private String name = "";
    private Calendar start;
    private Calendar end;
    private ArrayList<Course> courses = new ArrayList<Course>();

    public Semester() {

    }

    public Semester(String name,Calendar start,Calendar end) {
        this.name = name;
        this.start = start;
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getStart() {
        return start;
    }

    public void setStart(Calendar start) {
        this.start = start;
    }

    public Calendar getEnd() {
        return end;
    }

    public void setEnd(Calendar end) {
        this.end = end;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void addCourse() {
    }

    public Course[] getCoursesAsArray() {
        Course[] courseArray = new Course[courses.size()];
        int i = 0;
        for(Course c : courses) {
            courseArray[i] = c;
            i++;
        }
        return courseArray;
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }

    @Override
    public int compareTo(@NonNull Object o) {
        Semester a = (Semester) o;
        if(start == null) {
            return -1;
        }
        if(a.getStart() == null) {
            return 1;
        }
        return start.compareTo(a.getStart());
    }
}
