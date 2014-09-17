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

    //array reference to the serialized cube
    private static byte[] rubikCube;

    //static ints for colors
    private static final int RED = 0;
    private static final int GREEN = 1;
    private static final int YELLOW = 2;
    private static final int BLUE= 3;
    private static final int ORANGE = 4;
    private static final int WHITE = 5;

    /**
     * constructor to create a cube from a file
     * @param file the file to create a cube off of
     * @throws FileNotFoundException
     */
    public RubikCube(String file) throws FileNotFoundException {
        rubikCube = new byte[54];

        try{
            serialize(file);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * constructor to create a copy of a cube
     * @param cube
     */
    public RubikCube(byte [] cube){
        rubikCube = cube;
    }

    /**
     * method to serialize the cube
     * @param file of the cube to serialize
     * @throws java.io.FileNotFoundException if the file is not found
     */
    private void serialize(String file) throws FileNotFoundException {
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

    /**
     * Method to deserialize the cube as it is stored
     * @return a copy of the cube which is deserialized
     */
    public String deserialize(){
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

    /**
     * Method to test the validitiy of a cube
     * @return true if valid
     */
    public boolean validate(){
        System.out.println(this.toString());
        this.rotateDownRight(this);
        System.out.println(this.toString());
        return (this.count() && this.middles() && this.korf());
    }

    public byte getCube(int pos){
        return rubikCube[pos];
    }

    public int getSize(){
        return rubikCube.length;
    }

    private void setCube(int pos, byte value){
        rubikCube[pos] = value;
    }

    /**
     * Method to test the count of colors of a cube
     * @return true if valid
     */
    private boolean count(){
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
        return ((this.getCube(4) == 0) && (this.getCube(19) == 1) && (this.getCube(22) == 2) &&
                (this.getCube(25) == 3) && (this.getCube(40) == 4) && (this.getCube(49) == 5));
    }

    /**
     * Method to test the korf algorithm
     * @return true if valid
     */
    private boolean korf(){
        return cornerTest() && edgeTest() && permutationTest();
    }

    /**
     * Method to test the corner validitiy of a rubikCube
     * @return true if valid
     */
    private boolean cornerTest(){
        int sum = this.getCube(0) + this.getCube(2) + this.getCube(6) + this.getCube(8) + + this.getCube(9) + this.getCube(11) +
                  this.getCube(12) + this.getCube(14) + this.getCube(15) + this.getCube(17) + this.getCube(27) + this.getCube(29) +
                  this.getCube(30) + this.getCube(32) + this.getCube(33) + this.getCube(35) + this.getCube(36) + this.getCube(38) +
                  this.getCube(42) + this.getCube(44) + this.getCube(45) + this.getCube(47) + this.getCube(51) + this.getCube(53);

        return ((sum % 3) == 0);
    }

    /**
     * Method to test the edge validitiy of a rubikCube
     * @return true if valid
     */
    private boolean edgeTest(){
        RubikCube testCube = new RubikCube(rubikCube);

        return true;
    }

    /**
     * Method to rotate the left side of a cube left
     * @param cube the cube in which to rotate
     */
    private void rotateLeftUp(RubikCube cube){
        //values of current cubes
        byte spot1 = cube.getCube(12);
        byte spot2 = cube.getCube(21);
        byte spot3 = cube.getCube(30);
        byte spot4 = cube.getCube(0);
        byte spot5 = cube.getCube(3);
        byte spot6 = cube.getCube(6);
        byte spot7 = cube.getCube(45);
        byte spot8 = cube.getCube(48);
        byte spot9 = cube.getCube(51);
        byte spot10 = cube.getCube(36);
        byte spot11 = cube.getCube(39);
        byte spot12 = cube.getCube(42);

        //places to put those values
        cube.setCube(0, spot1);
        cube.setCube(3, spot2);
        cube.setCube(6, spot3);
        cube.setCube(45, spot4);
        cube.setCube(48, spot5);
        cube.setCube(51, spot6);
        cube.setCube(36, spot7);
        cube.setCube(39, spot8);
        cube.setCube(42, spot9);
        cube.setCube(12, spot10);
        cube.setCube(21, spot11);
        cube.setCube(30, spot12);
    }

    /**
     * Method to rotate the right side of a cube left
     * @param cube the cube in which to rotate
     */
    private void rotateRightUp(RubikCube cube){
        //values of the current cubes
        byte spot1 = cube.getCube(2);
        byte spot2 = cube.getCube(5);
        byte spot3 = cube.getCube(8);
        byte spot4 = cube.getCube(47);
        byte spot5 = cube.getCube(50);
        byte spot6 = cube.getCube(53);
        byte spot7 = cube.getCube(14);
        byte spot8 = cube.getCube(23);
        byte spot9 = cube.getCube(32);
        byte spot10 = cube.getCube(38);
        byte spot11 = cube.getCube(41);
        byte spot12 = cube.getCube(44);

        //places to put those values
        cube.setCube(47, spot1);
        cube.setCube(50, spot2);
        cube.setCube(53, spot3);
        cube.setCube(38, spot4);
        cube.setCube(41, spot5);
        cube.setCube(44, spot6);
        cube.setCube(2, spot7);
        cube.setCube(5, spot8);
        cube.setCube(8, spot9);
        cube.setCube(14, spot10);
        cube.setCube(23, spot11);
        cube.setCube(32, spot12);
    }

    /**
     * Method to rotate a cube Top
     * @param cube the cube in which to rotate
     */
    private void rotateTopLeft(RubikCube cube){
        //values of the current cubes
        byte spot1 = cube.getCube(12);
        byte spot2 = cube.getCube(13);
        byte spot3 = cube.getCube(14);
        byte spot4 = cube.getCube(9);
        byte spot5 = cube.getCube(10);
        byte spot6 = cube.getCube(11);
        byte spot7 = cube.getCube(15);
        byte spot8 = cube.getCube(16);
        byte spot9 = cube.getCube(17);
        byte spot10 = cube.getCube(45);
        byte spot11 = cube.getCube(46);
        byte spot12 = cube.getCube(47);

        //values to place those cubes
        cube.setCube(9, spot1);
        cube.setCube(10, spot2);
        cube.setCube(11, spot3);
        cube.setCube(45, spot4);
        cube.setCube(46, spot5);
        cube.setCube(47, spot6);
        cube.setCube(12, spot7);
        cube.setCube(13, spot8);
        cube.setCube(14, spot9);
        cube.setCube(15, spot10);
        cube.setCube(16, spot11);
        cube.setCube(17, spot12);
    }

    /**
     * Method to rotate a cube Down
     * @param cube the cube in which to rotate
     */
    private void rotateDownLeft(RubikCube cube){
        //values of the current cubes
        byte spot1 = cube.getCube(30);
        byte spot2 = cube.getCube(31);
        byte spot3 = cube.getCube(32);
        byte spot4 = cube.getCube(27);
        byte spot5 = cube.getCube(28);
        byte spot6 = cube.getCube(29);
        byte spot7 = cube.getCube(33);
        byte spot8 = cube.getCube(34);
        byte spot9 = cube.getCube(35);
        byte spot10 = cube.getCube(51);
        byte spot11 = cube.getCube(52);
        byte spot12 = cube.getCube(53);

        //values to place those cubes
        cube.setCube(27, spot1);
        cube.setCube(28, spot2);
        cube.setCube(29, spot3);
        cube.setCube(51, spot4);
        cube.setCube(52, spot5);
        cube.setCube(53, spot6);
        cube.setCube(30, spot7);
        cube.setCube(31, spot8);
        cube.setCube(32, spot9);
        cube.setCube(33, spot10);
        cube.setCube(34, spot11);
        cube.setCube(35, spot12);
    }

    /**
     * Method to rotate the front of a cube left
     * @param cube the cube in which to rotate
     */
    private void rotateFrontLeft(RubikCube cube){
        //values of the current cubes
        byte spot1 = cube.getCube(8);
        byte spot2 = cube.getCube(7);
        byte spot3 = cube.getCube(6);
        byte spot4 = cube.getCube(11);
        byte spot5 = cube.getCube(20);
        byte spot6 = cube.getCube(29);
        byte spot7 = cube.getCube(15);
        byte spot8 = cube.getCube(24);
        byte spot9 = cube.getCube(33);
        byte spot10 = cube.getCube(36);
        byte spot11 = cube.getCube(37);
        byte spot12 = cube.getCube(38);

        //values to place those cubes
        cube.setCube(11, spot1);
        cube.setCube(20, spot2);
        cube.setCube(29, spot3);
        cube.setCube(36, spot4);
        cube.setCube(37, spot5);
        cube.setCube(38, spot6);
        cube.setCube(6, spot7);
        cube.setCube(7, spot8);
        cube.setCube(8, spot9);
        cube.setCube(33, spot10);
        cube.setCube(24, spot11);
        cube.setCube(15, spot12);
    }

    /**
     * Method to rotate the back of a cube left
     * @param cube the cube in which to rotate
     */
    private void rotateBackLeft(RubikCube cube){
        //values of the current cubes
        byte spot1 = cube.getCube(0);
        byte spot2 = cube.getCube(1);
        byte spot3 = cube.getCube(2);
        byte spot4 = cube.getCube(9);
        byte spot5 = cube.getCube(18);
        byte spot6 = cube.getCube(27);
        byte spot7 = cube.getCube(42);
        byte spot8 = cube.getCube(43);
        byte spot9 = cube.getCube(44);
        byte spot10 = cube.getCube(17);
        byte spot11 = cube.getCube(26);
        byte spot12 = cube.getCube(35);

        //values to place those cubes
        cube.setCube(27, spot1);
        cube.setCube(18, spot2);
        cube.setCube(9, spot3);
        cube.setCube(42, spot4);
        cube.setCube(43, spot5);
        cube.setCube(44, spot6);
        cube.setCube(35, spot7);
        cube.setCube(26, spot8);
        cube.setCube(17, spot9);
        cube.setCube(0, spot10);
        cube.setCube(1, spot11);
        cube.setCube(2, spot12);
    }

    /**
     * Method to rotate the left side of a cube left
     * @param cube the cube in which to rotate
     */
    private void rotateLeftDown(RubikCube cube){
        //values of the current cubes
        byte spot1 = cube.getCube(0);
        byte spot2 = cube.getCube(3);
        byte spot3 = cube.getCube(6);
        byte spot4 = cube.getCube(12);
        byte spot5 = cube.getCube(21);
        byte spot6 = cube.getCube(30);
        byte spot7 = cube.getCube(36);
        byte spot8 = cube.getCube(39);
        byte spot9 = cube.getCube(42);
        byte spot10 = cube.getCube(45);
        byte spot11 = cube.getCube(48);
        byte spot12 = cube.getCube(51);

        //values to place those cubes
        cube.setCube(12, spot1);
        cube.setCube(21, spot2);
        cube.setCube(30, spot3);
        cube.setCube(36, spot4);
        cube.setCube(39, spot5);
        cube.setCube(42, spot6);
        cube.setCube(45, spot7);
        cube.setCube(48, spot8);
        cube.setCube(51, spot9);
        cube.setCube(0, spot10);
        cube.setCube(3, spot11);
        cube.setCube(6, spot12);
    }

    /**
     * Method to rotate the right side of a cube Right
     * @param cube the cube in which to rotate
     */
    private void rotateRightDown(RubikCube cube){
        byte spot1 = cube.getCube(47);
        byte spot2 = cube.getCube(50);
        byte spot3 = cube.getCube(53);
        byte spot4 = cube.getCube(38);
        byte spot5 = cube.getCube(41);
        byte spot6 = cube.getCube(44);
        byte spot7 = cube.getCube(2);
        byte spot8 = cube.getCube(5);
        byte spot9 = cube.getCube(8);
        byte spot10 = cube.getCube(14);
        byte spot11 = cube.getCube(23);
        byte spot12 = cube.getCube(31);

        cube.setCube(2, spot1);
        cube.setCube(5, spot2);
        cube.setCube(8, spot3);
        cube.setCube(47, spot4);
        cube.setCube(50, spot5);
        cube.setCube(53, spot6);
        cube.setCube(14, spot7);
        cube.setCube(23, spot8);
        cube.setCube(32, spot9);
        cube.setCube(38, spot10);
        cube.setCube(41, spot11);
        cube.setCube(44, spot12);
    }

    /**
     * Method to rotate the top of a cube right
     * @param cube the cube in which to rotate
     */
    private void rotateTopRight(RubikCube cube){
        //values of the current cubes
        byte spot1 = cube.getCube(9);
        byte spot2 = cube.getCube(10);
        byte spot3 = cube.getCube(11);
        byte spot4 = cube.getCube(12);
        byte spot5 = cube.getCube(13);
        byte spot6 = cube.getCube(14);
        byte spot7 = cube.getCube(15);
        byte spot8 = cube.getCube(16);
        byte spot9 = cube.getCube(17);
        byte spot10 = cube.getCube(45);
        byte spot11 = cube.getCube(46);
        byte spot12 = cube.getCube(47);

        //places to put those values
        cube.setCube(12, spot1);
        cube.setCube(13, spot2);
        cube.setCube(14, spot3);
        cube.setCube(15, spot4);
        cube.setCube(16, spot5);
        cube.setCube(17, spot6);
        cube.setCube(45, spot7);
        cube.setCube(46, spot8);
        cube.setCube(47, spot9);
        cube.setCube(9, spot10);
        cube.setCube(10, spot11);
        cube.setCube(11, spot12);
    }

    /**
     * Method to rotate the down of a cube right
     * @param cube the cube in which to rotate
     */
    private void rotateDownRight(RubikCube cube){
        //values of the current cubes
        byte spot1 = cube.getCube(27);
        byte spot2 = cube.getCube(28);
        byte spot3 = cube.getCube(29);
        byte spot4 = cube.getCube(30);
        byte spot5 = cube.getCube(31);
        byte spot6 = cube.getCube(32);
        byte spot7 = cube.getCube(33);
        byte spot8 = cube.getCube(34);
        byte spot9 = cube.getCube(35);
        byte spot10 = cube.getCube(51);
        byte spot11 = cube.getCube(52);
        byte spot12 = cube.getCube(53);

        //places to put those values
        cube.setCube(30, spot1);
        cube.setCube(31, spot2);
        cube.setCube(32, spot3);
        cube.setCube(33, spot4);
        cube.setCube(34, spot5);
        cube.setCube(35, spot6);
        cube.setCube(51, spot7);
        cube.setCube(52, spot8);
        cube.setCube(53, spot9);
        cube.setCube(27, spot10);
        cube.setCube(28, spot11);
        cube.setCube(29, spot12);
    }

    /**
     * Method to rotate a cube front
     * @param cube the cube in which to rotate
     */
    private void rotateFontRight(RubikCube cube){

    }

    /**
     * Method to rotate a cube back
     * @param cube the cube in which to rotate
     */
    private void rotateBackRight(RubikCube cube){

    }


    /**
     * Method to test the permutation validitiy of a rubikCube
     * @return true if valid
     */
    private boolean permutationTest(){
        RubikCube testCube = new RubikCube(rubikCube);

        return true;
    }

    /**
     * function to return the interger to string
     * @param value
     * @return
     */
    private String returnToChar(int value){
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
        return " ";
    }

    /**
     * Overwrite of the toString method
     * @return string printout of the current rubikCube
     */
    public String toString(){
        return this.deserialize();
    }
}
