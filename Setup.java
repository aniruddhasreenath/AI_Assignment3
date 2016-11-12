import java.io.IOException;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Sreenath on 6/11/2016.
 */
public class Setup {
    public static ArrayList<Image> trainingData;
    public static ArrayList<Image> testData;
    public static ArrayList<Double> prob;
    public static double[] numberOfImagesInTrainingClass;

    public static double[] frequencyOfClassInTestData;
    public static ArrayList<Integer> testlabels;

    public Setup() throws IOException{
        trainingData = new ArrayList<Image>();
        testData = new ArrayList<Image>();
        numberOfImagesInTrainingClass = new double[10];
        Arrays.fill(numberOfImagesInTrainingClass, 0);

        testlabels = new ArrayList<Integer>();

        frequencyOfClassInTestData = new double[10];
        Arrays.fill(frequencyOfClassInTestData, 0);

        readImage();

        readTestlables();
        readTestData();

    }


    public static void readImage()throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader("trainingimages"));
        BufferedReader reader2 = new BufferedReader((new FileReader("traininglabels")));
        int index = 0;
        String[] img = new String[28];
        String tmp = "";
        while(reader.ready()){

            tmp = reader.readLine();

            if(index == 27){

                if (reader2.ready()) {
                    img[index] = tmp;
                    String tmp2 = reader2.readLine();
                    int label = Integer.parseInt(tmp2);
                    Image newImg = new Image(label,generateImg(img));

                    numberOfImagesInTrainingClass[label] = numberOfImagesInTrainingClass[label] + 1;
                      trainingData.add(newImg);
                }
                index = 0;
            }
            else{
                img[index] = tmp;
                index++;
            }
        }

    }



    public static void readTestData() throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader("testimages"));
        int index = 0;
        String[] img = new String[28];
        String tmp = "";
        while(reader.ready()){

            tmp = reader.readLine();

            if(index == 27){

                    img[index] = tmp;

                    Image newImg = new Image(-1,generateImg(img));

                    testData.add(newImg);

                index = 0;
            }
            else{
                img[index] = tmp;
                index++;
            }
        }


    }

    public static char[][] generateImg(String[] dat){
        char[][] img = new char[28][28];
        String data = "";

        for(int row =0 ; row < dat.length; row++){
            data = dat[row];

            for (int col = 0; col <dat.length; col++){
                if (col == 28){
                    img[row][col] = data.charAt(col -1);
                }
               else{
                    img[row][col] = data.charAt(col);
                }

            }
        }
        return img;
    }

    public static void readTestlables()throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader("testlabels"));

        String tmp = "";
        while(reader.ready()){

            tmp = reader.readLine();

            testlabels.add(Integer.parseInt(tmp));

            frequencyOfClassInTestData[Integer.parseInt(tmp)] = frequencyOfClassInTestData[Integer.parseInt(tmp)] + 1.0;
        }

    }
}
