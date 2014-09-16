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

            System.out.println(solve.isValid(rubikCube));

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Method to test the validitiy of a cube
     * @param cube reference to the cube
     * @return true if valid
     */
    public boolean isValid(RubikCube cube){
        return (count(cube) && middles(cube) && korf(cube));
    }

    /**
     * Method to test the count of colors of a cube
     * @param cube reference to the cube
     * @return true if valid
     */
    public boolean count(RubikCube cube){

        int rCount=0, gCount=0, yCount=0, bCount=0, oCount=0, wCount=0;

        if(cube.getSize() < 54){
            return false;
        }

        for(int i=0; i < cube.getSize(); i++){
            switch (cube.getCube(i)) {
                case 0:
                    rCount++;
                    break;
                case 1:
                    gCount++;
                    break;
                case 2:
                    yCount++;
                    break;
                case 3:
                    bCount++;
                    break;
                case 4:
                    oCount++;
                    break;
                case 5:
                    wCount++;
                    break;
            }
        }

        return !(rCount != 9 && gCount != 9 && yCount != 9 && bCount != 9 && oCount != 9 && wCount != 9);
    }

    /**
     * Method to test the middle validitiy of a cube
     * @param cube reference to the cube
     * @return true if valid
     */
    public boolean middles(RubikCube cube){
        return ((cube.getCube(4) == 0) && (cube.getCube(19) == 1) && (cube.getCube(22) == 2) && (cube.getCube(25) == 3) && (cube.getCube(40) == 4) && (cube.getCube(49) == 5));
    }

    /**
     * Method to test the korf algorithm
     * @param cube reference to the cube
     * @return true if valid
     */
    public boolean korf(RubikCube cube){
        return cornerTest(cube) && edgeTest(cube) && permutationTest(cube);
    }

    /**
     * Method to test the corner validitiy of a cube
     * @param cube reference to the cube
     * @return true if valid
     */
    public boolean cornerTest(RubikCube cube){
        return true;
    }

    /**
     * Method to test the edge validitiy of a cube
     * @param cube reference to the cube
     * @return true if valid
     */
    public boolean edgeTest(RubikCube cube){
        return true;
    }

    /**
     * Method to test the permutation validitiy of a cube
     * @param cube reference to the cube
     * @return true if valid
     */
    public boolean permutationTest(RubikCube cube){
        return true;
    }

    /**
     * Method to deserialize the cube as it is stored
     * @param cube copy of the cube to be deserialized
     * @return a copy of the cube which is deserialized
     */
    public String deserialize(RubikCube cube){
        return "";
    }

    /**
     * Overwrite of the toString method
     * @return string printout of the current cube
     */

    public String toString(){
        String rubikString = "   ";

        for(int i=0; i < 9; i++){
            rubikString += rubikCube.getCube(i);
            if(i % 3 == 2 && i < 8){
                rubikString += "\n   ";
            }else if(i % 3 == 2){
                rubikString += "\n";
            }
        }
        for(int i = 9; i < 36; i++){
            rubikString += rubikCube.getCube(i);
            if(i % 9 == 8){
                rubikString += "\n";
            }
        }

        rubikString += "   ";

        for(int i = 36; i < 54; i++){
            rubikString += rubikCube.getCube(i);
            if(i % 3 == 2){
                rubikString += "\n   ";
            }
        }

        return rubikString;
    }



}
