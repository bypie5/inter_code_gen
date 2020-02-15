package inter_code_gen;

import org.junit.Test;

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

    @Test
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
    }

    @Test
    public void addTest() throws IOException {
        // PASSES
       assertEquals("", testFile("Add.java"));
    }

    @Test
    public void binaryTree() throws IOException {
        // PASSES
        assertEquals("", testFile("BinaryTree.java"));
    }

    @Test
    public void bubbleSort() throws IOException {
        // PASSES
        assertEquals("", testFile("BubbleSort.java"));
    }

    @Test
    public void callTest() throws IOException {
        // PASSES
        assertEquals("", testFile("Call.java"));
    }

    @Test
    public void factorialTest() throws IOException {
        // PASSES
        assertEquals("", testFile("Factorial.java"));
    }

    @Test
    public void linearSearchTest() throws IOException {
        // PASSES
        assertEquals("", testFile("LinearSearch.java"));
    }

    @Test
    public void linkedListTest() throws IOException {
        assertEquals("", testFile("LinkedList.java"));
    }

    @Test
    public void moreThan4() throws IOException {
        // PASSES
        assertEquals("", testFile("MoreThan4.java"));
    }

    @Test
    public void outOfBounds() throws IOException {
        // PASSES
        assertEquals("", testFile("OutOfBounds.error"));
    }

    @Test
    public void printLiteral() throws IOException {
        // PASSES
        assertEquals("", testFile("PrintLiteral.java"));
    }

    @Test
    public void quickSort() throws IOException {
        // PASSES
        assertEquals("", testFile("QuickSort.java"));
    }

    @Test
    public void treeVisitor() throws IOException {
        assertEquals("", testFile("TreeVisitor.java"));
    }

    @Test
    public void varsTest() throws IOException {
        // PASSES
        assertEquals("", testFile("Vars.java"));
    }

    @Test
    public void paramAccess() throws IOException {
        // PASSES
        assertEquals("a", testFile("ParamAccess.java"));
    }

    @Test
    public void functionCallTest() throws IOException {
        // PASSE
        assertEquals("a", testFile("FunctionCall.java"));
    }

}
