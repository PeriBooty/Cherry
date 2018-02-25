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

import java.util.HashMap;
import java.util.Map;

/**
 * Each of the values in this enum class represent a single terminal in the
 * grammar of this language, this is used to help type tokens and help the
 * parser match the input to the grammatical structure of this language.
 *
 * @author SoraKatadzuma
 * @author Abdur-rahmaanJ
 * @version 0.0.0.1
 */
public enum Type {
    /* Parser Dependent types. */
    /** Defines an Identifier. */ ID,
    /** Defines any integer number. */ REAL,
    /** Defines any hexadecimal number. */ HEX,
    /** Defines any octal number. */ OCT,
    /** Defines any binary number. */ BIN,
    /** Defines any decimal number. */ DEC,
    /** Defines any number that is considered 8 bytes wide. */ WIDE,
    /** Defines any number that is considered 2 bytes short. */ SKINNY,
    /** Defines a string literal. */ STRL,
    /** Defines a character literal. */ CHRL,
    /** Defines any character sequence that is undefined by this class. */ UNDEF,
    /** Defines the end of the token stream. */ EOTS,

    /* Keywords */
    /** If statement. */ IF,
    /** Else statement.  */ ELSE,
    /** For statement. */ FOR,
    /** While statement. */ WHILE,
    /** For each statement. */ FOREACH,
    /** Switch statement. */ SWITCH,
    /** Case statement. */ CASE,
    /** Do-while statement. */ DO,
    /** Return statement. */ RETURN,
    /** Continue statement. */ CONTINUE,
    /** Skip statement. */ SKIP,
    /** Break statement. */ BREAK,
    /** Byte type. */ BYTE,
    /** Boolean type. */ BOOL,
    /** Integer type. */ INT,
    /** Floating point type. */ FLOAT,
    /** Double precision type. */ DOUBLE,
    /** String type. */ STRING,
    /** Character type. */ CHAR,
    /** Void type. */ VOID,
    /** Short type. */ SHORT,
    /** Long type. */ LONG,
    /** Pointer subtype. */ PTR,
    /** Reference subtype. */ REF,
    /** Thread subtype. */ THREAD,
    /** Class object. */ CLASS,
    /** Structure object. */ STRUCT,
    /** Enum object. */ ENUM,
    /** Interface object. */ INTERFACE,
    /** Name space object. */ NAMESPACE,
    /** Use statement. */ USE,
    /** Global accessor. */ GLOBAL,
    /** External accessor. */ EXTERNAL,
    /** Local accessor. */ LOCAL,
    /** Internal accessor. */ INTERNAL,
    /** Secure accessor. */ SECURE,
    /** Static modifier. */ STATIC,
    /** Constant modifier. */ CONST,
    /** Immutable modifier. */ IMMUTABLE,
    /** Final modifier. */ FINAL,
    /** Abstract modifier. */ ABSTRACT,
    /** Override modifier. */ OVERRIDE,
    /** New keyword. */ NEW,
    /** This keyword. */ THIS,
    /** Super keyword. */ SUPER,
    /** In keyword. */ IN,

