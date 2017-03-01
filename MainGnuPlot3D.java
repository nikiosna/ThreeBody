import org.leores.plot.JGnuplot;
import org.leores.util.data.DataTableSet;

import java.util.ArrayList;

/**
 * Created by niklas on 26.12.16.
 */
public class MainGnuPlot3D {
    public static void main(String[] args) {
        double step = 3600;
        Universe universe = new Universe(3, step, new Unit(3,-2,-1));
        Body[] bodies = new Body[2];
        Body[] bodies_Temp = new Body[2];
        MathVector x,v;

        //Sonne 2457806.500000000 = A.D. 2017-Feb-22 00:00:00.0000 TDB
        //3.236391913635729E-03  4.284723639176048E-03 -1.529897257811437E-04
        //-3.138078028556716E-06  6.661636694605305E-06  6.708175430331265E-08
        x = new MathVector(3.236391913635729*Math.pow(10,-3), 4.284723639176048*Math.pow(10,-3), -1.529897257811437*Math.pow(10,-4));
        v = new MathVector(-3.138078028556716*Math.pow(10,-6), 6.661636694605305*Math.pow(10,-6), 6.708175430331265*Math.pow(10,-8));
        x = x.multiply(149597870700.0);
        v = v.multiply(149597870700.0);
        bodies[0] = new Body(1.988544*Math.pow(10,30), x, v);

        //Erde 2457806.500000000 = A.D. 2017-Feb-22 00:00:00.0000 TDB
        //-8.806169743802366E-01  4.485158460269819E-01 -1.678989099393571E-04
        //-8.015712939006431E-03 -1.543117101524157E-02  1.080599404501372E-06
        x = new MathVector(-8.806169743802366*Math.pow(10,-1), 4.485158460269819*Math.pow(10,-1), -1.678989099393571*Math.pow(10,-4));
        v = new MathVector(-8.015712939006431*Math.pow(10,-3), -1.543117101524157*Math.pow(10,-2), 1.080599404501372*Math.pow(10,-6));
        x = x.multiply(149597870700.0);
        v = v.multiply(149597870700.0);
        bodies[1] = new Body( 5.97219*Math.pow(10,24), x, v);

        boolean running = true;
        double time = 0;
        double max = Math.rint(31622400/step);
        int out = (int)Math.rint(max/50);
        double energy;
        ArrayList<Double> x1 = new ArrayList<>();
        ArrayList<Double> x2 = new ArrayList<>();
        ArrayList<Double> x3 = new ArrayList<>();

        ArrayList<Double> e = new ArrayList<>();
        ArrayList<Double> t = new ArrayList<>();

        while (running) {
            energy = 0;
            for (int i = 0; i < bodies.length; i++) {
                if(i==1 && (time%out==0)) {
                    x1.add(bodies[i].getPosition().getXi(0));
                    x2.add(bodies[i].getPosition().getXi(1));
                    x3.add(bodies[i].getPosition().getXi(2));
                }

                //energy += universe.energy(bodies[i]);
                energy += universe.pot_energy(bodies[i], bodies) + bodies[i].kin_energy();

                bodies_Temp[i] = universe.badIntegrator( universe.force(bodies[i],bodies), bodies[i]);

                /*String string = bodies[i].getPosition().toString();
                if (string.contains("NaN") || string.contains("Infinity")) {
                    running = false;
                    System.out.println(i + " : " + string);
                }*/
            }

            if((time%out)==0) {
                e.add(energy);
                t.add(time);
            }

            bodies = bodies_Temp;
            time++;
            if(time%((int)Math.rint(max/100))==0) {
                System.out.println(Math.rint((time/max)*100) + "%" + "    " + x1.size());
            }
            if(time>=max) running=false;

        }

        plot3d(toArray(x1), toArray(x2), toArray(x3), "plot3d", true);
        plot3d(toArray(t), toArray(e), null, "energy3d", false);

    }

    public static void plot3d(double[] x, double[] y, double[] z, String name, boolean a) {
        JGnuplot jg = new JGnuplot() {
            {
                terminal = "pngcairo enhanced dashed";
                output = name + ".png";
            }
        };
        JGnuplot.Plot plot;
        if (a) {
            plot = new JGnuplot.Plot("") {
                {
                    xrange = "[-180000000000:180000000000]";
                    yrange = "[-180000000000:180000000000]";
                    xlabel = "x";
                    ylabel = "y";
                    zlabel = "z";
                }
            };
            DataTableSet dts = plot.addNewDataTableSet("3D Plot");
            dts.addNewDataTable("", x, y, z);
            jg.execute(plot, jg.plot3d);
        } else {
            plot = new JGnuplot.Plot("") {
                {
                    xlabel = "Time";
                    ylabel = "Energy";
                }
            };
            DataTableSet dts = plot.addNewDataTableSet("2D Plot");
            dts.addNewDataTable("", x, y);
            jg.execute(plot, jg.plot2d);
        }
    }

    public static double[] toArray(ArrayList<Double> list) {
        double[] array = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

}
