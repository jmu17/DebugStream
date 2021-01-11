
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

    public static int countSubstrings(String big, String little) {
        int count = 0;
        int indexOf = 0;
        do {
            indexOf = big.indexOf(little, indexOf);
            if(indexOf != -1){
                count++;
            }
            indexOf++;
        } while (indexOf != 0);
        return count;
    }

    public void checkPreamble(String ret) {
        assertEquals(1, countSubstrings(ret, "DEBUG"));
        assertEquals(1, countSubstrings(ret, "SimpleTest"));
    }

    @Test
    public void check_println_empty() {
        dummy_output.println();
        String ret = input.toString();
        checkPreamble(ret);
        assertEquals(1, countSubstrings(ret, "\n"));
    }

    @Test
    public void check_println() {
        dummy_output.println(data_string);
        String ret = input.toString();
        checkPreamble(ret);
        assertEquals(1, countSubstrings(ret, data_string));
        assertEquals(1, countSubstrings(ret, "\n"));
    }

    @Test
    public void check_print() {
        dummy_output.print(data_string);
        String ret = input.toString();
        checkPreamble(ret);
        assertEquals(1, countSubstrings(ret, data_string));
        assertNotEquals(1, countSubstrings(ret, "\n"));
    }

    @Test
    public void check_printf() {
        dummy_output.print(data_string);
        String ret = input.toString();
        checkPreamble(ret);
        assertEquals(1, countSubstrings(ret, data_string));
        assertNotEquals(1, countSubstrings(ret, "\n"));
    }

    @Test
    public void check_println_int() {
        dummy_output.println(data_int);
        String ret = input.toString();
        checkPreamble(ret);
        assertEquals(1, countSubstrings(ret, Integer.toString(data_int)));
        assertEquals(1, countSubstrings(ret, "\n"));
    }

    @Test
    public void check_print_int() {
        dummy_output.print(data_int);
        String ret = input.toString();
        checkPreamble(ret);
        assertEquals(1, countSubstrings(ret, Integer.toString(data_int)));
        assertNotEquals(1, countSubstrings(ret, "\n"));
    }

    @Test
    public void check_printf_int() {
        dummy_output.print(data_int);
        String ret = input.toString();
        checkPreamble(ret);
        assertEquals(1, countSubstrings(ret, Integer.toString(data_int)));
        assertNotEquals(1, countSubstrings(ret, "\n"));
    }
}
