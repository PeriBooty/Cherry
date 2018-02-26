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
package cherry.frontend.lexer;

import cherry.frontend.grammar.Token;
import cherry.frontend.grammar.Type;
import cherry.util.exception.TypeConflictException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * /// Class Description ///
 *
 * @author SoraKatadzuma
 * @version alpha 0.0.0.1
 */
public final class Lexer {
    /** We keep a simple reference to the beginning of any file for convenience. */
    private static final int ORIGIN = 0;
    /** The name of the file we are reading. */
    private final String filename;
    /** The line we are currently at in a given file. */
    private int line = 1;
    /** The column we are currently at in a given file. */
    private int column = 0;
    /** Shows the beginning of a token. */
    private int beginning = 0;
    /** Denotes the end of the file. */
    private boolean EOF = false;
    /** Denotes the end of a line. */
    private boolean EOL = false;
    /** A way for us to move around the file easier. */
    private final FileChannel fchan;
    /** The underlying reader. */
    private final FileInputStream fis;
    /** A way for us to build our strings. */
    private final StringBuilder builder = new StringBuilder();
    /** The currently being read character. */
    private char current;
    /** The lexeme we just recently found or put together. */
    private String lexeme;


    /**
     * @param file The file that this lexer will scan.
     * @throws java.io.FileNotFoundException
     */
    public Lexer(File file) throws FileNotFoundException {
        fis = new FileInputStream(file);
        fchan = fis.getChannel();
        filename = file.getName();
    }

    /**
     * Responsible for finding and collecting the tokens of a source file and
     * returning them to the parser for parsing.
     *
     * @return The list of tokens found from the source code of the file.
     * @throws java.io.IOException
     */
    public List<Token> lex() throws Exception {
        // Our tokens to return
        List<Token> tokens = new ArrayList<>();
        // A holder to check our token for null
        Token token;

        // While the end of the file has not been found.
        while (!EOF) {
            // Assign the next token and check it.
            token = nextToken();

            // If that token is not null add it to the tokens we have found.
            if (token != null) tokens.add(token);
        }

        // This means we hit the end of the file so add in a token saying we have.
        // This will help the parser know what is the end of the stream and how
        // to determine the end of parsing.
        tokens.add(new Token(Type.EOTS, "$", filename, line, beginning));

        return tokens;
    }

    /**
     * Uses the FileInputStream to read a character from the source and then
     * returns it, whilst also incrementing where the reader is.
     *
     * @return A character formated integer returned by FileInputStream#read().
     * @throws java.io.IOException
     */
    private char read() throws IOException {
        // Increment the column.
        column++;
        // Return the result of FileInputStream#read().
        return (char)fis.read();
    }

    /**
     * Skips an entire line.
     *
     * @throws java.io.IOException
     */
    private void skipLine() throws IOException {
        while (!EOL && !EOF) {
            // read each character.
            current = read();

            // should catch the newlines and set the EOL flag if necessary.
            if (isSpace(current)) skipSpace();

            if (current == 0xFFFF) EOF = true;
        }

        // EOL was set so turn it off
        if (EOL == true) EOL = !EOL;
    }

    /**
     * Skips spaces and continues for as long as there is space.
     *
     * @throws java.io.IOException
     */
    private void skipSpace() throws IOException {
        while (true) {
            switch (current) {
                case ' ':
                case '\t':
                    current = read();
                    continue;
                case '\n':
                case '\r':
                    current = read();
                    line++;
                    column = 1;
                    EOL = true;
                    continue;
            }

            break;
        }
    }

    /**
     * Returns the next Token in the source code.
     *
     * @return The next source code token.
     * @throws Exception
     */
    private Token nextToken() throws Exception {
        return nextLexeme();
    }

