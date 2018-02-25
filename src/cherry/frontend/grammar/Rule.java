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
 * This class defines a single rule or production in the grammar of a language.
 * It contains a head and a body; the head consists of a single {@code NonTerminal}
 * that will be the name and representation of the following body, where the body
 * is a list of {@code Terminals} and {@code NonTerminals}.
 * 
 * <p>
 * Remember that a {@code Rule} is represented by {@code S → αBß}. This is what
 * we as can use to help identify what a {@code Item} should be and where we are
 * in parsing, because all we have to do is match the pattern. {@code S} is the
 * current start symbol or simply the head of the current production. {@code S}
 * is considered the start symbol if it is the head of the first rule of the
 * grammar but often times it is also used to represent the current start symbol
 * which is just the head of any latter rule or production. {@code →} literally
 * just tells us that there is a separation between the head and body of a rule
 * or production. {@code α} represent any number of previously seen {@code Terminal}s
 * and {@code NonTerminal}s. This can even mean that {@code α} can be 0. {@code B}
 * represents the {@code NonTerminal} we are currently on, as we only focus on
 * {@code NonTerminal}s when we are parsing. This is typically where we make a
 * jump to a different rule. And finally {@code ß} represents exactly what {@code α}
 * does but after the {@code NonTerminal} match {@code B}.
 * </p>
 * 
 * <p>
 * There is another two minor forms that are only prevalent when talking about
 * {@code Item} generation and {@code State} generation, but we'll mention them:
 * {@code B → γ} and {@code S → α·Bß}. The former just represents the insertion
 * of {@code NonTerminal B} with body {@code γ} into another production with the
 * {@code NonTerminal B} in it's body. And the latter represents an item, where
 * the dot ({@code ·}) represents the position in the production the {@code Item}
 * represents.
 * </p>
 *
 * @author SoraKatadzuma
 * @version 0.0.0.1
 * @deprecated Was added in error, but in case the error was itself an error, we
 *      will leave this in.
 */
@Deprecated
public final class Rule {
    /** This is the head of this {@code Rule}. */
    public final NonTerminal head;
    /** This is the body of this {@code Rule}. */
    public final Category[] body;
    
    /**
     * Constructs a new {@code Rule} with the given head and body.
     * 
     * @param head The head of this {@code Rule}.
     * @param body The list of {@code Terminal}s and {@code NonTerminal}s that
     *      make up the body of this {@code Rule}
     */
    private Rule(NonTerminal head, Category[] body) {
        this.head = head;
        this.body = body;
    }
}
