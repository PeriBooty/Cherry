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
public enum Terminal implements Symbol {
    /** Represents an identifier of any kind in the token stream. */
    ID("id"),
    /** Represents any integer in the token stream. */
    REAL("real"),
    /** Represents any hexadecimal number in the token stream. */
    HEX("hex"),
    /** Represents any octal number in the token stream. */
    OCT("oct"),
    /** Represents any binary number in the token stream. */
    BIN("bin"),
    /** Represents any decimal number in the token stream. */
    DEC("dec"),
    /** Represents a number that can be put into the long data type. */
    WIDE("wide"),
    /** Represents a number that can be put into the short data type. */
    SKINNY("skinny"),
    /** Represents any collection of words surrounded by double quotes that can be put into a string data type. */
    STRL("strl"),
    /** Represents any character surrounded by single quotes that can be put into a character data type. */
    CHRL("chrl"),
    /** Represents any undefined token in the token stream. */
    UNDEF("undef"),
    /** Represents the end of the token stream and the end of parsing. */
    EOTS("$"),
    /** Represents the keyword {@code if}. */
    IF("if"),
    /** Represents the keyword {@code else}. */
    ELSE("else"),
    /** Represents the keyword {@code for}. */
    FOR("for"),
    /** Represents the keyword {@code while}. */
    WHILE("while"),
    /** Represents the keyword {@code foreach}. */
    FOREACH("foreach"),
    /** Represents the keyword {@code switch}. */
    SWITCH("switch"),
    /** Represents the keyword {@code case}. */
    CASE("case"),
    /** Represents the keyword {@code do}. */
    DO("do"),
    /** Represents the keyword {@code return}. */
    RETURN("return"),
    /** Represents the keyword {@code continue}. */
    CONTINUE("continue"),
    /** Represents the keyword {@code skip}. */
    SKIP("skip"),
    /** Represents the keyword {@code break}. */
    BREAK("break"),
    /** Represents the data type {@code byte}. */
    BYTE("byte"),
    /** Represents the data type {@code bool}. */
    BOOL("bool"),
    /** Represents the data type {@code int}. */
    INT("int"),
    /** Represents the data type {@code float}. */
    FLOAT("float"),
    /** Represents the data type {@code double}. */
    DOUBLE("double"),
    /** Represents the data type {@code string}. */
    STRING("string"),
    /** Represents the data type {@code void}. */
    VOID("void"),
    /** Represents the data type {@code char}. */
    CHAR("char"),
    /** Represents the data type {@code long}. */
    LONG("long"),
    /** Represents the data type {@code short}. */
    SHORT("short"),
    /** Represents the sub-type {@code pointer}. */
    PTR("ptr"),
    /** Represents the sub-type {@code reference}. */
    REF("ref"),
    /** Represents the keyword {@code thread}. */
    THREAD("thread"),
    /** Represents the object type {@code class}. */
    CLASS("class"),
    /** Represents the object type {@code struct}. */
    STRUCT("struct"),
    /** Represents the object type {@code enum}. */
    ENUM("enum"),
    /** Represents the object type {@code interface}. */
    INTERFACE("interface"),
    /** Represents the object type {@code namespace}. */
    NAMESPACE("namespace"),
    /** Represents the keyword {@code use}. */
    USE("use"),
    /** Represents the modifier {@code global}. */
    GLOBAL("global"),
    /** Represents the modifier {@code external}. */
    EXTERNAL("external"),
    /** Represents the modifier {@code internal}. */
    INTERNAL("internal"),
    /** Represents the modifier {@code local}. */
    LOCAL("local"),
    /** Represents the modifier {@code secure}. */
    SECURE("secure"),
    /** Represents the modifier {@code static}. */
    STATIC("static"),
    /** Represents the modifier {@code const}. */
    CONST("const"),
    /** Represents the modifier {@code immutable}. */
    IMMUTABLE("immutable"),
    /** Represents the keyword {@code new}. */
    NEW("new"),
    /** Represents the keyword {@code this}. */
    THIS("this"),
    /** Represents the keyword {@code super}. */
    SUPER("super"),
    /** Represents the symbol for addition. */
    ADD("+"),
    /** Represents the symbol for subtraction. */
    SUB("-"),
    /** Represents the symbol for multiplication. */
    MUL("*"),
    /** Represents the symbol for division. */
    DIV("/"),
    /** Represents the symbol for modulo. */
    MOD("%"),
    /** Represents the symbol for incrementation. */
    INC("++"),
    /** Represents the symbol for decrementation. */
    DCM("--"),
    /** Represents the symbol for the operation of summation. */
    ADDEQ("+="),
    /** Represents the symbol for the operation of deduction.  */
    SUBEQ("-="),
    /** Represents the symbol for the operation of production. */
    MULEQ("*="),
    /** Represents the symbol for the operation of quotient. */
    DIVEQ("/="),
    /** Represents the symbol for the operation of modulus. */
    MODEQ("%="),
    /** Represents the symbol for boolean flip. */
    NOT("!"),
    /** Represents the symbol for logical not. */
    NOTEQ("!="),
    /** Represents the symbol for assignments. */
    ASG("="),
    /** Represents the symbol for the equals comparison. */
    LEQ("=="),
    /** Represents the symbol for the less than comparison. */
    LESS("<"),
    /** Represents the symbol for the greater than comparison. */
    MORE(">"),
    /** Represents the symbol for the less than or equal to comparison. */
    LESSEQ("<="),
    /** Represents the symbol for the greater than or equal to comparison. */
    MOREQ(">="),
    /** Represents the symbol for the binary left shift operation. */
    LSH("<<"),
    /** Represents the symbol for the binary right shift operation. */
    RSH(">>"),
    /** Represents the symbol for the binary left shift assignment operation. */
    LSHEQ(">>="),
    /** Represents the symbol for the binary right shift assignment operation. */
    RSHEQ("<<="),
    /** Represents the symbol for the binary logical left shift operation. */
    LLSH("<<<"),
    /** Represents the symbol for the binary logical right shift operation. */
    LRSH(">>>"),
    /** Represents the symbol for the binary and operation. */
    BWA("&"),
    /** Represents the symbol for the binary or operation. */
    BWO("|"),
    /** Represents the symbol for the logical and operation. */
    AND("&&"),
    /** Represents the symbol for the logical or operation. */
    OR("||"),
    /** Represents the symbol for the binary and assignment operation. */
    BWAEQ("&="),
    /** Represents the symbol for the binary or assignment operation. */
    BWOEQ("|="),
    /** Represents the symbol for member accesses. */
    DOT("."),
    /** Represents the symbol for ellipsis. */
    ELL("..."),
    /** Represents the symbol for a colon. */
    COL(":"),
    /** Represents the symbol for scope resolution. */
    SRO("::"),
    /** Represents the symbol for the binary exclusive or operation. */
    BWX("^"),
    /** Represents the symbol for the binary exclusive or assignment operation. */
    BWXEQ("^="),
    /** Represents the symbol for the binary flip, or binary not. */
    BWN("~"),
    /** Represents the symbol for the beginning of a delimited scope. */
    LBRC("{"),
    /** Represents the symbol for the end of a delimited scope. */
    RBRC("}"),
    /** Represents the symbol for the beginning of an array declaration. */
    LBRK("["),
    /** Represents the symbol for the end of and array declaration. */
    RBRK("]"),
    /** Represents the symbol for the beginning of a parameter list. */
    LPAR("("),
    /** Represents the symbol for the end of a parameter list. */
    RPAR(")"),
    /** Represents the symbol for the end of a statement. */
    SMC(","),
    /** Represents the symbol for the end of a condition in a ternary statement. */
    TERN("?"),
    /** Represents the symbol for performing a null coalescing operation. */
    COA("??"),
    /** Represents the symbol for list separation. */
    COM(",");
    
    /** The value of the given {@code Terminal}. */
    public final String value;
    /** The index of this {@code Terminal}. */
    public final int index = ordinal();
    
    /**
     * Constructs a new {@code Terminal} with the given value.
     * 
     * @param value The value that represents this {@code Terminal}.
     */
    Terminal(String value) {
        this.value = value;
    }
}
    
