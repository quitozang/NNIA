import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * Created by Tao on 2017/11/2.
 * NNIA - java
 */
public class MainNNIA {
    public static void main(String[] args) {
        /**
        Gmax:maximum number of generations
        nd:maximum size of dominant population)
        na:maximum size of active population)
        nc:size of clone population)
         */
        int Gmax = 200;
        int nd = 200;
        int na = 50;
        int nc = 200;

        /**
        initial the population
         */
        ArrayList<Population> B = new ArrayList<Population>();
        Individual indiv = new Individual();
        Random random = new Random();
        for (int i = 0; i < nd; i++) {
            ArrayList<Double> in = new ArrayList<Double>();
            in.add(random.nextDouble() * 1000);
            in.add(random.nextDouble() * 1000);
            Population tmp = indiv.fun(in);
            B.add(tmp);
        }

        for (int i = 0; i < Gmax; i++) {
            System.out.println(i);
            crowDistance(B);        //cal every individual's crowd distance
            ArrayList<Population> D = UpdateDomination(B,nd);
            ArrayList<Population> A = ActiveSelection(D, na);
            ArrayList<Population> C = Clone(A, nc);
            ArrayList<Population> CT = CrossOver(C, A);
            ArrayList<Population> Ct = Mutate(CT, 1.0 / nc);
            Ct.addAll(D);
            B = Ct;
        }
        ArrayList<Population> D = UpdateDomination(B,nd);
        ArrayList<Double> x = new ArrayList<Double>();
        ArrayList<Double> y = new ArrayList<Double>();
        for (int i = 0; i < D.size(); i++) {
            x.add(D.get(i).getF().get(0));
            y.add(D.get(i).getF().get(1));
        }
        System.out.println();
    }

    /**
     * 变异
     * @param T: 交叉变换后的种群
     * @param pm
     * @return
     */
    private static ArrayList<Population> Mutate(ArrayList<Population> T, double pm) {
        ArrayList<Population> R = new ArrayList<Population>();
        for (int i = 0; i < T.size(); i++) {
            if (Math.random() < pm) {
                ArrayList<String> str = IntergerToString(T.get(i).getX().get(0).intValue(),T.get(i).getX().get(1).intValue());
                String strc = str.get(0) + str.get(1);
                String tempStr = "";
                int r = (int)(Math.random() * (strc.length() - 2));
                if (strc.charAt(r) == '0') {
                    tempStr = strc.substring(0,r) + "1" + strc.substring(r+1);
                } else {
                    tempStr = strc.substring(0,r) + "0" + strc.substring(r+1);
                }
                int num1 = StringToInteger(tempStr.substring(0,str.get(0).length()));
                int num2 = StringToInteger(tempStr.substring(str.get(0).length()));
                while (num1 >= 1000) {
                    num1 = num1 / 10;
                }
                while (num2 >= 1000) {
                    num2 = num2 / 10;
                }
                ArrayList<Double> num = new ArrayList<Double>();
                num.add((double)num1);
                num.add((double)num2);
                Individual ind = new Individual();
                Population p = ind.fun(num);
                R.add(p);
            } else {
                R.add(T.get(i));
            }
        }
        return R;
    }

    /**
     * 交叉
     * @param C: the population after clone
     * @param A; the active selection population
     * @return
     */
    private static ArrayList<Population> CrossOver(ArrayList<Population> C, ArrayList<Population> A) {
        ArrayList<Population> T = new ArrayList<Population>();
        for (int i = 0; i < C.size(); i++) {
            int r = (int)(Math.random() * (A.size() - 1));
            Population a = A.get(r);
            ArrayList<Double> Xa = C.get(i).getX();
            ArrayList<Double> Xb = a.getX();
            ArrayList<Double> xtemp = new ArrayList<Double>();
            for (int j = 0; j < a.getX().size(); j++) {
                ArrayList<String> str = IntergerToString(Xa.get(j).intValue(),Xb.get(j).intValue());
                int R = (int)(Math.random() * (str.get(0).length() - 1));
                String temp = str.get(0).substring(0,R) + str.get(1).substring(R);
                xtemp.add((double)StringToInteger(temp));
            }
            Individual ind = new Individual();
            Population p = ind.fun(xtemp);
            T.add(p);
        }
        return T;
    }

    // binary string to integer
    private static int StringToInteger(String cadena) {
        int decimal = 0;
        for (int i = 0; i < cadena.length(); i++) {
            if (cadena.charAt(i) == '1') {
                decimal = decimal + (int)Math.pow(2,cadena.length() - 1 - i);
            }
        }
        return decimal;
    }

