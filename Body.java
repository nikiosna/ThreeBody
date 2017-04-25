/**
 * Created by niklas on 23.12.16.
 */
public class Body {
    private String id;
    private double mass;
    private MathVector position;
    private MathVector velocity;
    private MathVector acceleration;
    private double radius;

    public Body(double mass, MathVector position, MathVector velocity, MathVector acceleration, double radius) {
        this.mass = mass;
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.radius = radius;
    }

    public Body(double mass, MathVector position, MathVector velocity, MathVector acceleration) {
        this(mass, position, velocity, acceleration, 0);
    }

    public Body(double mass, MathVector position, MathVector velocity, double radius) {
        this(mass, position,velocity,null, radius);
    }

    public Body(double mass, MathVector position, MathVector velocity) {
        this(mass, position,velocity,null, 0);
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

    public double getRadius() {
        return radius;
    }

}
