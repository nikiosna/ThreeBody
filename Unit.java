/**
 * Created by niklas on 26.02.17.
 */
public class Unit {
    private int length_dimension;
    private int time_dimension;
    private int mass_dimension;

    private double length_factor_si;
    private double time_factor_si;
    private double mass_factor_si;

    public Unit(int length_dimension, int time_dimension, int mass_dimension, double length_factor_si, double time_factor_si, double mass_factor_si) {
        this.length_dimension = length_dimension;
        this.time_dimension = time_dimension;
        this.mass_dimension = mass_dimension;

        this.length_factor_si = length_factor_si;
        this.time_factor_si = time_factor_si;
        this.mass_factor_si = mass_factor_si;
    }

    public Unit(int length_dimension, int time_dimension, int mass_dimension) {
        this(length_dimension, time_dimension, mass_dimension, 1.0, 1.0, 1.0);
    }

    public Unit getOtherDimension(int length_dimension, int time_dimension, int mass_dimension) {
        return new Unit(length_dimension, time_dimension, mass_dimension, this.length_factor_si, this.time_factor_si, this.mass_factor_si);
    }

    public boolean equal_dimension(Unit a) {
        if(length_dimension != a.getLength_dimension())
            return false;
        if(time_dimension != a.getTime_dimension())
            return false;
        if(mass_dimension != a.getMass_dimension())
            return false;

        return true;
    }

    public static double convert(double a, Unit from, Unit to) {
        if(!from.equal_dimension(to)) throw new RuntimeException("Unit dimensions does not match");

        a *= Math.pow(from.getLength_factor_si(),from.getLength_dimension());
        a *= Math.pow(from.getTime_factor_si(),from.getTime_dimension());
        a *= Math.pow(from.getMass_factor_si(),from.getMass_dimension());

        a /= Math.pow(to.getLength_factor_si(),to.getLength_dimension());
        a /= Math.pow(to.getTime_factor_si(),to.getTime_dimension());
        a /= Math.pow(to.getMass_factor_si(),to.getMass_dimension());

        return a;
    }

    public static MathVector convert(MathVector a, Unit from, Unit to) {
        if(!from.equal_dimension(to)) throw new RuntimeException("Unit dimensions does not match");

        a = a.multiply(Math.pow(from.getLength_factor_si(),from.getLength_dimension()));
        a = a.multiply(Math.pow(from.getTime_factor_si(),from.getTime_dimension()));
        a = a.multiply(Math.pow(from.getMass_factor_si(),from.getMass_dimension()));

        a = a.divide(Math.pow(to.getLength_factor_si(),to.getLength_dimension()));
        a = a.divide(Math.pow(to.getTime_factor_si(),to.getTime_dimension()));
        a = a.divide(Math.pow(to.getMass_factor_si(),to.getMass_dimension()));

        return a;
    }

    public int getLength_dimension() {return  length_dimension;}
    public int getTime_dimension() {return  time_dimension;}
    public int getMass_dimension() {return  mass_dimension;}

    public double getLength_factor_si() {return  length_factor_si;}
    public double getTime_factor_si() {return  time_factor_si;}
    public double getMass_factor_si() {return  mass_factor_si;}

}
