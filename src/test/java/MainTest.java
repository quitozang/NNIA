import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Tao on 2017/11/2.
 */
public class MainTest {
    public static void main(String[] args) {
        ArrayList<Double> list = new ArrayList();
        list.add(1.0);
        list.add(3.0);
        list.add(7.0);
        list.add(4.0);
        ArrayList<Double> list2 = new ArrayList();
        list2.add(1.0);
        list.addAll(list2);
        ArrayList<Double> l = new ArrayList<Double>();
        l = list;
//        list.add(Double.POSITIVE_INFINITY);
//        MyComparator compare = new MyComparator();
//        Collections.sort(list, compare);
        for (int i = 0; i <l.size(); i++) {
            System.out.println(l.get(i) + " ");
        }

        System.out.println();
    }
}
