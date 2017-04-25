import java.io.*;

/**
 * Created by niklas on 03.03.17.
 */
public class FileManager {
    private static FileManager fm;

    private File startParams;
    private File[] output;

    private FileManager() {
        output = new File[1];
        output[1] = new File("/home/niklas/test.txt");
        //TODO
    }

    public static FileManager getFileManager() {
        if (fm == null)
            fm = new FileManager();
        return fm;
    }

    public Body[] getStartBodys() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(startParams));
            String zeile = reader.readLine();
            while (zeile != null) {
                //TODO parse strings
                zeile = reader.readLine();
            }
        } catch (IOException e) {
            System.exit(-1);
        }
        return null;
    }

    private boolean writeString(String[] s) {
        try {
            PrintWriter writer = new PrintWriter(output[1], "UTF-8");
            writer.println(s);
            writer.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }


    public boolean writeBody(Body n) {
        //TODO
        return true;
    }
}
