import java.util.ArrayList;

/**
 * Created by niklas on 23.12.16.
 */
public class Universe {

    private double delta_t;
    private double age; //TODO
    private  double G;
    private final double Gsi = 6.67408*Math.pow(10,-11); //Gravitational_constant m^3/(kg * s^2)
    private int d;

    //TODO move bodys here

    public Universe(int dimension, double timesteps, Unit basis) {
        d = dimension;
        delta_t = timesteps;

        G = Unit.convert(Gsi,new Unit(3,-2,-1), basis);
    }

    /**
     * let a force acting on a body
     * @param force the force as a vector
     * @param body the patient body
     * @return the new body with other velocities etc
     */
    public Body badIntegrator(MathVector force, Body body) {
        //Todo improve the integrator with variable timesteps (eventually with the change of the acceleration)
        if(body==null) return null;
        MathVector acceleration = force.divide(body.getMass());
        MathVector velocity = body.getVelocity().add(acceleration.multiply(delta_t));
        MathVector position = body.getPosition().add(body.getVelocity().multiply(delta_t));
        return(new Body(body.getMass(), position,velocity));
    }

    /**
     * calculates the force on a body to n other bodies
     * @param z zentral body
     * @param bodies
     * @return
     */
    public MathVector force(Body z, Body... bodies) {
        if(bodies.length==0) throw new RuntimeException("test");

        MathVector sum = MathVector.nullvector(d);
        if(bodies.length==1) {
            if (bodies[0]==null || z == null) return MathVector.nullvector(d);
            if (z.equals(bodies[0])) return MathVector.nullvector(d);
            else {
                MathVector r = bodies[0].getPosition().subtract(z.getPosition());
                double abs = r.abs();
                if(abs==0) throw new RuntimeException("ahh durch null teilen!");
                double c = (G*z.getMass()*bodies[0].getMass())/(abs*abs*abs);
                return(r.multiply(c));
            }
        } else {
            for (int i = 0; i < bodies.length; i++) {
                sum = sum.add(force(z, bodies[i]));
            }
        }
        //System.out.println(sum);
        return sum;
    }

    /**
     * calculates the energy of a body
     * @param z the body
     * @return energy in (kg*m*m)/(s*s)
     */
    public double pot_energy(Body z, Body... bodies) {
        double energy = 0;
        if(bodies.length==1) {
            if (z.equals(bodies[0])) return 0;
            else {
                double r = z.getPosition().subtract(bodies[0].getPosition()).abs();
                if(r==0) throw new RuntimeException("ahh durch null teilen!");
                return (G*bodies[0].getMass()*z.getMass())/r;
            }
        } else {
            for (int i = 0; i < bodies.length; i++) {
                energy += pot_energy(z, bodies[i]);
            }
        }
        return energy;
    }

}
