package vanheek.justin.dev.studentcompanion.objects;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by justi on 2017-12-29.
 */

public class Assignment {

    private String name = "";
    private Course course;
    private double percentOfGrade = 0;
    private Calendar due;
    private Milestone[] milestones;
    private boolean complete = false;
    private String notes = "";
    private double grade = -1;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public double getPercentOfGrade() {
        return percentOfGrade;
    }

    public void setPercentOfGrade(double percentOfGrade) {
        this.percentOfGrade = percentOfGrade;
    }

    public Calendar getDue() {
        return due;
    }

    public void setDue(Calendar due) {
        this.due = due;
    }

    public Milestone[] getMilestones() {
        return milestones;
    }

    public void setMilestones(Milestone[] milestones) {
        this.milestones = milestones;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
