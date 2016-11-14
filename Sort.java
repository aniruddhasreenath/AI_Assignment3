import java.util.ArrayList;
import java.util.HashMap;

public class Sort {

    ArrayList<Image> testData;

    public HashMap<String, ArrayList<double[]>> knowledge;

    ArrayList<Image> sortedData;
    public ArrayList<Double> maxPost;
    public ArrayList<Double> minPost;
    ArrayList<Image> max;
    ArrayList<Image> min;

    public Sort(ArrayList<Image> data, HashMap<String, ArrayList<double[]>> know){
        testData = new ArrayList<Image>();
        sortedData = new ArrayList<Image>();
        max = new ArrayList<Image>();
        min = new ArrayList<Image>();
        testData = data;

        maxPost = new ArrayList<Double>();
        minPost = new ArrayList<Double>();

        knowledge = know;

        fillLabels();
        sort();
        printMaxMin();
    }

    public void fillLabels(){

        for(int i= 0; i < testData.size(); i++){

            testData.get(i).tureLabel = Setup.testlabels.get(i);

        }
    }

    public void sort(){

        for (int i = 0 ; i < 10; i++){
            ArrayList<Image> tmp = new ArrayList<Image>();
            for(int j = 0; j < testData.size(); j++){

                if(Setup.testlabels.get(j) == i){

                    tmp.add(testData.get(j));
                }
            }
            //sortedData.add(tmp);
            test(tmp);

        }


    }

    public void test(ArrayList<Image> data){
        int maxIndex = 0;
        int minIndex = 0;

        for(int i = 0; i < data.size(); i ++){

            ArrayList<Double> posterior = new ArrayList<Double>();

            //get the first image and classify it
            Image newImage = data.get(i);

            //get probs of all classes
            for(int cls = 0; cls < 10; cls ++){

                //collect all the posteriors of this class
                posterior.add(getProbabilityOFThisClass(newImage.image, cls));

            }
            //get the max posterior's index
            maxIndex  = maximisePosterior(posterior);
            minIndex = minimisePosterior(posterior);

        }

        min.add(data.get(pickWorstClass()));
        max.add(data.get(pickBestClass()));

        maxPost.clear();
        minPost.clear();

    }

    public int pickWorstClass(){

        int index = 0;
        double ind = 0;
        //System.out.println(list.size());
        for(int i = 0; i < maxPost.size(); i++){

            if(maxPost.get(i) < ind){

                ind = maxPost.get(i);
                index = i;
            }
        }

        return  index;
    }

    public int pickBestClass(){

        int index = 0;
        double ind = -99999.009;

        for(int i = 0; i < maxPost.size(); i++){

            if(maxPost.get(i) > ind){

                ind = maxPost.get(i);
                index = i;
            }
        }

        return  index;
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

    public int maximisePosterior(ArrayList<Double> list){

        double max = -999999999.0;
        int maxIndex = 0;

        for(int i = 0; i < list.size(); i++){

            if(list.get(i) > max){

                max = list.get(i);
                maxIndex = i;
            }
        }

        maxPost.add(max);
        return maxIndex;
    }

    public int generateSymbolKey(char sym){

        if (sym == ' ') return  2;
        if (sym == '#') return  1;
        if (sym == '+') return  0;

        System.out.println("COULD NOT FIND VALUE OF F");
        return  -1;
    }

    public int minimisePosterior(ArrayList<Double> list){

        double min = 0;
        int minIndex = 0;

        for(int i = 0; i < list.size(); i++){

            if(list.get(i) < min){
                min = list.get(i);
                minIndex = i;
            }
        }
        minPost.add(min);
        return minIndex;
    }


    public void printMaxMin(){

            for(int i = 0; i < max.size(); i++){

                System.out.print(max.get(i));
                System.out.print(min.get(i));
                System.out.println();

            }

    }
}
