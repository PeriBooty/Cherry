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
package cherry.frontend.grammar;

/**
 * This class represents a single grammatical structure or scope as defined by a
 * particular rule in the grammar. {@code NonTerminal}s can be used as both the
 * head of a rule or production as well as part of the body of a rule or production.
 * 
 * @author SoraKatadzuma
 * @version 0.0.0.1
 */
public enum NonTerminal implements Symbol {
    /** Represents the start of parsing. */
    DOCUMENT,
    /** Technical start of parsing. */
    START,
    /** Defines a namespace or object directive to include into the parsing and use of a file. */
    DIRECTIVE,
    /** Defines a namespace for the current code until the next namespace declaration. */
    PACKAGING,
    /** Defines a single identifier or a packaged identifier. */
    TYPE_NAME;
    
    /** The name of this {@code NonTerminal} in lowercase form. */
    private final String name = name().toLowerCase();
    /** The index value of this {@code NonTerminal} making referencing easier. */
    private final int index = ordinal();
    
    /**
     * Returns the {@code NonTerminal} at the given index, null if the index does
     * not exist.
     * 
     * @param index The index of the {@code NonTerminal} we are retrieving.
     * @return The {@code NonTerminal} at the index provided.
     */
    public static NonTerminal get(int index) {
        NonTerminal result = null;
        
        for (NonTerminal nt : values())
            if (nt.index == index) result = nt;
        
        return result;
    }
    
    /**
     * Inherited from {@code Symbol}.
     * 
     * @return The name of this symbol.
     */
    @Override
    public String symbolName() {
        return name;
    }
    
    /**
     * Inherited from {@code Symbol}.
     * 
     * @return The index of this symbol.
     */
    @Override
    public int getIndex() {
        return index;
    }
}
