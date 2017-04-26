import java.math.BigInteger;

/**
 * Created by niklas on 26.04.17.
 */
public class DataSet {
    public BigInteger[] time;
    public double[][] vectors;

    public DataSet(BigInteger[] time, double[][] vectors) {
        if(time.length != vectors.length) {
            throw new RuntimeException("Dimensions dont match!");
        }
        this.time = time;
        this.vectors = vectors;
    }
}
