package debuggable;

import java.io.PrintStream;

/**
 *
 * @author Jack Ullery
 */
public class DebugStreamExample {

    public static void main(String[] args) {
        PrintStream debug = DebugStream.ON;
        debug.println("(1) This is the first line");
        debug.flush();

        debug = DebugStream.ERROR;
        debug.println("(2) This is the second line");
        debug.flush();

        debug = DebugStream.OFF;
        debug.println("(3) This line shouldn't show");
        debug.flush();

        debug = DebugStream.ON;
        debug.println("(4) This is the last line");
        debug.flush();
    }
}
