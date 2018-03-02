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
package cherry.util.handler.flag;

import java.util.ArrayList;
import java.util.List;

/**
 * Scans a flag string and returns the broken string results. These results are
 * sub string sections divide by the character types. In many respects this acts
 * highly similar to the {@code Lexer}.
 * 
 * @author SoraKatadzuma
 * @version 0.0.0.2
 */
public final class FlagScanner {
    /** Our flag split into individual characters. */
    private static char[] flag;
    /** The current character we are looking at. */
    private static char current;
    /** Our current index in the input. */
    private static int index = 0;
    /** A way for us to build our strings. */
    private static final StringBuilder BUILDER = new StringBuilder();
    
    /** Scans and breaks up the string passed to it. */
    static List<String> scan(String flagDetails) {
        List<String> result = new ArrayList<>();
        flag = flagDetails.toCharArray();
        
        for ( ; index != flag.length; ) {
            BUILDER.delete(0, BUILDER.length());
            
            // just in case
            if (flag[index] == 0xFFFF) break;
            
            if (flag[index] == '-' && flag[index+1] == '-') {
                BUILDER.append(new char[] { flag[index++], flag[index++] });
                result.add(BUILDER.toString());
                continue;
            }
            
            if (Character.isLetter(flag[index])) {
                result.add(flagName());
                continue;
            }
            
            if (Character.isDigit(flag[index])) {
                result.add(number());
                continue;
            }
            
            if (isDelimiter(flag[index])) {
                result.add(delimiter());
            }
        }
        
        return result;
    }
    
    /** returns the flag name. */
    static String flagName() {
        current = flag[index++];
        
        while (Character.isLetter(current) || current == '-' || current == '_') {
            BUILDER.append(current);
            
            if (index == flag.length) break;
            
            current = flag[index++];
        }
        
        // compensate
        if (index != flag.length) index--;
        
        if (index == flag.length && isDelimiter(current)) index--;
        
        return BUILDER.toString();
    }
    
    /** returns a number. */
    static String number() {
        current = flag[index++];
        
        while (Character.isLetter(current)) {
            BUILDER.append(current);
            
            if (index == flag.length) break;
            
            current = flag[index++];
        }
        
        // compensate
        if (index != flag.length) index--;
        
        if (index == flag.length && isDelimiter(current)) index--;
        
        return BUILDER.toString();
    }
    
    /** returns a delimiter. */
    static String delimiter() {
        if (index != flag.length) current = flag[index++];
        
        BUILDER.append(current);
        return BUILDER.toString();
    }
    
    /** checks if the current character is a delimiter. */
    static boolean isDelimiter(char curr) {
        return curr == '{' || curr == '}' || curr == '[' || curr == ']'
            || curr == '(' || curr == ')' || curr == ',';
    }
}
