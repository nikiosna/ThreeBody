import org.leores.plot.JGnuplot;
import org.leores.util.data.DataTableSet;

import java.util.ArrayList;

/**
 * Created by niklas on 26.12.16.
 */
public class MainGnuPlot2D {
    public static void main(String[] args) {
        double step = Unit.convert(300.0, new Unit(0,1,0), new Unit(0,1,0,149597870700.0,31536000.0,5.9722*Math.pow(10,24)));
        Universe universe = new Universe(2, step, new Unit(3,-2,-1,149597870700.0,31536000.0,5.9722*Math.pow(10,24)));
        Body[] bodies = new Body[3];
        Body[] bodies_Temp = new Body[3];
        MathVector x,v;

        //Sonne
        x = new MathVector(0.0, 0.0);
        v = new MathVector(0.0, 0.0);
        bodies[0] = new Body(333000.0, x, v);

        //Erde
        x = new MathVector(0.0, 1.0);
        v = new MathVector(-6.277777053948402, 0.0);
        bodies[1] = new Body(1.0, x, v);

        //Jupiter
        x = new MathVector(0.0, 5.2);
        v = new MathVector(-2.7552231731063, 0.0);
        bodies[2] = new Body(318.0, x, v);

        boolean running = true;
        double time = 0;
        double max = Math.rint(100.0/step);
        int out = (int)Math.rint(max/6000);
        double energy;
        ArrayList<Double> x1 = new ArrayList<>();
        ArrayList<Double> x2 = new ArrayList<>();

        ArrayList<Double> e = new ArrayList<>();
        ArrayList<Double> t = new ArrayList<>();
        while (running) {
            energy = 0;
            for (int i = 0; i < bodies.length; i++) {
                //1. und 100. jahr ausgeben
                if(i==1 && (time%out==0) && ((time > 98.0/step) || (time < 1.0/step))) {
                    x1.add(bodies[i].getPosition().getXi(0));
                    x2.add(bodies[i].getPosition().getXi(1));
                }
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

        plot2d(toArray(x1), toArray(x2), "plot2d", true);
        plot2d(toArray(t), toArray(e), "energy2d", false);

    }

    public static void plot2d(double[] x, double[] y, String name, boolean a) {
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
                    xrange = "[-6:6]";
                    yrange = "[-6:6]";
                    xlabel = "x";
                    ylabel = "y";
                }
            };
        } else {
            plot = new JGnuplot.Plot("") {
                {
                    xlabel = "Time";
                    ylabel = "Energy";
                }
            };
        }
        DataTableSet dts = plot.addNewDataTableSet("2D Plot");
        dts.addNewDataTable("", x, y);
        jg.execute(plot, jg.plot2d);
    }

    public static double[] toArray(ArrayList<Double> list) {
        double[] array = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

}
