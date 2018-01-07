package vanheek.justin.dev.studentcompanion;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Calendar;

import vanheek.justin.dev.studentcompanion.objects.Checklist;
import vanheek.justin.dev.studentcompanion.objects.Semester;

/**
 * Created by justi on 2017-12-30.
 */

public class EditProgramChecklistFragment extends ListFragment {

    View myView;
    private Checklist checklist;
    private String[] courses;
    public Boolean newChecklist = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.edit_program_checklist, container, false);

        if(checklist == null) {
            newChecklist = true;
            checklist = new Checklist();
        }

        //Change Header Title
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Edit Program Checklist");

        //Button Listeners
        myView.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new ProgramChecklistFragment()).commit();
            }

        });
        myView.findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                updateAssignment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new ProgramChecklistFragment()).commit();
            }

        });
        myView.findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                delete();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new ProgramChecklistFragment()).commit();
            }

        });

        myView.findViewById(R.id.addCourse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                EditChecklistFragment frag = new EditChecklistFragment();
                frag.setChecklist(checklist, newChecklist);
                fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
            }
        });

        if(newChecklist) {
            myView.findViewById(R.id.addCourse).setVisibility(View.GONE);
        }

        //Fill form
        fillAssignmentForm();

        return myView;
    }

    private void delete() {
        ((MainActivity)getActivity()).checklists.remove(checklist);

        //Save changes to storage
        DataManager.saveData((MainActivity) getActivity());
    }

    private void updateAssignment() {
        if(newChecklist) {
            checklist = new Checklist();
            ((MainActivity)getActivity()).checklists.add(checklist);
        }

        checklist.setName(((EditText) myView.findViewById(R.id.editName)).getText().toString());


        //Save changes to storage
        DataManager.saveData((MainActivity) getActivity());

    }

    private void fillAssignmentForm() {
        if(!newChecklist) {
            ((EditText) myView.findViewById(R.id.editName)).setText(checklist.getName());
        }
    }

    public void setChecklist(Checklist s) {
        this.checklist = s;
    }

    public void setChecklist(Checklist s, Boolean n) {
        this.checklist = s;
        newChecklist = n;
    }

    //Creates the list of checklists
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        generateList();
    }

    private void generateList() {
        if(checklist!= null) {
        courses = new String[checklist.getCourses().size()];
        for(int i = 0; i < courses.length; i++) {
            courses[i] = checklist.getCourses().get(i);
        }
        EditChecklistArrayAdapter adapter = new EditChecklistArrayAdapter(getActivity(), courses, checklist,this);
        adapter.notifyDataSetChanged();
        setListAdapter(adapter);
    }}

}
