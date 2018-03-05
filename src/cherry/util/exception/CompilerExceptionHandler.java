/*
 * The MIT License
 *
 * Copyright 2018 SoraKatadzuma.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package cherry.util.exception;

import cherry.frontend.grammar.Token;
import java.util.LinkedList;
import java.util.List;
import javafx.util.Pair;

// TODO: Comment methods and variables ig
/**
 * Class for any compile errors
 * 
 * @author PeriBooty
 * @version 0.0.0.1
 */
public class CompilerExceptionHandler {
    private final List<Pair<Throwable, ExceptionLevel>> exceptions = new LinkedList<>();
    private final ExceptionLevel minLevel;
    
    public static enum ExceptionLevel {
        LOW,
        WARN,
        SEVERE
    }
    
    /**
     * Constructor 
     * 
     * @param level The minimum exception level
     */
    public CompilerExceptionHandler(ExceptionLevel level) {
        minLevel = level;
    }
    
    /**
     * Constructor
     * @see #CompilerExceptionHandler(cherry.util.exception.CompilerExceptionHandler.ExceptionLevel)
     */
    public CompilerExceptionHandler() {
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
        //System.out.println("\nExceptions under the " + CallingClass.getName() + " class:"); || You might wanna like, uncomment this line if you have something in mind
        exceptions.stream().filter((exception) -> (exception.getValue().ordinal() >= level.ordinal())).map((exception) -> String.format("[%s] %s", exception.getValue().name(), exception.getKey().getMessage())).forEachOrdered((output) -> {
            System.out.println(output);
        });
        System.out.println(); // To seperate the messages
    }
}
