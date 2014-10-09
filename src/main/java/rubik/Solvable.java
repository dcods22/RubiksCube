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

    //Global for the cube
    protected static RubikCube rubikCube;

    /**
     * constructor to create a solvable class
     */
    public Solvable(String file) throws FileNotFoundException {
        rubikCube = new RubikCube(file);
    }

    /**
     * Main method to be called
     * @param args the file of the cube to be executed on
     */
    public static void main(String args[]){
        try{
            Solvable solve = new Solvable(args[0]);

            rubikCube.heuristicTables();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Method to test the validitiy of a cube
     * @return true if valid
     */
    public boolean isValid(){
        return rubikCube.validate();
    }

    /**
     * Overwrite of the toString method
     * @return string printout of the current cube
     */
    public String toString(){
        return rubikCube.toString();
    }



}
