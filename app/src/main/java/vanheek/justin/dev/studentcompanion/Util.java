package vanheek.justin.dev.studentcompanion;

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

}
