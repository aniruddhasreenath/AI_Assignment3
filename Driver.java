import java.io.IOException;

/**
 * Created by Sreenath on 6/11/2016.
 */
public class Driver {

    public static void main(String[] args) throws IOException{

        Setup trainingData = new Setup();

        Train train = new Train(Setup.trainingData, 0.1, 3, true);

        Test testing = new Test(train.knowledge);

        ConfusionMatrix mat = new ConfusionMatrix(testing.classifiedData);

        Odds odds1 = new Odds(train.knowledge, 1, 8, true);

        Odds odds2 = new Odds(train.knowledge, 2, 3, true);
        Odds odds3 = new Odds(train.knowledge, 4, 5, true);
        Odds odds4 = new Odds(train.knowledge, 6, 9, true);

    }

}
