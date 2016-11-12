import java.util.AbstractCollection;
import java.util.ArrayList;

/**
 * Created by sreenath on 11/11/2016.
 */
public class ConfusionMatrix {

    double[][] confusion;
    ArrayList<Image> classifiedData;

    ArrayList<Double> classifiedLabels;
    ArrayList<Integer> testlabels;

    public ConfusionMatrix(ArrayList<Image> data){
        confusion = new double[10][10];
        classifiedData = data;
        testlabels = Setup.testlabels;
        classifiedLabels = new ArrayList<Double>();

        populateClassifiedLabels();
        generateConfusion();
        printMatrix();

    }

    public void populateClassifiedLabels(){
        for(int i = 0; i < classifiedData.size(); i++){

            classifiedLabels.add(classifiedData.get(i).tureLabel);

        }
    }

    public void generateConfusion(){

        for(int i = 0; i < 10 ; i++){

            for (int j = 0; j < 10; j++){

                confusion[i][j] = classifiedNumberOfTimes(i , j);
            }
        }


    }

    public int classifiedNumberOfTimes(int trueLab, int clasLab){
        int count = 0;

        if(classifiedLabels.size() != testlabels.size()){
            System.out.println("ERROR: Lists are of different sizes");

        }

        for(int i = 0; i < classifiedLabels.size(); i++){
            if(classifiedLabels.get(i) == clasLab && testlabels.get(i) == trueLab){
                count++;
            }
        }

        return count;

    }

    public void printMatrix(){

        for(int i = 0; i < 10 ; i++){

            for (int j = 0; j < 10; j++){

                System.out.printf("%.2f", (confusion[i][j]/Setup.frequencyOfClassInTestData[j])*100);
                System.out.print("\t");

            }
            System.out.println();
        }



    }
}
