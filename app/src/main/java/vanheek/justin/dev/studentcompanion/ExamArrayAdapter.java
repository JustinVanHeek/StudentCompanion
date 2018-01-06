package vanheek.justin.dev.studentcompanion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Calendar;

import vanheek.justin.dev.studentcompanion.objects.Assignment;
import vanheek.justin.dev.studentcompanion.objects.Exam;

/**
 * Created by justi on 2017-12-29.
 */

public class ExamArrayAdapter extends ArrayAdapter<Exam> {

    private final Context context;
    private final Exam[] values;

    public ExamArrayAdapter(Context context, Exam[] values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.assignment_row, parent, false);

        Exam assignment = values[position];

        ((TextView) rowView.findViewById(R.id.assignmentName)).setText(assignment.getName());
        rowView.findViewById(R.id.assignmentContainer).setBackgroundColor(assignment.getCourse().getColor());
        if(assignment.getDate() != null) {
            String dueTimeText = "DUE";
            Calendar time = Calendar.getInstance();
            Calendar due = assignment.getDate();
            due.set(Calendar.HOUR_OF_DAY,assignment.getTime()/100);
            due.set(Calendar.MINUTE,assignment.getTime()-(assignment.getTime()/100)*100);
            int start = time.get(Calendar.DAY_OF_YEAR);
            int end = due.get(Calendar.DAY_OF_YEAR);
            int dueIn = end - start;
            dueIn = dueIn + 365 * (due.get(Calendar.YEAR) - time.get(Calendar.YEAR));


            if (dueIn < 0) {
                dueTimeText = "Exam was " + Math.abs(dueIn) + " days ago";

                //No longer show exam
                rowView.setVisibility(View.GONE);
            } else if (dueIn == 0) {
                dueTimeText = "Exam is today";
                int hours = due.get(Calendar.HOUR_OF_DAY)-time.get(Calendar.HOUR_OF_DAY);
                if(hours > 0) {
                    dueTimeText = "Exam is in " + hours + " hours";
                }
                else if(hours == 0) {
                    int minutes = due.get(Calendar.MINUTE)-time.get(Calendar.MINUTE);
                    if(minutes > 0) {
                        dueTimeText = "Exam is in " + minutes + " minutes";
                    }
                    else if(minutes == 0) {
                        dueTimeText = "Exam is NOW!";
                    }
                    else {
                        rowView.setVisibility(View.GONE);
                    }
                }
                else {
                    rowView.setVisibility(View.GONE);
                }
            } else if (dueIn == 1) {
                dueTimeText = "Exam is tomorrow";
            } else if (dueIn > 1) {
                dueTimeText = "Exam is in " + dueIn + " days";
            }

            ((TextView) rowView.findViewById(R.id.assignmentTime)).setText(dueTimeText);
        }
        else {
            ((TextView) rowView.findViewById(R.id.assignmentTime)).setText("");
        }

        return rowView;
    }

}
