/*
 * The MIT License
 *
 * Copyright 2018 Dell.
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

// imports
import cherry.frontend.lexer.Token;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dell
 */

public class Lexer 
{
    private String source;
    private int line;
    private final List<Token> tokens = new ArrayList<>();
    private int current = 0;
    
    Lexer(String src)
    {
        source = src;
    }
    
    private char getNext() 
    {
        current++;
        return source.charAt(current - 1); 
    }
    
    public String[] getLexemes()
    {
       String[] lexemes = {}; 
       return lexemes;
    }
    
    public List<Token> getTokens()
    {
    
       return tokens;
    }
}
