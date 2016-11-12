import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sreenath on 12/11/2016.
 */
public class Odds {
    public HashMap<String, ArrayList<double[]>> knowledge;

    public double[][] oddsRatios;

    public Odds(HashMap<String, ArrayList<double[]>> learnedData){
        knowledge = new HashMap<String, ArrayList<double[]>>();
        oddsRatios = new double[28][28];
        knowledge = learnedData;

        genereateOddsRatios(1,8);
        printOdds();
    }

    public void genereateOddsRatios(int c1, int c2){

        for (int i = 0; i < 28; i ++){


            for(int j = 0; j < 28; j++){

                //TODO USE THE ARRAY INDEX 1 FOR THE #

                //generate key for the hashmap
                String key = Integer.toString(i)+Integer.toString(j);

                //get the prob for c1 being a foreground value at position i,j
                double c1Likelihood = Math.log(knowledge.get(key).get(c1)[1])/Math.log(2);

                //get the prob for c2 being a foreground value at position i,j
                double c2Likelihood = Math.log(knowledge.get(key).get(c2)[1])/Math.log(2);

                //divide the likelihoods to get the odds ratio and store it in the array
                oddsRatios[i][j] = c1Likelihood/c2Likelihood;


            }

        }
    }

    public void printOdds(){
        System.out.println();
        System.out.println("Printing Odds Ratios...");
        for(int i = 0; i < 28 ; i++){

            for (int j = 0; j < 28; j++){

                System.out.printf("%.2f", (oddsRatios[i][j]));
                System.out.print("\t");

            }
            System.out.println();
        }
    }
}
