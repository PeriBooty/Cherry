package cherry.util.exception;

import cherry.frontend.grammar.Token;
import java.util.LinkedList;
import java.util.List;
import javafx.util.Pair;

/**
 * Class for any compile errors
 * 
 * @author PeriBooty
 * @version 0.0.0.1
 * @param <T> CallingClass
 */
public class CompilerExceptionHandler<T> {
    private final List<Pair<Throwable, ExceptionLevel>> exceptions = new LinkedList<>();
    private final Class CallingClass;
    private final ExceptionLevel minLevel;
    
    public static enum ExceptionLevel {
        LOW,
        WARN,
        SEVERE
    }
    
    /**
     * Constructor 
     * 
     * @param t The calling class instance
     * @param level The minimum exception level
     */
    public CompilerExceptionHandler(Class t, ExceptionLevel level) {
        CallingClass = t;
        minLevel = level;
    }
    
    /**
     * Constructor
     * @see #CompilerExceptionHandler(java.lang.Object, cherry.util.exception.CompilerExceptionHandler.ExceptionLevel)
     * @param t The calling class instance
     */
    public CompilerExceptionHandler(Class t) {
       CallingClass = t;
       minLevel = ExceptionLevel.WARN;
    }
    
    /**
     * Adds an exception to the list of exceptions, handling if necessary
     * 
     * @param exception The exception to handle
     * @param severity The level of the exception
     * @throws Exception if the {@link ExceptionLevel} is {@link ExceptionLevel#SEVERE}
     */
    public void addException(Throwable exception, ExceptionLevel severity) throws Exception {
        if(severity == ExceptionLevel.SEVERE) {
            report();
            throw new Exception("Exception came in as SEVERE", exception);
        } else {
            exceptions.add(new Pair(exception, severity));
        }
    }
    
    public void addException(String message, ExceptionLevel severity) throws Exception {
        addException(new Throwable(message), severity);
    }
    
    public void addException(String message, int line, int column, ExceptionLevel severity) throws Exception {
        addException("(" + line + ":" + column + ") " + message, severity);
    }
    
    public void addException(String message, Token token, ExceptionLevel severity) throws Exception {
        addException(message + "\n\tat " + token.value + "(" + token.filename + ":" + token.line + ")", severity);
    }
    
    /**
     * @see #report(cherry.util.exception.CompilerExceptionHandler.ExceptionLevel) 
     */
    public void report() {
        report(minLevel);
    }
    
    /**
     * Prints all stored exceptions to the console if they are greater or equal to the {@link ExceptionLevel}
     * 
     * @param level The minimum level to print.
     */
    public void report(ExceptionLevel level) {
        System.out.println("\nExceptions under the " + CallingClass.getName() + " class:");
        exceptions.stream().filter((exception) -> (exception.getValue().ordinal() >= level.ordinal())).map((exception) -> String.format("[%s:%s] %s", CallingClass.getName(), exception.getValue().name(), exception.getKey().getMessage())).forEachOrdered((output) -> {
            System.out.println(output);
        });
        System.out.println(); // To seperate the messages
    }
}
