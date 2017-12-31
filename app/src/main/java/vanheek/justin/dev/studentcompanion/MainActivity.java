package vanheek.justin.dev.studentcompanion;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Calendar;
import java.util.Date;

import vanheek.justin.dev.studentcompanion.objects.Assignment;
import vanheek.justin.dev.studentcompanion.objects.Course;
import vanheek.justin.dev.studentcompanion.objects.Exam;
import vanheek.justin.dev.studentcompanion.objects.Semester;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public Semester[] semesters;
    public int currentSemester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //TESTING PURPOSES ONLY
        Semester semester = new Semester("Test Semester",Calendar.getInstance(),Calendar.getInstance());
        Course testCourse = new Course("Test Course", Color.CYAN, semester);
        Assignment a = new Assignment();
        a.setName("Test Assignment");
        a.setPercentOfGrade(80);
        a.setCourse(testCourse);
        a.setDue(Calendar.getInstance());
        testCourse.addAssignment(a);
        Exam e = new Exam();
        e.setName("Test Exam");
        e.setPercentOfGrade(20);
        e.setCourse(testCourse);
        e.setDate(Calendar.getInstance());
        testCourse.addExam(e);
        semester.addCourse(testCourse);
        semesters = new Semester[1];
        semesters[0] = semester;
        currentSemester = 0;



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

        if (id == R.id.nav_courses_layout) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new CoursesFragment()).commit();
        } else if (id == R.id.nav_assignment_layout) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new AssignmentFragment()).commit();
        } else if (id == R.id.nav_exam_layout) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new ExamFragment()).commit();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addSemester(Semester semester) {
        Semester[] temp = new Semester[semesters.length+1];
        for (int i = 0; i < semesters.length; i++) {
            temp[i] = semesters[i];
        }
        temp[semesters.length] = semester;
        semesters = temp;
    }
}
