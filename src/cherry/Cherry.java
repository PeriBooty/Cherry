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
package cherry;

import cherry.frontend.parser.Parser;
import cherry.util.handler.command.CLI;
import cherry.util.handler.file.FileHandler;
import cherry.util.object.ParseTree;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is strictly responsible for starting the compiler, and starting
 * any off shoot processes that return all the way to the main thread. These
 * processes include: The {@code CLI#handleCLOPS(String...)}, calling the threader
 * to invoke all necessary threads to execute the given tasks,
 * {@code SemanticAnalyzer#analyze(List<Future<ParseTree>>))}, and
 * {@code CodeGenerator#generate(ParseTree...)}. All other processes are called
 * by internal means in order to keep the main function rather clean, also, it
 * makes it easy to follow the flow of control.
 *
 * @author SoraKatadzuma
 * @version 0.0.0.1
 */
public final class Cherry {
    /** Powerhouse of this compiler. */
    private static final ExecutorService THREADER = Executors.newCachedThreadPool(Executors.defaultThreadFactory());
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CLI.handleCLOPS(args);
        
        ////////////////// Parser calls ///////////////////////
        // After file registry, send registered files off to be parsed.
        Collection<Callable<ParseTree>> tasks = new ArrayList<>();
        
        for (File file : FileHandler.registeredFiles()) {
            tasks.add(new Parser(file));
        }
        
        // A place to store our results.
        Collection<Future<ParseTree>> results;
        
        // Sending each task into it's own thread to be ran.
        try {
            // Invoke all threads
            results = THREADER.invokeAll(tasks);
            // Call shutdown directly after
            THREADER.shutdown();
            // Wait for all threads to finish.
            THREADER.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(Cherry.class.getName()).log(Level.WARNING, "Thread interruption", ex);
        }
        ////////////////// End Parser calls ///////////////////
        
        // continue code here.
    }
}
