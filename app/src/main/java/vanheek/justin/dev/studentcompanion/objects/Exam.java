package vanheek.justin.dev.studentcompanion.objects;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by justi on 2017-12-29.
 */

public class Exam implements Comparable, Serializable {

    private String name = "";
    private Course course;
    private Calendar date;
    private double percentOfGrade = 0;
    private String notes = "";
    private String room = "";
    private String seat = "";
    private double grade = -1;
    private int time;


    public Exam() {

    }
    public Exam(Course course) {
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

    @Override
    public int compareTo(@NonNull Object o) {
        Exam a = (Exam) o;
        if(date == null) {
            return -1;
        }
        if(a.getDate() == null) {
            return 1;
        }
        if (date.compareTo(a.getDate()) > 0) {
            return 1;
        }
        if (date.compareTo(a.getDate()) < 0) {
            return -1;
        }
        if (date.compareTo(a.getDate()) == 0) {
            if (getTime() < a.getTime()) {
                return -1;
            }
            if (getTime() > a.getTime()) {
                return 1;
            }
            if (getTime() == a.getTime()) {
                return 0;
            }
        }
        return 1;
    }

    public int getTime() {
        return time;
    }

    public String getTimeAsString() {
        String startHour = (time/100)+"";
        int startMinInt = (time-(time/100)*100);
        String startMin = startMinInt+"";
        if(startMinInt < 10) {
            startMin = "0" + startMinInt;
        }
        return startHour + ":" + startMin;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
