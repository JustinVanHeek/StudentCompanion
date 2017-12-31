package vanheek.justin.dev.studentcompanion.objects;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by justi on 2017-12-29.
 */

public class Exam {

    private String name = "";
    private Course course;
    private Calendar date;
    private double percentOfGrade = 0;
    private String notes = "";
    private String room = "";
    private String seat = "";
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

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public double getPercentOfGrade() {
        return percentOfGrade;
    }

    public void setPercentOfGrade(double percentOfGrade) {
        this.percentOfGrade = percentOfGrade;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
