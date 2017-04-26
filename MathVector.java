/**
 * Created by niklas on 23.12.16.
 */
public class MathVector {
    private double[] x;

    public MathVector(double... vector) {
        x = vector;
    }

    public static MathVector nullvector(int dimension) {
        double[] temp = new double[dimension];
        for (int i = 0; i < dimension; i++) {
            temp[i] = 0.0;
        }
        return(new MathVector(temp));
    }

    public MathVector multiply(double a) {
        double[] clone = x.clone();
        for (int i = 0; i < x.length; i++) {
            clone[i] *= a;
        }
        return(new MathVector(clone));
    }

    public MathVector divide(double a) {
        return multiply(1/a);
    }

    public double abs() {
        double temp = 0;
        for (int i = 0; i < x.length; i++) {
            temp += (x[i]*x[i]);
        }
        return Math.sqrt(temp);
    }

    public MathVector subtract(MathVector a) {
        if (x.length != a.getDimension()) throw new RuntimeException("Dimension does not match!");
        double[] clone = x.clone();
        for (int i = 0; i < x.length; i++) {
            clone[i] -= a.getXi(i);
        }
        return (new MathVector(clone));
    }

    public MathVector add(MathVector a) {
        if (x.length != a.getDimension()) throw new RuntimeException("Dimension does not match!");
        double[] clone = x.clone();
        for (int i = 0; i < x.length; i++) {
            clone[i] += a.getXi(i);
        }
        return (new MathVector(clone));
    }

    //TODO skalarprodukt, kreuzprodukt

    public int getDimension() {
        return x.length;
    }

    public double getXi(int i) {
        return x[i];
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj.getClass() != MathVector.class) return false;
        MathVector vec = (MathVector) obj;
        if (vec.getDimension() != x.length) return false;
        for (int i = 0; i < x.length; i++) {
            if(x[i] != vec.getXi(i)) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String a = "";
        for (int i = 0; i < x.length-1; i++) {
            a += x[i] + " ";
        }
        a+=x[x.length-1];
        return a;
    }
}
