# Cherry
Cherry is a 4th generation computation language designed to tie up loose ends in
the community of programming by inheriting some of the best implementations of
other languages while preserving its own unique nature and including other
interesting things. **This repository houses the distribution and source of the
compiler for the language**.

## Goals of Cherry
The goals of Cherry are simple: provide an Object Oriented Programming (OOP)
syntax that is widely accepted, such as those as C, C++, C#, Java, and other C
related languages; provide a rich standard library and easy to use classes and
structures; and powerful implementations of the former.

## End goal of Cherry
To have formed one of the newest and soon to be leading computational languages
in the community, whilst having it community driven and community manipulatable.
As one of Cherry's end goals, while not a main goal, is to allow the community
to help form it and make it the great language that it could be.

## Syntax
Syntax is not final yet, but the general syntax will be provided here once it is.
However the running example for the moment is this little snippet:
```
# Directives and Namespaces
use System.Console;       # Namespace directives include all namespace objects
use System.Type.Object;   # Class directives include just the class object it references

namespace Test;           # Namespaces work for a full file or until parsing reaches namespace.

@Annotation               # Example annotation

@Template(T)
global class Example {
    # Constructor
    global Example(T tempex) {
        this.tempex = tempex;
    }

    # Declaration examples
    internal string name = "Barry";
    global T tempex;
    local int number = new int(0x05);

    # Method example
    global int add(int... numbers) {
        int result = 0;
        
        foreach (int number in numbers) {
            result += number;
        }

        return result;
    }

    # Method with template example
    @Template(R : System.Type.Number)
    global R add(R... nums) {
        R result = 0;

        for (System.Type.Number number in nums) {
            result += number;
        }

        return result;
    }

    # Main method
    global static void Main(String... args) {
        Float Example ex = new Example(0.123);
        Console.write(ex.add(98, -234.673, 0x23, 0b10100100, ex.tempex));
    }
}
```

## Foreground and Background implementations
Cherry's compiler will consist of a handwritten front end, including it's own
personal Lexer (as these are typically easy to write) and it's own fully
extensible Parser. This Parser is unique as it will be built with the idea of
combinatory parsers, or micro parsers, that of which can be used to form a full
parser by calling one micro parser from another, emulating the top-down approach
of LL parsers whilst preserving the ability to report informative error reports
as soon as they are encountered unless the error is context driven. In the
future this will likely change to an enhanced GLR Parser Generator for. However,
for now it will remain as it is for the simplicity of the programmers. Not to
mention that the micro parsers can be independently called, that they do not
follow a specific structure like a generator would need. This can typically lead
to less things to code, but generators are often smaller than handwritten parsers
are anyways. The backend is comprised of a intermediate generator (generates
assembly three address or four address code) and an NASM assembler and linker
which will be used to assembly and link the assembly produced by the compiler
intermediate stage. This will cause a slow up in the compiler but it is worth it
for the amount of power the language provides.

## Contact and collaboration
If you would like to join the effort in helping build Cherry then you can contact
me personally at [my email](dungeonsandanime@gmail.com) or at my Discord
[SoraKatadzuma#7406](https://discordapp.com). If you want to keep up with the
work being done on Cherry from other than your own GitHub or email, then you can
join our [Discord server](https://discordapp.com/channels/382078993505189888/382085275968602112).