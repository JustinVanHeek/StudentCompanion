package vanheek.justin.dev.studentcompanion;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import vanheek.justin.dev.studentcompanion.objects.Checklist;

/**
 * Created by justi on 2017-12-29.
 */

public class EditChecklistArrayAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] values;
    private final Checklist checklist;
    private final Fragment fragment;

    public EditChecklistArrayAdapter(Context context, String[] values, Checklist checklist, Fragment f) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
        this.checklist = checklist;
        fragment = f;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View rowView = inflater.inflate(R.layout.edit_checklist_row, parent, false);

        String courses = values[position];


        ((CheckBox)rowView.findViewById(R.id.checkBox)).setChecked(checklist.getChecked().get(position));
        ((CheckBox)rowView.findViewById(R.id.checkBox)).setText(courses);

        ((CheckBox)rowView.findViewById(R.id.checkBox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checklist.setChecked(position,((CheckBox)rowView.findViewById(R.id.checkBox)).isChecked());
            }
        });

        rowView.findViewById(R.id.editCourse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = fragment.getFragmentManager();
                EditChecklistFragment frag = new EditChecklistFragment();
                frag.setChecklist(checklist,((EditProgramChecklistFragment)fragment).newChecklist,position);
                fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
            }
        });


        return rowView;
    }

}
