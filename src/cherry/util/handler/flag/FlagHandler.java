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

import cherry.util.exception.FlagDoesNotExistException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is responsible for checking flags for correctness, whether they
 * exist, and whether or not they applicable to any of the current files or
 * current compiler configurations.
 * 
 * @author SoraKatadzuma
 * @version 0.0.0.2
 */
public final class FlagHandler {
    /**
     * An enum containing all the usable flags that the compiler can set.
     */
    public enum RuntimeFlag {
        /**
         * The {@code --diagnose(subflags)} allows the compiler to set various
         * diagnostic flags. These are very handy for debugging issues with the
         * compiler during development times. The current sub-flags for
         * {@code --diagnose} are: {@code FIRST, FOLLOW}.
         */
        DIAGNOSE(true, false),
        /**
         * A sub flag that tells the compiler that it should output data about
         * the FIRST sets built by the grammar.
         */
        FIRST(false, true),
        /**
         * A sub flag that tells the compiler that it should output data about
         * the FOLLOW sets built by the grammar.
         */
        FOLLOW(false, true);
        
        /** A place for all our flags to reside for the exists method. */
        private static final Map<String, RuntimeFlag> flags = new HashMap<>();
        /** Tells if the flag is parsable. */
        public final boolean parsable;
        /** Tells if the flag is a sub flag. */
        public final boolean subFlag;
        
        /** Fills our flags set. */
        static {
            for (RuntimeFlag flag : values()) {
                flags.put(flag.name().toLowerCase(), flag);
            }
        }
        
        RuntimeFlag(boolean parsable, boolean subFlag) {
            this.parsable = parsable;
            this.subFlag = subFlag;
        }
        
        /** Checks if the given flag exists. */
        static boolean exists(String flag) {
            return flags.containsKey(flag);
        }
        
        /** Gets a specific flag given the name of it. */
        static RuntimeFlag get(String flag) {
            return flags.get(flag);
        }
    }
    
    /** A place to put all our raised runtime flags. */
    public static final EnumSet<RuntimeFlag> raisedFlags = EnumSet.noneOf(RuntimeFlag.class);
    
    /**
     * @param flags The flags that will be examined.
     * @throws cherry.util.exception.FlagDoesNotExistException If one of the given
     *      flags does not exist.
     */
    public FlagHandler(String... flags) throws Exception {
        raiseFlags(flags);
        System.out.println("Flags raised: " + raisedFlags);
    }
    
    /**
     * Scans each flag and parses if necessary to collect all possible flags and
     * sub flags from the command line strings that the compiler believed were
     * flags.
     */
    private void raiseFlags(String... flags) throws Exception {
        if (flags == null) return;
        
        for (String flag : flags) {
            List<String> flagDetails = FlagScanner.scan(flag);
            
            // first check if the given flag exists
            if (!RuntimeFlag.exists(flagDetails.get(1)))
                throw new FlagDoesNotExistException(flag);
            
            // else
            RuntimeFlag runFlag = RuntimeFlag.get(flagDetails.get(1));
            List<RuntimeFlag> parsedFlags = null;
            
            // attempt to raise the file, first check if it one of the parsable flags.
            if (runFlag.parsable)
                parsedFlags = FlagParser.parseFlag(runFlag, flagDetails);
            
            // after parsing the flag if necessary, add the flags to the raisedFlags
            // EnumSet.
            if (parsedFlags != null) raisedFlags.addAll(parsedFlags);
            
            raisedFlags.add(runFlag);
        }
    }
}