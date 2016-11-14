import java.io.IOException;

/**
 * Created by Sreenath on 6/11/2016.
 */
public class Driver {

    public static void main(String[] args) throws IOException{

        //sets up the environment
        new Setup();
        double ktest = 0.1;
        //trains on the data
        Train learn = new Train(Setup.trainingData, ktest, 3, false);
        Test test = new Test(learn.knowledge);

        //generates evaluation metric
        new ConfusionMatrix(test.classifiedData);

        //generates odds ratios
        new Odds(learn.knowledge, 4, 9, true);
        new Odds(learn.knowledge, 0, 5, true);
        new Odds(learn.knowledge, 5, 8, true);
        new Odds(learn.knowledge, 3, 5, true);
        new Odds(learn.knowledge, 1, 8, true);

        //pick highest and lowest posterior probabilities for every class
        new Sort(Setup.testData, learn.knowledge);

    }

}
