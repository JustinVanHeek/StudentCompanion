package vanheek.justin.dev.studentcompanion;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import vanheek.justin.dev.studentcompanion.objects.Checklist;

/**
 * Created by justi on 2017-12-30.
 */

public class EditChecklistFragment extends Fragment {

    View myView;
    private Checklist checklist;
    Integer index = -1;
    private boolean newChecklist = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.edit_checklist, container, false);

        //Change Header Title
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Edit Checklist");

        //Button Listeners
        myView.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                EditProgramChecklistFragment frag = new EditProgramChecklistFragment();
                frag.setChecklist(checklist, newChecklist);
                fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
            }

        });
        myView.findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                updateAssignment();
                FragmentManager fragmentManager = getFragmentManager();
                EditProgramChecklistFragment frag = new EditProgramChecklistFragment();
                frag.setChecklist(checklist, newChecklist);
                fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
            }

        });
        myView.findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                delete();
                FragmentManager fragmentManager = getFragmentManager();
                EditProgramChecklistFragment frag = new EditProgramChecklistFragment();
                frag.setChecklist(checklist, newChecklist);
                fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
            }

        });

        //Fill form
        fillAssignmentForm();

        return myView;
    }

    private void delete() {
        checklist.removeCourse(index);

        //Save changes to storage
        DataManager.saveData((MainActivity) getActivity());
    }

    private void updateAssignment() {
        if(index > -1) {
            checklist.setCourse(index, ((EditText) myView.findViewById(R.id.editName)).getText().toString());
        }
        else {
            Log.d("Checklist","Update" + index);
            checklist.addCourse(((EditText) myView.findViewById(R.id.editName)).getText().toString());

        }

        //Save changes to storage
        DataManager.saveData((MainActivity) getActivity());

    }

    private void fillAssignmentForm() {
        if(index > -1) {
            ((EditText) myView.findViewById(R.id.editName)).setText(checklist.getCourse(index));
        }
    }

    public void setChecklist(Checklist s) {
        this.checklist = s;

    }

    public void setChecklist(Checklist s, int i) {
        this.checklist = s;
        index = i;
    }

    public void setChecklist(Checklist s,boolean n, int i) {
        this.checklist = s;
        index = i;
        newChecklist = n;

    }

    public void setChecklist(Checklist s, boolean n) {
        this.checklist = s;
        newChecklist = n;
    }

}
