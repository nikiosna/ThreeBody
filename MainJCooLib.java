import javax.swing.*;
import java.io.IOException;
public class MainJCooLib extends JFrame{

    public static void main(String[] args) {

        //Cartesian cartesian = new Cartesian(10.0);
        int seconds = 600;
        Unit simulation_unit = new Unit(3,-2,-1,149597870700.0,86400.0,5.9722*Math.pow(10,24));
        Universe universe = new Universe(3, seconds, simulation_unit);
        Body[] bodies = new Body[9];
        MathVector x,v;
        double radius, mass;

        // startconditions #2017 04 27 #https://ssd.jpl.nasa.gov/horizons.cgi
        x = new MathVector(3.012731685483044E-03, 4.704912545479554E-03, -1.480766596769380E-04);
        v = new MathVector(-3.853435595315066E-06, 6.426753836738919E-06, 8.675019449068602E-08);
        radius = Unit.convert(6.963*100000000, new Unit(1,0,0), simulation_unit.getOtherDimension(1,0,0));
        mass = Unit.convert(1.988544*Math.pow(10,30), new Unit(0,0,1), simulation_unit.getOtherDimension(0,0,1));
        bodies[0] = new Body("0", mass, x, v, radius);

        x = new MathVector(-2.850489669623145E-01, -3.462398423196038E-01, -2.397405130319285E-03);
        v = new MathVector(1.603894141831771E-02, -1.653760175647236E-02, -2.823555467054760E-03);
        radius = Unit.convert(2440*1000, new Unit(1,0,0), simulation_unit.getOtherDimension(1,0,0));
        mass = Unit.convert(48.685*Math.pow(10,23), new Unit(0,0,1), simulation_unit.getOtherDimension(0,0,1));
        bodies[1] = new Body("1", mass, x, v, radius);

        x = new MathVector(-3.911721689119536E-01, -6.031463954270009E-01, 1.426100540272387E-02);
        v = new MathVector(1.682536953326316E-02, -1.109138964919402E-02, -1.123287080000847E-03);
        radius = Unit.convert(6051.8*1000, new Unit(1,0,0), simulation_unit.getOtherDimension(1,0,0));
        mass = Unit.convert(48.685*Math.pow(10,23), new Unit(0,0,1), simulation_unit.getOtherDimension(0,0,1));
        bodies[2] = new Body("2", mass, x, v, radius);

        x = new MathVector(-8.040556460420196E-01, -5.967327452971498E-01, -1.169214776797153E-04);
        v = new MathVector(1.000202728922878E-02, -1.385868012999256E-02, 8.024029038263612E-07);
        radius = Unit.convert(6378.14*1000, new Unit(1,0,0), simulation_unit.getOtherDimension(1,0,0));
        mass = Unit.convert(5.97219*Math.pow(10,24), new Unit(0,0,1), simulation_unit.getOtherDimension(0,0,1));
        bodies[3] = new Body("3", mass, x, v, radius);

        x = new MathVector(2.517808806032628E-01, 1.531155636639839E+00, 2.573425676860129E-02);
        v = new MathVector(-1.328525572283837E-02, 3.447647178705755E-03, 3.981500517609080E-04);
        radius = Unit.convert(3389.9*1000, new Unit(1,0,0), simulation_unit.getOtherDimension(1,0,0));
        mass = Unit.convert(6.4185*Math.pow(10,23), new Unit(0,0,1), simulation_unit.getOtherDimension(0,0,1));
        bodies[4] = new Body("4", mass, x, v, radius);

        x = new MathVector(-5.139584074432149E+00, -1.811803970375081E+00, 1.224671755320262E-01);
        v = new MathVector(2.420416159578501E-03, -6.758715151774982E-03, -2.606947186175747E-05);
        radius = Unit.convert(71492*1000, new Unit(1,0,0), simulation_unit.getOtherDimension(1,0,0));
        mass = Unit.convert(1898.13*Math.pow(10,24), new Unit(0,0,1), simulation_unit.getOtherDimension(0,0,1));
        bodies[5] = new Body("5", mass, x, v, radius);

        x = new MathVector(-1.261452842735085E+00, -9.968579611898170E+00, 2.235308543486502E-01);
        v = new MathVector(5.229016708338742E-03, -7.187816999006481E-04, -1.953351693195063E-04);
        radius = Unit.convert(60268*1000, new Unit(1,0,0), simulation_unit.getOtherDimension(1,0,0));
        mass = Unit.convert( 5.68319*Math.pow(10,26), new Unit(0,0,1), simulation_unit.getOtherDimension(0,0,1));
        bodies[6] = new Body("6", mass, x, v, radius);

        x = new MathVector(1.815556746639274E+01, 8.226489996652862E+00, -2.046551720401192E-01);
        v = new MathVector(-1.652116739373167E-03, 3.399166261465940E-03, 3.402667951894338E-05);
        radius = Unit.convert(25559*1000, new Unit(1,0,0), simulation_unit.getOtherDimension(1,0,0));
        mass = Unit.convert(86.8103*Math.pow(10,24), new Unit(0,0,1), simulation_unit.getOtherDimension(0,0,1));
        bodies[7] = new Body("7", mass, x, v, radius);

        x = new MathVector(2.845274617436709E+01, -9.342052082006541E+00, -4.633404061968008E-01);
        v = new MathVector(9.583130385849068E-04,  3.001501806641447E-03, -8.350756939815502E-05);
        radius = Unit.convert(24766*1000, new Unit(1,0,0), simulation_unit.getOtherDimension(1,0,0));
        mass = Unit.convert(102.41*Math.pow(10,24), new Unit(0,0,1), simulation_unit.getOtherDimension(0,0,1));
        bodies[8] = new Body("8", mass, x, v, radius);

        boolean running = true;
        double time = 0;
        double max = Math.rint(5000*380.0/universe.getDelta_t());
        int out = (int)Math.rint(max/10000000);

        FileManager file = null;
        try {
            file = new FileManager(bodies);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        double a,b;
        boolean[] esacpe = new boolean[bodies.length];
        for (int i = 0; i < esacpe.length; i++) {
            esacpe[i] = false;
        }
        while (running) {

            //the magic
            universe.calculate_step(bodies);

            //write to files
            if((time%out)==0) {
                file.writeBodyPosition(bodies, universe.getAge_si());
            }

            for (int i = 0; i < bodies.length; i++) {
                if(bodies[i] != null) {

                    a = bodies[i].getPosition().getXi(0);
                    b = bodies[i].getPosition().getXi(1);

                    int border = 200;
                    if(a > border || a < -border || b > border || b < -border) {
                        if(!esacpe[i]){
                            System.out.println("#bye");
                            file.writeComment("bye", bodies[i].getId());
                            System.out.println("#Escapevelocity: " + Unit.convert(bodies[i].getVelocity().abs(), simulation_unit.getOtherDimension(1,-1,0), new Unit(1,-1,0)));
                            file.writeComment("Escapevelocity: " + Unit.convert(bodies[i].getVelocity().abs(), simulation_unit.getOtherDimension(1,-1,0), new Unit(1,-1,0)), bodies[i].getId());
                        }
                        esacpe[i]=true;
                        //bodies[i] = null;//running=false;
                    }
                    for (int j = 0; j < bodies.length; j++) {
                        if(universe.collision(bodies[i], bodies[j])) {
                            System.out.println("#collision");
                            file.writeComment("collision", bodies[i].getId());
                            MathVector velocity_difference = bodies[i].getVelocity()-bodies[j].getVelocity();
                            System.out.println("#relative speed: " + Unit.convert(velocity_difference.abs(), simulation_unit.getOtherDimension(1, -1, 0), new Unit(1, -1, 0)));
                            file.writeComment("relative speed: " + Unit.convert(velocity_difference.abs(), simulation_unit.getOtherDimension(1, -1, 0), new Unit(1, -1, 0)), bodies[i].getId());
                            running=false;
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
            if(time%((int)Math.rint(max/10000))==0) {
                System.out.println(Math.rint((time/max)*10000)/100 + "%");
                /*if(Math.rint((time/max)*10000)/100 == 0.1) {
                    file.close();
                    System.exit(0);
                }*/
            }
            if(time>=max) running=false;
        }

        file.close();
        System.exit(0);
    }

}