    /**
     * int 转 二进制字符串
     * @param numero
     * @param numero2
     * @return
     */
    private static ArrayList<String> IntergerToString(int numero, int numero2) {
        ArrayList<String> cadenas = new ArrayList<String>();
        cadenas.add(Integer.toBinaryString(numero));
        cadenas.add(Integer.toBinaryString(numero2));
        if (cadenas.get(0).length() > cadenas.get(1).length()) {
            StringBuilder tmp = new StringBuilder();
            int len = cadenas.get(0).length() - cadenas.get(1).length();
            for (int i = 0; i < len; i++) {
                tmp.append("0");
            }
            tmp.append(cadenas.get(1));
            cadenas.set(1,tmp.toString());
        } else {
            StringBuilder tmp = new StringBuilder();
            int len = cadenas.get(1).length() - cadenas.get(0).length();
            for (int i = 0; i < len; i++) {
                tmp.append("0");
            }
            tmp.append(cadenas.get(0));
            cadenas.set(0,tmp.toString());
        }
        return cadenas;
    }

    /**
     * proportional clone operation
     * @param A: active population
     * @param Nc: size of clone population
     * @return
     */
    private static ArrayList<Population> Clone(ArrayList<Population> A, int Nc) {
        ArrayList<Population> c = new ArrayList<Population>();
        double num = 0.0;
        int k = 0;
        ComparatorCrowdReverse comparator = new ComparatorCrowdReverse();   //降序排列
        Collections.sort(A, comparator);
        for (int i = 0; i < A.size(); i++) {
            if (A.get(i).getCrowd() != Double.POSITIVE_INFINITY) {
                k = i;
                break;
            }
        }
        for (int j = 0; j < k; j++) {
            A.get(j).setCrowd(A.get(k).getCrowd() * 2);
        }
        for (int i = 0; i < A.size(); i++) {
            num += A.get(i).getCrowd();
        }
        //copy by proportion
        for (int i = 0; i < A.size(); i++) {
            int l = (int)(A.get(i).getCrowd() * Nc / num);
            for (int j = 0; j < l; j++) {
                c.add(A.get(i));
            }
        }
        return c;
    }

    /**
     *
     * @param D: domination
     * @param na: maximum size of active population
     * @return
     */
    private static ArrayList<Population> ActiveSelection(ArrayList<Population> D, int na) {
        ArrayList<Population> res = new ArrayList<Population>();
        if (D.size() <= na) {
            return D;
        } else {
            ComparatorCrowdReverse comparator = new ComparatorCrowdReverse();   //降序排列
            Collections.sort(D, comparator);
            for (int i = 0; i < na; i++) {
                res.add(D.get(i));
            }
            return res;
        }
    }

    /**
    计算个体的拥挤距离
     */
    private static void crowDistance(ArrayList<Population> B) {
        int n = B.size();
        //B.get(0).getF().size() = 2
        for (int i = 0; i < B.get(0).getF().size(); i++) {
            if (i == 0) {                   //根据Population对象中属性f1，f2进行排序
                ComparatorF1 comparator = new ComparatorF1();
                Collections.sort(B,comparator);
            } else {
                ComparatorF2 comparator = new ComparatorF2();
                Collections.sort(B,comparator);
            }
            B.get(0).setCrowd(Double.POSITIVE_INFINITY);
            B.get(n-1).setCrowd(Double.POSITIVE_INFINITY);
            double h = B.get(n-1).getF().get(i) - B.get(0).getF().get(i);
            for (int j = 1; j < n-1; j++) {
                double tmpCrowd = B.get(j).getCrowd();
                double tmp = (B.get(j+1).getF().get(i) - B.get(j-1).getF().get(i)) / h;
                B.get(j).setCrowd(tmpCrowd + tmp);
            }
        }
    }

    /**
     *input: B -- pre updated population    Nd -- maximum number of domiantion population
     * output: dominated solution
     */
    private static ArrayList<Population> UpdateDomination(ArrayList<Population> B, int Nd) {
        int n = B.size();
        ArrayList<Population> DT = new ArrayList<Population>();
        ArrayList<Population> res = new ArrayList<Population>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (Dominant(B.get(i),B.get(j))) {      //whether B.get(i) is dominated B.get(j)
                    B.get(i).setD(B.get(i).getD() + 1);     //how many individual it dominates
                    B.get(j).setBd(B.get(j).getBd() + 1);   //how many individuals dominate it
                }
            }
        }
        for (int i = 0; i < n; i++) {
            if (B.get(i).getBd() == 0) {        //not dominated by any other individual
                DT.add(B.get(i));
            }
        }
        if (DT.size() <= Nd) {
            return DT;
        } else {
            ComparatorCrowdReverse comparator = new ComparatorCrowdReverse();   //降序排列
            Collections.sort(DT, comparator);
            for (int i = 0; i < Nd; i++) {
                res.add(DT.get(i));
            }
            return res;
        }
    }

    /**
     * cal x dominated y or not
     * @param x:Populationi
     * @param y:Population
     * @return
     */
    private static boolean Dominant(Population x, Population y) {
        for (int i = 0; i < x.getF().size(); i++) {
            if (x.getF().get(i) > y.getF().get(i)) {
                return false;
            }
        }
        return true;
    }
}
