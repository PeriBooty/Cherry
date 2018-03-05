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
package cherry.util.handler.diagnostic;

import cherry.frontend.grammar.NonTerminal;
import cherry.frontend.grammar.Symbol;
import cherry.frontend.grammar.Token;
import cherry.util.handler.flag.FlagHandler.RuntimeFlag;
import java.io.File;
import java.io.IOException;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This class is responsible for handling any diagnostic needs, such as printing
 * the various diagnostics to files and putting them in their various folders.
 * 
 * @author SoraKatadzuma
 * @version 0.0.0.2
 */
public final class DiagnosticHandler {
    /** The root folder for the {@code DiagnosticsHandler} to work from. */
    private static final File ROOT = new File("diagnostics");
    
    /**
     * Prints to a file (filename) all the given tokens of a file scanned by the
     * {@code Lexer}.
     * 
     * @param tokens The tokens found during lexing of a file.
     * @param filename The name of the file to put the results of this method in.
     */
    public static void print(List<Token> tokens, String filename) {
        File subroot = new File(ROOT,"lexer");

        // check if the root folder "/diagnostics" exists.
        if (!ROOT.exists()) ROOT.mkdir();

        // check if the subroot "lexer" exists.
        if (!subroot.exists()) subroot.mkdir();

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            
            Element rootElement = doc.createElement("tokens");
            doc.appendChild(rootElement);
            
            tokens.forEach((token) ->{
                Element tokenElement = doc.createElement("token");
                rootElement.appendChild(tokenElement);
                
                Element typeElement = doc.createElement("type");
                Attr value = doc.createAttribute("value");
                value.setValue(token.type.toString());
                typeElement.setAttributeNode(value);
                tokenElement.appendChild(typeElement);
                
                Element nameElement = doc.createElement("name");
                Attr value1 = doc.createAttribute("value");
                value1.setValue(token.value);
                nameElement.setAttributeNode(value1);
                tokenElement.appendChild(nameElement);
                
                Element lineElement = doc.createElement("line");
                Attr value2 = doc.createAttribute("value");
                value2.setValue(Integer.toString(token.line));
                lineElement.setAttributeNode(value2);
                tokenElement.appendChild(lineElement);
                
                Element columnElement = doc.createElement("column");
                Attr value3 = doc.createAttribute("value");
                value3.setValue(Integer.toString(token.column));
                columnElement.setAttributeNode(value3);
                tokenElement.appendChild(columnElement);
            });
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(subroot, filename + ".xml"));
            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerException | DOMException ex) {
            Logger.getLogger(DiagnosticHandler.class.getName())
                  .log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
    /**
     * Creates a diagnostic file {@code raised_flags.xml} with contents pertaining
     * to the flags raised by the {@code FlagHandler}.
     * 
     * @param raisedFlags
     */
    public static void print(EnumSet<RuntimeFlag> raisedFlags) {
        File subroot = new File(ROOT,"command_line");

        // check if the root folder "/diagnostics" exists.
        if (!ROOT.exists()) ROOT.mkdir();

        // check if the subroot "lexer" exists.
        if (!subroot.exists()) subroot.mkdir();
        
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            
            Element rootElement = doc.createElement("flags");
            doc.appendChild(rootElement);
            
            raisedFlags.forEach((flag) -> {
                Element flagElement = doc.createElement("flag");
                rootElement.appendChild(flagElement);
            });
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(subroot, "raised_flags.xml"));
            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerException | DOMException ex) {
            Logger.getLogger(DiagnosticHandler.class.getName())
                  .log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
    /**
     * Prints to a file one of the grammar attributes: FIRST or FOLLOW, respectively
     * naming the file "FIRSTS.xml" or "FOLLOWS.xml" in a folder named "diagnostics/~".
     * 
     * @param gramAttr The grammar attributes, either FIRST or FOLLOW, that will
     *      be printed to a file in the diagnostics folder.
     * @param firsts Tells whether this is the FIRST set or not, useful for naming
     *      the output file since both FIRST and FOLLOW are the same data type.
     */
    public static void print(Map<NonTerminal, Set<Symbol>> gramAttr, boolean firsts) {
        File subroot = new File(ROOT, "grammar_attributes");
        File file;
        
        // check if root folder "/diagnostics" exists
        if (!ROOT.exists()) ROOT.mkdir();
        
        // check if subroot "/grammar_attributes" exists
        if (!subroot.exists()) subroot.mkdir();
        
        if (firsts) {
            file = new File(subroot, "FIRSTS.xml");
        } else {
            file = new File(subroot, "FOLLOWS.xml");
        }
        
        try {
            // Do file writing.
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            
            // Root of this file.
            Element rootElement = doc.createElement("firsts");
            doc.appendChild(rootElement);
            
            Attr type = doc.createAttribute("type");
            type.setValue("Cherry");
            rootElement.setAttributeNode(type);
            
            gramAttr.keySet().forEach((nt) -> {
                Element node = doc.createElement(nt.name());
                rootElement.appendChild(node);
                
                Set<Symbol> set = gramAttr.get(nt);
                
                set.forEach((sym) -> {
                    String name = sym.symbolName();
                    
                    if (".".equals(name))
                        name = "dot";
                    
                    if ("\u03B5".equals(name))
                        name = "epsilon";
                    
                    Element symbol = doc.createElement(name);
                    node.appendChild(symbol);
                });
            });
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerException | DOMException ex) {
            Logger.getLogger(DiagnosticHandler.class.getName()).log(Level.WARNING, ex.getMessage(), ex);
        }
    }
}
