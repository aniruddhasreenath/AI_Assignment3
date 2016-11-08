import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sreenath on 7/11/2016.
 */
public class Pixel {

    public int xCoord;

    public int yCoord;

    //stores the probs for all the classes given a value of f
    public HashMap<Character, ArrayList<Double>> probabilities;

    public Pixel(int x, int y){
        xCoord = x;
        yCoord = y;
    }

    public void setProbabilities(char sym, ArrayList<Double> prob){

        probabilities.put(sym, prob);

    }
}
