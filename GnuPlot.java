import org.leores.plot.JGnuplot;
import org.leores.util.data.DataTableSet;

import java.util.ArrayList;

/**
 * Created by niklas on 02.03.17.
 */
public class GnuPlot {

    public static void plot2d(ArrayList<Double> x, ArrayList<Double> y, String name) {
        plot2d(toArray(x), toArray(y), name);
    }

    public static void plot2d(double[] x, double[] y, String name) {
        JGnuplot jg = new JGnuplot() {
            {
                terminal = "pngcairo enhanced dashed";
                output = name + ".png";
            }
        };

        JGnuplot.Plot plot = new JGnuplot.Plot("") {
            {
                xlabel = "x";
                ylabel = "y";
            }
        };

        DataTableSet dts = plot.addNewDataTableSet("2D Plot");
        dts.addNewDataTable("", x, y);
        jg.execute(plot, jg.plot2d);
    }

    public static void plot3d(ArrayList<Double> x, ArrayList<Double> y, ArrayList<Double> z, String name) {
        plot3d(toArray(x), toArray(y), toArray(z), name);
    }

    public static void plot3d(double[] x, double[] y, double[] z, String name) {
        JGnuplot jg = new JGnuplot() {
            {
                terminal = "pngcairo enhanced dashed";
                output = name + ".png";
            }
        };
        JGnuplot.Plot plot = new JGnuplot.Plot("") {
            {
                xlabel = "x";
                ylabel = "y";
                zlabel = "z";
            }
        };
        DataTableSet dts = plot.addNewDataTableSet("3D Plot");
        dts.addNewDataTable("", x, y, z);
        jg.execute(plot, jg.plot3d);
    }

    public static double[] toArray(ArrayList<Double> list) {
        double[] array = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }
}
