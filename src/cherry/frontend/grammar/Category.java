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
 * This class is the base for the sub-classes {@code NonTerminal} and
 * {@code Terminal}, and it contains all the necessary methods that the sub-classes
 * need.
 * 
 * @author SoraKatadzuma
 * @version 0.0.0.1
 * @deprecated Due to changes with how {@code NonTerminal}s and {@code Terminal}s
 *      are handled in the grammar. This is however left for legacy and reuse if
 *      necessary.
 */
@Deprecated
public abstract class Category {
    /** This is the value of this {@code Category}. */
    public final String value;
    /** Tells whether or not this is a terminal category. */
    public final boolean terminal;
    
    /**
     * Constructs a new {@code Category} with the given value as a representation
     * and the boolean {@code terminal} denoting whether or not this {@code Category}
     * was designed as a terminal.
     * 
     * @param value The value of this {@code Category}.
     * @param terminal Tells whether or not this {@code Category} is a terminal.
     */
    protected Category(String value, boolean terminal) {
        this.value = value;
        this.terminal = terminal;
    }
}
