import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sreenath on 7/11/2016.
 */
public class Train {

    public ArrayList<Pixel> posterior;
    public ArrayList<Image> images;
    public HashMap<String, ArrayList<double[]>> knowledge;
    public int valueOfK;
    public int valueOfV;


    public Train(ArrayList<Image> img, int kVal, int V){
        posterior = new ArrayList<Pixel>();
        knowledge = new HashMap<String, ArrayList<double[]>>();
        images = img;
        valueOfK = kVal;
        valueOfV = V;
        learn();
    }

    public void learn(){

        fillProbabilites();
        //System.out.println(knowledge.get("").toString());
    }

    public double selectClass(char f, int i, int j, int clas){

        ArrayList<Image> candidateList = new ArrayList<Image>();
        int occurances = 0;
        int total = 500;
        double prob = 0.0;
        //generate the cand list
        for(int img = 0; img < images.size(); img++){
            //check if this image is of this class
            if(images.get(i).tureLabel == clas){
                candidateList.add(images.get(i));
            }
        }

        //iterate over the cand list and calculate probabilities of f
        for(int cand = 0; cand < candidateList.size(); cand++){
            Image tmp;

            tmp = candidateList.get(i);

            if(tmp.image[i][j] == f){
                occurances++;
            }
        }

        //applying the smoothing constant
        prob = (occurances + valueOfK)/(total + (valueOfK*valueOfV));
        return prob;
    }


    public ArrayList<Image> generateCandidateList(int clas){

            ArrayList<Image> candidateList = new ArrayList<Image>();

            double occurances = 0;
            double total = 500;
            double prob = 0.0;

            //generate the cand list
            for(int img = 0; img < images.size(); img++){
                //check if this image is of this class
                if(images.get(img).tureLabel == clas){
                    candidateList.add(images.get(img));
                }
            }
        return candidateList;
    }

    public void fillProbabilites(){

        ArrayList<double[]> probMap = new ArrayList<double[]>();

        for (int row = 0; row < 28; row++){

            for (int col = 0; col < 28; col++){

                //get first coordinate
                for(int i = 0; i < 10; i ++){
                    double[] newProb = probability(generateCandidateList(i), row, col);
                    probMap.add(newProb);
                }

                knowledge.put(Integer.toString(row)+Integer.toString(col), probMap);
            }

        }

    }


    public double[] probability(ArrayList<Image> list, int row, int col){

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

                plusOccur++;
            }
            else if(list.get(i).image[row][col] == ' '){

                dashOccur++;
            }
            else if(list.get(i).image[row][col] == '#'){

                hashOccur++;
            }

        }

        //smoothing and calculate probs
        probPlus = (plusOccur + valueOfK)/(500 + (valueOfV*valueOfK));
        probHash = (hashOccur + valueOfK)/(500 + (valueOfV*valueOfK));
        probDash = (dashOccur + valueOfK)/(500 + (valueOfV*valueOfK));

        double[] probabilityAtPos = {probPlus, probHash, probDash};

        return probabilityAtPos;

    }
}

