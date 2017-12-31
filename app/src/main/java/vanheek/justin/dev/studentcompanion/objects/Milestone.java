package vanheek.justin.dev.studentcompanion.objects;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by justi on 2017-12-29.
 */

public class Milestone {

    private String name;
    private boolean complete;
    private Calendar date;
    private boolean reminder;

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
}
