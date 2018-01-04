package vanheek.justin.dev.studentcompanion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import vanheek.justin.dev.studentcompanion.objects.Course;

/**
 * Created by justi on 2017-12-29.
 */

public class HomeCourseArrayAdapter extends ArrayAdapter<Course> {

    private final Context context;
    private final Course[] values;

    public HomeCourseArrayAdapter(Context context, Course[] values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.home_row, parent, false);

        Course course = values[position];

        rowView.findViewById(R.id.container).setBackgroundColor(course.getColor());

        ((TextView) rowView.findViewById(R.id.itemName)).setText(course.getName());

        return rowView;
    }

}
