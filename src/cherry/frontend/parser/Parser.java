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
package cherry.frontend.parser;

import cherry.frontend.grammar.Grammar;
import cherry.frontend.grammar.Token;
import cherry.frontend.lexer.Lexer;
import cherry.util.handler.diagnostic.DiagnosticHandler;
import cherry.util.handler.flag.FlagHandler;
import cherry.util.object.ParseTree;
import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * /// Class Description ///
 * 
 * @author SoraKatadzuma
 * @version 0.0.0.2
 */
public final class Parser implements Callable<ParseTree> {
    /** The file this parser will be parsing. */
    private final File file;
    /** The list of tokens found by the lexer. */
    private List<Token> tokens;
    /** The current token being parsed. */
    private Token token;
    
    /**
     * Constructs a new {@code Parser} that will parse the given file.
     * 
     * @param file The file this {@code Parser} will parse.
     */
    public Parser(File file) {
        this.file = file;
    }
    
    /**
     * Inherited method.
     * 
     * @return The resulting {@code ParseTree} of the input.
     * @throws Exception if unable to parse.
     * @see java.util.concurrent.Callable#call()
     */
    @Override
    public ParseTree call() throws Exception {
        // Lex the file for tokens and start the actual parsing process.
        tokens = new Lexer(file).lex();
        
        if (FlagHandler.raisedFlags.contains(FlagHandler.RuntimeFlag.TOKENS))
            DiagnosticHandler.print(tokens, file.getName());
        
        return null; // only temporary
    }
}
