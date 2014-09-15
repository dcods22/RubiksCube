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
    private static final int RED = 0;
    private static final int GREEN = 1;
    private static final int YELLOW = 2;
    private static final int BLUE= 3;
    private static final int ORANGE = 4;
    private static final int WHITE = 5;

    public Solvable(){
        rubikCube = new byte[54];
    }

    public static void main(String args[]){
        try{

            Solvable solv = new Solvable();

            solv.serialize(args[0]);

            System.out.println(solv.isValid(rubikCube));

        }catch(Exception e){
            e.printStackTrace();
        }

    }


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

    public boolean isValid(byte[] cube){
        return (count(cube) && middles(cube) && korf(cube));
    }

    public boolean count(byte[] cube){

        int rCount=0, gCount=0, yCount=0, bCount=0, oCount=0, wCount=0;

        if(cube.length < 54){
            return false;
        }

        for (byte aCube : cube) {
            switch (aCube) {
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

    public boolean middles(byte[] cube){
        return ((cube[4] == 0) && (cube[19] == 1) && (cube[22] == 2) && (cube[25] == 3) && (cube[40] == 4) && (cube[49] == 5));
    }

    public boolean korf(byte[] cube){
        return true;
    }

    public String desearilize(byte[] cube){
        String rubikString = "   ";

        for(int i=0; i < 9; i++){
            rubikString += cube[i];
            if(i % 3 == 2 && i < 8){
                rubikString += "\n   ";
            }else if(i % 3 == 2){
                rubikString += "\n";
            }
        }
        for(int i = 9; i < 36; i++){
            rubikString += cube[i];
            if(i % 9 == 8){
                rubikString += "\n";
            }
        }

        rubikString += "   ";

        for(int i = 36; i < 54; i++){
            rubikString += cube[i];
            if(i % 3 == 2){
                rubikString += "\n   ";
            }
        }

        return rubikString;
    }

    public String toString(){
        return desearilize(rubikCube);
    }



}