    /**
     * Gets the next lexeme, whether it is a word, number, string, character, or
     * symbol and returns it.
     *
     * @return The lexeme of source code that resembles a possible token.
     * @throws java.io.IOException
     */
    private Token nextLexeme() throws Exception {
        // Our token result.
        Token result;

        // Check that we haven't hit the end of the file.
        if (current == 0xFFFF) EOF = true;

        // Read the next character so we can check it.
        current = read();

        // If it is a comment symbol, skip an entire line.
        // Else if it is a space character, call the skipSpace() method.
        if (current == '#') {
            skipLine();
        } else if (isSpace(current)) {
            skipSpace();
            if (EOL == true) EOL = !EOL;
        }

        // Set the beginning of a token to this column.
        beginning = column;

        // check again for comments
        if (current == '#') skipLine();

        // Check what type of lexeme we will be lexing.
        if (Character.isLetter(current))
            result = nextWord();
        else if (Character.isDigit(current))
            result = nextNumber();
        else if (current == '\'')
            result = nextChar();
        else if (current == '\"')
            result = nextString();
        else if (current != 0xFFFF)
            result = nextSymbol();
        else {
            result = null;
        }

        return result;
    }

    /**
     * Gets the next word lexeme in the source code.
     *
     * @return The next word lexeme as a token.
     * @throws java.io.IOException
     */
    private Token nextWord() throws IOException {
        // For as long as the current character is a letter or digit or _ then
        // append the current character and read again.
        while (Character.isLetterOrDigit(current) || current == '_') {
            builder.append(current);
            current = read();

            if (current == 0xFFFF) {
                EOF = true;
            }
        }

        // compensating.
        seek(-1);
        column--;

        // Build the lexeme and clear the buffer.
        lexeme = builder.toString();
        builder.delete(0, builder.length());

        // Return the result.
        return new Token(Type.get(lexeme), lexeme, filename, line, beginning);
    }

    /**
     * Gets the next number lexeme, which can be in the form of a hexadecimal,
     * binary, octal, integer, decimal, long, or short number.
     *
     * @return The tokenized number lexeme.
     * @throws Exception
     */
    private Token nextNumber() throws Exception {
        Token result;

        // By default.
        Type type = Type.REAL;

        // Append the current number.
        builder.append(current);

        // possibly 0, hex, oct, or bin
        if (current == '0') {
            current = read();

            switch (current) {
                case 'X':
                case 'x':
                    type = Type.HEX;
                    break;
                case 'B':
                case 'b':
                    type = Type.BIN;
                    break;
                default:
                    if (Character.isDigit(current)) type = Type.OCT;
                    else {
                        seek(-1);
                        column--;
                    }
                    break;
            }
        }

        if (type != Type.REAL) {
            builder.append(current);
        }

        current = read();

        switch (type) {
            case HEX:
                while (Character.isDigit(current) || isHexChar(current)) {
                    if (current == 0xFFFF) EOF = true;

                    builder.append(current);
                    current = read();
                }

                break;
            case BIN:
                while (isBinChar(current)) {
                    if (current == 0xFFFF) EOF = true;

                    builder.append(current);
                    current = read();
                }

                break;
            case OCT:
            case REAL:
                while (Character.isDigit(current)) {
                    if (current == 0xFFFF) EOF = true;

                    builder.append(current);
                    current = read();
                }

                if (type == Type.REAL && current == '.') {
                    type = Type.DEC;
                    builder.append(current);
                    current = read();

                    while (Character.isDigit(current)) {
                        if (current == 0xFFFF) EOF = true;

                        builder.append(current);
                        current = read();
                    }
                }

                break;
        }

        if (type != Type.REAL && isOtherType(current)) {
            switch (current) {
                case 'L':
                case 'l':
                    throw new Exception(new TypeConflictException(type, Type.WIDE));
                case 'S':
                case 's':
                    throw new Exception(new TypeConflictException(type, Type.SKINNY));
            }
        }

        if (type == Type.REAL && isOtherType(current)) {
            builder.append(current);

            switch (current) {
                case 'L':
                case 'l':
                    type = Type.WIDE;
                    break;
                case 'S':
                case 's':
                    type = Type.SKINNY;
                    break;
            }
        } else {
            // compensating
            seek(-1);
            column--;
        }

        lexeme = builder.toString();
        builder.delete(0, builder.length());

        return new Token(type, lexeme, filename, line, beginning);
    }

