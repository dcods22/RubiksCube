import org.junit.Assert;
import org.junit.Test;
import rubik.Solvable;

import java.io.FileNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * User: Dan
 * Date: 9/22/14
 * Time: 4:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class InvalidTest {

    public static final String path = "/Users/Dan/Documents/Marist/Semester 7/AI/RubiksCube/src/main/resources/";
    public static final String invalid = "/invalid/";

    @Test
    public void invalidTest1() throws FileNotFoundException {
        String filePath = path + invalid + "invalid1.txt";
        Solvable s1 = new Solvable(filePath);
        Assert.assertFalse(s1.isValid());
    }

    @Test
    public void invalidTest2() throws FileNotFoundException {
        String filePath = path + invalid + "invalid2.txt";
        Solvable s1 = new Solvable(filePath);
        Assert.assertFalse(s1.isValid());
    }

    @Test
    public void invalidTest3() throws FileNotFoundException {
        String filePath = path + invalid + "invalid3.txt";
        Solvable s1 = new Solvable(filePath);
        Assert.assertFalse(s1.isValid());
    }

    @Test
    public void invalidTest4() throws FileNotFoundException {
        String filePath = path + invalid + "invalid4.txt";
        Solvable s1 = new Solvable(filePath);
        Assert.assertFalse(s1.isValid());
    }

    @Test
    public void invalidTest5() throws FileNotFoundException {
        String filePath = path + invalid + "test8.txt";
        Solvable s1 = new Solvable(filePath);
        Assert.assertFalse(s1.isValid());
    }
}
