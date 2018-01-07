package vanheek.justin.dev.studentcompanion;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;

import vanheek.justin.dev.studentcompanion.objects.Checklist;
import vanheek.justin.dev.studentcompanion.objects.Course;
import vanheek.justin.dev.studentcompanion.objects.Milestone;

/**
 * Created by justi on 2017-12-29.
 */

public class ProgramChecklistFragment extends ListFragment implements AdapterView.OnItemClickListener {

    View myView;
    private Checklist[] checklists;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.program_checklist, container, false);

        //Change Header Title
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Program Checklist");
        ((MainActivity) getActivity()).findViewById(R.id.toolbar).setBackgroundColor(getResources().getColor(R.color.colorPrimary));



        return myView;
    }

    private int getTotalProgress() {
        return (int)(((double)getTotalChecked())/getTotalAmount()*100+0.5);
    }

    private int getTotalAmount() {
        int checked = 0;
        for(Checklist checklist : checklists) {
            checked = checked + checklist.getTotalCourses();
        }
        return checked;
    }

    private int getTotalChecked() {
        int checked = 0;
        for(Checklist checklist : checklists) {
            checked = checked + checklist.getAmountChecked();
        }
        return checked;
    }

    //Creates the list of checklists
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        generateList();

        //Update data
        ((TextView) myView.findViewById(R.id.programSummary)).setText(getTotalChecked() + " / " + getTotalAmount() + " Courses Completed");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ((ProgressBar) myView.findViewById(R.id.progressBar)).setProgress(getTotalProgress(),true);
        }
        else {
            ((ProgressBar) myView.findViewById(R.id.progressBar)).setProgress(getTotalProgress());
        }
    }

    private void generateList() {
        checklists = new Checklist[((MainActivity)getActivity()).checklists.size()];
        for(int i = 0; i < checklists.length; i++) {
            checklists[i] = ((MainActivity)getActivity()).checklists.get(i);
        }
        ProgramChecklistArrayAdapter adapter = new ProgramChecklistArrayAdapter(getActivity(), checklists);
        adapter.notifyDataSetChanged();
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    //Open the CourseDetailsFragment with the clicked course
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Checklist c = checklists[position];
        ChecklistFragment frag = new ChecklistFragment();
        frag.setChecklist(c);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
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
        inflater.inflate(R.menu.courses_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_item_add_course:
                FragmentManager fragmentManager = getFragmentManager();
                EditProgramChecklistFragment frag = new EditProgramChecklistFragment();
                fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
                return true;
        }
        return false;
    }


}
