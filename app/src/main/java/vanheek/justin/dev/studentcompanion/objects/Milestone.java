package vanheek.justin.dev.studentcompanion.objects;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by justi on 2017-12-29.
 */

public class Milestone implements Comparable, Serializable {

    private String name = "";
    private boolean complete = false;
    private Calendar date;
    private boolean reminder = false;

    public Milestone() {

    }

    public Milestone(String name, Calendar due) {
        this.name = name;
        date = due;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public boolean isReminder() {
        return reminder;
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        Milestone a = (Milestone) o;
        if(date == null) {
            return -1;
        }
        if(a.getDate() == null) {
            return 1;
        }
        return date.compareTo(a.getDate());
    }
}
