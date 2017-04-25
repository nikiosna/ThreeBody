/**
 * Created by niklas on 23.12.16.
 */
public class Body {
    private String id;
    private double mass;
    //TODO make somehow private
    public MathVector position;
    public MathVector velocity;
    public MathVector acceleration;
    private double radius;

    public Body(String id, double mass, MathVector position, MathVector velocity, MathVector acceleration, double radius) {
        this.id = id;
        this.mass = mass;
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.radius = radius;
    }

    public Body(String id, double mass, MathVector position, MathVector velocity, MathVector acceleration) {
        this(id, mass, position, velocity, acceleration, 0);
    }

    public Body(String id, double mass, MathVector position, MathVector velocity, double radius) {
        this(id, mass, position,velocity,null, radius);
    }

    public Body(String id, double mass, MathVector position, MathVector velocity) {
        this(id, mass, position,velocity,null, 0);
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

    public String getId(){return id;}

}
