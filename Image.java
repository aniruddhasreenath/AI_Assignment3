/**
 * Created by Sreenath on 6/11/2016.
 */
public class Image implements Comparable<Image> {

    public int tureLabel;
    public char[][] image;

    public Image(int lab, char[][] img){
        tureLabel = lab;
        image = img;
    }

    public int compareTo(Image a){
        if(a.tureLabel == this.tureLabel){
            return 1;
        }
        return 0;
    }

    public String toString(){
        String imag = "";
        if (image.length == 0){
            System.out.println("DOES NOT HAVE AN IMAGE");
        }
        else{
            for(int i = 0; i < image.length; i++){
                imag = imag + "\n";
                for (int j = 0; j < image.length; j++){
                    imag = imag + Character.toString(image[i][j]);
                }
            }
        }
        return imag;
    }
}
