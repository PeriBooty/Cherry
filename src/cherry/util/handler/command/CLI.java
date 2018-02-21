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
package cherry.util.handler.command;
 
import cherry.Cherry;
import cherry.util.handler.file.FileHandler;
import cherry.util.handler.flag.FlagHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The {@code CLI} class is responsible for handling and beginning the command
 * line operations, such as flag and file checks and sending files off to be
 * parsed by their own respective parsers.
 *
 * @author SoraKatadzuma
 * @version 0.0.0.1
 */
public final class CLI {
    /**
     * This method is responsible for handling all command line operations, these
     * operations are mainly: collect flags and files, have them checked by their
     * respective handlers, and then finally sending the files off to be parsed.
     * 
     * @param args The command line arguments.
     */
    public static void handleCLOPS(String... args) {
        // For storing important command line objects.
        final List<String> files = new ArrayList<>();
        final List<String> flags = new ArrayList<>();
        
        // Loop through args and place them in appropriate containers.
        for (String arg : args)
            if (arg.charAt(0) == '-')
                flags.add(arg);
            else
                files.add(arg);
        
        // Send files and flags off to be registered.
        Thread fileThread = new Thread(() -> {
            try {
                FileHandler fileHandler = new FileHandler(files.toArray(new String[0]));
            } catch (Exception ex) {
                Logger.getLogger(Cherry.class.getName()).log(Level.WARNING, ex.getMessage());
            }
        });
        
        Thread flagThread = new Thread(() -> {
            FlagHandler flagHandler = new FlagHandler(flags.toArray(new String[0]));
        });
        
        // Starting the threads
        fileThread.start();
        flagThread.start();
        
        // Wait for threads to finish
        try {
            fileThread.join();
            flagThread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Cherry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
