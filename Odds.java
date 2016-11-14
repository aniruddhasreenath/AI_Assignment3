import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;
import java.util.regex.Matcher;

/**
 * Created by sreenath on 12/11/2016.
 */
public class Odds {
    public HashMap<String, ArrayList<double[]>> knowledge;
    public double minVal;
    public double maxVal;
    public double average;
    public int numPixelsBelow1 = 0;

    public double[][] oddsRatios;

    public int[][] oddsAsHeatMap;

    public Odds(HashMap<String, ArrayList<double[]>> learnedData, int cls1, int cls2, boolean printOdds){
        knowledge = new HashMap<String, ArrayList<double[]>>();
        oddsRatios = new double[28][28];
        oddsAsHeatMap = new int[28][28];
        knowledge = learnedData;
        minVal = 99999.99;
        maxVal = 0.0;

        genereateOddsRatios(cls1,cls2);
        if(printOdds){
            printOddsAsHeatMap(cls1, cls2);
        }
        else {
            printOddsAsHeatMap(cls1, cls2);
        }
    }

    public void genereateOddsRatios(int c1, int c2){

        for (int i = 0; i < 28; i ++){


            for(int j = 0; j < 28; j++){

                //TODO USE THE ARRAY INDEX 1 FOR THE #

                //generate key for the hashmap
                String key = Integer.toString(i)+Integer.toString(j);

                //get the prob for c1 being a foreground value at position i,j
                double c1Likelihood = knowledge.get(key).get(c1)[1];

                //get the prob for c2 being a foreground value at position i,j
                double c2Likelihood = knowledge.get(key).get(c2)[1];

                //divide the likelihoods to get the odds ratio and store it in the array
                oddsRatios[i][j] = Math.log(c1Likelihood/c2Likelihood)/Math.log(2);
            }

        }

        setMinMaxVals();
    }

    public void setMinMaxVals(){

        for(int i = 0; i < oddsRatios.length; i++){

            for(int j = 0; j < oddsRatios.length; j++){
                //get max
                if(oddsRatios[i][j] > maxVal){
                    maxVal = oddsRatios[i][j];
                }

            }
        }

        for(int i = 0; i < oddsRatios.length; i++){

            for(int j = 0; j < oddsRatios.length; j++){
                //get min
                if(oddsRatios[i][j] < minVal){
                    minVal = oddsRatios[i][j];
                }

            }
        }

        convertToHeatMapFormat();
    }

    public void printOdds(int a, int b){
        System.out.println();
        System.out.println("Printing Odds Ratios..." + " [" +a+" , " + b+ "] ");
        for(int i = 0; i < 28 ; i++){

            for (int j = 0; j < 28; j++){

                System.out.printf("%.2f", (oddsRatios[i][j]));
                System.out.print("\t");

            }
            System.out.println();
        }
    }

    public void convertToHeatMapFormat(){

        double midpoint = Math.abs(minVal);
        maxVal = maxVal + Math.abs(minVal);
        minVal = 0;

        for(int i = 0; i < oddsRatios.length; i++){

            for(int j = 0; j < oddsRatios.length; j++){
                //System.out.println(oddsRatios[i][j]);
                if(oddsRatios[i][j] <= 0){
                    oddsAsHeatMap[i][j] = (int)(((oddsRatios[i][j] + midpoint)/(midpoint) ) * 140);
                }
                else{
                    oddsAsHeatMap[i][j] = 141 + (int)(((oddsRatios[i][j] + midpoint)/(maxVal + midpoint) ) * (255-140));
                    if(oddsAsHeatMap[i][j]>300) {

                    }
                }

            }
        }
    }

    public void printOddsAsHeatMap(int a, int b){

        System.out.println();
        System.out.println("Printing Odds As Heat Map Ratios..." + " [" +a+" , " + b+ "] ");
        for(int i = 0; i < 28 ; i++){
            System.out.print("[");
            for (int j = 0; j < 28; j++){
                if(j<27) {
                    System.out.print(oddsAsHeatMap[i][j] + ",");
                }else{
                    System.out.print(oddsAsHeatMap[i][j]);
                }
                System.out.print(" ");

            }
            System.out.print("],");
            System.out.println();
        }
    }
}
