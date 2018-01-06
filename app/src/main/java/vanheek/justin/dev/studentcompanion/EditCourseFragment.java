package vanheek.justin.dev.studentcompanion;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;

import java.util.ArrayList;
import java.util.Calendar;

import vanheek.justin.dev.studentcompanion.objects.Assignment;
import vanheek.justin.dev.studentcompanion.objects.Course;
import vanheek.justin.dev.studentcompanion.objects.Semester;
import vanheek.justin.dev.studentcompanion.objects.WeeklySchedule;

/**
 * Created by justi on 2017-12-29.
 */

public class EditCourseFragment extends Fragment {

    View myView;
    private Course course;
    private WeeklySchedule courseSchedule = new WeeklySchedule();
    private WeeklySchedule officeSchedule = new WeeklySchedule();
    private int chosenColor = Color.BLUE;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.edit_course_layout, container, false);

        //Change Header Title
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Edit Course");

        //Set up Semester Spinner
        Spinner spinner = (Spinner) myView.findViewById(R.id.editSemester);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item, getStringArrayOfSemesters()); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        //Button Listeners
        myView.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                if(course != null) {
                    CourseDetailsFragment frag = new CourseDetailsFragment();
                    frag.setCourse(course);
                    fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
                }
                else {
                    fragmentManager.beginTransaction().replace(R.id.content_frame, new CoursesFragment()).commit();
                }
            }

        });
        myView.findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                updateCourse();
                FragmentManager fragmentManager = getFragmentManager();
                CourseDetailsFragment frag = new CourseDetailsFragment();
                frag.setCourse(course);
                fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
            }

        });
        myView.findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                delete();
                FragmentManager fragmentManager = getFragmentManager();

                    fragmentManager.beginTransaction().replace(R.id.content_frame, new SemesterFragment()).commit();
                }


        });

        //Open Popup Forms
        myView.findViewById(R.id.editSchedule).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popup(inflater, courseSchedule, R.id.editSchedule);
            }

        });
        myView.findViewById(R.id.editOfficeHours).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popup(inflater, officeSchedule, R.id.editOfficeHours);
            }

        });

        //Fill form
        fillCourseForm();

        changeColor();

        return myView;
    }

    private void delete() {
        course.getSemester().removeCourse(course);

        //Save changes to storage
        DataManager.saveData((MainActivity) getActivity());
    }

    private String[] getStringArrayOfSemesters() {
        ArrayList<Semester> semesters = ((MainActivity)getActivity()).semesters;
        String[] result = new String[semesters.size()];
        for(int i = 0; i < semesters.size(); i++) {
            result[i] = semesters.get(i).getName();
        }
        return result;
    }

    private void changeColor() {
        final ColorPicker cp = ((MainActivity) getActivity()).cp;
        //Change color
        myView.findViewById(R.id.editColor).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                /* Show color picker dialog */
                cp.show();

    /* Set a new Listener called when user click "select" */
                cp.setCallback(new ColorPickerCallback() {
                    @Override
                    public void onColorChosen(@ColorInt int color) {
                        chosenColor = color;
                        myView.findViewById(R.id.editColor).setBackgroundColor(color);
                    }
                });

            }

        });
        ((MainActivity)getActivity()).findViewById(R.id.toolbar).setBackgroundColor(chosenColor);
    }

    private void popup(LayoutInflater inflater, final WeeklySchedule schedule, final int displayText) {
        try {
            //Inflate the view from a predefined XML layout
            final View layout = inflater.inflate(R.layout.weekly_schedule_selection, null);
            // create a 300px width and 470px height PopupWindow
            final PopupWindow pw = new PopupWindow(layout, myView.getWidth(), myView.getHeight(), true);
            // display the popup in the center
            pw.showAtLocation(layout, Gravity.CENTER, 0, 0);

            //Popups for time
            layout.findViewById(R.id.editStart).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            String minute = selectedMinute+"";
                            if(selectedMinute < 10) {
                                minute = "0" + minute;
                            }
                            ((TextView)layout.findViewById(R.id.editStart)).setText( selectedHour + ":" + minute);
                            schedule.start = Integer.parseInt(selectedHour+""+minute);
                        }
                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();

                }
            });
            layout.findViewById(R.id.editEnd).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            String minute = selectedMinute+"";
                            if(selectedMinute < 10) {
                                minute = 0 + minute;
                            }
                            ((TextView)layout.findViewById(R.id.editEnd)).setText( selectedHour + ":" + minute);
                            schedule.end = Integer.parseInt(selectedHour+""+minute);
                        }
                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();

                }
            });

            //Cancel Button
            layout.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    pw.dismiss();
                }

            });

            //Submit Button
            layout.findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    schedule.mon = ((CheckBox)layout.findViewById(R.id.checkMon)).isChecked();
                    schedule.tue = ((CheckBox)layout.findViewById(R.id.checkTue)).isChecked();
                    schedule.wed = ((CheckBox)layout.findViewById(R.id.checkWed)).isChecked();
                    schedule.thu = ((CheckBox)layout.findViewById(R.id.checkThu)).isChecked();
                    schedule.fri = ((CheckBox)layout.findViewById(R.id.checkFri)).isChecked();
                    schedule.sat = ((CheckBox)layout.findViewById(R.id.checkSat)).isChecked();
                    schedule.sun = ((CheckBox)layout.findViewById(R.id.checkSun)).isChecked();

                    ((EditText) myView.findViewById(displayText)).setText(schedule.toString());

                    pw.dismiss();
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateCourse() {
        if(course == null) {
            course = new Course();

        }
        else {
            //Check if semester changed
            //if(((Spinner)myView.findViewById(R.id.editSemester)).getSelectedItemPosition() != new Util<Semester>().find(course.getSemester(), ((MainActivity) getActivity()).semesters)) {
                course.getSemester().removeCourse(course);
            //}
        }
        //Set the semester
        ((MainActivity) getActivity()).semesters.get(((Spinner)myView.findViewById(R.id.editSemester)).getSelectedItemPosition()).addCourse(course);
        course.setSemester(((MainActivity) getActivity()).semesters.get(((Spinner)myView.findViewById(R.id.editSemester)).getSelectedItemPosition()));

        course.setName(((EditText) myView.findViewById(R.id.editName)).getText().toString());
        course.setColor(chosenColor);
        course.setCourseCode(((EditText) myView.findViewById(R.id.editCode)).getText().toString());
        try {
            course.setCredits(Integer.parseInt(((EditText) myView.findViewById(R.id.editCredits)).getText().toString()));
        }
        catch (NumberFormatException e){
            course.setCredits(0);
        }
        course.setRoom(((EditText) myView.findViewById(R.id.editRoom)).getText().toString());
        course.setCourseHours(courseSchedule);
        course.setProf(((EditText) myView.findViewById(R.id.editProfessor)).getText().toString());
        course.setOffice(((EditText) myView.findViewById(R.id.editOffice)).getText().toString());
        course.setOfficeHours(officeSchedule);
        //TODO Course Evaluation
        course.setNotes(((EditText) myView.findViewById(R.id.editNotes)).getText().toString());

        //Save changes to storage
        DataManager.saveData((MainActivity) getActivity());
    }

    private void fillCourseForm() {



        if(course != null) {

            ((Spinner) myView.findViewById(R.id.editSemester)).setSelection(new Util<Semester>().find(course.getSemester(), ((MainActivity) getActivity()).semesters));
            chosenColor = course.getColor();
            myView.findViewById(R.id.editColor).setBackgroundColor(chosenColor);
            ((EditText) myView.findViewById(R.id.editName)).setText(course.getName());
            ((EditText) myView.findViewById(R.id.editCode)).setText(course.getCourseCode());
            ((EditText) myView.findViewById(R.id.editCredits)).setText(""+course.getCredits());
            ((EditText) myView.findViewById(R.id.editRoom)).setText(course.getRoom());
            courseSchedule = course.getCourseHours();
            if (courseSchedule != null) {
                ((EditText) myView.findViewById(R.id.editSchedule)).setText(courseSchedule.toString());
            }
            else {
                courseSchedule = new WeeklySchedule();
            }
            ((EditText) myView.findViewById(R.id.editProfessor)).setText(course.getProf());
            ((EditText) myView.findViewById(R.id.editOffice)).setText(course.getOffice());
            officeSchedule = course.getOfficeHours();
            if (officeSchedule != null) {
                ((EditText) myView.findViewById(R.id.editOfficeHours)).setText(officeSchedule.toString());
            }
            else {
                officeSchedule = new WeeklySchedule();
            }
            createCourseEvalList(course);
            ((EditText) myView.findViewById(R.id.editNotes)).setText(course.getNotes());
        }
        else {
            ((Spinner) myView.findViewById(R.id.editSemester)).setSelection(((MainActivity) getActivity()).currentSemester);
        }
    }

    private void createCourseEvalList(Course c) {
        CourseEvalArrayAdapter adapter = new CourseEvalArrayAdapter(getActivity(), c.getAssignmentsAsArray());
        ListView list = ((ListView) myView.findViewById(R.id.editCourseEval));
        list.setAdapter(adapter);
        CourseEvalExamArrayAdapter adapter2 = new CourseEvalExamArrayAdapter(getActivity(), c.getExamsAsArray());
        ListView list2 = ((ListView) myView.findViewById(R.id.editCourseEvalExams));
        list2.setAdapter(adapter2);
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
