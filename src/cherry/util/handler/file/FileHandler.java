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
package cherry.util.handler.file;

import cherry.util.exception.InvalidExtensionException;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.LinkOption;

/**
 * The {@code FileHandler} is responsible for checking each file the correct
 * file extension as well as whether the file exists.
 * 
 * @author SoraKatadzuma
 * @version alpha 0.0.0.1
 */
public final class FileHandler {
    /**
     * The {@link Java.io.File} versions of the given file paths.
     * 
     * @see Java.io.File
     */
    private static File[] registeredFiles;

    /**
     * Constructs a new {@code FileHandler} with an array of file paths.
     * 
     * @param files The paths to the files that must be registered.
     * @throws java.lang.NullPointerException
     * @throws java.io.FileNotFoundException
     */
    public FileHandler(String... files) throws Exception {
        if (files.length == 0)
            throw new NullPointerException("Compiler must contain one file to parse.");
        
        registeredFiles = new File[files.length];
        register(files);
    }
    
    /**
     * @param files The paths to register into {@code java.io.File}(s).
     * @throws java.io.FileNotFoundException
     */
    private static void register(String... files) throws Exception {
        int i = 0;
        
        // For each path in files, add 1 new java.io.File to registeredFiles 
        for (String path : files) {
            // Check path extension before continuing.
            checkExtension(path);
            // Will automatically throw an error if file path does not exist.
            registeredFiles[i++] = checkExists(new File(path));
        }
    }
    
    /**
     * @param path The path of the file to be registered / being checked.
     */
    private static void checkExtension(String path) throws Exception {
        String extension = "";

        int i = path.lastIndexOf('.');
        int p = Math.max(path.lastIndexOf('/'), path.lastIndexOf('\\'));

        if (i > p) {
            extension = path.substring(i+1);
        }
        
        if (!"cherry".equals(extension) && !"ry".equals(extension) && !"ch".equals(extension) && ! "h".equals(extension))
            // Throw error, bad file extension
            throw new Exception(new InvalidExtensionException("Extension: " + extension + "is not one of the valid Cherry file extensions."));
    }
    
    private static File checkExists(File file) throws FileNotFoundException {
        if (Files.notExists(file.toPath(), LinkOption.NOFOLLOW_LINKS))
            throw new FileNotFoundException(file.getName());
        
        return file;
    }
    
    /**
     * @return The file paths that have successfully been registered as 
     * {@code java.io.File}(s).
     */
    public static File[] registeredFiles() { return registeredFiles; }
}
