package vanheek.justin.dev.studentcompanion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import vanheek.justin.dev.studentcompanion.objects.Assignment;
import vanheek.justin.dev.studentcompanion.objects.Course;

/**
 * Created by justi on 2017-12-29.
 */

public class CourseEvalArrayAdapter extends ArrayAdapter<Assignment>{

    private final Context context;
    private final Assignment[] values;

    public CourseEvalArrayAdapter(Context context, Assignment[] values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.course_eval_row, parent, false);

        Assignment assignment = values[position];

        ((TextView) rowView.findViewById(R.id.assignmentName)).setText(assignment.getName());
        if(assignment.getGrade() < 0) {
            ((TextView) rowView.findViewById(R.id.mark)).setText("--");
        }
        else {
            ((TextView) rowView.findViewById(R.id.mark)).setText(assignment.getGrade() + "%");
        }
        ((TextView) rowView.findViewById(R.id.percentOfGrade)).setText(assignment.getPercentOfGrade()+"%");

        return rowView;
    }

}
