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

import java.util.EnumMap;
import java.util.Map;

/**
 * This represents the entire grammar of the Cherry language. The following snippet
 * is from the previous but not removed {@code Rule.java} that meant to do what
 * this class does.
 * 
 * <p>
 * There are a few discrepancies we must address:
 *  <ol>
 *      <li>
 *          There can never be more than one of any type of rule in the Grammar,
 *          which means we are unable to show multiple paths.
 *      </li>
 *      <li>
 *          Because of the first issue, this also has an effect on what can be
 *          epsilon and what cannot be.
 *      </li>
 *      <li>
 *          This will all ultimately to confusion and the breaking of the parser,
 *          unless we can understand a few fundamentals about how we plan to use
 *          these rules in reality.
 *      </li>
 *  </ol>
 * Let's look at it like this: a rule is a set of possible outcomes. Since we can
 * have multiple outcomes we need to be able to address multiple situations. If
 * we can adapt the information that the rule holds then we can affect how we
 * effectively generate the proper {@code Item}s and {@code State}s for the grammar.
 * All we really need to do, is make it so that each rule is actually a 2 dimensional
 * array of {@code Symbol}s. Why you may ask? Remember that thing about sets of
 * possible outcomes? We can only truly represent this by making it possible to
 * have more than one body in the rule, as you will see in the Grammar.md most,
 * if not all the rules, have at least 2 bodies to them.
 * </p>
 * 
 * @author SoraKatadzuma
 * @version 0.0.0.1
 * @see cherry.frontend.grammar.Rule
 */
public enum Grammar {
    /**
     * This is the rule for the very beginning of the parsing operation.
     * <br> Rule:
     * <pre> &lt;document&gt; ::= &lt;start&gt; &lt;document&gt; $ | ε </pre>
     */
    DOCUMENT(new Symbol[][] {
        { NonTerminal.START, NonTerminal.DOCUMENT, Terminal.EOTS },
        { Terminal.EPSILON }
    }),
    /**
     * This is the technical start of parsing in which actual constructs begin to be broken down.
     * <br> Rule:
     * <pre> &lt;start&gt; ::= &lt;directive&gt; | &lt;packaging&gt; | ε </pre>
     */
    START(new Symbol[][] {
        { NonTerminal.DIRECTIVE }, { NonTerminal.PACKAGING },  { Terminal.EPSILON }
    }),
    /**
     * This represents the namespace or object directive that will be included in the current parse.
     * <br> Rule:
     * <pre> &lt;directive&gt; ::= "use" &lt;type-name&gt; ";" | ε  </pre>
     */
    DIRECTIVE(new Symbol[][] {
        {
            Terminal.USE, Terminal.ID,
            NonTerminal.TYPE_NAME, Terminal.SMC
        }
    }),
    /**
     * This represents the declaration of the namespace for the current code block.
     * <br> Rule:
     * <pre> &lt;packaging&gt; ::= "namespace" &lt;type-name&gt; ";" | ε </pre>
     */
    PACKAGING(new Symbol[][] {
        {
            Terminal.NAMESPACE, Terminal.ID,
            NonTerminal.TYPE_NAME, Terminal.SMC
        }
    }),
    /**
     * This represents a structure where an identifier is the call for a directive
     * or packaging statement. However, identifiers aren't the only thing as we
     * could be calling an identifier inside a namespace.
     * <br> Rule:
     * <pre> &lt;type-name&gt; ::= "id" | &lt;type-name&gt; "." "id" </pre>
     */
    TYPE_NAME(new Symbol[][] {
        { Terminal.ID }, { NonTerminal.TYPE_NAME, Terminal.DOT, Terminal.ID }
    });
    
    /**
     * This map contains a reference to the {@code NonTerminal} head of a rule
     * and the integer index that references the grammar rule that the
     * {@code NonTerminal} is in reference to, respectively.
     */
    public static final Map<NonTerminal, Integer> rules = new EnumMap<>(NonTerminal.class);
    /** The list of {@code Category} that represents the body of the representative rule. */
    public final Symbol[][] bodies;
    /** The name of this rule, specifically named after the {@code NonTerminal} that it represents. */
    public final String name = name().toLowerCase();
    
    /**
     * Constructs a new {@code Grammar} rule with the given body.
     * 
     * @param body The body of the representative rule.
     */
    Grammar(Symbol[][] bodies) {
        this.bodies = bodies;
    }
}
