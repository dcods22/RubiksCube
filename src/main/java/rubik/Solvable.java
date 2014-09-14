package rubik;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Dan
 * Date: 9/14/14
 * Time: 5:08 PM
 */
public class Solvable {

    private static byte[] rubikCube;

    public static void main(String args[]){
        try{

            rubikCube = new byte[54];

            serialize(args[0]);

            isValid(rubikCube);

        }catch(Exception e){
            e.printStackTrace();
        }

    }


    public static void serialize(String file) throws FileNotFoundException {

        String line = null;

        try {

            BufferedReader reader = new BufferedReader(new FileReader(file));

            int pos = 0;

            while((line = reader.readLine()) != null){
                line = line.trim();
                for(int i=0; i < line.length(); i++){
                    String cube = line.substring(i, i+1);

                    if (cube.equals("R")) {
                        rubikCube[pos++] = 0;
                    } else if (cube.equals("G")) {
                        rubikCube[pos++] = 1;
                    } else if (cube.equals("Y")) {
                        rubikCube[pos++] = 2;
                    } else if (cube.equals("B")) {
                        rubikCube[pos++] = 3;
                    } else if (cube.equals("O")) {
                        rubikCube[pos++] = 4;
                    } else if (cube.equals("W")) {
                        rubikCube[pos++] = 5;
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void isValid(byte[] cube){

    }



}
