package vanheek.justin.dev.studentcompanion.objects;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by justi on 2018-01-06.
 */

public class Checklist implements Serializable{

    private ArrayList<String> courses = new ArrayList<>();
    private ArrayList<Boolean> checked = new ArrayList<>();
    private String name;

    public void addCourse(String name) {
        courses.add(name);
        checked.add(false);
    }

    public int getAmountChecked() {
        int i = 0;
        for(Boolean b : checked) {
            if(b) {
                i++;
            }
        }
        return i;
    }

    public int getTotalCourses() {
        return courses.size();
    }

    public double getProgress() {
        double i = getAmountChecked();
        return i/checked.size()*100;
    }

    public void removeCourse(int i) {
        courses.remove(i);
        checked.remove(i);
    }

    public Boolean isChecked(int i) {
        return checked.get(i);
    }

    public ArrayList<String> getCourses() {
        ArrayList<String> temp = new ArrayList<>();
        for(String s : courses) {
            temp.add(s);
        }
        return temp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse(int position) {
        return courses.get(position);
    }

    public ArrayList<Boolean> getChecked() {
        return checked;
    }

    public void setCourse(Integer index, String name) {
        courses.set(index,name);
    }

    public void setChecked(int position, boolean check) {
        checked.set(position,check);
    }
}
