package vanheek.justin.dev.studentcompanion.objects;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by justi on 2017-12-29.
 */

public class Course {

    private int color = Color.WHITE;
    private String name = "";
    private String courseCode = "";
    private int credits = 0;
    private String room = "";
    private String prof = "";
    private String office = "";
    private WeeklySchedule officeHours;
    private WeeklySchedule courseHours;
    private String notes = "";
    private ArrayList<Assignment> assignments = new ArrayList<Assignment>();
    private ArrayList<Exam> exams = new ArrayList<Exam>();
    private Semester semester;


    public Course() {
    }

    public Course(String name, int color, Semester semester) {
        this.name = name;
        this.color = color;
        this.semester = semester;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public WeeklySchedule getOfficeHours() {
        return officeHours;
    }

    public void setOfficeHours(WeeklySchedule officeHours) {
        this.officeHours = officeHours;
    }

    public WeeklySchedule getCourseHours() {
        return courseHours;
    }

    public void setCourseHours(WeeklySchedule courseHours) {
        this.courseHours = courseHours;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(ArrayList<Assignment> assignments) {
        this.assignments = assignments;
    }

    public ArrayList<Exam> getExams() {
        return exams;
    }

    public void setExams( ArrayList<Exam>  exams) {
        this.exams = exams;
    }

    public Exam[] getExamsAsArray() {
        Exam[] exArray = new Exam[exams.size()];
        int i = 0;
        for(Exam c : exams) {
            exArray[i] = c;
            i++;
        }
        return exArray;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Assignment[] getAssignmentsAsArray() {
        Assignment[] assArray = new Assignment[assignments.size()];
        int i = 0;
        for(Assignment c : assignments) {
            assArray[i] = c;
            i++;
        }
        return assArray;
    }

    public void addAssignment(Assignment a) {
        assignments.add(a);
    }

    public void addExam(Exam exam) {
        exams.add(exam);
    }
}
