import java.math.BigInteger;

/**
 * Created by niklas on 23.12.16.
 */
public class Universe {

    private double delta_t;
    private int delta_t_si;
    private BigInteger age_si;
    private  double G;
    private final double Gsi = 6.67408*Math.pow(10,-11); //Gravitational_constant m^3/(kg * s^2)
    private int d;
    private Unit basis;

    public Universe(int dimension, int timesteps_si, Unit basis) {
        d = dimension;
        this.basis = basis;
        delta_t = Unit.convert((double)timesteps_si, new Unit(0,1,0), basis.getOtherDimension(0,1,0));
        age_si = BigInteger.ZERO;
        delta_t_si = timesteps_si;
        G = Unit.convert(Gsi,new Unit(3,-2,-1), basis);
    }

    public BigInteger calculate_step(Body[] body) {
        for (int i = 0; i < body.length; i++) {
            badIntegrator( force(body[i],body), body[i]);
            aging();
        }
        return age_si;
    }

    /**
     * let a force acting on a body
     * @param force the force as a vector
     * @param body the patient body
     */
    private void badIntegrator(MathVector force, Body body) {
        //Todo improve the integrator with variable timesteps (eventually with the change of the acceleration)
        //Todo note https://github.com/MatthewPeterKelly/myJavaPkgs/blob/master/mpk_dsc/Integrator.java
        if(body!=null) {
            MathVector acceleration = force/body.getMass();
            MathVector velocity = body.getVelocity() + acceleration*delta_t;
            //MathVector position = body.getPosition() + body.getVelocity()*delta_t; //euler
            MathVector position = body.getPosition() + velocity*delta_t; //sympletic
            body.position = position;
            body.velocity = velocity;
            body.acceleration = acceleration;
        }
    }

    private void betterIntegrator(MathVector force, Body body) {
        if(body!=null) {
            MathVector acceleration_neu = force/body.getMass();
            MathVector velocity_neu;
            if(body.getAcceleration()==null) {
                velocity_neu = body.getVelocity() + acceleration_neu*delta_t;
            } else {
                velocity_neu = body.getVelocity() + body.getAcceleration()*delta_t + (acceleration_neu - body.getAcceleration())*0.5*delta_t;
            }

            MathVector position_neu = body.getPosition() + body.getVelocity()*delta_t + (velocity_neu - body.getVelocity())*0.5*delta_t;

            body.position = position_neu;
            body.velocity = velocity_neu;
            body.acceleration = acceleration_neu;
        }
    }

    private void heunIntegrator(MathVector force, Body body) {
        if(body!=null) {
            MathVector acceleration = force/body.getMass();
            MathVector velocity = body.getVelocity() + acceleration*delta_t;
            MathVector position = body.getPosition() + velocity*delta_t;
            body.velocity = body.getVelocity() + acceleration*delta_t + acceleration*0.5*delta_t*delta_t;

        }
    }

    private void rk4Integrator(MathVector force, Body body) {
        if(body!=null) {
            MathVector p1, p2, p3, p4;
            MathVector v1, v2, v3, v4;
            MathVector a1, a2, a3, a4;
            p1 = body.getPosition();
            v1 = body.getVelocity();
            a1 = force/body.getMass();

            p2 = p1 + v1*0.5*delta_t;
            v2 = v1 + a1*0.5*delta_t;
            a2 = a1; //TODO

            p3 = p1 + v2*0.5*delta_t;
            v3 = v1 + a2*0.5*delta_t;
            a3 = a2; //TODO

            p4 = p1 + v3*0.5*delta_t;
            v4 = v1 + a3*0.5*delta_t;
            a4 = a3; //TODO

            body.position = p1 + (v1 + v2*2.0 + v3*2.0 + v4)*(delta_t/6.0);
            body.velocity = v1 + (a1 + a2*2.0 + a3*2.0 + a4)*(delta_t/6.0);

        }
    }


    /**
     * calculates the force on a body to n other bodies
     * @param z zentral body
     * @param bodies
     * @return
     */
    private MathVector force(Body z, Body... bodies) {
        if(bodies.length==0) throw new RuntimeException("test");

        MathVector sum = MathVector.nullvector(d);
        if(bodies.length==1) {
            if (bodies[0]==null || z == null) return MathVector.nullvector(d);
            if (z.equals(bodies[0])) return MathVector.nullvector(d);
            else {
                MathVector r = bodies[0].getPosition() - z.getPosition();
                double abs = r.abs();
                if(abs==0) throw new RuntimeException("divide by zero!");
                double c = (G*z.getMass()*bodies[0].getMass())/(abs*abs*abs);
                return(r*c);
            }
        } else {
            for (int i = 0; i < bodies.length; i++) {
                sum = sum.add( force(z, bodies[i]) );
            }
        }
        return sum;
    }

    private void aging() {
        age_si  = age_si.add(BigInteger.valueOf(delta_t_si));
    }

    /**
     * returns if body a and body b are collided
     * @param a
     * @param b
     * @return true if a collision is detected
     */
    public boolean collision(Body a, Body b) {
        if(a==b || a==null || b==null) return false;
        double distance = (a.getPosition()-b.getPosition()).abs();
        if(distance<0) distance *= -1;
        //if(distance < Unit.convert(100000000.0, new Unit(1,0,0), basis.getOtherDimension(1,0,0))) System.out.println("nearby");
        return(distance <= (a.getRadius()+b.getRadius()));
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
                double r = ( z.getPosition() - bodies[0].getPosition() ).abs();
                if(r==0) throw new RuntimeException("divide by zero!");
                return (G*bodies[0].getMass()*z.getMass())/r;
            }
        } else {
            for (int i = 0; i < bodies.length; i++) {
                energy += pot_energy(z, bodies[i]);
            }
        }
        return energy;
    }

    /**
     * calculates the energy of a body
     * @return energy in (kg*m*m)/(s*s)
     */
    public double kin_energy(Body b) {
        double v = b.getVelocity().abs();
        return 0.5*b.getMass()*v*v;
    }

    public double getDelta_t() {
        return delta_t;
    }

    public int getDelta_t_si() {
        return delta_t_si;
    }

    public BigInteger getAge_si() {
        return age_si;
    }

}
