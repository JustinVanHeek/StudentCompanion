package vanheek.justin.dev.studentcompanion.objects;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import vanheek.justin.dev.studentcompanion.Util;

/**
 * Created by justi on 2017-12-29.
 */

public class Assignment implements Comparable, Serializable {

    private String name = "";
    private Course course;
    private double percentOfGrade = 0;
    private Calendar due;
    private ArrayList<Milestone> milestones = new ArrayList<Milestone>();
    private boolean complete = false;
    private String notes = "";
    private double grade = -1;

    public Assignment() {

    }
    public Assignment(Course course) {
        this.course = course;
    }

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

    public Milestone[] getMilestonesAsArray() {
            Milestone[] exArray = new Milestone[milestones.size()];
            int i = 0;
            for(Milestone c : milestones) {
                exArray[i] = c;
                i++;
            }
            return exArray;
    }

    public ArrayList<Milestone> getMilestones() {
        return milestones;
    }

    public void addMilestone(Milestone milestone) {
        for(int i = 0; i < milestones.size(); i++) {
            if(milestone.compareTo(milestones.get(i)) <= 0) {
                new Util<Milestone>().insertInto(milestones,i,milestone);
                return;
            }
        }
        milestones.add(milestone);
    }

    public void removeMilestone(Milestone milestone) {
        milestones.remove(milestone);
    }

    @Override
    public int compareTo(@NonNull Object o) {
        Assignment a = (Assignment) o;
        if(due == null) {
            return -1;
        }
        if(a.getDue() == null) {
            return 1;
        }
        return due.compareTo(a.getDue());
    }
}
