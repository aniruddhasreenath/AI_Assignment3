import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sreenath on 7/11/2016.
 */
public class Train {

    public ArrayList<Pixel> posterior;
    public ArrayList<Image> images;
    public HashMap<String, ArrayList<double[]>> knowledge;
    public double valueOfK;
    public double valueOfV;
    public static boolean isBinaryFeatured;


    public Train(ArrayList<Image> img, double kVal, double V, boolean binary){
        posterior = new ArrayList<Pixel>();
        knowledge = new HashMap<String, ArrayList<double[]>>();
        images = img;
        valueOfK = kVal;
        valueOfV = V;
        isBinaryFeatured = binary;
        learn();
        //printknowledge();

    }

    public void learn(){

        fillProbabilites();

    }

    public ArrayList<Image> generateCandidateList(int clas){

            ArrayList<Image> candidateList = new ArrayList<Image>();

            //generate the cand list
            for(int img = 0; img < images.size(); img++){
                //check if this image is of this class
                if(images.get(img).tureLabel == clas){
                    candidateList.add(images.get(img));
                }
            }
            if (candidateList.size() == 0){
                System.out.println("ERROR: candidate list is empty");
            }
        return candidateList;
    }

    public void fillProbabilites(){

        for (int row = 0; row < 28; row++){

            for (int col = 0; col < 28; col++){
                ArrayList<double[]> probMap = new ArrayList<double[]>();
                //get first coordinate
                for(int i = 0; i < 10; i ++){
                    //get all the images that correspond to this class from generateCanditList method
                    double[] newProb = probability(generateCandidateList(i), row, col, i);

                    probMap.add(newProb);
                }

                knowledge.put(Integer.toString(row)+Integer.toString(col), probMap);
            }

        }

    }


    public double[] probability(ArrayList<Image> list, int row, int col, int clas){

        double plusOccur = 0;
        double dashOccur = 0;
        double hashOccur = 0;

        double probPlus = 0;
        double probHash = 0;
        double probDash = 0;


        double[] probs = new double[3];

        for(int i = 0; i < list.size(); i++){

            //get the first image
            if(list.get(i).image[row][col] == '+'){

                if (!isBinaryFeatured){
                    plusOccur = plusOccur + 1.0;
                }

            }
            else if(list.get(i).image[row][col] == ' '){

                dashOccur = dashOccur + 1.0;
            }
            else if(list.get(i).image[row][col] == '#'){

                hashOccur = hashOccur + 1.0;
            }
            else{
                System.out.println("Error "+list.get(i).image[row][col]);
            }

        }

        //smoothing and calculate probs
        probPlus = (plusOccur + valueOfK)/((Setup.numberOfImagesInTrainingClass[clas] + (valueOfV*valueOfK)));
        probHash = (hashOccur + valueOfK)/( (Setup.numberOfImagesInTrainingClass[clas] + (valueOfV*valueOfK)));
        probDash = (dashOccur + valueOfK)/(Setup.numberOfImagesInTrainingClass[clas] + (valueOfV*valueOfK));


        double[] probabilityAtPos = {probPlus,probHash, probDash};

        return probabilityAtPos;

    }

    public  void printknowledge() {
        for (int row = 0; row < 28; row++) {
            for (int col = 0; col < 28; col++) {

                String key = Integer.toString(row) + Integer.toString(col);

                for (int i = 0; i < knowledge.get(key).size(); i++) {

                    for (int j = 0; j < knowledge.get(key).get(i).length; j++) {


                        System.out.println("Coordinate: " + row + ":"
                                + col + " Class: " + i + " Symbol: " + j + " Prob: " + knowledge.get(key).get(i)[j]
                                + " KEY USED: " + key);

                    }
                }
            }
        }
    }
}

