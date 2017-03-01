import javax.swing.JFrame;
import cartesian.coordinate.CCPoint;
import cartesian.coordinate.CCSystem;
import cartesian.coordinate.CCLine;

import java.awt.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cartesian extends JFrame {

    private static final long serialVersionUID = 1L;
    private CCSystem s;
    private double[] size;

    public Cartesian(double a , double b, double c, double d) {
        super("Viewer");
        setTitle("Viewer");
        size = new double[] {a,b,c,d};

        setVisible(true);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        s = new CCSystem(a, b, c, d);
        add(s);

    }

    /**
     * Creates a cartesian coordinate system with x-axis from -a to a and y-axis from -a to a
     * @param a size
     */
    public Cartesian(double a) {
        this(-a,-a,a,a);
    }

    public void addPoint(CCPoint point) {
        s.lock.lock();
        //if(s.points.size() > 5000) s.points.remove(0);
        /*if(s.points.size() > 5000) {
            for (int i = 0; i < 200; i++) {
                s.points.remove(0);
            }
        }*/
        s.add(point);
        s.lock.unlock();
    }

    public void addLine(CCLine line) {
        s.add(line);
    }


}
