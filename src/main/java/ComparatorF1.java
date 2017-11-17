import java.util.Comparator;

/**
 * Created by Tao on 2017/11/3.
 */
public class ComparatorF1 implements Comparator {
    public int compare(Object p1, Object p2) {
        Population x1 = (Population)p1;
        Population x2 = (Population)p2;
        int flag = x1.getF().get(0).compareTo(x2.getF().get(0));
        return flag;
    }
}
