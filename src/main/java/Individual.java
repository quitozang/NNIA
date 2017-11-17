import java.util.ArrayList;

/**
 * Created by Tao on 2017/11/2.
 */
public class Individual {
    public Population fun(ArrayList<Double> x) {
        double x1 = x.get(0);
        double x2 = x.get(1);
        ArrayList<Double> listx = new ArrayList<Double>();
        listx.add(x1);
        listx.add(x2);
        ArrayList<Double> listf = new ArrayList<Double>();
        Population res = new Population();
        double f1 = x1 / 1000;
        double h = 1 + (x2 / 100);
        double f2 = h * (1 - Math.pow((f1/h),2) - (f1/h) * Math.sin(8 * Math.PI * f1));
        listf.add(f1);
        listf.add(f2);
        res.setF(listf);
        res.setList(listx);
        res.setNumX(2);
        return res;
    }

}
