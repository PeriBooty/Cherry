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
    private Terminal(String value) {
        super(value, true);
    }
    
    /*************************** Declarations *********************************/
    /** Represents an identifier of any kind in the token stream. */
    public static final Terminal ID = new Terminal("id");
    /** Represents any integer in the token stream. */
    public static final Terminal REAL = new Terminal("real");
    /** Represents any hexadecimal number in the token stream. */
    public static final Terminal HEX = new Terminal("hex");
    /** Represents any octal number in the token stream. */
    public static final Terminal OCT = new Terminal("oct");
    /** Represents any binary number in the token stream. */
    public static final Terminal BIN = new Terminal("bin");
    /** Represents any decimal number in the token stream. */
    public static final Terminal DEC = new Terminal("dec");
    /** Represents a number that can be put into the long data type. */
    public static final Terminal WIDE = new Terminal("wide");
    /** Represents a number that can be put into the short data type. */
    public static final Terminal SKINNY = new Terminal("skinny");
    /** Represents any collection of words surrounded by double quotes that can be put into a string data type. */
    public static final Terminal STRL = new Terminal("strl");
    /** Represents any character surrounded by single quotes that can be put into a character data type. */
    public static final Terminal CHRL = new Terminal("chrl");
    /** Represents any undefined token in the token stream. */
    public static final Terminal UNDEF = new Terminal("undef");
    /** Represents the end of the token stream and the end of parsing. */
    public static final Terminal EOTS = new Terminal("$");
    /** Represents the keyword {@code if}. */
    public static final Terminal IF = new Terminal("if");
    /** Represents the keyword {@code else}. */
    public static final Terminal ELSE = new Terminal("else");
    /** Represents the keyword {@code for}. */
    public static final Terminal FOR = new Terminal("for");
    /** Represents the keyword {@code while}. */
    public static final Terminal WHILE = new Terminal("while");
    /** Represents the keyword {@code foreach}. */
    public static final Terminal FOREACH = new Terminal("foreach");
    /** Represents the keyword {@code switch}. */
    public static final Terminal SWITCH = new Terminal("switch");
    /** Represents the keyword {@code case}. */
    public static final Terminal CASE = new Terminal("case");
    /** Represents the keyword {@code do}. */
    public static final Terminal DO = new Terminal("do");
    /** Represents the keyword {@code return}. */
    public static final Terminal RETURN = new Terminal("return");
    /** Represents the keyword {@code continue}. */
    public static final Terminal CONTINUE = new Terminal("continue");
    /** Represents the keyword {@code skip}. */
    public static final Terminal SKIP = new Terminal("skip");
    /** Represents the keyword {@code break}. */
    public static final Terminal BREAK = new Terminal("break");
    /** Represents the data type {@code byte}. */
    public static final Terminal BYTE = new Terminal("byte");
    /** Represents the data type {@code bool}. */
    public static final Terminal BOOL = new Terminal("bool");
    /** Represents the data type {@code int}. */
    public static final Terminal INT = new Terminal("int");
    /** Represents the data type {@code float}. */
    public static final Terminal FLOAT = new Terminal("float");
    /** Represents the data type {@code double}. */
    public static final Terminal DOUBLE = new Terminal("double");
    /** Represents the data type {@code string}. */
    public static final Terminal STRING = new Terminal("string");
    /** Represents the data type {@code void}. */
    public static final Terminal VOID = new Terminal("void");
    /** Represents the data type {@code char}. */
    public static final Terminal CHAR = new Terminal("char");
    /** Represents the data type {@code long}. */
    public static final Terminal LONG = new Terminal("long");
    /** Represents the data type {@code short}. */
    public static final Terminal SHORT = new Terminal("short");
    /** Represents the sub-type {@code pointer}. */
    public static final Terminal PTR = new Terminal("ptr");
    /** Represents the sub-type {@code reference}. */
    public static final Terminal REF = new Terminal("ref");
    /** Represents the keyword {@code thread}. */
    public static final Terminal THREAD = new Terminal("thread");
    /** Represents the object type {@code class}. */
    public static final Terminal CLASS = new Terminal("class");
    /** Represents the object type {@code struct}. */
    public static final Terminal STRUCT = new Terminal("struct");
    /** Represents the object type {@code enum}. */
    public static final Terminal ENUM = new Terminal("enum");
    /** Represents the object type {@code interface}. */
    public static final Terminal INTERFACE = new Terminal("interface");
    /** Represents the object type {@code namespace}. */
    public static final Terminal NAMESPACE = new Terminal("namespace");
    /** Represents the keyword {@code use}. */
    public static final Terminal USE = new Terminal("use");
    /** Represents the modifier {@code global}. */
    public static final Terminal GLOBAL = new Terminal("global");
    /** Represents the modifier {@code external}. */
    public static final Terminal EXTERNAL = new Terminal("external");
    /** Represents the modifier {@code internal}. */
    public static final Terminal INTERNAL = new Terminal("internal");
    /** Represents the modifier {@code local}. */
    public static final Terminal LOCAL = new Terminal("local");
    /** Represents the modifier {@code secure}. */
    public static final Terminal SECURE = new Terminal("secure");
    /** Represents the modifier {@code static}. */
    public static final Terminal STATIC = new Terminal("static");
    /** Represents the modifier {@code const}. */
    public static final Terminal CONST = new Terminal("const");
    /** Represents the modifier {@code immutable}. */
    public static final Terminal IMMUTABLE = new Terminal("immutable");
    /** Represents the keyword {@code new}. */
    public static final Terminal NEW = new Terminal("new");
    /** Represents the keyword {@code this}. */
    public static final Terminal THIS = new Terminal("this");
    /** Represents the keyword {@code super}. */
    public static final Terminal SUPER = new Terminal("super");
    /** Represents the symbol for addition. */
    public static final Terminal ADD = new Terminal("+");
    /** Represents the symbol for subtraction. */
    public static final Terminal SUB = new Terminal("-");
    /** Represents the symbol for multiplication. */
    public static final Terminal MUL = new Terminal("*");
    /** Represents the symbol for division. */
    public static final Terminal DIV = new Terminal("/");
    /** Represents the symbol for modulo. */
    public static final Terminal MOD = new Terminal("%");
    /** Represents the symbol for incrementation. */
    public static final Terminal INC = new Terminal("++");
    /** Represents the symbol for decrementation. */
    public static final Terminal DCM = new Terminal("--");
    /** Represents the symbol for the operation of summation. */
    public static final Terminal ADDEQ = new Terminal("+=");
    /** Represents the symbol for the operation of deduction.  */
    public static final Terminal SUBEQ = new Terminal("-=");
    /** Represents the symbol for the operation of production. */
    public static final Terminal MULEQ = new Terminal("*=");
    /** Represents the symbol for the operation of quotient. */
    public static final Terminal DIVEQ = new Terminal("/=");
    /** Represents the symbol for the operation of modulus. */
    public static final Terminal MODEQ = new Terminal("%=");
    /** Represents the symbol for boolean flip. */
    public static final Terminal NOT = new Terminal("!");
    /** Represents the symbol for logical not. */
    public static final Terminal NOTEQ = new Terminal("!=");
    /** Represents the symbol for assignments. */
    public static final Terminal ASG = new Terminal("=");
    /** Represents the symbol for the equals comparison. */
    public static final Terminal LEQ = new Terminal("==");
    /** Represents the symbol for the less than comparison. */
    public static final Terminal LESS = new Terminal("<");
    /** Represents the symbol for the greater than comparison. */
    public static final Terminal MORE = new Terminal(">");
    /** Represents the symbol for the less than or equal to comparison. */
    public static final Terminal LESSEQ = new Terminal("<=");
    /** Represents the symbol for the greater than or equal to comparison. */
    public static final Terminal MOREQ = new Terminal(">=");
    /** Represents the symbol for the binary left shift operation. */
    public static final Terminal LSH = new Terminal("<<");
    /** Represents the symbol for the binary right shift operation. */
    public static final Terminal RSH = new Terminal(">>");
    /** Represents the symbol for the binary left shift assignment operation. */
    public static final Terminal LSHEQ = new Terminal(">>=");
    /** Represents the symbol for the binary right shift assignment operation. */
    public static final Terminal RSHEQ = new Terminal("<<=");
    /** Represents the symbol for the binary logical left shift operation. */
    public static final Terminal LLSH = new Terminal("<<<");
    /** Represents the symbol for the binary logical right shift operation. */
    public static final Terminal LRSH = new Terminal(">>>");
    /** Represents the symbol for the binary and operation. */
    public static final Terminal BWA = new Terminal("&");
    /** Represents the symbol for the binary or operation. */
    public static final Terminal BWO = new Terminal("|");
    /** Represents the symbol for the logical and operation. */
    public static final Terminal AND = new Terminal("&&");
    /** Represents the symbol for the logical or operation. */
    public static final Terminal OR = new Terminal("||");
    /** Represents the symbol for the binary and assignment operation. */
    public static final Terminal BWAEQ = new Terminal("&=");
    /** Represents the symbol for the binary or assignment operation. */
    public static final Terminal BWOEQ = new Terminal("|=");
    /** Represents the symbol for member accesses. */
    public static final Terminal DOT = new Terminal(".");
    /** Represents the symbol for ellipsis. */
    public static final Terminal ELL = new Terminal("...");
    /** Represents the symbol for a colon. */
    public static final Terminal COL = new Terminal(":");
    /** Represents the symbol for scope resolution. */
    public static final Terminal SRO = new Terminal("::");
    /** Represents the symbol for the binary exclusive or operation. */
    public static final Terminal BWX = new Terminal("^");
    /** Represents the symbol for the binary exclusive or assignment operation. */
    public static final Terminal BWXEQ = new Terminal("^=");
    /** Represents the symbol for the binary flip, or binary not. */
    public static final Terminal BWN = new Terminal("~");
    /** Represents the symbol for the beginning of a delimited scope. */
    public static final Terminal LBRC = new Terminal("{");
    /** Represents the symbol for the end of a delimited scope. */
    public static final Terminal RBRC = new Terminal("}");
    /** Represents the symbol for the beginning of an array declaration. */
    public static final Terminal LBRK = new Terminal("[");
    /** Represents the symbol for the end of and array declaration. */
    public static final Terminal RBRK = new Terminal("]");
    /** Represents the symbol for the beginning of a parameter list. */
    public static final Terminal LPAR = new Terminal("(");
    /** Represents the symbol for the end of a parameter list. */
    public static final Terminal RPAR = new Terminal(")");
    /** Represents the symbol for the end of a statement. */
    public static final Terminal SMC = new Terminal(";");
    /** Represents the symbol for the end of a condition in a ternary statement. */
    public static final Terminal TERN = new Terminal("?");
    /** Represents the symbol for performing a null coalescing operation. */
    public static final Terminal COA = new Terminal("??");
    /** Represents the symbol for list separation. */
    public static final Terminal COM = new Terminal(",");
}
    
