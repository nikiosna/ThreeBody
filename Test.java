/**
 * Created by niklas on 27.02.17.
 */
public class Test {
    public static void main(String[] args) {
        double a = Unit.convert(13070, new Unit(1,-1,0), new Unit(1,-1,0,149597870700.0,31536000.0,5.9722*Math.pow(10,24)));
        System.out.println(a);
    }
}
