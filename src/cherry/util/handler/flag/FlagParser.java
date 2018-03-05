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
package cherry.util.handler.flag;

import cherry.util.exception.FlagParseException;
import cherry.util.handler.flag.FlagHandler.RuntimeFlag;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses command line flags that have the ability to contain embedded data.
 * 
 * @author SoraKatadzuma
 * @version 0.0.0.2
 */
public final class FlagParser {   
    /** Chooses between one of the parsable flags that the parameter "flag" is. */
    static List<RuntimeFlag> parseFlag(RuntimeFlag flag, List<String> flagTokens) throws FlagParseException {
        switch (flag) {
            case DIAGNOSE:
                return diagnoseSubFlags(flagTokens);
        }
        
        return null;
    }
    
    /** "Parses" the diagnose flag for sub flags. */
    private static List<RuntimeFlag> diagnoseSubFlags(List<String> flagTokens) throws FlagParseException {
        List<RuntimeFlag> result = new ArrayList<>();
        String token;
        boolean shouldParse = true;
        
        // remove the first two token as they are not important.
        flagTokens.remove(0);
        flagTokens.remove(0);
        
        // set all diagnostic subflags
        if (flagTokens.isEmpty()) {
            result.add(RuntimeFlag.FILES);
            result.add(RuntimeFlag.FLAGS);
            result.add(RuntimeFlag.TOKENS);
            result.add(RuntimeFlag.FIRST);
            result.add(RuntimeFlag.FOLLOW);
            return result;
        }
        
        token = flagTokens.get(0);
        
        if (!"(".equals(token))
            throw new FlagParseException("Expected: \"(\" but received: \"" + token + "\".");
        
        flagTokens.remove(0);
        
        // continue until we only have one token left (it should be the RPAR)
        while (flagTokens.size() != 1) {
            token = flagTokens.remove(0);
            
            // see if it exists in RuntimeFlag
            if (!RuntimeFlag.exists(token))
                throw new FlagParseException("Expected a sub flag, but received: \"" + token + "\".");
            
            // add the sub flag to the result.
            result.add(RuntimeFlag.get(token));
            
            // Check that we haven't reached the end of the list yet, incase we
            // have more than one sub flag.
            if (flagTokens.size() == 1) break;
            
            // we expect a comma next
            token = flagTokens.remove(0);
            
            if (!",".equals(token))
                throw new FlagParseException("Expected a \",\" but received: \"" + token + "\".");
        }
        
        token = flagTokens.remove(0);
        
        if (!")".equals(token))
            throw new FlagParseException("Expected: \")\" but received: \"" + token + "\".");
        
        return result;
    }
}
