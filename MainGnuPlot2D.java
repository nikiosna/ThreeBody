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

        x = new MathVector(0.0, 0.0);
        v = new MathVector(0.0, 0.0);
        bodies[0] = new Body(333000.0, x, v);

        x = new MathVector(0.0, 1.0);
        v = new MathVector(-6.277777053948402, 0.0);
        bodies[1] = new Body(1.0, x, v);

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

        GnuPlot.plot2d(x1, x2, "plot2d");
        GnuPlot.plot2d(t, e, "energy2d");

    }
}
