package vanheek.justin.dev.studentcompanion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import vanheek.justin.dev.studentcompanion.objects.Course;
import vanheek.justin.dev.studentcompanion.objects.Semester;

/**
 * Created by justi on 2017-12-29.
 */

public class CourseArrayAdapter extends ArrayAdapter<Course> {

    private final Context context;
    private final Course[] values;

    public CourseArrayAdapter(Context context, Course[] values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.course_row, parent, false);

        Course course = values[position];

        rowView.findViewById(R.id.courseContainer).setBackgroundColor(course.getColor());

        ((TextView) rowView.findViewById(R.id.courseName)).setText(course.getName());
        ((TextView) rowView.findViewById(R.id.courseCode)).setText(course.getCourseCode());
        ((TextView) rowView.findViewById(R.id.courseCredits)).setText(""+course.getCredits());
        ((TextView) rowView.findViewById(R.id.courseProf)).setText(course.getProf());
        ((TextView) rowView.findViewById(R.id.courseRoom)).setText(course.getRoom());
        if (course.getCourseHours() != null) {
            ((TextView) rowView.findViewById(R.id.courseTime)).setText(course.getCourseHours().toString());
        }
        else {
            ((TextView) rowView.findViewById(R.id.courseTime)).setText("");
        }

        return rowView;
    }

}
