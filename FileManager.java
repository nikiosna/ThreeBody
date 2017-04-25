import java.io.*;
import java.math.BigInteger;

/**
 * Created by niklas on 03.03.17.
 */
public class FileManager {
    private String path;
    File[] file;
    PrintWriter[] writer;

    public FileManager(Body[] body) throws IOException {
        path = "/home/niklas/ThreeBody/";

        file = new File[body.length];
        writer = new PrintWriter[body.length];
        for (int i = 0; i < body.length; i++) {
            file[i] = new File(path + body[i].getId() + ".lvm");
            file[i].createNewFile();
            writer[i] = new PrintWriter(file[i], "UTF-8");
            writer[i].println("#t x1 x2 [x3]");
        }
    }


    public void writeBodyPosition(Body[] body, BigInteger time) {
        for (int i = 0; i < body.length; i++) {
            writer[i].println(time + " " + body[i].getPosition());
        }
    }

    public void close() {
        for (int i = 0; i < writer.length; i++) {
            writer[i].close();
        }
    }

    public Body[] getStartBodys() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("TODO"));
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

}
