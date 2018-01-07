package vanheek.justin.dev.studentcompanion;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import vanheek.justin.dev.studentcompanion.objects.Assignment;
import vanheek.justin.dev.studentcompanion.objects.Checklist;
import vanheek.justin.dev.studentcompanion.objects.Course;
import vanheek.justin.dev.studentcompanion.objects.Exam;
import vanheek.justin.dev.studentcompanion.objects.Milestone;
import vanheek.justin.dev.studentcompanion.objects.Semester;
import vanheek.justin.dev.studentcompanion.objects.WeeklySchedule;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //Data
    public ArrayList<Semester> semesters = new ArrayList<Semester>();
    public int currentSemester;
    public ArrayList<Checklist> checklists = new ArrayList<>();

    public ColorPicker cp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Testing purposes
        boolean cleanStart = false;
        if(cleanStart) {
            DataManager.saveData(this);
        }

        //Load the app's data from previous uses
        DataManager.loadData(this);

        //Default code
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Initialize Color Picker
        cp = new ColorPicker(MainActivity.this, 0, 0, 255);

        //Display home fragment
        android.app.FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new HomeFragment()).commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        android.app.FragmentManager fragmentManager = getFragmentManager();
        Util.restoreActionBar(this);

        if (id == R.id.nav_courses_layout) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new CoursesFragment()).commit();
        } else if (id == R.id.nav_assignment_layout) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new AssignmentFragment()).commit();
        } else if (id == R.id.nav_exam_layout) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new ExamFragment()).commit();
        } else if (id == R.id.nav_home_layout) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new HomeFragment()).commit();
        } else if (id == R.id.nav_grade_pred_layout) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new GradePredictionFragment()).commit();
        } else if (id == R.id.nav_checklist_layout) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new ProgramChecklistFragment()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addSemester(Semester semester) {
        semesters.add(semester);
    }
}
