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
 * The {@code Token} class is one that takes the a lexeme from a file, it's
 * position in that file, and the name of the file and turns it into a
 * {@code Parser} understandable structure with a type that defines it as a
 * terminal in the grammar of the language, if at all possible.
 * 
 * <p>
 * {@code Token}s which are typed as {@code UNDEF} or {@code undefined} are those
 * which the {@code Lexer} is unable to determine, possibly because it doesn't
 * match any other known types, or it just doesn't make sense lexically.
 * </p>
 * 
 * @author SoraKatadzuma
 * @author Abdur-rahmaanJ
 * @version 0.0.0.2
 */
public final class Token {
    /** The type of terminal this {@code Token} represents. */
    public final Type type;
    /** The lexeme that this {@code Token} is founded on. */
    public final String value;
    /** The name of the file this {@code Token} was found in. */
    public final String filename;
    /** The line of the file in which this {@code Token} was found in. */
    public final int line;
    /** The column in the line of which this {@code Token} was found in. */
    public final int column;
    
    /**
     * Constructs a new {@code Token} with the given data.
     * 
     * @param type The type of the this {@code Token} is the name of the terminal
     *      that this {@code Token} represents.
     * @param value The value of this {@code Token} is the lexeme or source
     *      string that was collected from the input file.
     * @param filename This is the name of the file this {@code Token} came from.
     * @param line This is the line in the file this {@code Token} came from.
     * @param column This is the column in the line this {@code Token} came from.
     */
    public Token(Type type, String value, String filename, int line, int column) {
        this.type = type;
        this.value = value;
        this.filename = filename;
        this.line = line;
        this.column = column;
    }
    
    /**
     * @return The string form of the token.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "FILE: \"" + filename + "\" TYPE: \"" + type + "\" VALUE: \"" +
                value + "\" AT: " + line + ":" + column;
    }
}
