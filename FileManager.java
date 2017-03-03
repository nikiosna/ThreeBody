import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by niklas on 03.03.17.
 */
public class FileManager {
    private static FileManager fm;

    private File startParams; //idea: each file represents one body and contains 4 or 6 values (x and v vector)
    private File[] output;

    private FileManager() {
        //TODO
    }

    public static FileManager getFileManager() {
        if(fm==null)
            fm = new FileManager();
        return fm;
    }

    public Body[] getStartBodys() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(startParams));
            String zeile = reader.readLine();
            while(zeile != null){
                //TODO parse strings
                zeile = reader.readLine();
            }
        } catch (IOException e) {
            System.exit(-1);
        }
        return null;
    }

    private boolean writeString(String[] s) {
        //TODO
        return false;
    }

    public boolean write2D(double[] time, double[] x, double[] y) {
        if(time.length != x.length || time.length != y.length) throw new RuntimeException("Number of entries do not match");
        String[] s = new String[time.length];
        for (int i = 0; i < time.length; i++) {
            s[i] = time[i] + " " + x[i] + " " + y[i];
        }
        return writeString(s);
    }

    public boolean write3D(double[] time, double[] x, double[] y, double[] z) {
        if(time.length != x.length || time.length != y.length || time.length != z.length) throw new RuntimeException("Number of entries do not match");
        String[] s = new String[time.length];
        for (int i = 0; i < time.length; i++) {
            s[i] = time[i] + " " + x[i] + " " + y[i] + " " + z[i];
        }
        return writeString(s);
    }

}
