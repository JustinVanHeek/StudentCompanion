package vanheek.justin.dev.studentcompanion;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import vanheek.justin.dev.studentcompanion.objects.Checklist;

/**
 * Created by justi on 2017-12-29.
 */

public class ChecklistArrayAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] values;
    private final Checklist checklist;

    public ChecklistArrayAdapter(Context context, String[] values, Checklist checklist) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
        this.checklist = checklist;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View rowView = inflater.inflate(R.layout.checklist_row, parent, false);

        String courses = values[position];


        ((CheckBox)rowView.findViewById(R.id.checkBox)).setChecked(checklist.getChecked().get(position));
        ((CheckBox)rowView.findViewById(R.id.checkBox)).setText(courses);

        ((CheckBox)rowView.findViewById(R.id.checkBox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checklist.setChecked(position,((CheckBox)rowView.findViewById(R.id.checkBox)).isChecked());
            }
        });


        return rowView;
    }

}
