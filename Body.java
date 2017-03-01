/**
 * Created by niklas on 23.12.16.
 */
public class Body {
    private double mass;
    private MathVector position;
    private MathVector velocity;

    public Body(double mass, MathVector position, MathVector velocity) {
        this.mass = mass;
        this.position = position;
        this.velocity = velocity;
    }

    public double getMass() {
        return mass;
    }

    public MathVector getPosition() {
        return position;
    }

    public MathVector getVelocity() {
        return velocity;
    }

    /**
     * calculates the energy of a body
     * @return energy in (kg*m*m)/(s*s)
     */
    public double kin_energy() {
        double v = velocity.abs();
        return 0.5*mass*v*v;
    }
}
