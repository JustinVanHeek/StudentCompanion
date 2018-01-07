package vanheek.justin.dev.studentcompanion;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import vanheek.justin.dev.studentcompanion.objects.Checklist;
import vanheek.justin.dev.studentcompanion.objects.Course;

/**
 * Created by justi on 2017-12-29.
 */

public class ProgramChecklistArrayAdapter extends ArrayAdapter<Checklist> {

    private final Context context;
    private final Checklist[] values;

    public ProgramChecklistArrayAdapter(Context context, Checklist[] values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.program_checklist_row, parent, false);

        Checklist checklist = values[position];


        ((TextView)rowView.findViewById(R.id.programName)).setText(checklist.getName());
        ((TextView)rowView.findViewById(R.id.programAmount)).setText(checklist.getAmountChecked()+"/"+checklist.getTotalCourses());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ((ProgressBar)rowView.findViewById(R.id.programProgress)).setProgress((int)(checklist.getProgress()+0.5),true);
            Log.d("Checklist","progress"+((int)(checklist.getProgress()+0.5)));
        }
        else {
            ((ProgressBar)rowView.findViewById(R.id.programProgress)).setProgress((int)(checklist.getProgress()+0.5));
        }


        return rowView;
    }

}
