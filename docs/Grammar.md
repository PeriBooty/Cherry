# Cherry Grammar
This file contains all the explicit constructs and scopes of the Cherry computational
language in BNF format as well as at least one example of usage for clarification.

**NOTE**: *Annotations are likely to change, and some of the other structures may but
likely they will remain. Annotations are not the easiest to mix in with the rest
of the code, so suggestions are accepted!*

-----

```bnf
// global start symbol
<document> ::= <start> <document> $ | ε

// local start
<start> ::= <directive> | <packaging> | <object> | ε

// directive
<directive> ::= "use" <member-access> ";"
<member-access> ::= "id" "." <member-access> | ε
use System;
use System.Console;

// packaging or namespace
<packaging> ::= "namespace" <member-access> ";"
namespace Test;
namespace Example.Two;

// object
<object> ::= <object-modifiers> <inheritance> <object-type> "id" <object-block>
<object-modifiers> ::= <annotation> <accessors> <adapters> | ε
<annotation> ::= "id" <annotation-parameter-list> | ε
<annotation-parameter-list> ::= "(" "id" <id-list> ")" | ε
<id-list> ::= "," "id" <id-list> | ε
<accessors> ::= "global" | "local" | "secure" | "internal" | "external" | ε
<adapters> ::= "static" | "const" | "immutable" | "abstract" | "override" | ε
<inheritance> ::= <single-inheritance> | <multi-inheritance> | ε
<single-inheritance> ::= <class-enum-inheritance> | <interface-struct-inheritance> | ε
<multi-inheritance> ::= <class-enum-inheritance> ":" <interface-struct-inheritance> | ε
<class-enum-inheritance> ::= "id" | ε
<interface-struct-inheritance> ::= "id" <id-list> | ε
<object-type> ::= <generic> <object-classification>
<generic> ::= <extension> "id" <id-list> | ε
<extension> ::= "id" <id-list> ":" | ε
<object-classification> ::= "class" | "enum" | "interface" | "struct"
<object-block> ::= "{" <object-block-contents> "}"
<object-block-contents> ::= <declaration> <object-block-contents>
						  | <method> <object-block-contents> | ε

// object example
Serializable global abstract Object:Castable,Type class T Class {}

// declarations
<declaration> ::= <field-modifiers> <field-type> <variables-values> ";"
<field-modifiers> ::= <annotation> <accessors> <adapters> | ε
<field-type> ::= <generic> <data-type> <array-declaration> <access-type>
<data-type> ::= "bool" | "char" | "double" | "float" | "int" | "string" | "void" | "id"
<array-declaration> ::= "[" <multidimensional-array> "]" | "..." | ε
<multidimensional-array> ::= "," <multidimensional-array> | ε
<access-type> ::= "ptr" | "ref" | ε
<variables-values> ::= "id" <value> <variable-value-list>
<value> ::= "=" <factor> | ε
<variable-value-list> ::= "," <variables-values> | ε

// declaration examples
Range(5, 10) global static int number = 9, hex = 0x08;
NonNullable Property local T thing;

// methods
<method> ::= <field-modifiers> <field-type> <method-parameters> <block>
<method-parameters> ::= "(" <typed-parameters> ")"
<typed-parameters> ::= <field-type> <variables-values> <typed-parameters-list> | ε
<typed-parameters-list> ::= "," <typed-parameters> | ε
<block> ::= "{" <block-contents> "}"
<block-contents> ::= <inner-declarations> <method-block-contents>
						  | <statements> <method-block-contents>
						  | ε

// method example
internal T T add(T... args) { }

<inner-declarations> ::= <field-type> <variables-values> ";"
<statements> ::= <if-statement>
			   | <for-statement>
			   | <for-each-statement>
			   | <do-statement>
			   | <while-statement>
			   | <switch-statement>
			   | <break-statement>
			   | <skip-statement>
			   | <return-statement>
			   | <function-call>
			   | <assignment-statement>
<if-statement> ::= "if" <factor> <block> <else-statement> | ε
<else-statement> ::= "else" <if-statement> | "else" <block> | ε
<for-statement> ::= "for" <for-argument> <block> | ε
<for-argument> ::= "(" <inner-declaration> <singleton-statement> <singleton-statement> ")"
<for-each-statement> ::= "foreach" <foreach-argument> <block> | ε
<foreach-argument> ::= "(" <foreach-parameter> "in" "id" ")"
<foreach-parameter> ::= <data-type> "id" | "id"
<do-statement> ::= "do" <block> "while" <factor> | ε
<while-statement> ::= "while" <factor> <block>
<switch-statement> ::= "switch" <factor> <switch-block> | ε
<switch-block> ::= "{" <switch-block-contents> <default-statement> "}"
<default-statement> ::= "default" ":" <case-block>
<case-block> ::= <block> | <block-contents> | ε
<switch-block-contents> ::= <case-statement> <switch-block-contents> | ε
<case-statement> ::= "case" <factor> ":" <case-block>
<break-statment> ::= "break" ";"
<skip-statement> ::= "skip" <factor> ";"
<return-statement> ::= "return" <factor> ";" | "return" ";"
<function-call> ::= "id" <function-arguments> ";"
<function-arguments> ::= "(" <arguments> ")"
<arguments> ::= <factor> <argument-list> | ε
<argument-list> ::= "," <factor> <argument-list> | ε
<assignment-statement> ::= "id" "=" <factor> ";"
<factor> ::= <primitive-value>
		   | <lambda>
		   | <new-statement>
		   | <array-initializer>
		   | <array-access>
		   | "(" <some-statement> ")"
<primitive-value> ::= any basic value including "id"s.
<lambda> ::= <lambda-parameters> "~>" <block>
<lambda-parameters> ::= "(" "id" <id-list> ")" | <typed-parameters>
<new-statement> ::= "new" <field-type> <function-arguments>
				  | "new" <field-type> <array-resolution>
<array-resolution> ::= "[" <primitive-value> <multidimensional-resolution> "]"
<multidimensional-resolution> ::= "," <primitive-value> <multidimensional-resolution> | ε
<array-initializer> ::= "{" <factor> "}"
<array-access> ::= "id" <array-resolution>
```
