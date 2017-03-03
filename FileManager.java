import java.io.File;

/**
 * Created by niklas on 03.03.17.
 */
public class FileManager {
    private static FileManager fm;

    private File startParams;
    private File output;

    private FileManager() {
        //TODO
    }

    public static FileManager getFileManager() {
        if(fm==null)
            fm = new FileManager();
        return fm;
    }

    public Body[] getStartBodys() {
        //TODO
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
