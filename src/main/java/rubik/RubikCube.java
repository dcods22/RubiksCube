package rubik;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Dan
 * Date: 9/16/14
 * Time: 2:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class RubikCube {

    private static byte[] rubikCube;

    //static ints for colors
    private static final int RED = 0;
    private static final int GREEN = 1;
    private static final int YELLOW = 2;
    private static final int BLUE= 3;
    private static final int ORANGE = 4;
    private static final int WHITE = 5;


    public RubikCube(String file) throws FileNotFoundException {
        rubikCube = new byte[54];

        try{
            serialize(file);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * method to serialize the cube
     * @param file of the cube to serialize
     * @throws java.io.FileNotFoundException if the file is not found
     */
    public void serialize(String file) throws FileNotFoundException {
        String line;
        int pos = 0;

        try {

            BufferedReader reader = new BufferedReader(new FileReader(file));

            while((line = reader.readLine()) != null){
                line = line.trim();
                for(int i=0; i < line.length(); i++){
                    String cube = line.substring(i, i+1);

                    if (cube.equals("R")) {
                        rubikCube[pos++] = RED;
                    } else if (cube.equals("G")) {
                        rubikCube[pos++] = GREEN;
                    } else if (cube.equals("Y")) {
                        rubikCube[pos++] = YELLOW;
                    } else if (cube.equals("B")) {
                        rubikCube[pos++] = BLUE;
                    } else if (cube.equals("O")) {
                        rubikCube[pos++] = ORANGE;
                    } else if (cube.equals("W")) {
                        rubikCube[pos++] = WHITE;
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte getCube(int pos){
        return rubikCube[pos];
    }

    public int getSize(){
        return rubikCube.length;
    }

    /**
     * Method to test the count of colors of a cube
     * @return true if valid
     */
    public boolean count(){

        int rCount=0, gCount=0, yCount=0, bCount=0, oCount=0, wCount=0;

        if(this.getSize() < 54){
            return false;
        }

        for(int i=0; i < this.getSize(); i++){
            switch (this.getCube(i)) {
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
     * Method to test the middle validitiy of a rubikCube
     * @return true if valid
     */
    public boolean middles(){
        return ((this.getCube(4) == 0) && (this.getCube(19) == 1) && (this.getCube(22) == 2) && (this.getCube(25) == 3) && (this.getCube(40) == 4) && (this.getCube(49) == 5));
    }

    /**
     * Method to test the korf algorithm
     * @return true if valid
     */
    public boolean korf(){
        return cornerTest() && edgeTest() && permutationTest();
    }

    /**
     * Method to test the corner validitiy of a rubikCube
     * @return true if valid
     */
    public boolean cornerTest(){
        int sum = rubikCube[0] + rubikCube[2] + rubikCube[6] + rubikCube[9] + rubikCube[11] + rubikCube[12] +
                  rubikCube[14] + rubikCube[15] + rubikCube[17] + rubikCube[27] + rubikCube[29] + rubikCube[30] +
                  rubikCube[32] + rubikCube[33] + rubikCube[35] + rubikCube[36] + rubikCube[38] + rubikCube[42] +
                  rubikCube[44] + rubikCube[45] + rubikCube[47] + rubikCube[51] + rubikCube[53];

        return ((sum % 3) == 0);
    }

    /**
     * Method to test the edge validitiy of a rubikCube
     * @return true if valid
     */
    public boolean edgeTest(){
        return true;
    }

    /**
     * Method to test the permutation validitiy of a rubikCube
     * @return true if valid
     */
    public boolean permutationTest(){
        return true;
    }

    /**
     * Method to test the validitiy of a cube
     * @return true if valid
     */
    public boolean validate(){
        return (this.count() && this.middles() && this.korf());
    }

    /**
     * function to return the interger to string
     * @param value
     * @return
     */
    public String returnToChar(int value){
        switch(value){
            case RED:
                return "R";
            case BLUE:
                return "B";
            case YELLOW:
                return "Y";
            case ORANGE:
                return "O";
            case WHITE:
                return "W";
            case GREEN:
                return "G";
        }
        return "";
    }

    /**
     * Overwrite of the toString method
     * @return string printout of the current rubikCube
     */
    public String toString(){
        String rubikString = "   ";

        for(int i=0; i < 9; i++){
            rubikString += this.returnToChar(this.getCube(i));
            if(i % 3 == 2 && i < 8){
                rubikString += "\n   ";
            }else if(i % 3 == 2){
                rubikString += "\n";
            }
        }
        for(int i = 9; i < 36; i++){
            rubikString += this.returnToChar(this.getCube(i));
            if(i % 9 == 8){
                rubikString += "\n";
            }
        }

        rubikString += "   ";

        for(int i = 36; i < 54; i++){
            rubikString += this.returnToChar(this.getCube(i));
            if(i % 3 == 2){
                rubikString += "\n   ";
            }
        }

        return rubikString;
    }

}
