package vanheek.justin.dev.studentcompanion;

import java.util.ArrayList;

/**
 * Created by justi on 2017-12-30.
 */

public class Util<T> {

    public int find(T target, T[] array) {
        for(int i = 0; i < array.length; i++) {
            if(array[i] == target) {
                return i;
            }
        }
        return -1;
    }

    public void insertInto(ArrayList<T> list, int position, T item) {
        ArrayList<T> removed = new ArrayList<T>();
        removed.add(item);
        //Remove all elements and insert them in the same order into a temp arraylist
        for(int i = position; i < list.size(); i++) {
            removed.add(list.get(i));
        }
        while(list.size() > position) {
            list.remove(position);
        }
        //put them all back with the inserted one at the front
        for(T element : removed) {
            list.add(element);
        }
    }

}
