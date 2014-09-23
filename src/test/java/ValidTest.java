import org.junit.*;
import rubik.Solvable;

import java.io.File;
import java.io.FileNotFoundException;

public class ValidTest {

    public static final String path = "/Users/Dan/Documents/Marist/Semester 7/AI/RubiksCube/src/main/resources/";
    public static final String valid = "/valid/";

    @Test
    public void validTest1() throws FileNotFoundException {
        String filePath = path + valid + "valid1.txt";
        Solvable s1 = new Solvable(filePath);
        Assert.assertTrue(s1.isValid());
    }

    @Test
    public void validTest2() throws FileNotFoundException {
        String filePath = path + valid + "valid2.txt";
        Solvable s1 = new Solvable(filePath);
        Assert.assertTrue(s1.isValid());
    }

    @Test
    public void validTest3() throws FileNotFoundException {
        String filePath = path + valid + "valid3.txt";
        Solvable s1 = new Solvable(filePath);
        Assert.assertTrue(s1.isValid());
    }

    @Test
    public void validTest4() throws FileNotFoundException {
        String filePath = path + valid + "valid4.txt";
        Solvable s1 = new Solvable(filePath);
        Assert.assertTrue(s1.isValid());
    }

    @Test
    public void validTest5() throws FileNotFoundException {
        String filePath = path + valid + "valid5.txt";
        Solvable s1 = new Solvable(filePath);
        Assert.assertTrue(s1.isValid());
    }

}