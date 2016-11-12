import java.io.IOException;

/**
 * Created by Sreenath on 6/11/2016.
 */
public class Driver {

    public static void main(String[] args) throws IOException{

        //sets up the environment
        new Setup();

        //trains on the data
        Train learn = new Train(Setup.trainingData, 0.1, 3, false);

        //tests for classification accuracy
        Test test = new Test(learn.knowledge);

        //generates evaluation metric
        new ConfusionMatrix(test.classifiedData);

        //generates odds ratios
        new Odds(learn.knowledge, 1, 8, true);
        new Odds(learn.knowledge, 2, 3, true);
        new Odds(learn.knowledge, 4, 5, true);
        new Odds(learn.knowledge, 6, 9, true);

    }

}
