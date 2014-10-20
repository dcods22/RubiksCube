package rubik;

//import org.junit.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.LinkedList;

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

    //theoretical goalstate array to keep all cubes in order
//    private int[] goalStates = new int[28];
//    //yellow sides
//    goalStates[0] = 1;      //yellow red green corner cube
//    goalStates[1] = 1;      //yellow red edge cube
//    goalStates[2] = 2;      //yellow red blue corner cube
//    goalStates[3] = 2;      //yellow green edge cube
//    goalStates[4] = 1;      //yellow middle cube
//    goalStates[5] = 3;      //yellow blue edge cube
//    goalStates[6] = 5;      //yellow green orange corner cube
//    goalStates[7] = 4;      //yellow orange edge cube
//    goalStates[8] = 6;      //yellow blue orange corner cube
//
//    //red sides
//    goalStates[9] = 3;      //red green white corner cube
//    goalStates[10] = 5;     //red white edge cube
//    goalStates[11] = 4;     //red white blue corner cube
//    goalStates[12] = 6;     //red green edge cube
//    goalStates[13] = 2;     //red middle cube
//    goalStates[14] = 7;     //red blue edge cube
//
//    //green side
//    goalStates[15] = 8;     //green white edge cube
//    goalStates[16] = 3;     //green middle cube
//    goalStates[17] = 7;     //green white orange corner cube
//    goalStates[18] = 9;     //green orange edge cube
//
//    //blue side
//    goalStates[19] = 4;     //blue middle cube
//    goalStates[20] = 10;     //blue white edge cube
//    goalStates[21] = 11;     //blue orange edge cube
//    goalStates[22] = 8;     //blue orange white corner cube
//
//    //orange side
//    goalStates[23] = 5;     //orange middle cube
//    goalStates[24] = 12;    //orange white edge cube
//
//    //white side
//    goalStates[25] = 6;    //white middle cube


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
     * @param cube a copy of the cube to create off of
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

            //turn file into a serialized version in a byte array
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
        }catch (IOException e) {
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

    /************************ Validation ********************************/

    /**
     * Method to test the validitiy of a cube
     * @return true if valid
     */
    public boolean validate(){
        //return to make sure the  cube is valid based on counting, middles, and korfs algorithm
        if(!count()){
            //System.out.println("Failed Count Test");
            return false;
        }else if(!middles()){
            //System.out.println("Failed Middles Test");
            return false;
        }else if(!korf()){
            //System.out.println("Failed Korf Test");
            return false;
        }

        return true;
    }


    /**
     * method to get a specific position color based on index in byte array
     * @param pos position of specific face color in question
     * @return the int of the color of the position by index
     */
    public byte getCubies(int pos){
        if(pos < this.getSize())
            return rubikCube[pos];

        return -1;
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
        //initalize the variables
        int rCount=0, gCount=0, yCount=0, bCount=0, oCount=0, wCount=0;

        //if the cube size is not 54 then return false
        if(this.getSize() != 54){
            return false;
        }

        //loop through to count based on each color
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

        //return bool of if each color is there 9 times
        return !(rCount != 9 && gCount != 9 && yCount != 9 && bCount != 9 && oCount != 9 && wCount != 9);
    }


    /**
     * Method to test the middle validitiy of a rubikCube
     * @return true if valid
     */
    public boolean middles(){
        //return to make sure the middles are of correct color
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
            //System.out.println("Failed Corner Test");
            return false;
        }else if(!edgeTest()){
            //System.out.println("Failed Edge Test");
            return false;
        }
        else if(!permutationTest()){
            //System.out.println("Failed Permutation Test");
            return false;
        }

        return true;
    }


    /**
     * method to test the validity of the corners
     * @return the bool if the corners are valid
     */
    private boolean cornerTest(){
        int sum = 0;

        //for each corner check if it is add value to sum so the total should be 36 bc 8 + 7 ... 1 for each corner

        //check each corner
        sum += cornerValue(this.getCubies(6), this.getCubies(11), this.getCubies(12));
        sum += cornerValue(this.getCubies(0), this.getCubies(9), this.getCubies(51));
        sum += cornerValue(this.getCubies(2), this.getCubies(17), this.getCubies(53));
        sum += cornerValue(this.getCubies(8), this.getCubies(14), this.getCubies(15));
        sum += cornerValue(this.getCubies(29), this.getCubies(30), this.getCubies(36));
        sum += cornerValue(this.getCubies(32), this.getCubies(33), this.getCubies(38));
        sum += cornerValue(this.getCubies(44), this.getCubies(47), this.getCubies(35));
        sum += cornerValue(this.getCubies(27), this.getCubies(42), this.getCubies(45));

        if(sum != 36)
            return false;

        //corner paradity to check if corners are % 3 away
        sum=0;

        sum += cornerSingleParity(this.getCubies(6), this.getCubies(12), this.getCubies(11));
        sum += cornerSingleParity(this.getCubies(0), this.getCubies(9), this.getCubies(51));
        sum += cornerSingleParity(this.getCubies(2), this.getCubies(53), this.getCubies(17));
        sum += cornerSingleParity(this.getCubies(8), this.getCubies(15), this.getCubies(14));
        sum += cornerSingleParity(this.getCubies(36), this.getCubies(29), this.getCubies(30));
        sum += cornerSingleParity(this.getCubies(38), this.getCubies(32), this.getCubies(33));
        sum += cornerSingleParity(this.getCubies(44), this.getCubies(35), this.getCubies(47));
        sum += cornerSingleParity(this.getCubies(42), this.getCubies(45), this.getCubies(27));

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
    private int cornerValue(int side1, int side2, int side3){

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
     * method to get the specific edge value
     * @param side1 side 1 of the cubie
     * @param side2 side 2 of the cubie
     * @return the value of that specific edge
     */
    private int edgeValue(int side1,int side2){
        if(side1 == YELLOW || side2 == YELLOW){
            if(side1 == RED || side2 == RED){
                return 1;
            }else if(side1 == GREEN || side2 == GREEN){
                return 2;
            }else if(side1 == BLUE || side2 == BLUE){
                return 3;
            }else if(side1 == ORANGE || side2 == ORANGE){
                return 4;
            }
        }else if(side1 == RED || side2 == RED){
            if(side1 == WHITE || side2 == WHITE){
                return 5;
            }else if(side1 == GREEN || side2 == GREEN){
                return 6;
            }else if(side1 == BLUE || side2 == BLUE){
                return 7;
            }
        }else if(side1 == GREEN || side2 == GREEN){
            if(side1 == WHITE || side2 == WHITE){
                return 8;
            }else if(side1 == ORANGE || side2 == ORANGE){
                return 9;
            }
        }else if(side1 == BLUE || side2 == BLUE){
            if(side1 == WHITE || side2 == WHITE){
                return 10;
            }else if(side1 == ORANGE || side2 == ORANGE){
                return 11;
            }
        }else if(side1 == ORANGE || side2 == ORANGE){
            if(side1 == WHITE || side2 == WHITE){
                return 12;
            }
        }

        return 0;
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
        int cornerSum=0, edgeSum=0;

        cornerSum += cornerDistanceToGoal(6, 11, 12);
        cornerSum += cornerDistanceToGoal(0, 9, 51);
        cornerSum += cornerDistanceToGoal(2, 17, 53);
        cornerSum += cornerDistanceToGoal(8, 14, 15);
        cornerSum += cornerDistanceToGoal(36, 29, 30);
        cornerSum += cornerDistanceToGoal(38, 33, 32);
        cornerSum += cornerDistanceToGoal(44, 47, 35);
        cornerSum += cornerDistanceToGoal(42, 45, 27);

        edgeSum += edgeDistanceToGoal(13, 7);
        edgeSum += edgeDistanceToGoal(21, 20);
        edgeSum += edgeDistanceToGoal(23, 24);
        edgeSum += edgeDistanceToGoal(31, 37);
        edgeSum += edgeDistanceToGoal(1, 52);
        edgeSum += edgeDistanceToGoal(3, 10);
        edgeSum += edgeDistanceToGoal(5, 16);
        edgeSum += edgeDistanceToGoal(18, 48);
        edgeSum += edgeDistanceToGoal(28, 39);
        edgeSum += edgeDistanceToGoal(26, 50);
        edgeSum += edgeDistanceToGoal(34, 41);
        edgeSum += edgeDistanceToGoal(43, 46);

        return ((edgeSum + cornerSum) % 2) == 0;
    }


    /**
     * Method to count the edge distance from goal state
     * @return int of how far it is from goal state
     */
    private int cornerDistanceToGoal(int topSide, int side1, int side2){
        int topColor = this.getCubies(topSide);
        int side1Color = this.getCubies(side1);
        int side2Color = this.getCubies(side2);

        int goalLocation = cornerGoalFinder(cornerValue(topColor, side1Color, side2Color));

        int cornerLocation = cornerLocation(topColor, side1Color, side2Color);

        int distance=0;

        if(goalLocation > cornerLocation)
            distance = goalLocation - cornerLocation;
        else if(goalLocation < cornerLocation)
            distance = cornerLocation - goalLocation;
        else if(goalLocation == cornerLocation)
            distance = 0;

        return distance;
    }


    /**
     * method to find the goal of the specific corer
     * @param topSide top side of the corner cubie
     * @return the location of its end goal
     */
    private int cornerGoalFinder(int topSide){

        //based on the top side, know where in the goal states to return
        switch(topSide){
            case 1:
                return 0;
            case 2:
                return 2;
            case 3:
                return 9;
            case 4:
                return 11;
            case 5:
                return 6;
            case 6:
                return 8;
            case 7:
                return 17;
            case 8:
                return 22;
        }

        return 0;
    }

    /**
     * Method that finds the location of that specific corner cube based on color
     * @param topSide topside color
     * @param side1 side1 color
     * @param side2 side2 color
     * @return location of that cube in array of cubies
     */
    private int cornerLocation(int topSide, int side1, int side2){
        if(this.getCubies(6) == topSide || this.getCubies(6) == side1 || this.getCubies(6) == side2){
            if(this.getCubies(11) == side1 || this.getCubies(11) == side2 || this.getCubies(11) == topSide){
                if(this.getCubies(12) == side1 || this.getCubies(12) == side2 || this.getCubies(12) == topSide)
                    return 0;
            }
        }
        if(this.getCubies(0) == topSide || this.getCubies(0) == side1 || this.getCubies(0) == side2){
            if(this.getCubies(9) == side1 || this.getCubies(9) == side2 || this.getCubies(9) == topSide){
                if(this.getCubies(51) == side1 || this.getCubies(51) == side2 || this.getCubies(51) == topSide)
                    return 9;
            }
        }
        if(this.getCubies(2) == topSide || this.getCubies(2) == side1 || this.getCubies(2) == side2){
            if(this.getCubies(17) == side1 || this.getCubies(17) == side2 || this.getCubies(17) == topSide){
                if(this.getCubies(53) == side1 || this.getCubies(53) == side2 || this.getCubies(53) == topSide)
                    return 11;
            }
        }
        if(this.getCubies(8) == topSide || this.getCubies(8) == side1 || this.getCubies(8) == side2){
            if(this.getCubies(14) == side1 || this.getCubies(14) == side2 || this.getCubies(14) == topSide){
                if(this.getCubies(15) == side1 || this.getCubies(15) == side2 || this.getCubies(15) == topSide)
                    return 2;
            }
        }
        if(this.getCubies(36) == topSide || this.getCubies(36) == side1 || this.getCubies(36) == side2){
            if(this.getCubies(29) == side1 || this.getCubies(29) == side2 || this.getCubies(29) == topSide){
                if(this.getCubies(30) == side1 || this.getCubies(30) == side2 || this.getCubies(30) == topSide)
                    return 6;
            }
        }
        if(this.getCubies(38) == topSide || this.getCubies(38) == side1 || this.getCubies(38) == side2){
            if(this.getCubies(33) == side1 || this.getCubies(33) == side2 || this.getCubies(33) == topSide){
                if(this.getCubies(32) == side1 || this.getCubies(32) == side2 || this.getCubies(32) == topSide)
                    return 8;
            }
        }
        if(this.getCubies(44) == topSide || this.getCubies(42) == side1 || this.getCubies(42) == side2){
            if(this.getCubies(47) == side1 || this.getCubies(47) == side2 || this.getCubies(47) == topSide){
                if(this.getCubies(35) == side1 || this.getCubies(35) == side2 || this.getCubies(35) == topSide)
                    return 22;
            }
        }
        if(this.getCubies(42) == topSide || this.getCubies(42) == side1 || this.getCubies(42) == side2){
            if(this.getCubies(45) == side1 || this.getCubies(45) == side2  || this.getCubies(45) == topSide){
                if(this.getCubies(27) == side1 || this.getCubies(27) == side2 || this.getCubies(27) == topSide)
                    return 17;
            }
        }

        return 100;
    }


    /**
     * Method to count the edge distance from goal state
     * @return int of how far it is from goal state
     */
    private int edgeDistanceToGoal(int side1, int side2){
        int side1Color = this.getCubies(side1);
        int side2Color = this.getCubies(side2);

        int goalLocation = edgeGoalFinder(edgeValue(side1Color, side2Color));

        int edgeLocation = edgeLocation(side1Color, side2Color);

        int distance=0;

        if(goalLocation > edgeLocation)
            distance = goalLocation - edgeLocation;
        else if(goalLocation < edgeLocation)
            distance = edgeLocation - goalLocation;
        else if(goalLocation == edgeLocation)
            distance = 0;

        return distance;
    }


    /**
     * method to find the goal of the specific edge
     * @param side1 side 1 of the edge
     * @return the location of its end goal
     */
    private int edgeGoalFinder(int side1){
        //based on the top side, know where in the goal states to return
        switch(side1){
            case 1:
                return 1;
            case 2:
                return 3;
            case 3:
                return 5;
            case 4:
                return 7;
            case 5:
                return 10;
            case 6:
                return 12;
            case 7:
                return 14;
            case 8:
                return 15;
            case 9:
                return 18;
            case 10:
                return 20;
            case 11:
                return 21;
            case 12:
                return 24;
        }

        return 0;
    }

    /**
     * Method that finds the location of that specific edge cube based on color
     * @param side1 side1 color
     * @param side2 side2 color
     * @return location of that cube in array of cubies
     */
    private int edgeLocation(int side1, int side2){
        if(this.getCubies(13) == side1 || this.getCubies(13) == side2){
            if(this.getCubies(7) == side1 || this.getCubies(7) == side2){
                return 1;
            }
        }
        if(this.getCubies(21) == side1 || this.getCubies(21) == side2){
            if(this.getCubies(20) == side1 || this.getCubies(20) == side2){
                return 3;
            }
        }
        if(this.getCubies(23) == side1 || this.getCubies(23) == side2){
            if(this.getCubies(24) == side1 || this.getCubies(24) == side2){
                return 5;
            }
        }
        if(this.getCubies(31) == side1 || this.getCubies(31) == side2){
            if(this.getCubies(37) == side1 || this.getCubies(37) == side2){
                return 7;
            }
        }
        if(this.getCubies(1) == side1 || this.getCubies(1) == side2){
            if(this.getCubies(52) == side1 || this.getCubies(52) == side2){
                return 10;
            }
        }
        if(this.getCubies(3) == side1 || this.getCubies(3) == side2){
            if(this.getCubies(10) == side1 || this.getCubies(10) == side2){
                return 12;
            }
        }
        if(this.getCubies(5) == side1 || this.getCubies(5) == side2){
            if(this.getCubies(16) == side1 || this.getCubies(16) == side2){
                return 14;
            }
        }
        if(this.getCubies(18) == side1 || this.getCubies(18) == side2){
            if(this.getCubies(48) == side1 || this.getCubies(48) == side2){
                return 15;
            }
        }
        if(this.getCubies(28) == side1 || this.getCubies(28) == side2){
            if(this.getCubies(39) == side1 || this.getCubies(39) == side2){
                return 18;
            }
        }
        if(this.getCubies(26) == side1 || this.getCubies(26) == side2){
            if(this.getCubies(50) == side1 || this.getCubies(50) == side2){
                return 20;
            }
        }
        if(this.getCubies(34) == side1 || this.getCubies(34) == side2){
            if(this.getCubies(41) == side1 || this.getCubies(41) == side2){
                return 21;
            }
        }
        if(this.getCubies(43) == side1 || this.getCubies(43) == side2){
            if(this.getCubies(46) == side1 || this.getCubies(46) == side2){
                return 24;
            }
        }

        return 100;
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

    /************************ Heuristic Tables ********************************/

    /**
     * function to make a goal state cube
     */
    private byte[] goalState(){
        byte[] goal = new byte[54];

        String line;
        int pos = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader("/Users/Dan/Documents/Marist/Semester 7/AI/RubiksCube/src/main/java/rubik/goalstate.txt"));

            //turn file into a serialized version in a byte array
            while((line = reader.readLine()) != null){
                line = line.trim();
                for(int i=0; i < line.length(); i++){
                    String cube = line.substring(i, i+1);

                    if (cube.equals("R")) {
                        goal[pos++] = RED;
                    } else if (cube.equals("G")) {
                        goal[pos++] = GREEN;
                    } else if (cube.equals("Y")) {
                        goal[pos++] = YELLOW;
                    } else if (cube.equals("B")) {
                        goal[pos++] = BLUE;
                    } else if (cube.equals("O")) {
                        goal[pos++] = ORANGE;
                    } else if (cube.equals("W")) {
                        goal[pos++] = WHITE;
                    }
                }

            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        return goal;
    }

    /**
     * Function to make the heuristic tables
     */
    public void heuristicTables(){
        //create three goal state cubes
        RubikCube cube1 = new RubikCube(goalState());
        RubikCube cube2 = new RubikCube(goalState());
        RubikCube cube3 = new RubikCube(goalState());

        //create the three tables
        firstEdgeTable(cube1);
        secondEdgeTable(cube2);
        cornerTable(cube3);
    }

    /**
     * Function to make the first set of edge heuristic tables
     * @param cube copy of a goalstate cube
     */
    private void firstEdgeTable(RubikCube cube){
        //pick 6 edges
        //make every possible move for each branch
        //continue to do so until your node has been repeated
        //save how deep each move is
        //use breadth first search
        //do all 18 possible moves


    }

    /**
     * Function to make the second set of edge heuristic tables
     * @param cube copy of a goalstate cube
     */
    private void secondEdgeTable(RubikCube cube){
        //pick 6 edges
        //make every possible move for each branch
        //continue to do so until your node has been repeated
        //save how deep each move is
        //use breadth first search
        //do all 18 possible moves

        //declerations before search
        byte table[] = new byte[21288960];
        int depth, loc;

        //use queue add new objects to end bredth first search
        LinkedList<RubikCube> searchQueue = new LinkedList();

        RubikCube currCube;
        RubikCube parentCube;
        searchQueue.add(cube);
        depth = 0;

        //operate search
        while(searchQueue.size() > 0){
            //get the current cube and add it to the final array
            parentCube = searchQueue.remove();

            //TODO figure out depth
            loc = getSecondEdgeLoc(parentCube);
            table[loc] = (byte) depth;

            //turn cube every possible way and add them to the queue

            //side 1
            currCube = parentCube;
            currCube.rotateBack(1);
            if(edgeExists(currCube, table, searchQueue))
                searchQueue.add(currCube);

            currCube = parentCube;
            currCube.rotateBack(2);
            if(edgeExists(currCube, table, searchQueue))
                searchQueue.add(currCube);

            currCube = parentCube;
            currCube.rotateBack(3);
            if(edgeExists(currCube, table, searchQueue))
                searchQueue.add(currCube);


            //side 2
            currCube = parentCube;
            currCube.rotateDown(1);
            if(edgeExists(currCube, table, searchQueue))
                searchQueue.add(currCube);

            currCube = parentCube;
            currCube.rotateDown(2);
            if(edgeExists(currCube, table, searchQueue))
                searchQueue.add(currCube);

            currCube = parentCube;
            currCube.rotateDown(3);
            if(edgeExists(currCube, table, searchQueue))
                searchQueue.add(currCube);


            //side 3
            currCube = parentCube;
            currCube.rotateFront(1);
            if(edgeExists(currCube, table, searchQueue))
                searchQueue.add(currCube);

            currCube = parentCube;
            currCube.rotateFront(2);
            if(edgeExists(currCube, table, searchQueue))
                searchQueue.add(currCube);

            currCube = parentCube;
            currCube.rotateFront(3);
            if(edgeExists(currCube, table, searchQueue))
                searchQueue.add(currCube);


            //side 4
            currCube = parentCube;
            currCube.rotateLeft(1);
            if(edgeExists(currCube, table, searchQueue))
                searchQueue.add(currCube);

            currCube = parentCube;
            currCube.rotateLeft(2);
            if(edgeExists(currCube, table, searchQueue))
                searchQueue.add(currCube);

            currCube = parentCube;
            currCube.rotateLeft(3);
            if(edgeExists(currCube, table, searchQueue))
                searchQueue.add(currCube);


            //side 5
            currCube.rotateRight(1);
            if(edgeExists(currCube, table, searchQueue))
                searchQueue.add(currCube);
            currCube.rotateRight(2);
            if(edgeExists(currCube, table, searchQueue))
                searchQueue.add(currCube);
            currCube.rotateRight(3);
            if(edgeExists(currCube, table, searchQueue))
                searchQueue.add(currCube);


            //side 6
            currCube = parentCube;
            currCube.rotateTop(1);
            if(edgeExists(currCube, table, searchQueue))
                searchQueue.add(currCube);

            currCube = parentCube;
            currCube.rotateTop(2);
            if(edgeExists(currCube, table, searchQueue))
                searchQueue.add(currCube);

            currCube = parentCube;
            currCube.rotateTop(3);
            if(edgeExists(currCube, table, searchQueue))
                searchQueue.add(currCube);
        }

        //after search ends make the searchQueue
        writeFile(table, "SecondEdge");
    }

    /**
     * Function to check if an edge exists
     * @param cube the cube your looking for
     * @param table the table of current rubiks cubes
     * @return true if the edge does not exist
     */
    private boolean edgeExists(RubikCube cube, byte[] table, LinkedList<RubikCube> list){
        int loc = getSecondEdgeLoc(cube);
        byte t = table[loc];

        if(t == 0 && table.length > 1){
            return false;
        }

        for(int i=0; i < list.size(); i++){
            if(list.get(i) == cube){
                return false;
            }
        }

        return true;
    }

    /**
     * Function to get the location of it at the table
     * @param cube copy of the current rubiks cube
     * @return location at the table the depth should be stored
     */
    private int getSecondEdgeLoc(RubikCube cube){

        //do search now
        int[] row = makeSecondEdgeRow(cube);

        //function that gets the location of the depth in the table
        return hashEdgeRow(row);
    }

    /**
     * function to make a row off of a specific instance of a cube
     * @param cube a copy of the cube
     * @return the row of the specific edge values
     */
    private int[] makeSecondEdgeRow(RubikCube cube){
        //initialize the variables
        int edgeValue, edgeOrientation, finalValue;
        int row[] = new int[5];

        edgeValue = edgeValueForRow(cube, RED, YELLOW);
        edgeOrientation = singleEdgeParity(RED, YELLOW);
        finalValue = 3 * edgeValue + edgeOrientation + 1;
        row[0] = finalValue;

        edgeValue = edgeValueForRow(cube, RED, BLUE);
        edgeOrientation = singleEdgeParity(RED, BLUE);
        finalValue = 3 * edgeValue + edgeOrientation + 1;
        row[1] = finalValue;

        edgeValue = edgeValueForRow(cube, RED, WHITE);
        edgeOrientation = singleEdgeParity(RED, WHITE);
        finalValue = 3 * edgeValue + edgeOrientation + 1;
        row[2] = finalValue;

        edgeValue = edgeValueForRow(cube, RED, GREEN);
        edgeOrientation = singleEdgeParity(RED, GREEN);
        finalValue = 3 * edgeValue + edgeOrientation + 1;
        row[3] = finalValue;

        edgeValue = edgeValueForRow(cube, YELLOW, GREEN);
        edgeOrientation = singleEdgeParity(YELLOW, GREEN);
        finalValue = 3 * edgeValue + edgeOrientation + 1;
        row[4] = finalValue;

        edgeValue = edgeValueForRow(cube, YELLOW, BLUE);
        edgeOrientation = singleEdgeParity(YELLOW, BLUE);
        finalValue = 3 * edgeValue + edgeOrientation + 1;
        row[5] = finalValue;

        return row;
    }

    /**
     * function to get the edge value for the row
     * @param cube copy of the cube
     * @param color1 the colors were looking for
     * @param color2 the colors were looking for
     * @return the value of where that cube is
     */
    private int edgeValueForRow(RubikCube cube, int color1, int color2){
        int edgeVal = edgeValue(color1, color2);

        if(edgeValue(cube.getCubies(7), cube.getCubies(13)) == edgeVal){
            return 0;
        }else if(edgeValue(cube.getCubies(5), cube.getCubies(16)) == edgeVal){
            return 1;
        }else if(edgeValue(cube.getCubies(1), cube.getCubies(52)) == edgeVal){
            return 2;
        }else if(edgeValue(cube.getCubies(3), cube.getCubies(10)) == edgeVal){
            return 3;
        }else if(edgeValue(cube.getCubies(20), cube.getCubies(23)) == edgeVal){
            return 4;
        }else if(edgeValue(cube.getCubies(23), cube.getCubies(24)) == edgeVal){
            return 5;
        }else if(edgeValue(cube.getCubies(18), cube.getCubies(48)) == edgeVal){
            return 6;
        }else if(edgeValue(cube.getCubies(26), cube.getCubies(50)) == edgeVal){
            return 7;
        }else if(edgeValue(cube.getCubies(31), cube.getCubies(37)) == edgeVal){
            return 8;
        }else if(edgeValue(cube.getCubies(28), cube.getCubies(39)) == edgeVal){
            return 9;
        }else if(edgeValue(cube.getCubies(41), cube.getCubies(34)) == edgeVal){
            return 10;
        }else if(edgeValue(cube.getCubies(43), cube.getCubies(46)) == edgeVal){
            return 11;
        }

        return 100;
    }

    /**
     * function to hash the edge cubes for the heuristic tables
     * @param row the copy of the specific edges in an integer array
     */
    private int hashEdgeRow(int[] row){
        int val = (row[0] - 1) * factorial(5);
        int sum = val;

        decreaseArray(row, 1);
        val = (row[1] - 1) * factorial(4);
        sum += val;

        decreaseArray(row, 2);
        val = (row[2] - 1) * factorial(3);
        sum += val;

        decreaseArray(row, 3);
        val = (row[3] - 1) * factorial(2);
        sum += val;

        decreaseArray(row, 4);
        val = (row[4] - 1) * factorial(1);
        sum += val;

        decreaseArray(row, 5);
        //always is 0
        val = (row[5] - 1) * 0;
        sum += val;

        return sum;
    }

    /**
     * function to decrease every value in array
     * @param row the reference to the array
     * @param index location to start at
     */
    private void decreaseArray(int[] row, int index){
        for(int i=index; i < row.length; i++){
            int val = row[i];
            val--;
            row[i] = val;
        }
    }

    /**
     * Function to make the corner heuristic tables
     * @param cube copy of a goalstate cube
     */
    private void cornerTable(RubikCube cube){

    }

    /**
     * function to write the table to a file
     * @param table byte array of the table
     * @param fileName name of the file to be written
     */
    private void writeFile(byte[] table, String fileName){

        File file = new File(fileName);
        FileOutputStream fos = null;

        try{
            fos = new FileOutputStream(file);
            fos.write(table);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    /************************ Helper Functions ********************************/

    /**
     * Overwrite of the toString method
     * @return string printout of the current rubikCube
     */
    public String toString(){
        return this.deserialize();
    }

    /**
     * function to get the factorial of a number
     * @param num the number to get the factorial of
     * @return the factorial of that number
     */
    private int factorial(int num){
        int fact = 0;

        for(int i=num; i >= 1; i--){
            fact *= i;
        }

        return fact;
    }

    /********************** Turning The Cube *******************************/

    /**
     * Method to rotate the left side of a cube left
     * @param turns number of turns to make
     */
    public void rotateLeft(int turns){
        for(int i=0; i < turns; i++){
            //values of current cubes
            byte spot1 = this.getCubies(12);
            byte spot2 = this.getCubies(21);
            byte spot3 = this.getCubies(30);
            byte spot4 = this.getCubies(0);
            byte spot5 = this.getCubies(3);
            byte spot6 = this.getCubies(6);
            byte spot7 = this.getCubies(45);
            byte spot8 = this.getCubies(48);
            byte spot9 = this.getCubies(51);
            byte spot10 = this.getCubies(36);
            byte spot11 = this.getCubies(39);
            byte spot12 = this.getCubies(42);

            //places to put those values
            this.setCube(0, spot1);
            this.setCube(3, spot2);
            this.setCube(6, spot3);
            this.setCube(45, spot4);
            this.setCube(48, spot5);
            this.setCube(51, spot6);
            this.setCube(36, spot7);
            this.setCube(39, spot8);
            this.setCube(42, spot9);
            this.setCube(12, spot10);
            this.setCube(21, spot11);
            this.setCube(30, spot12);
        }
    }

    /**
     * Method to rotate the right side of a this left
     * @param turns number of turns to make
     */
    private void rotateRight(int turns){
        for(int i=0; i < turns; i++){
            //values of the current cubes
            byte spot1 = this.getCubies(2);
            byte spot2 = this.getCubies(5);
            byte spot3 = this.getCubies(8);
            byte spot4 = this.getCubies(47);
            byte spot5 = this.getCubies(50);
            byte spot6 = this.getCubies(53);
            byte spot7 = this.getCubies(14);
            byte spot8 = this.getCubies(23);
            byte spot9 = this.getCubies(32);
            byte spot10 = this.getCubies(38);
            byte spot11 = this.getCubies(41);
            byte spot12 = this.getCubies(44);

            //places to put those values
            this.setCube(47, spot1);
            this.setCube(50, spot2);
            this.setCube(53, spot3);
            this.setCube(38, spot4);
            this.setCube(41, spot5);
            this.setCube(44, spot6);
            this.setCube(2, spot7);
            this.setCube(5, spot8);
            this.setCube(8, spot9);
            this.setCube(14, spot10);
            this.setCube(23, spot11);
            this.setCube(32, spot12);
        }
    }

    /**
     * Method to rotate the top of a this right
     * @param turns number of turns to make
     */
    public void rotateTop(int turns){
        for(int i=0; i < turns; i++){
            //values of the current cubes
            byte spot1 = this.getCubies(9);
            byte spot2 = this.getCubies(10);
            byte spot3 = this.getCubies(11);
            byte spot4 = this.getCubies(12);
            byte spot5 = this.getCubies(13);
            byte spot6 = this.getCubies(14);
            byte spot7 = this.getCubies(15);
            byte spot8 = this.getCubies(16);
            byte spot9 = this.getCubies(17);
            byte spot10 = this.getCubies(45);
            byte spot11 = this.getCubies(46);
            byte spot12 = this.getCubies(47);

            //places to put those values
            this.setCube(12, spot1);
            this.setCube(13, spot2);
            this.setCube(14, spot3);
            this.setCube(15, spot4);
            this.setCube(16, spot5);
            this.setCube(17, spot6);
            this.setCube(45, spot7);
            this.setCube(46, spot8);
            this.setCube(47, spot9);
            this.setCube(9, spot10);
            this.setCube(10, spot11);
            this.setCube(11, spot12);
        }
    }

    /**
     * Method to rotate the down of a cube right
     * @param turns number of turns to make
     */
    public void rotateDown(int turns){
        for(int i=0; i < turns; i++){
            //values of the current cubes
            byte spot1 = this.getCubies(27);
            byte spot2 = this.getCubies(28);
            byte spot3 = this.getCubies(29);
            byte spot4 = this.getCubies(30);
            byte spot5 = this.getCubies(31);
            byte spot6 = this.getCubies(32);
            byte spot7 = this.getCubies(33);
            byte spot8 = this.getCubies(34);
            byte spot9 = this.getCubies(35);
            byte spot10 = this.getCubies(51);
            byte spot11 = this.getCubies(52);
            byte spot12 = this.getCubies(53);

            //places to put those values
            this.setCube(30, spot1);
            this.setCube(31, spot2);
            this.setCube(32, spot3);
            this.setCube(33, spot4);
            this.setCube(34, spot5);
            this.setCube(35, spot6);
            this.setCube(51, spot7);
            this.setCube(52, spot8);
            this.setCube(53, spot9);
            this.setCube(27, spot10);
            this.setCube(28, spot11);
            this.setCube(29, spot12);
        }
    }

    /**
     * Method to rotate a cube front
     * @param turns number of turns to make
     */
    public void rotateFront(int turns){
        for(int i=0; i < turns; i++){
            //values of the current cubes
            byte spot1 = this.getCubies(6);
            byte spot2 = this.getCubies(7);
            byte spot3 = this.getCubies(8);
            byte spot4 = this.getCubies(11);
            byte spot5 = this.getCubies(20);
            byte spot6 = this.getCubies(29);
            byte spot7 = this.getCubies(36);
            byte spot8 = this.getCubies(37);
            byte spot9 = this.getCubies(38);
            byte spot10 = this.getCubies(15);
            byte spot11 = this.getCubies(24);
            byte spot12 = this.getCubies(33);

            //places to put those values
            this.setCube(15, spot1);
            this.setCube(24, spot2);
            this.setCube(33, spot3);
            this.setCube(6, spot4);
            this.setCube(7, spot5);
            this.setCube(8, spot6);
            this.setCube(11, spot7);
            this.setCube(20, spot8);
            this.setCube(29, spot9);
            this.setCube(38, spot10);
            this.setCube(37, spot11);
            this.setCube(36, spot12);
        }
    }

    /**
     * Method to rotate a this back
     * @param turns number of turns to make
     */
    public void rotateBack(int turns){
        for(int i=0; i < turns; i++){
            //values of the current cubes
            byte spot1 = this.getCubies(0);
            byte spot2 = this.getCubies(1);
            byte spot3 = this.getCubies(2);
            byte spot4 = this.getCubies(9);
            byte spot5 = this.getCubies(18);
            byte spot6 = this.getCubies(27);
            byte spot7 = this.getCubies(17);
            byte spot8 = this.getCubies(26);
            byte spot9 = this.getCubies(35);
            byte spot10 = this.getCubies(42);
            byte spot11 = this.getCubies(43);
            byte spot12 = this.getCubies(44);

            //places to put those values
            this.setCube(17, spot1);
            this.setCube(26, spot2);
            this.setCube(35, spot3);
            this.setCube(0, spot4);
            this.setCube(1, spot5);
            this.setCube(2, spot6);
            this.setCube(44, spot7);
            this.setCube(43, spot8);
            this.setCube(42, spot9);
            this.setCube(9, spot10);
            this.setCube(18, spot11);
            this.setCube(27, spot12);
        }
    }

}
