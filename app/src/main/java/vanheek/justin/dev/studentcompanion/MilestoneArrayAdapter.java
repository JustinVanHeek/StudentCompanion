package vanheek.justin.dev.studentcompanion;

import android.app.FragmentManager;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

import vanheek.justin.dev.studentcompanion.objects.Assignment;
import vanheek.justin.dev.studentcompanion.objects.Milestone;

/**
 * Created by justi on 2017-12-29.
 */

public class MilestoneArrayAdapter extends ArrayAdapter<Milestone> {

    private final Context context;
    private final Milestone[] values;
    private AssignmentDetailsFragment assignmentViewer;

    public MilestoneArrayAdapter(Context context, Milestone[] values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.milestones_row, parent, false);

        final Milestone milestone = values[position];

        ((TextView) rowView.findViewById(R.id.milestoneName)).setText(milestone.getName());

        if(milestone.getDate() != null) {

            String dueTimeText = "DUE";
            Calendar time = Calendar.getInstance();
            Calendar due = milestone.getDate();
            int start = time.get(Calendar.DAY_OF_YEAR);
            int end = due.get(Calendar.DAY_OF_YEAR);
            int dueIn = end - start;
            dueIn = dueIn + 365 * (due.get(Calendar.YEAR) - time.get(Calendar.YEAR));


            if (dueIn < 0) {
                dueTimeText = "To do " + Math.abs(dueIn) + " days ago";
            } else if (dueIn == 0) {
                dueTimeText = "To do today";
            } else if (dueIn == 1) {
                dueTimeText = "To do tomorrow";
            } else if (dueIn > 1) {
                dueTimeText = "To do in " + dueIn + "days";
            }

            ((TextView) rowView.findViewById(R.id.milestoneDate)).setText(dueTimeText);
        }
        else {
            ((TextView) rowView.findViewById(R.id.milestoneDate)).setText("");
        }
        ((CheckBox) rowView.findViewById(R.id.milestoneComplete)).setChecked(milestone.isComplete());

        ((CheckBox) rowView.findViewById(R.id.milestoneComplete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()) {
                    milestone.setComplete(true);
                }
                else {
                    milestone.setComplete(false);
                }
                if (assignmentViewer != null) {
                    assignmentViewer.updateProgressBar();
                }
            }
        });
        ((ImageView) rowView.findViewById(R.id.milestoneEdit)).setVisibility(View.GONE);

        return rowView;
    }

    public void setAssignmentViewer(AssignmentDetailsFragment assignmentViewer) {
        this.assignmentViewer = assignmentViewer;

    }
}
