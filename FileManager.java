import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;

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

    public void writeComment(String s) {
        for (int i = 0; i < writer.length; i++) {
            writer[i].println("#" + s);
        }
    }

    public void close() {
        for (int i = 0; i < writer.length; i++) {
            writer[i].close();
        }
    }

    public Body[] getStartBodys() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("TODO"));
        String zeile = reader.readLine();
        while (zeile != null) {
             //TODO parse strings
             zeile = reader.readLine();
        }
        return null;
    }

    public static DataSet readFile(File f) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(f));
        ArrayList<String> lines = new ArrayList<>();
        String zeile = reader.readLine();
        while (zeile != null) {
            if(zeile.charAt(0)!='#') {
                    lines.add(zeile);
            }
            zeile = reader.readLine();
        }
        int d = lines.get(0).split(" ").length;
        String[] n;
        double[][] number = new double[lines.size()][d-1];
        BigInteger[] time = new BigInteger[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            n = lines.get(i).split(" ");
            for (int j = 0; j < d; j++) {
                if(j==0) {
                    time[i] = new BigInteger(n[j]);
                } else {
                    number[i][j-1] = Double.valueOf(n[j]);
                }
            }
        }
        return new DataSet(time, number);
    }

}
