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
 * This class represents a single Terminal string in the grammar of a language.
 * It is extended from {@code Category} which is the basis of this class and the
 * {@code NonTerminal class}. All they token types from {@code Type} will
 * eventually be turned into a {@code Terminal} to fit into the {@code Rule} and
 * {@code Grammar} classes.
 * 
 * @author SoraKatadzuma
 * @version 0.0.0.1 
 */
public final class Terminal extends Category {
    /**
     * Constructs a new {@code Terminal} with the given value.
     * 
     * @param value The value that represents this {@code Terminal}.
     */
    public Terminal(String value) {
        super(value, true);
    }
}
