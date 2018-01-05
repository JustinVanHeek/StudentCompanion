package vanheek.justin.dev.studentcompanion;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import vanheek.justin.dev.studentcompanion.objects.Assignment;
import vanheek.justin.dev.studentcompanion.objects.Exam;

/**
 * Created by justi on 2017-12-29.
 */

public class HomeExamArrayAdapter extends ArrayAdapter<Exam> {

    private final Context context;
    private final Exam[] values;
    private final Fragment fragment;

    public HomeExamArrayAdapter(Context context, Exam[] values, Fragment f) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
        fragment = f;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.home_row, parent, false);

        final Exam exam = values[position];

        rowView.findViewById(R.id.container).setBackgroundColor(exam.getCourse().getColor());

        ((TextView) rowView.findViewById(R.id.itemName)).setText(exam.getName());

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = fragment.getFragmentManager();
                ExamDetailsFragment frag = new ExamDetailsFragment();
                frag.setExam(exam);
                fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
            }
        });

        return rowView;
    }

}
