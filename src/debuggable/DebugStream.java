package debuggable;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 *
 * @author Jack Ullery
 */
public class DebugStream extends OutputStream {

    /**
     * These are wrapped versions of common PrintStream variables. They can be
     * easily imported, and used.
     */
    public final static PrintStream ERROR = wrapPrintStream(System.err);
    public final static PrintStream ON = wrapPrintStream(System.out);
    public final static PrintStream OFF = wrapPrintStream(null);

    /* 
     The number on the stack where the calling class should be located.
     This is used to find the name of the class that called a particular method.
     This constant is used for the debug_message() method. 
    
     This constant is a rather hacky solution. 
     It has only been tested with certain values of println() and printf()
     */
    private static final int STACK_LEVEL = 10;

    private final PrintStream output;
    private final Boolean shouldPrint;

    private DebugStream(PrintStream output, boolean val) {
        this.output = output;
        shouldPrint = val;
    }

    /**
     * Returns a custom PrintStream object that wraps the input parameter.The
     * returned object can be used like a normal PrintStream variable. When
     * written to, it prepends a message noting the class the wrote the message.
     * Furthermore, a null PrintStream parameter creates a dummy PrintStream,
     * that writes nothing.
     *
     * @param stream The PrintStream to wrap with debugging data
     * @return A new PrintStream that outputs debugging data whenever written to
     */
    public static PrintStream wrapPrintStream(PrintStream stream) {
        if (stream != null) {
            return new PrintStream(new DebugStream(stream, true), true);
        }

        return new PrintStream(new DebugStream(null, false));
    }

    @Override
    public void write(byte[] b, int off, int len) {
        if (shouldPrint) {
            output.print(debug_message());
            output.write(b, off, len);
        }
    }

    @Override
    public void write(byte[] b) throws IOException {
        if (shouldPrint) {
            output.print(debug_message());
            output.write(b);
        }
    }

    @Override
    public void write(int b) {
        if (shouldPrint) {
            output.print(debug_message());
            output.write(b);
        }
    }

    private String debug_message() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        String caller;

        if (trace.length > STACK_LEVEL) {
            caller = trace[STACK_LEVEL].getClassName();
        } else {
            caller = "Unknown Caller";
        }
        return String.format("[DEBUG] %-40.40s:", caller);
    }
}
