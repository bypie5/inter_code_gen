package inter_code_gen;

import org.junit.Test;
import syntax_checker.Typecheck;

import java.io.*;

import static org.junit.Assert.*;

public class J2VTest {

    void passFileToMain(String name) throws IOException {
        String[] args = null;
        final InputStream original = System.in;
        try {
            final FileInputStream fips = new FileInputStream(new File("src/test/resources/part2_input/" + name));
            System.setIn(fips);
            J2V.generateCode();
            fips.close();
        } finally {
            System.setIn(original);
        }
    }

    String testFile(String name) throws IOException {
        // Setup
        final PrintStream originalOut = System.out;
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        passFileToMain(name);

        // Clean up
        System.setOut(originalOut);
        myOut.close();

        return myOut.toString();
    }

    /*@Test
    public void typeErrorTest() throws IOException {
        final PrintStream originalOut = System.out;
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        String[] args = null;
        final InputStream original = System.in;
        try {
            final FileInputStream fips = new FileInputStream(new File("src/test/resources/input_files/ComplexParams-error.java"));
            System.setIn(fips);
            J2V.generateCode();
            fips.close();
        } finally {
            System.setIn(original);
        }

        // Clean up
        System.setOut(originalOut);
        myOut.close();

        assertEquals("Type error\n", myOut.toString());
    }*/

    @Test
    public void addTest() throws IOException {
       assertEquals("", testFile("Add.java"));
    }

    @Test
    public void binaryTree() throws IOException {
        assertEquals("", testFile("BinaryTree.java"));
    }

    @Test
    public void moreThan4() throws IOException {
        assertEquals("", testFile("MoreThan4.java"));
    }

    @Test
    public void treeVisitor() throws IOException {
        assertEquals("", testFile("TreeVisitor.java"));
    }

}
