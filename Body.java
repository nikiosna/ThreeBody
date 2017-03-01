/**
 * Created by niklas on 23.12.16.
 */
public class Body {
    private double mass;
    private MathVector position;
    private MathVector velocity;
    private MathVector acceleration;

    public Body(double mass, MathVector position, MathVector velocity, MathVector acceleration) {
        this.mass = mass;
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
    }

    public Body(double mass, MathVector position, MathVector velocity) {
        this(mass, position,velocity,null);
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

    public MathVector getAcceleration() {
        return acceleration;
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
