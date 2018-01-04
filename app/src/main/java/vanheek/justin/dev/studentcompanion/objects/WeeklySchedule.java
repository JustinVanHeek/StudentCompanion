package vanheek.justin.dev.studentcompanion.objects;

import java.io.Serializable;

/**
 * Created by justi on 2017-12-29.
 */

public class WeeklySchedule implements Serializable{

    public boolean mon;
    public boolean tue;
    public boolean wed;
    public boolean thu;
    public boolean fri;
    public boolean sat;
    public boolean sun;
    public int start;
    public int end;

    public WeeklySchedule() {

    }

    public WeeklySchedule(boolean m, boolean t, boolean w, boolean th, boolean f, boolean s, boolean su, int st, int en) {
        mon = m;
        tue = t;
        wed = w;
        thu = th;
        fri = f;
        sat = s;
        sun = su;
        start = st;
        end = en;
    }

    public String toString() {
        String result = "";
        if (mon) {
            result = result + "M";
        }
        if (tue) {
            result = result + "T";
        }
        if (wed) {
            result = result + "W";
        }
        if (thu) {
            result = result + "R";
        }
        if (fri) {
            result = result + "F";
        }
        if (sat) {
            result = result + "S";
        }
        if (sun) {
            result = result + "S";
        }
        String startHour = (start/100)+"";
        String endHour = (end/100)+"";
        int startMinInt = (start-(start/100)*100);
        int endMinInt = (end-(end/100)*100);
        String startMin = startMinInt+"";
        String endMin = endMinInt+"";
        if(startMinInt < 10) {
            startMin = "0" + startMinInt;
        }
        if(endMinInt < 10) {
            endMin = "0" + endMinInt;
        }
        result = result + " " + startHour + ":" + startMin + " - " + endHour + ":" + endMin;
        return result;
    }

}
