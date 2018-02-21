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
package cherry.frontend.lexer;

import cherry.frontend.grammar.Token;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
/**
 * /// Class Description ///
 * 
 * @author SoraKatadzuma
 * @author Abdur-rahmaanJ
 * @version 0.0.0.1
 */
public class Lexer {
    /***/
    private final File source;
    /***/
    private int line = 1;
    /***/
    private int column = 1;
    /***/
    private final List<Token> tokens = new ArrayList<>();
    
    /**
     * 
     * @param source
     */
    public Lexer(File source) {
        this.source = source;
    }
    
    /**
     * TODO: Fix this so that we read from the file {@code source}.
     */
    private char getNext() {
        column++;
        return ' '; 
    }
    
    /**
     * TODO: Change this, it's useless.
     */
    public String[] getLexemes() {
       String[] lexemes = {}; 
       return lexemes;
    }
    
    /**
     * @return The tokens collected by this {@code Lexer}.
     */
    public List<Token> getTokens() {
       return tokens;
    }
}
