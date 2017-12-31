package vanheek.justin.dev.studentcompanion.objects;

/**
 * Created by justi on 2017-12-29.
 */

public class WeeklySchedule {

    public boolean mon;
    public boolean tue;
    public boolean wed;
    public boolean thu;
    public boolean fri;
    public boolean sat;
    public boolean sun;
    public int start;
    public int end;

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
