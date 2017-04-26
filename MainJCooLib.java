import javax.swing.*;
import java.io.IOException;
public class MainJCooLib extends JFrame{

    public static void main(String[] args) {

        Cartesian cartesian = new Cartesian(80.0);
        int seconds = 300;
        Unit simulation_unit = new Unit(3,-2,-1,149597870700.0,31536000.0,5.9722*Math.pow(10,24));
        Universe universe = new Universe(2, seconds, simulation_unit);
        Body[] bodies = new Body[4];
        MathVector x,v;
        double radius = Unit.convert(1000000.0, new Unit(1,0,0), simulation_unit.getOtherDimension(1,0,0));

        x = new MathVector(5.5, 0.0);
        v = new MathVector(3.0, 0.5);
        bodies[0] = new Body("1", 1.54*333000.0, x, v, radius);

        x = new MathVector(9.0, 3.0);
        v = new MathVector(-3.0, 0.0);
        bodies[1] = new Body("2", 333000.0, x, v, radius);

        x = new MathVector(-1.0, -15.2);
        v = new MathVector(2.0, -1.0);
        bodies[2] = new Body("3", 333000.0, x, v, radius);

        x = new MathVector(-20.5, 2.5);
        v = new MathVector(-1.2, 1.5);
        bodies[3] = new Body("4", 1.55*333000.0, x, v, radius);

        boolean running = true;
        double time = 0;
        double max = Math.rint(500.0/universe.getDelta_t());
        int out = (int)Math.rint(max/5000);

        FileManager file = null;
        try {
            file = new FileManager(bodies);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        double a,b;
        while (running) {

            //the magic
            universe.calculate_step(bodies);

            //write to files
            if((time%out)==0) file.writeBodyPosition(bodies, universe.getAge_si());

            for (int i = 0; i < bodies.length; i++) {
                if(bodies[i] != null) {

                    a = bodies[i].getPosition().getXi(0);
                    b = bodies[i].getPosition().getXi(1);

                    int border = 200;
                    if(a > border || a < -border || b > border || b < -border) {
                        System.out.println("#bye");
                        file.writeComment("bye");
                        System.out.println("#Escapevelocity: " + Unit.convert(bodies[i].getVelocity().abs(), simulation_unit.getOtherDimension(1,-1,0), new Unit(1,-1,0)));
                        file.writeComment("Escapevelocity: " + Unit.convert(bodies[i].getVelocity().abs(), simulation_unit.getOtherDimension(1,-1,0), new Unit(1,-1,0)));
                        bodies[i] = null;
                        running=false;
                    }
                    for (int j = 0; j < bodies.length; j++) {
                        if(universe.collision(bodies[i], bodies[j])) {
                            System.out.println("#collision");
                            file.writeComment("collision");
                            MathVector velocity_difference = bodies[i].getVelocity()-bodies[j].getVelocity();
                            System.out.println("#relative speed: " + Unit.convert(velocity_difference.abs(), simulation_unit.getOtherDimension(1, -1, 0), new Unit(1, -1, 0)));
                            file.writeComment("relative speed: " + Unit.convert(velocity_difference.abs(), simulation_unit.getOtherDimension(1, -1, 0), new Unit(1, -1, 0)));
                            running=false;
                            i=bodies.length;
                            j=bodies.length;
                        }
                    }

                    /*if((time%out)==0) {
                        if (i==0) {
                            cartesian.addPoint(new CCPoint(a,b, Color.red));
                        } else if (i==1) {
                            cartesian.addPoint(new CCPoint(a,b, Color.green));
                        } else if (i==2) {
                            cartesian.addPoint(new CCPoint(a,b, Color.blue));
                        } else if (i==3) {
                            cartesian.addPoint(new CCPoint(a,b, Color.orange));
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
                    }*/

                }

            }

            time++;
            if(time%((int)Math.rint(max/100))==0) {
                System.out.println(Math.rint((time/max)*100) + "%");
            }
            if(time>=max) running=false;
        }

        file.close();
        System.exit(0);
    }

}
