
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import debuggable.DebugStream;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Jack Ullery
 */
public class SimpleTest {

    private PrintStream dummy_output;
    private ByteArrayOutputStream input;

    private String data_string;
    private int data_int;

    @Before
    public void setUp() {
        input = new ByteArrayOutputStream();
        dummy_output = DebugStream.wrapPrintStream(new PrintStream(input));
        data_string = "This is some data to test with!";
        data_int = -37;
    }

    @After
    public void tearDown() throws IOException {
        dummy_output.close();
        input.close();
    }

    @Test
    public void empty() {
        assertEquals(0, input.size());
    }

    @Test
    public void non_empty() {
        dummy_output.println();
        assertNotEquals(0, input.size());
    }

    public void checkPreamble(String ret) {
        assertTrue(ret.contains("[DEBUG]"));
        assertTrue(ret.contains("SimpleTest"));
    }

    @Test
    public void check_println_empty() {
        dummy_output.println();
        String ret = input.toString();
        checkPreamble(ret);
        assertTrue(ret.contains("\n"));
    }

    @Test
    public void check_println() {
        dummy_output.println(data_string);
        String ret = input.toString();
        checkPreamble(ret);
        assertTrue(ret.contains(data_string));
        assertTrue(ret.contains("\n"));
    }

    @Test
    public void check_print() {
        dummy_output.print(data_string);
        String ret = input.toString();
        checkPreamble(ret);
        assertTrue(ret.contains(data_string));
        assertFalse(ret.contains("\n"));
    }

    @Test
    public void check_printf() {
        dummy_output.print(data_string);
        String ret = input.toString();
        checkPreamble(ret);
        assertTrue(ret.contains(data_string));
        assertFalse(ret.contains("\n"));
    }

    @Test
    public void check_println_int() {
        dummy_output.println(data_int);
        String ret = input.toString();
        checkPreamble(ret);
        assertTrue(ret.contains(Integer.toString(data_int)));
        assertTrue(ret.contains("\n"));
    }

    @Test
    public void check_print_int() {
        dummy_output.print(data_int);
        String ret = input.toString();
        checkPreamble(ret);
        assertTrue(ret.contains(Integer.toString(data_int)));
        assertFalse(ret.contains("\n"));
    }

    @Test
    public void check_printf_int() {
        dummy_output.print(data_int);
        String ret = input.toString();
        checkPreamble(ret);
        assertTrue(ret.contains(Integer.toString(data_int)));
        assertFalse(ret.contains("\n"));
    }
}
