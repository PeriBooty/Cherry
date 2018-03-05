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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        { NonTerminal.START }
    }),
    /**
     * This is the technical start of parsing in which actual constructs begin to be broken down.
     * <br> Rule:
     * <pre> &lt;start&gt; ::= &lt;directive&gt; | &lt;packaging&gt; | ε </pre>
     */
    START(new Symbol[][] {
        { NonTerminal.DIRECTIVE },
        { NonTerminal.PACKAGING },
        { Terminal.EPSILON }
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
        { Terminal.ID }, { Terminal.ID, NonTerminal.EXT_TYPE_NAME }
    }),
    EXT_TYPE_NAME(new Symbol[][] {
        { Terminal.DOT, Terminal.ID, NonTerminal.EXT_TYPE_NAME },
        { Terminal.EPSILON }
    });
    
    /** The list of {@code Category} that represents the body of the representative rule. */
    public final Symbol[][] bodies;
    /** The name of this rule, specifically named after the {@code NonTerminal} that it represents. */
    public final String name = name().toLowerCase();
    /**
     * The FIRST sets of this language.
     */
    public static final Map<NonTerminal, Set<Symbol>> firsts = new HashMap<>();
    /**
     * The follow sets of this language. The follows map follows the same rules
     * as the firsts map, due to its inherit nature.
     */
    public static final Map<Integer, Set<Integer>> follows = new HashMap<>();
    
    /**
     * Constructs a new {@code Grammar} rule with the given body.
     * 
     * @param body The body of the representative rule.
     */
    Grammar(Symbol[][] bodies) {
        this.bodies = bodies;
    }
    
    /**
     * Finds the rule at the given index and returns it.
     * 
     * @param index The index we are looking for.
     * @return The grammar rule at the given index.
     */
    public static Grammar get(int index) {
        Grammar rule = null;
        
        for (Grammar value : values()) {
            if (value.ordinal() == index) rule = value;
        }
        
        return rule;
    }
    
    /**
     * The language FIRST sets are used to determine what lookahead is available
     * when we are parsing, typically when we are looking for a {@code NonTerminal},
     * but this is only useful when we are doing LL parsing, which the parser of
     * this language will not; we will be using LR, specifically GLR, parsing.
     * 
     * <p>
     * The following are a few rules that the construction of FIRST sets abide by:
     *  <ul>
     *      <li>
     *          We are only focused on the first thing of the right hand side of
     *          a rule, hence the name FIRST set.
     *      </li>
     *      <li>
     *          When building FIRST sets we need to keep one thing in mind: that
     *          if the first of a rule is a {@code NonTerminal} then we need to
     *          add the first of that symbol to the first of the current symbol.
     *          This typically means that we must continually loop through each
     *          rule until all sets are properly built.
     *      </li>
     *      <li>
     *          If the current rule is nullable then we add the "ε" to the first
     *          of that rule. The nullable "ε" is highly useful during parsing
     *          so we want to make sure that we keep it.
     *      </li>
     *  </ul>
     * <br>
     * To get a little bit more intuitive about how building the FIRST sets works,
     * let's look at the specifics of the algorithm with pattern matching:
     *  <ol>
     *      <li>
     *          {@code FIRST(S → ε) = ε;} <br>
     *          We already sort of explained this, but in detail, if the body of
     *          a rule such that: {@code S → ε} is possible, then {@code ε} is 
     *          included in the first of rule {@code S}.
     *      </li>
     *      <li>
     *          {@code FIRST(S → αBß) = B, if B is a Terminal and α is null or ε.} <br>
     *          The basic premise of this is that, if we take a rule: {@code S → αBß}
     *          and where {@code B} is a terminal and there is no symbols before
     *          it or {@code α → ε} then we add {@code B} to the FIRST of rule
     *          {@code S}.
     *      </li>
     *      <li>
     *          {@code FIRST(S → αBß) = FIRST(α) if α is a NonTerminal and does not contain ε.} <br>
     *          So this would have a similar effect to the last step, but if
     *          {@code α} is not null and is a {@code NonTerminal} respectively,
     *          then we add the FIRST of that {@code NonTerinal} to the current
     *          rule.
     *      </li>
     *      <li>
     *          {@code FIRST(S → αBß) = FIRST(α - ε) U FIRST(B), if α is a NonTerminal and it contains ε in one of it's bodies.}
     *          This is because if the first is nullable, then it needs to be able
     *          what can replace the first as a first, this is typically a Terminal.
     *          However, this means that the algorithm can continue to search the
     *          rule for a valid first if one does not exist.
     *      </li>
     *  </ol>
     * <br>
     * To finalize the algorithm portion, as it wasn't talked about: the algorithm
     * works in multiple steps. It 1) Checks each rule for a first, it places all
     * the available {@code Terminals} into the FIRST for that rule; 2) if the
     * first of the rule is {@code NonTerminal} then it attempts to add all the
     * firsts for that rule into the current one; 3) It is likely that the algorithm
     * will not obtain all the firsts in a single pass, so it will continue to
     * steps one and two until it cannot add anymore into the FIRST of any rule.
     * </p>
     */
    public static void fillFirsts() {
        boolean finished;
        
        // first add all the NonTerminals into the map
        for (Grammar rule : values()) {
            firsts.put(NonTerminal.get(rule.ordinal()), new HashSet<>());
        }
        
        // use a do statement to collect all the firsts and then add all the firsts
        // to their corresponding NonTerminals.
        do {
            finished = false;
            
            try {
                for (Grammar rule : values()) {
                    Set<Symbol> ruleFirsts = getFirstsFor(rule, firsts);

                    if (firsts.get(NonTerminal.get(rule.ordinal())).addAll(ruleFirsts))
                        finished = true;
                }
            } catch (Throwable t) {
                Logger.getLogger(Grammar.class.getName()).log(Level.SEVERE, t.getMessage(), t);
            }
        } while (finished);
    }
    
    /**
     * Follows the rules stated by the {@code firsts()} method.
     */
    private static Set<Symbol> getFirstsFor(Grammar rule, Map<NonTerminal, Set<Symbol>> rules) {
        Set<Symbol> result = new HashSet<>();
        
        for (Symbol[] body : rule.bodies) {
            for (Symbol symbol : body) {
                if (symbol instanceof Terminal) {
                    result.add(symbol);
                    rules.get(NonTerminal.get(rule.ordinal())).add(symbol);
                    break;
                }
                
                // else if symbol is an instance of NonTerminal
                
                // check if the rule we are getting the FIRST for contains ε
                result.addAll(getFirstsFor(Grammar.get(symbol.getIndex()), rules));
                
//                if (result.contains(Terminal.EPSILON)) {
//                    // check set size
//                    if (result.size() > 1) {
//                        // add and break;
//                        rules.get((NonTerminal)symbol).addAll(result);
//                        break;
//                    }
//                    
//                    // else
//                    // add all but the epsilon and continue.
//                    result.remove(Terminal.EPSILON);
//                    rules.get((NonTerminal)symbol).addAll(result);
//                }
            }
        }
        
        return result;
    }
    
    /**
     * 
     */
    private static Map<Integer, Set<Integer>> follows() {
        
        return null;
    }
}
