package vanheek.justin.dev.studentcompanion;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import vanheek.justin.dev.studentcompanion.objects.Assignment;
import vanheek.justin.dev.studentcompanion.objects.Course;

/**
 * Created by justi on 2017-12-29.
 */

public class AssignmentArrayAdapter extends ArrayAdapter<Assignment> {

    private final Context context;
    private final Assignment[] values;

    public AssignmentArrayAdapter(Context context, Assignment[] values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.assignment_row, parent, false);

        Assignment assignment = values[position];

        ((TextView) rowView.findViewById(R.id.assignmentName)).setText(assignment.getName());
        rowView.findViewById(R.id.assignmentContainer).setBackgroundColor(assignment.getCourse().getColor());

        if(assignment.getDue() != null) {
            String dueTimeText = "DUE";
            Calendar time = Calendar.getInstance();
            Calendar due = assignment.getDue();
            int start = time.get(Calendar.DAY_OF_YEAR);
            int end = due.get(Calendar.DAY_OF_YEAR);
            int dueIn = end - start;
            dueIn = dueIn + 365 * (due.get(Calendar.YEAR) - time.get(Calendar.YEAR));


            if (dueIn < 0) {
                dueTimeText = "Due " + Math.abs(dueIn) + " days ago";
            } else if (dueIn == 0) {
                dueTimeText = "Due today";
            } else if (dueIn == 1) {
                dueTimeText = "Due tomorrow";
            } else if (dueIn > 1) {
                dueTimeText = "Due in " + dueIn + " days";
            }

            Log.d("DueDateCalc",position+ " " + start + " " + end + " " + dueIn);

            ((TextView) rowView.findViewById(R.id.assignmentTime)).setText(dueTimeText);
        }
        else {
            ((TextView) rowView.findViewById(R.id.assignmentTime)).setText("");
        }

        if(assignment.isComplete()) {
            rowView.setVisibility(View.GONE);
        }

        return rowView;
    }

}
