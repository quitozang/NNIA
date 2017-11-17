import java.util.Comparator;

/**
 * Created by Tao on 2017/11/3.
 */
public class MyComparator implements Comparator {
    public int compare(Object p1, Object p2) {
        Double x1 = (Double) p1;
        Double x2 = (Double) p2;
        int flag = x2.compareTo(x1);
        return flag;
    }
}
