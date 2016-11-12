import java.io.IOException;

/**
 * Created by Sreenath on 6/11/2016.
 */
public class Driver {

    public static void main(String[] args) throws IOException{

        Setup trainingData = new Setup();

        Train train = new Train(Setup.trainingData, 0.1, 3);

        Test testing = new Test(train.knowledge);

        ConfusionMatrix mat = new ConfusionMatrix(testing.classifiedData);


    }

}
