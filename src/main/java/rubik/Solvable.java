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

    /**
     * constructor to create a solvable class
     */
    public Solvable(String file) throws FileNotFoundException {

    }

    /**
     * Main method to be called
     * @param args the file of the cube to be executed on
     */
    public static void main(String args[]){
        try{
            RubikCube rubikCube = new RubikCube(args[0]);

            rubikCube.heuristicTables();
//            System.out.println(rubikCube);
//            rubikCube.rotate(1);
//            System.out.println(rubikCube);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
