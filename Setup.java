import java.io.IOException;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by Sreenath on 6/11/2016.
 */
public class Setup {
    public static ArrayList<Image> trainingData;
    public static ArrayList<Image> testData;
    public static ArrayList<Double> prob;

    public Setup() throws IOException{
        trainingData = new ArrayList<Image>();
        testData = new ArrayList<Image>();
        readImage();
        readTestData();

      //System.out.println(trainingData.get(trainingData.size()-343) + " "+ trainingData.get(trainingData.size()-343).tureLabel);
      //System.out.println(testData.toString());
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
//                    System.out.println("===================================================");
//                    System.out.println(newImg);
//                    System.out.println("===================================================");
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
                    //String tmp2 = reader2.readLine();
                    //int label = Integer.parseInt(tmp2);
                    Image newImg = new Image(-1,generateImg(img));
//                    System.out.println("===================================================");
//                    System.out.println(newImg);
//                    System.out.println("===================================================");
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

}
