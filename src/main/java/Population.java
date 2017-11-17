import java.util.ArrayList;

/**
 * Created by Tao on 2017/11/2.
 */
public class Population {
    private ArrayList<Double> x = null;
    private int NumX = 0;
    private double crowd = 0.0;
    private int d = 0;      //dominate
    private int bd = 0;     //by dominated
    private ArrayList<Double> f = null;

    /** SET */
    public void setBd(int bd) {     this.bd = bd;   }
    public void setNumX(int numX) {     NumX = numX;    }
    public void setCrowd(double crowd) {    this.crowd = crowd;    }
    public void setD(int d) {   this.d = d;     }
    public void setList(ArrayList<Double> listx) {    this.x = listx;   }
    public void setF(ArrayList<Double> f) {     this.f = f;     }

    /** GET */
    public ArrayList<Double> getF() {   return f;   }
    public ArrayList<Double> getX() {   return x;   }
    public double getCrowd() {  return crowd;   }
    public int getBd() {    return bd;  }
    public int getD() {    return d;   }
    public int getNumX() {  return NumX;    }


}
