import org.junit.*;
import rubik.Solvable;
import java.io.FileNotFoundException;

public class ValidTest {

    public static final String path = "/Users/Dan/Documents/Marist/Semester 7/AI/RubiksCube/src/main/resources/";
    public static final String valid = "/valid/";
    public static final String invalid = "/invalid/";

    @Test
    public void validTest1() throws FileNotFoundException {
        try{
            String filePath = path + valid + "valid1.txt";
            Solvable s1 = new Solvable(filePath);
            Assert.assertTrue(s1.isValid());
        }catch(Exception e){
            System.out.println("Failed valid test 1");
            Assert.fail();
        }
    }

    @Test
    public void validTest2() throws FileNotFoundException {
        try{
            String filePath = path + valid + "valid2.txt";
            Solvable s1 = new Solvable(filePath);
            Assert.assertTrue(s1.isValid());
        }catch(Exception e){
            System.out.println("Failed valid test 1");
            Assert.fail();
        }
    }

    @Test
    public void validTest3() throws FileNotFoundException {
        try{
            String filePath = path + valid + "valid3.txt";
            Solvable s1 = new Solvable(filePath);
            Assert.assertTrue(s1.isValid());
        }catch(Exception e){
            System.out.println("Failed valid test 1");
            Assert.fail();
        }
    }

    @Test
    public void validTest4() throws FileNotFoundException {
        try{
            String filePath = path + valid + "valid4.txt";
            Solvable s1 = new Solvable(filePath);
            Assert.assertTrue(s1.isValid());
        }catch(Exception e){
            System.out.println("Failed valid test 1");
            Assert.fail();
        }
    }

    @Test
    public void validTest5() throws FileNotFoundException {
        try{
            String filePath = path + valid + "valid5.txt";
            Solvable s1 = new Solvable(filePath);
            Assert.assertTrue(s1.isValid());
        }catch(Exception e){
            System.out.println("Failed valid test 1");
            Assert.fail();
        }
    }

    @Test
    public void invalidTest1() throws FileNotFoundException {
        try{
            String filePath = path + invalid + "invalid1.txt";
            Solvable s1 = new Solvable(filePath);
            Assert.assertFalse(s1.isValid());
        }catch(Exception e){
            System.out.println("Failed invalid test 1");
            Assert.fail();
        }
    }

    @Test
    public void invalidTest2() throws FileNotFoundException {
        try{
            String filePath = path + invalid + "invalid2.txt";
            Solvable s1 = new Solvable(filePath);
            Assert.assertFalse(s1.isValid());
        }catch(Exception e){
            System.out.println("Failed invalid test 1");
            Assert.fail();
        }
    }

    @Test
    public void invalidTest3() throws FileNotFoundException {
        try{
            String filePath = path + invalid + "invalid3.txt";
            Solvable s1 = new Solvable(filePath);
            Assert.assertFalse(s1.isValid());
        }catch(Exception e){
            System.out.println("Failed invalid test 1");
            Assert.fail();
        }
    }

    @Test
    public void invalidTest4() throws FileNotFoundException {
        try{
            String filePath = path + invalid + "invalid4.txt";
            Solvable s1 = new Solvable(filePath);
            Assert.assertFalse(s1.isValid());
        }catch(Exception e){
            System.out.println("Failed invalid test 1");
            Assert.fail();
        }
    }

    @Test
    public void invalidTest5() throws FileNotFoundException {
        try{
            String filePath = path + invalid + "invalid5.txt";
            Solvable s1 = new Solvable(filePath);
            Assert.assertFalse(s1.isValid());
        }catch(Exception e){
            System.out.println("Failed invalid test 1");
            Assert.fail();
        }
    }


}