    /* Symbols */
    /** Addition symbol. */ ADD("+"),
    /** Subtraction symbol. */ SUB("-"),
    /** Multiplication symbol. */ MUL("*"),
    /** Division symbol. */ DIV("/"),
    /** Modulus symbol. */ MOD("%"),
    /** Increment symbol. */ INC("++"),
    /** Decrement symbol. */ DCM("--"),
    /** Add and equal symbol. */ ADDEQ("+="),
    /** Subtract and equal symbol. */ SUBEQ("-="),
    /** Multiply and equal symbol. */ MULEQ("*="),
    /** Divide and equal symbol. */ DIVEQ("/="),
    /** Mod and equal symbol. */ MODEQ("%="),
    /** Unary not operator. */ NOT("!"),
    /** Not equal to operator. */ NOTEQ("!="),
    /** Assignment operator. */ ASG("="),
    /** Logical equals. */ LEQ("=="),
    /** Less than operator. */ LESS("<"),
    /** More than operator. */ MORE(">"),
    /** Less than or equal to operator. */ LESSEQ("<="),
    /** More than or equal to operator. */ MOREQ(">="),
    /** Left shift binary operator. */ LSH("<<"),
    /** Right shift binary operator. */ RSH(">>"),
    /** Left shift and equal operator. */ LSHEQ("<<="),
    /** Right shift and equal operator. */ RSHEQ(">>="),
    /** Logical Left shift. */ LLSH("<<<"),
    /** Logical Right shift. */ LRSH(">>>"),
    /** Bit wise And operator. */ BWA("&"),
    /** Bit wise Or operator. */ BWO("|"),
    /** Logical And operator. */ AND("&&"),
    /** Logical Or operator. */ OR("||"),
    /** Bit wise And and equal operator. */ BWAEQ("&="),
    /** Bit wise Or and equal operator. */ BWOEQ("|="),
    /** Dot operator. */ DOT("."),
    /** Ellipsis operator. */ ELL("..."),
    /** Colon operator. */ COL(":"),
    /** Scope resolution symbol. */ SRO("::"),
    /** Bit wise XOR operator. */ BWX("^"),
    /** Bit wise XOR and equal operator. */ BWXEQ("^="),
    /** Bit wise Not operator. */ BWN("~"),
    /** Left brace symbol. */ LBRC("{"),
    /** Right brace symbol. */ RBRC("}"),
    /** Left bracket symbol. */ LBRK("["),
    /** Right bracket symbol. */ RBRK("]"),
    /** Left parenthesis symbol. */ LPAR("("),
    /** Right parenthesis symbol. */ RPAR(")"),
    /** Semi colon symbol. */ SMC(";"),
    /** Ternary operator. */ TERN("?"),
    /** Coalescing operator. */ COA("??"),
    /** Comma symbol. */ COM(",");

    /** A map that makes it easier to reference the types. */
    private static final Map<String, Type> typeTable = new HashMap<>();

    /** Filling the Type Table before using the class. */
    static {
        for (Type type : values())
            typeTable.put(type.value, type);
    }

    /** The value of this {@code Type} */
    private final String value;
    /** The index of this {@code Type}, in case it is needed. */
    private final int index = ordinal();

    /**
     * Constructs a new {@code Type} with a value of the name in lowercase form.
     */
    Type() {
        value = name().toLowerCase();
    }

    /**
     * Constructs a new {@code Type} with the given value.
     */
    Type(String value) {
        this.value = value;
    }

    /**
     * Allows the caller to get the {@code Type} that is represented by the string
     * {@code lexeme} if it exists in this enum. It is synchronized because calls
     * to {@code Map#get(K key)} is not thread safe. Since this is rather a quick
     * operation, synchronizing it will have little effect on performance while
     * making sure there are no conflicts.
     * 
     * @param lexeme The lexeme to get the {@code Type} for.
     * @return The {@code Type} that is represented by the lexeme, if it exists.
     */
    public static synchronized Type get(String lexeme) {
        Type result = typeTable.get(lexeme);
        return result == null ? ID : result;
    }

    /**
     * Does the same as {@code get(String lexme)} except it technically only is
     * supposed to be used for symbols, but since it calls {@code get(String lexeme)}
     * and it can return {@code Type.ID}, and we don't want to return an identifier
     * for a symbol if it doesn't exist; we have it change to {@code Type.UNDEF}
     * and return that the symbol is undefined.
     * 
     * @param lexeme The lexeme to get the {@code Type} for.
     * @return The {@code Type} that is represented by the lexeme, if it exists.
     */
    public static synchronized Type getSymbol(String lexeme) {
        Type result = get(lexeme);
        return result == ID ? UNDEF : result;
    }

    /**
     * Inherited method; returns the type as a string.
     * 
     * @return The value of this type.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() { return value; }
}