    /***/
    private Token nextString() throws IOException {
        current = read();

        while (current != '\"') {
            if (current == 0xFFFF) EOF = true;

            builder.append(current);
            current = read();
        }

        lexeme = builder.toString();
        builder.delete(0, builder.length());

        return new Token(Type.STRL, lexeme, filename, line, beginning);
    }

    /***/
    private Token nextChar() throws IOException {
        byte indice = 0, limit = 2;

        current = read();
        builder.append(current);
        indice++;

        if (current == '\\') {
            if ((current = read()) == 'u') {
                builder.append(current);
                limit = 9;
            } else {
                limit = 3;
            }
        }

        while (current != '\'') {
            current = read();
            indice++;

            if (current == 0xFFFF) EOF = true;

            if (indice < limit) builder.append(current);
        }

        if (indice > limit)
            System.err.println("Number of characters exceeded expected limit.");

        lexeme = builder.toString();
        builder.delete(0, builder.length());

        return new Token(Type.CHRL, lexeme, filename, line, beginning);
    }

    /***/
    private Token nextSymbol() throws IOException {
        builder.append(current);
        
        // build the lexeme and then get the type when constructing the token.
        switch (current) {
            case '+':
                checkAndBuild('+', '=');
                break;
            case '-':
                checkAndBuild('-', '=');
                break;
            case '*':
                checkAndBuild('*', '=');
                break;
            case '/':
                checkAndBuild('/', '=');
                break;
            case '=':
                checkAndBuild('=');
                break;
            case '%':
            case '!':
            case '^':
                checkAndBuild('=');
                break;
            case '<':
                checkAndBuild('<', '=');
                checkAndBuild('<', '=');
                break;
            case '>':
                checkAndBuild('>', '=');
                checkAndBuild('>', '=');
                break;
            case '&':
                checkAndBuild('&', '=');
                break;
            case '|':
                checkAndBuild('|', '=');
                break;
            case '.':
                checkAndBuild('.');
                if (current == '.') {
                    builder.append(current);
                    current = read();
                }

                break;
            case ':':
                checkAndBuild(':');
                break;
            case '?':
                checkAndBuild('?');
                break;
            case '~':
            case '{':
            case '}':
            case '[':
            case ']':
            case '(':
            case ')':
            case ';':
            case '@':
            case ',':
                break;
        }

        lexeme = builder.toString();
        builder.delete(0, builder.length());

        return new Token(Type.getSymbol(lexeme), lexeme, filename, line, beginning);
    }

    /***/
    private boolean isSpace(char current) {
        return current == ' ' || current == '\t' || current == '\n' || current == '\r';
    }

    /***/
    private boolean isHexChar(char current) {
        return current >= 'a' && current <= 'f' ||
               current >= 'A' && current <= 'F';
    }

    /***/
    private boolean isBinChar (char current) {
        return current == '1' || current == '0';
    }

    /***/
    private boolean isOtherType(char current) {
        return current == 'L' ||
               current == 'l' ||
               current == 'S' ||
               current == 's';
    }

    /***/
    private void seek(int dist) throws IOException {
        fchan.position(fchan.position() + dist);
    }

    /***/
    private void checkAndBuild(char type) throws IOException {
        current = read();

        if (current == type) builder.append(current);
        else {
            seek(-1);
            column--;
        }
    }

    /***/
    private void checkAndBuild(char typeOne, char typeTwo) throws IOException {
        current = read();

        if (current == typeOne || current == typeTwo)
            builder.append(current);
        else {
            seek(-1);
            column--;
        }
    }
}
