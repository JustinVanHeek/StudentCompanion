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

        String dueTimeText = "DUE";
        Calendar time = Calendar.getInstance();
        Calendar due = assignment.getDate();
        int start = time.get(Calendar.DAY_OF_YEAR);
        int end = due.get(Calendar.DAY_OF_YEAR);
        int dueIn = end-start;
        dueIn = dueIn + 365 * (due.get(Calendar.YEAR) - time.get(Calendar.YEAR));


        if (dueIn < 0) {
            dueTimeText = "Exam was " + Math.abs(dueIn) + " days ago";
        }
        else if (dueIn == 0) {
            dueTimeText = "Exam is today";
        }
        else if (dueIn == 1) {
            dueTimeText = "Exam is tomorrow";
        }
        else if (dueIn > 1) {
            dueTimeText = "Exam is in " + dueIn + "days";
        }

        ((TextView) rowView.findViewById(R.id.assignmentTime)).setText(dueTimeText);

        return rowView;
    }

}
