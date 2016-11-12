import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sreenath on 8/11/2016.
 */
public class Test {


    public ArrayList<Image> testData;

    //String key is coordinates for example(01 => (0,1) and 2828 => (28,28)) This will return a linked list of arrays that hold probs for every symbol
    public HashMap<String, ArrayList<double[]>> knowledge;

    public ArrayList<Image> classifiedData;

    public static double accuracy;

    public Test(HashMap<String, ArrayList<double[]>> know){
        classifiedData = new ArrayList<Image>();
        testData = Setup.testData;
        knowledge = know;
        accuracy = 0.0;

        test();

        //TODO activate printClassifiedData to see the full list of classifications
        System.out.println();
        System.out.println("activate printClassifiedData to see the full list of classifications in the constructor of the Test.java class");
        //printClassifiedData();
    }

    public void test(){

        int count = 0;
        int maxIndex = 0;

        for(int i = 0; i < testData.size(); i ++){
            ArrayList<Double> posterior = new ArrayList<Double>();

            //get the first image and classify it
            Image newImage = testData.get(i);

            //get probs of all classes
            for(int cls = 0; cls < 10; cls ++){

                //collect all the posteriors of this class
                posterior.add(getProbabilityOFThisClass(newImage.image, cls));


            }
            //get the max posterior's index
            maxIndex  = maximisePosterior(posterior);

            //classify the image
            newImage.tureLabel = maxIndex;
            classifiedData.add(newImage);

            if(classifiedData.get(i).tureLabel == Setup.testlabels.get(i)){
                count++;
            }

        }
        accuracy = (count/1000.0)*100.0;
    }

    public double getProbabilityOFThisClass(char[][] img, int clas){
        //the class
        int classNumber = clas;

        //this is the key for the hashmap
        String key = "";

        //store the relavent probs here
        ArrayList<Double> likelihoods = new ArrayList<Double>();

        ArrayList<Double> post = new ArrayList<Double>();


        //likelihood across different classes
            for (int row = 0; row < 28; row ++){

                for(int col = 0; col < 28; col++){
                    key = Integer.toString(row)+Integer.toString(col);
                    ArrayList<double[]> probabilities = knowledge.get(key);
                    likelihoods.add(probabilities.get(classNumber)[generateSymbolKey(img[row][col])]);
                }

            }

        double likelihood = 0.0;

        double prior = Math.log(Setup.numberOfImagesInTrainingClass[clas])/Math.log(2);

        //calculate posterior
        for (int len = 0; len < likelihoods.size(); len++){

            likelihood = likelihood + Math.log(likelihoods.get(len))/Math.log(2);

        }

        post.add((prior+likelihood));

        return (prior + likelihood);
    }

    public int generateSymbolKey(char sym){

        if (sym == ' ') return  2;
        if (sym == '#') return  1;
        if (sym == '+') return  0;

        System.out.println("COULD NOT FIND VALUE OF F");
        return  -1;
    }

    public int maximisePosterior(ArrayList<Double> list){

        double max = -999999999.0;
        int maxIndex = 0;

        for(int i = 0; i < list.size(); i++){

            if(list.get(i) > max){

                max = list.get(i);
                maxIndex = i;
            }
         }

       for(int i = 0; i < list.size(); i++){

            if(list.get(i) == max){

                return i;
            }


        }
        System.out.println("COULD NOT FIND MAX!");
        return -1;
    }

    public void printClassifiedData(){
        for(int i = 0; i < classifiedData.size(); i++){
            System.out.println("Classified label: " + classifiedData.get(i).tureLabel + " Actual label: "
                    + Setup.testlabels.get(i));
        }

        System.out.println("Classified: " + classifiedData.size() + " Images");
    }


}
