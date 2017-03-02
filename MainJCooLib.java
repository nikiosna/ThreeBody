import cartesian.coordinate.CCPoint;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by niklas on 23.12.16.
 */
public class MainJCooLib extends JFrame{

    public static void main(String[] args) {
        Cartesian cartesian = new Cartesian(15.0);

        double seconds = 5.0;
        double step = Unit.convert(seconds, new Unit(0,1,0), new Unit(0,1,0,149597870700.0,31536000.0,5.9722*Math.pow(10,24)));
        Universe universe = new Universe(2, step, new Unit(3,-2,-1,149597870700.0,31536000.0,5.9722*Math.pow(10,24)));
        Body[] bodies = new Body[4];
        Body[] bodies_Temp = new Body[4];
        MathVector x,v;

        x = new MathVector(5.0, 0.0);
        v = new MathVector(2.0, 0.5);
        bodies[0] = new Body(0.5*333000.0, x, v);

        x = new MathVector(-1.0, 1.0);
        v = new MathVector(-3.277777053948402, 0.0);
        bodies[1] = new Body(333000.0, x, v);

        x = new MathVector(0.0, -5.2);
        v = new MathVector(2.0, 2.5);
        bodies[2] = new Body(333000.0, x, v);

        x = new MathVector(-2.5, 2.5);
        v = new MathVector(-0.5, 0.0);
        bodies[3] = new Body(1.5*333000.0, x, v);

        boolean running = true;
        double time = 0;
        double max = Math.rint(100.0/step);
        int out = (int)Math.rint(max/5000);

        double a,b;
        while (running) {
            for (int i = 0; i < bodies.length; i++) {

                if(bodies[i] != null) {
                    a = bodies[i].getPosition().getXi(0);
                    b = bodies[i].getPosition().getXi(1);

                    int border = 250;
                    if(a > border || a < -border || b > border || b < -border) {
                        bodies[i] = null;
                        System.out.println("bye");
                    }

                    if((time%out)==0) {
                        if (i==0) {
                            cartesian.addPoint(new CCPoint(a,b, Color.orange));
                        } else if (i==1) {
                            cartesian.addPoint(new CCPoint(a,b, Color.green));
                        } else if (i==2) {
                            cartesian.addPoint(new CCPoint(a,b, Color.blue));
                        } else if (i==3) {
                            cartesian.addPoint(new CCPoint(a,b, Color.red));
                        } else if (i==4) {
                            cartesian.addPoint(new CCPoint(a,b, Color.pink));
                        } else if (i==5) {
                            cartesian.addPoint(new CCPoint(a,b, Color.lightGray));
                        } else if (i==6) {
                            cartesian.addPoint(new CCPoint(a,b, Color.darkGray));
                        } else if (i==7) {
                            cartesian.addPoint(new CCPoint(a,b, Color.yellow));
                        }
                        cartesian.repaint();
                    }
                    bodies_Temp[i] = universe.betterIntegrator( universe.force(bodies[i],bodies), bodies[i]);

                }

            }

            bodies = bodies_Temp;

            time++;
            if(time%((int)Math.rint(max/100))==0) {
                System.out.println(Math.rint((time/max)*100) + "%");
            }
            if(time>=max) running=false;
        }
    }

}
