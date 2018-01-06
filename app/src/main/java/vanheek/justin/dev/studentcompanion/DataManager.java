package vanheek.justin.dev.studentcompanion;

import android.app.Fragment;
import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import vanheek.justin.dev.studentcompanion.objects.Semester;

/**
 * Created by justi on 2018-01-04.
 */

public class DataManager {

    private static final String SAVED_DATA_FILENAME = "student_companion_saved_data";

    public static void saveData(MainActivity act) {
        Log.d("DataManager", "Saving Data...");
        FileOutputStream fos = null;
        try {
            fos = act.openFileOutput(SAVED_DATA_FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(act.semesters);
            oos.writeInt(act.currentSemester);

            oos.close();
            Log.d("DataManager", "Save Successful");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void loadData(MainActivity act) {
        Log.d("DataManager", "Loading Data...");
        FileInputStream fis = null;
        try {
            fis = act.openFileInput(SAVED_DATA_FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);

            try {
                act.semesters = (ArrayList<Semester>) ois.readObject();
                act.currentSemester = ois.readInt();
                Log.d("DataManager", "Load Successful");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
