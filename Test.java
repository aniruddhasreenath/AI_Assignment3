import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sreenath on 8/11/2016.
 */
public class Test {


    public ArrayList<Image> testData;
    public HashMap<String, ArrayList<double[]>> knowledge;

    public Test(HashMap<String, ArrayList<double[]>> know){
        testData = Setup.testData;
        knowledge = know;
    }

    public void test(){

        for(int i = 0; i < testData.size(); i ++){

            //get the first image and classify it
            Image newImage = testData.get(i);

            for(int row = 0; row < 28; row++){
                for(int col = 0; col < 28; col++){
                    char fVal = newImage.image[row][col];


                }
            }

        }

    }

    public int classify(char f, int i, int j){

        int numberClass = 0;

        //this is the key for the hashmap
        String key = Integer.toString(i)+Integer.toString(j);

        //an index that helps in retriving the correct prob from the list
        int fSymbKey = 0;
        if (f == ' ') fSymbKey = 2;
        if (f == '#') fSymbKey = 1;
        if (f == '+') fSymbKey = 0;

        //get the probability matrix for this key
        ArrayList<double[]> probabilities = knowledge.get(key);

        //store the relavent probs here
        ArrayList<Double> probs = new ArrayList<Double>();

        //interate over all the classes in teh prob list and find the maximum value for that symbol
        double maxProb = 0;

        //extract the relavent probabilities
        for(int clas = 0; clas < probabilities.size(); clas ++){
            probs.add(probabilities.get(clas)[fSymbKey]);
        }

        maxProb = chooseMax(probs);
        
    }

    public double chooseMax(ArrayList<Double> list){

        double max = 0;

        for(int i = 0; i < list.size(); i++){
            if(list.get(i) > max){
                max = list.get(i);
            }
        }
        return max;
    }


}
