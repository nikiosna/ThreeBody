import cartesian.coordinate.CCPoint;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by niklas on 03.03.17.
 */
public class Main {

    public static void main(String[] args) {
        double[][] numbers = null;
        try {
            numbers = FileManager.readFile(new File("/home/niklas/ThreeBody/1.lvm")).vectors;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        Cartesian cartesian = new Cartesian(80.0);
        for (int i = 0; i < numbers.length; i++) {
            cartesian.addPoint(new CCPoint(numbers[i][0],numbers[i][1], Color.blue));
        }
        
    }

    private static void printHelp() {

    }

}
