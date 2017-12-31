package vanheek.justin.dev.studentcompanion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import vanheek.justin.dev.studentcompanion.objects.Exam;
import vanheek.justin.dev.studentcompanion.objects.Semester;

/**
 * Created by justi on 2017-12-29.
 */

public class SemesterArrayAdapter extends ArrayAdapter<Semester> {

    private final Context context;
    private final Semester[] values;

    public SemesterArrayAdapter(Context context, Semester[] values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.semester_row, parent, false);

        Semester semester = values[position];

        ((TextView) rowView.findViewById(R.id.semesterName)).setText(semester.getName());

        SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");

        ((TextView) rowView.findViewById(R.id.semesterStart)).setText(format.format(semester.getStart().getTime()));
        ((TextView) rowView.findViewById(R.id.semesterEnd)).setText(format.format(semester.getEnd().getTime()));


        return rowView;
    }

}
