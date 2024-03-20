package Logger;

public class Logger {
    private static final String INDENT = "|    "; // Indentation string
    private static ThreadLocal<Integer> callDepth = ThreadLocal.withInitial(() -> 0);

    // Logs the entry of a method with indentation based on call depth
    public static void logEntry(String className, String methodName) {
        indent();
        System.out.println(">" + "[ " + className.toLowerCase() + ": " + className + "]." + methodName + "()");
        callDepth.set(callDepth.get() + 1);
    }

    // Logs the exit of a method with indentation based on call depth
    public static void logExit(String className, String methodName) {
        callDepth.set(callDepth.get() - 1);
        indent();
        System.out.println("<");
    }

    // Helper method to print the correct indentation
    private static void indent() {
        // Skip the first level of indentation for cleaner log start
        for (int i = 1; i < callDepth.get(); i++) {
            System.out.print(INDENT);
        }
    }
}
