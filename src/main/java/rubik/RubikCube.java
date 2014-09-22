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

    private int[] goalStates = new int[28];

    /**
     * constructor to create a cube from a file
     * @param file the file to create a cube off of
     * @throws FileNotFoundException
     */
    public RubikCube(String file) throws FileNotFoundException {
        rubikCube = new byte[54];
        initalizeGoalStates();

        try{
            serialize(file);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * constructor to create a copy of a cube
     * @param cube a copy of the cube to create off of
     */
    public RubikCube(byte [] cube){
        rubikCube = cube;
    }

    private void initalizeGoalStates(){
         //order goes front, top, left, right, bottom, back
        //always starting in top left corner

        //yellow sides
        goalStates[0] = 1;      //yellow red green corner cube
        goalStates[1] = 0;      //yellow red edge cube
        goalStates[2] = 2;      //yellow red blue corner cube
        goalStates[3] = 0;      //yellow green edge cube
        goalStates[4] = 0;      //yellow middle cube
        goalStates[5] = 0;      //yellow blue edge cube
        goalStates[6] = 5;      //yellow green orange corner cube
        goalStates[7] = 0;      //yellow orange edge cube
        goalStates[8] = 6;      //yellow blue orange corner cube

        //red sides
        goalStates[9] = 3;      //red green white corner cube
        goalStates[10] = 0;     //red white edge cube
        goalStates[11] = 4;     //red white blue corner cube
        goalStates[12] = 0;     //red green edge cube
        goalStates[13] = 0;     //red middle cube
        goalStates[14] = 0;     //red blue edge cube

        //green side
        goalStates[15] = 0;     //green white edge cube
        goalStates[16] = 0;     //green middle cube
        goalStates[17] = 7;     //green white orange corner cube
        goalStates[18] = 0;     //green orange edge cube

        //blue side
        goalStates[19] = 0;     //blue middle cube
        goalStates[20] = 0;     //blue white edge cube
        goalStates[21] = 0;     //blue orange edge cube
        goalStates[22] = 8;     //blue orange white corner cube

        //orange side
        goalStates[23] = 0;     //orange middle cube
        goalStates[24] = 0;     //orange white edge cube

        //white side
        goalStates[25] = 0;    //white middle cube
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
            rubikString += this.returnToChar(this.getCubies(i));
            if(i % 3 == 2 && i < 8){
                rubikString += "\n   ";
            }else if(i % 3 == 2){
                rubikString += "\n";
            }
        }
        for(int i = 9; i < 36; i++){
            rubikString += this.returnToChar(this.getCubies(i));
            if(i % 9 == 8){
                rubikString += "\n";
            }
        }

        rubikString += "   ";

        for(int i = 36; i < 54; i++){
            rubikString += this.returnToChar(this.getCubies(i));
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
        return (this.count() && this.middles() && this.korf());
    }

    /**
     * method to get a specific position color based on index in byte array
     * @param pos position of specific face color in question
     * @return the int of the color of the position by index
     */
    public byte getCubies(int pos){
        return rubikCube[pos];
    }

    /**
     * Method to return the size of the rubiks cube
     * @return the length of the array that stores the cube
     */
    public int getSize(){
        return rubikCube.length;
    }

    /**
     * method to set a position in a cube with a byte value
     * @param pos position to change
     * @param value byte value to add
     */
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
            switch (this.getCubies(i)) {
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
        return ((this.getCubies(4) == 0) && (this.getCubies(19) == 1) && (this.getCubies(22) == 2) &&
                (this.getCubies(25) == 3) && (this.getCubies(40) == 4) && (this.getCubies(49) == 5));
    }

    /**
     * Method to test the korf algorithm
     * @return true if valid
     */
    private boolean korf(){
        //return cornerTest() && edgeTest() && permutationTest();
        if(!cornerTest()){
            System.out.println("Failed Corner Test");
            return false;
        }else if(!middles()){
            System.out.println("Failed Middles Test");
            return false;
        }else if(!edgeTest()){
            System.out.println("Failed Edge Test");
            return false;
        }

        return true;
    }

    /**
     * Method to test the corner validitiy of a rubikCube
     * @return true if valid
     */
    private boolean cornerTest(){
        //check how far corner is from goal state, if total % 3 then true
        validateCorners();

        return true;
    }

    public boolean validateCorners(){
        int sum = 0;

        //for each corner check if it is add value to sum so the total should be 36 bc 8 + 7 ... 1 for each corner

        //check each corner
        sum += validateSingleCorner(this.getCubies(6), this.getCubies(11), this.getCubies(12));
        sum += validateSingleCorner(this.getCubies(0), this.getCubies(9), this.getCubies(51));
        sum += validateSingleCorner(this.getCubies(2), this.getCubies(17), this.getCubies(53));
        sum += validateSingleCorner(this.getCubies(8), this.getCubies(14), this.getCubies(15));
        sum += validateSingleCorner(this.getCubies(29), this.getCubies(30), this.getCubies(36));
        sum += validateSingleCorner(this.getCubies(32), this.getCubies(33), this.getCubies(38));
        sum += validateSingleCorner(this.getCubies(44), this.getCubies(47), this.getCubies(35));
        sum += validateSingleCorner(this.getCubies(27), this.getCubies(42), this.getCubies(45));

        if(sum != 36)
            return false;

        //corner paradity

        sum=0;

        sum += cornerSingleParity(this.getCubies(6), this.getCubies(11), this.getCubies(12));
        sum += cornerSingleParity(this.getCubies(0), this.getCubies(9), this.getCubies(51));
        sum += cornerSingleParity(this.getCubies(2), this.getCubies(17), this.getCubies(53));
        sum += cornerSingleParity(this.getCubies(8), this.getCubies(14), this.getCubies(15));
        sum += cornerSingleParity(this.getCubies(36), this.getCubies(29), this.getCubies(30));
        sum += cornerSingleParity(this.getCubies(38), this.getCubies(33), this.getCubies(32));
        sum += cornerSingleParity(this.getCubies(44), this.getCubies(47), this.getCubies(35));
        sum += cornerSingleParity(this.getCubies(42), this.getCubies(45), this.getCubies(47));

        //if its greather than 100 there was an error
        return sum <= 100 && (sum % 3) == 0;
    }

    /**
     * method that checks if a corner cubie is one of the 8 valid ones
     * @param side1 first side of the corner cubie
     * @param side2 second side of the corner cubie
     * @param side3 this side of the corner cubie
     * @return the corner number
     */
    private int validateSingleCorner(int side1, int side2, int side3){

        if(side1 == RED || side2 == RED || side3 == RED ){
            if(side1 == YELLOW || side2 == YELLOW || side3 == YELLOW){
                //Test for cube 1
                if(side1 == GREEN || side2 == GREEN || side3 == GREEN){
                    return 1;
                }
                //test for cube 2
                else if(side1 == BLUE || side2 == BLUE || side3 == BLUE){
                    return 2;
                }
            }else if(side1 == WHITE || side2 == WHITE || side3 == WHITE){
                //test for cube 3
                if(side1 == GREEN || side2 == GREEN || side3 == GREEN){
                    return 3;
                }
                //test for cube 4
                else if(side1 == BLUE || side2 == BLUE || side3 == BLUE){
                    return 4;
                }
            }
        }else if(side1 == ORANGE || side2 == ORANGE || side3 == ORANGE){
            if(side1 == YELLOW || side2 == YELLOW || side3 == YELLOW){
                //test for cube 5
                if(side1 == GREEN || side2 == GREEN || side3 == GREEN){
                    return 5;
                }
                //test for cube 6
                else if(side1 == BLUE || side2 == BLUE || side3 == BLUE){
                    return 6;
                }
            }else if(side1 == WHITE || side2 == WHITE || side3 == WHITE){
                //test for cube 7
                if(side1 == GREEN || side2 == GREEN || side3 == GREEN){
                    return 7;
                }
                //test for cube 8
                else if(side1 == BLUE || side2 == BLUE || side3 == BLUE){
                    return 8;
                }
            }
        }

        return 0;
    }

    /**
     * method that checks which direction a cube is facing for its paradity number
     * @param sideTop top side of the corner cubie
     * @param side1 first side of the corner cubie
     * @param side2 second side of the corner cubie
     * @return the paradity integer
     */
    private int cornerSingleParity(int sideTop, int side1, int side2){

        //check to see the direction of the cubie, which figures out what value to assign to the parity
        if(sideTop == RED || sideTop == ORANGE){
            return 0;
        }else if(side1 == RED || side1 == ORANGE){
            return 1;
        }else if(side2 == RED || side2 == ORANGE){
            return 2;
        }

        //no cases where hit so there was an error return 100
        return 100;
    }

    /**
     * Method to test the edge validitiy of a rubikCube
     * @return true if valid
     */
    private boolean edgeTest(){
        //place a 0 or 1 on an edge, if they opposite edge sums for total are % 2 then true
        int sum=0;

        //blue window 1
        sum += singleEdgeParity(this.getCubies(1),this.getCubies(52));
        sum += singleEdgeParity(this.getCubies(7),this.getCubies(13));

        //blue window 2
        sum += singleEdgeParity(this.getCubies(23),this.getCubies(24));
        sum += singleEdgeParity(this.getCubies(21),this.getCubies(20));

        //blue window 3
        sum += singleEdgeParity(this.getCubies(37),this.getCubies(31));
        sum += singleEdgeParity(this.getCubies(43),this.getCubies(46));

        //blue window 4
        sum += singleEdgeParity(this.getCubies(50),this.getCubies(26));
        sum += singleEdgeParity(this.getCubies(48),this.getCubies(18));

        //blue window 5
        sum += singleEdgeParity(this.getCubies(16),this.getCubies(5));
        sum += singleEdgeParity(this.getCubies(34),this.getCubies(41));

        //blue window 6
        sum += singleEdgeParity(this.getCubies(10),this.getCubies(3));
        sum += singleEdgeParity(this.getCubies(28),this.getCubies(39));

        //check to make sure each windows sum is a factor of two
        return (sum % 2) == 0;
    }

    /**
     * Method to get the edge parity value of the blue window position
     * @param topCubie top cubie
     * @param sideCubie side cubie
     * @return
     */
    private int singleEdgeParity(int topCubie, int sideCubie){
        //RED TOP
        if(topCubie==RED && sideCubie==YELLOW)
            return 1;
        else if(topCubie==RED && sideCubie==WHITE)
            return 1;
        else if(topCubie==RED && sideCubie==BLUE)
            return 0;
        else if(topCubie==RED && sideCubie==GREEN)
            return 0;

        //ORANGE BOTTOM
        else if(topCubie==ORANGE && sideCubie==YELLOW)
            return 1;
        else if(topCubie==ORANGE && sideCubie==WHITE)
            return 1;
        else if(topCubie==ORANGE && sideCubie==BLUE)
            return 0;
        else if(topCubie==ORANGE && sideCubie==GREEN)
            return 0;

        //WHITE SIDES
        else if(topCubie==WHITE && sideCubie==BLUE)
            return 1;
        else if(topCubie==WHITE && sideCubie==GREEN)
            return 1;

        //YELLOW SIDES
        else if(topCubie==YELLOW && sideCubie==BLUE)
            return 1;
        else if(topCubie==YELLOW && sideCubie==GREEN)
            return 1;

        //BLUE SIDES
        else if(topCubie==BLUE && sideCubie==RED)
            return 1;
        else if(topCubie==BLUE && sideCubie==ORANGE)
            return 1;

        //GREEN SIDES
        else if(topCubie==GREEN && sideCubie==RED)
            return 1;
        else if(topCubie==GREEN && sideCubie==ORANGE)
            return 1;

        return 0;
    }

    /**
     * Method to test the permutation validitiy of a rubikCube
     * @return true if valid
     */
    private boolean permutationTest(){
        RubikCube testCube = new RubikCube(rubikCube);
        int sum = 0;

        sum += cornerDistanceToGoal(this.getCubies(6), this.getCubies(11), this.getCubies(12));
        sum += cornerDistanceToGoal(this.getCubies(0), this.getCubies(9), this.getCubies(51));
        sum += cornerDistanceToGoal(this.getCubies(2), this.getCubies(17), this.getCubies(53));
        sum += cornerDistanceToGoal(this.getCubies(8), this.getCubies(14), this.getCubies(15));
        sum += cornerDistanceToGoal(this.getCubies(36), this.getCubies(29), this.getCubies(30));
        sum += cornerDistanceToGoal(this.getCubies(38), this.getCubies(33), this.getCubies(32));
        sum += cornerDistanceToGoal(this.getCubies(44), this.getCubies(47), this.getCubies(35));
        sum += cornerDistanceToGoal(this.getCubies(42), this.getCubies(45), this.getCubies(47));



        return (sum % 2) == 0;
    }

    /**
     * Method to count the edge distance from goal state
     * @return int of how far it is from goal state
     */
    private int edgeDistanceToGoal(int side1, int side2){
        int edgeLocation = 0;

        int goalLocation = edgeGoalFinder(side1, side2);

        return (edgeLocation - goalLocation);
    }

    /**
     * method to find the goal of the specific edge
     * @param side1 side 1 of the cubie
     * @param side2 side 2 of the cubie
     * @return the location of its end goal
     */
    private int edgeGoalFinder(int side1, int side2){
        return 0;
    }

    /**
     * Method to count the edge distance from goal state
     * @return int of how far it is from goal state
     */
    private int cornerDistanceToGoal(int topSide, int side1, int side2){
        int cornerLocation = validateSingleCorner(topSide, side1, side2);

        int goalLocation = cornerGoalFinder(topSide);

        return (cornerLocation - goalLocation);
    }

    /**
     * method to find the goal of the specific corer
     * @param topSide top side of the corner cubie
     * @return the location of its end goal
     */
    private int cornerGoalFinder(int topSide){

        switch(topSide){
            case 0:
                return goalStates[9];
            case 6:
                return goalStates[0];
            case 2:
                return goalStates[11];
            case 8:
                return goalStates[2];
            case 36:
                return goalStates[6];
            case 38:
                return goalStates[8];
            case 44:
                return goalStates[22];
            case 42:
                return goalStates[17];
        }

        return 0;
    }

    /**
     * function to return the interger to string
     * @param value of the byte to turn into a string again
     * @return string of the char based on the int
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

    /**
     * Method to rotate the left side of a cube left
     * @param cube the cube in which to rotate
     */
    public void rotateLeftUp(RubikCube cube){
        //values of current cubes
        byte spot1 = cube.getCubies(12);
        byte spot2 = cube.getCubies(21);
        byte spot3 = cube.getCubies(30);
        byte spot4 = cube.getCubies(0);
        byte spot5 = cube.getCubies(3);
        byte spot6 = cube.getCubies(6);
        byte spot7 = cube.getCubies(45);
        byte spot8 = cube.getCubies(48);
        byte spot9 = cube.getCubies(51);
        byte spot10 = cube.getCubies(36);
        byte spot11 = cube.getCubies(39);
        byte spot12 = cube.getCubies(42);

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
    public void rotateRightUp(RubikCube cube){
        //values of the current cubes
        byte spot1 = cube.getCubies(2);
        byte spot2 = cube.getCubies(5);
        byte spot3 = cube.getCubies(8);
        byte spot4 = cube.getCubies(47);
        byte spot5 = cube.getCubies(50);
        byte spot6 = cube.getCubies(53);
        byte spot7 = cube.getCubies(14);
        byte spot8 = cube.getCubies(23);
        byte spot9 = cube.getCubies(32);
        byte spot10 = cube.getCubies(38);
        byte spot11 = cube.getCubies(41);
        byte spot12 = cube.getCubies(44);

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
    public void rotateTopLeft(RubikCube cube){
        //values of the current cubes
        byte spot1 = cube.getCubies(12);
        byte spot2 = cube.getCubies(13);
        byte spot3 = cube.getCubies(14);
        byte spot4 = cube.getCubies(9);
        byte spot5 = cube.getCubies(10);
        byte spot6 = cube.getCubies(11);
        byte spot7 = cube.getCubies(15);
        byte spot8 = cube.getCubies(16);
        byte spot9 = cube.getCubies(17);
        byte spot10 = cube.getCubies(45);
        byte spot11 = cube.getCubies(46);
        byte spot12 = cube.getCubies(47);

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
    public void rotateDownLeft(RubikCube cube){
        //values of the current cubes
        byte spot1 = cube.getCubies(30);
        byte spot2 = cube.getCubies(31);
        byte spot3 = cube.getCubies(32);
        byte spot4 = cube.getCubies(27);
        byte spot5 = cube.getCubies(28);
        byte spot6 = cube.getCubies(29);
        byte spot7 = cube.getCubies(33);
        byte spot8 = cube.getCubies(34);
        byte spot9 = cube.getCubies(35);
        byte spot10 = cube.getCubies(51);
        byte spot11 = cube.getCubies(52);
        byte spot12 = cube.getCubies(53);

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
    public void rotateFrontLeft(RubikCube cube){
        //values of the current cubes
        byte spot1 = cube.getCubies(8);
        byte spot2 = cube.getCubies(7);
        byte spot3 = cube.getCubies(6);
        byte spot4 = cube.getCubies(11);
        byte spot5 = cube.getCubies(20);
        byte spot6 = cube.getCubies(29);
        byte spot7 = cube.getCubies(15);
        byte spot8 = cube.getCubies(24);
        byte spot9 = cube.getCubies(33);
        byte spot10 = cube.getCubies(36);
        byte spot11 = cube.getCubies(37);
        byte spot12 = cube.getCubies(38);

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
    public void rotateBackLeft(RubikCube cube){
        //values of the current cubes
        byte spot1 = cube.getCubies(0);
        byte spot2 = cube.getCubies(1);
        byte spot3 = cube.getCubies(2);
        byte spot4 = cube.getCubies(9);
        byte spot5 = cube.getCubies(18);
        byte spot6 = cube.getCubies(27);
        byte spot7 = cube.getCubies(42);
        byte spot8 = cube.getCubies(43);
        byte spot9 = cube.getCubies(44);
        byte spot10 = cube.getCubies(17);
        byte spot11 = cube.getCubies(26);
        byte spot12 = cube.getCubies(35);

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
    public void rotateLeftDown(RubikCube cube){
        //values of the current cubes
        byte spot1 = cube.getCubies(0);
        byte spot2 = cube.getCubies(3);
        byte spot3 = cube.getCubies(6);
        byte spot4 = cube.getCubies(12);
        byte spot5 = cube.getCubies(21);
        byte spot6 = cube.getCubies(30);
        byte spot7 = cube.getCubies(36);
        byte spot8 = cube.getCubies(39);
        byte spot9 = cube.getCubies(42);
        byte spot10 = cube.getCubies(45);
        byte spot11 = cube.getCubies(48);
        byte spot12 = cube.getCubies(51);

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
    public void rotateRightDown(RubikCube cube){
        byte spot1 = cube.getCubies(47);
        byte spot2 = cube.getCubies(50);
        byte spot3 = cube.getCubies(53);
        byte spot4 = cube.getCubies(38);
        byte spot5 = cube.getCubies(41);
        byte spot6 = cube.getCubies(44);
        byte spot7 = cube.getCubies(2);
        byte spot8 = cube.getCubies(5);
        byte spot9 = cube.getCubies(8);
        byte spot10 = cube.getCubies(14);
        byte spot11 = cube.getCubies(23);
        byte spot12 = cube.getCubies(31);

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
    public void rotateTopRight(RubikCube cube){
        //values of the current cubes
        byte spot1 = cube.getCubies(9);
        byte spot2 = cube.getCubies(10);
        byte spot3 = cube.getCubies(11);
        byte spot4 = cube.getCubies(12);
        byte spot5 = cube.getCubies(13);
        byte spot6 = cube.getCubies(14);
        byte spot7 = cube.getCubies(15);
        byte spot8 = cube.getCubies(16);
        byte spot9 = cube.getCubies(17);
        byte spot10 = cube.getCubies(45);
        byte spot11 = cube.getCubies(46);
        byte spot12 = cube.getCubies(47);

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
    public void rotateDownRight(RubikCube cube){
        //values of the current cubes
        byte spot1 = cube.getCubies(27);
        byte spot2 = cube.getCubies(28);
        byte spot3 = cube.getCubies(29);
        byte spot4 = cube.getCubies(30);
        byte spot5 = cube.getCubies(31);
        byte spot6 = cube.getCubies(32);
        byte spot7 = cube.getCubies(33);
        byte spot8 = cube.getCubies(34);
        byte spot9 = cube.getCubies(35);
        byte spot10 = cube.getCubies(51);
        byte spot11 = cube.getCubies(52);
        byte spot12 = cube.getCubies(53);

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
    public void rotateFrontRight(RubikCube cube){
        //values of the current cubes
        byte spot1 = cube.getCubies(6);
        byte spot2 = cube.getCubies(7);
        byte spot3 = cube.getCubies(8);
        byte spot4 = cube.getCubies(11);
        byte spot5 = cube.getCubies(20);
        byte spot6 = cube.getCubies(29);
        byte spot7 = cube.getCubies(36);
        byte spot8 = cube.getCubies(37);
        byte spot9 = cube.getCubies(38);
        byte spot10 = cube.getCubies(15);
        byte spot11 = cube.getCubies(24);
        byte spot12 = cube.getCubies(33);

        //places to put those values
        cube.setCube(15, spot1);
        cube.setCube(24, spot2);
        cube.setCube(33, spot3);
        cube.setCube(6, spot4);
        cube.setCube(7, spot5);
        cube.setCube(8, spot6);
        cube.setCube(11, spot7);
        cube.setCube(20, spot8);
        cube.setCube(29, spot9);
        cube.setCube(38, spot10);
        cube.setCube(37, spot11);
        cube.setCube(36, spot12);
    }

    /**
     * Method to rotate a cube back
     * @param cube the cube in which to rotate
     */
    public void rotateBackRight(RubikCube cube){
        //values of the current cubes
        byte spot1 = cube.getCubies(0);
        byte spot2 = cube.getCubies(1);
        byte spot3 = cube.getCubies(2);
        byte spot4 = cube.getCubies(9);
        byte spot5 = cube.getCubies(18);
        byte spot6 = cube.getCubies(27);
        byte spot7 = cube.getCubies(17);
        byte spot8 = cube.getCubies(26);
        byte spot9 = cube.getCubies(35);
        byte spot10 = cube.getCubies(42);
        byte spot11 = cube.getCubies(43);
        byte spot12 = cube.getCubies(44);

        //places to put those values
        cube.setCube(17, spot1);
        cube.setCube(26, spot2);
        cube.setCube(35, spot3);
        cube.setCube(0, spot4);
        cube.setCube(1, spot5);
        cube.setCube(2, spot6);
        cube.setCube(44, spot7);
        cube.setCube(43, spot8);
        cube.setCube(42, spot9);
        cube.setCube(9, spot10);
        cube.setCube(18, spot11);
        cube.setCube(27, spot12);
    }
}
