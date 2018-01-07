package vanheek.justin.dev.studentcompanion;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ToggleButton;

import java.util.ArrayList;

import vanheek.justin.dev.studentcompanion.objects.Assignment;
import vanheek.justin.dev.studentcompanion.objects.Checklist;
import vanheek.justin.dev.studentcompanion.objects.Course;
import vanheek.justin.dev.studentcompanion.objects.Milestone;

/**
 * Created by justi on 2017-12-29.
 */

public class ChecklistFragment extends ListFragment implements AdapterView.OnItemClickListener {

    View myView;
    Checklist checklist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.courses_layout, container, false);

            //Change Header Title
            ((MainActivity) getActivity()).getSupportActionBar().setTitle(checklist.getName());
            ((MainActivity) getActivity()).findViewById(R.id.toolbar).setBackgroundColor(getResources().getColor(R.color.colorPrimary));


        return myView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Needed to add additional buttons to the top menu
        setHasOptionsMenu(true);
    }

    //Adds the (+) button to the top menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.course_details_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //Creates the list of courses
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String[] checked = new String[checklist.getChecked().size()];
        for(int i = 0; i < checklist.getCourses().size(); i++) {
            checked[i] = checklist.getCourses().get(i);
        }
        ChecklistArrayAdapter adapter = new ChecklistArrayAdapter(getActivity(),checked,checklist);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_item_edit_course:
                FragmentManager fragmentManager = getFragmentManager();
                EditProgramChecklistFragment frag = new EditProgramChecklistFragment();
                frag.setChecklist(checklist);
                fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
                return true;
        }
        return false;
    }

    public void setChecklist(Checklist c) {
        checklist = c;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        FragmentManager fragmentManager = getFragmentManager();
        EditChecklistFragment frag = new EditChecklistFragment();
        frag.setChecklist(checklist);
        fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
    }
}
