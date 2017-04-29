import cartesian.coordinate.CCPoint;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by niklas on 03.03.17.
 */
public class Main {

    public static void main(String[] args) {
        DataSet[] data = new DataSet[9];
        try {
            data[0] = FileManager.readFile(new File("/home/niklas/ThreeBody/0.lvm"));
            data[1] = FileManager.readFile(new File("/home/niklas/ThreeBody/1.lvm"));
            data[2] = FileManager.readFile(new File("/home/niklas/ThreeBody/2.lvm"));
            data[3] = FileManager.readFile(new File("/home/niklas/ThreeBody/3.lvm"));
            data[4] = FileManager.readFile(new File("/home/niklas/ThreeBody/4.lvm"));
            data[5] = FileManager.readFile(new File("/home/niklas/ThreeBody/5.lvm"));
            data[6] = FileManager.readFile(new File("/home/niklas/ThreeBody/6.lvm"));
            data[7] = FileManager.readFile(new File("/home/niklas/ThreeBody/7.lvm"));
            data[8] = FileManager.readFile(new File("/home/niklas/ThreeBody/8.lvm"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        for (int i = 0; i < data.length-1; i++) {
            if(data[i].vectors.length != data[i+1].vectors.length)
                System.exit(0);
        }

        Cartesian cartesian = new Cartesian(2.0);
        for (int i = 0; i < data[0].vectors.length; i+=10) {
            cartesian.addPoint(new CCPoint(data[0].vectors[i][0],data[0].vectors[i][1], Color.orange));
            cartesian.addPoint(new CCPoint(data[1].vectors[i][0],data[1].vectors[i][1], Color.yellow));
            cartesian.addPoint(new CCPoint(data[2].vectors[i][0],data[2].vectors[i][1], Color.gray));
            cartesian.addPoint(new CCPoint(data[3].vectors[i][0],data[3].vectors[i][1], Color.green));
            cartesian.addPoint(new CCPoint(data[4].vectors[i][0],data[4].vectors[i][1], Color.red));
            //cartesian.addPoint(new CCPoint(data[5].vectors[i][0],data[5].vectors[i][1], Color.black));
            //cartesian.addPoint(new CCPoint(data[6].vectors[i][0],data[6].vectors[i][1], Color.lightGray));
            //cartesian.addPoint(new CCPoint(data[7].vectors[i][0],data[7].vectors[i][1], Color.blue));
            //cartesian.addPoint(new CCPoint(data[8].vectors[i][0],data[8].vectors[i][1], Color.magenta));
            /*try {
                if(i>0)Thread.sleep((data[0].time[i].subtract(data[0].time[i-1])).divide(BigInteger.valueOf(100000)).longValue());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cartesian.repaint();*/
        }
        
    }

    private static void printHelp() {

    }

}
