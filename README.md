# Simple
## A simple language for String procession.
This project was created for the Compilers course at the Faculty of Informatics of the University of Debrecen.

### Basic Syntax
In simple, there is only two type for variables and functions: **string** and **integer**.
You can declare variables or functions of these types with either *str* or *int* keywords.

In the main scope, there are four rules:
* You can declare variables
* You can declare functions
* You **must** declare a function called *"main"*
* You can use capital or non-capital letters, numbers, and *"_"* for naming variables, and thir names must not start with numbers. 

No assignments, expressions or any other statements are allowed in the main scope, except this three type.

Within the block of each function, you can however:
* Declare variables
* Declare other functions
* Assign values to priveously declared variables
* Call functions
* Use *while*, *if* or *for* block and cycles.
* Use various built-in operations

You can end each statement with the ';' character, expect for function declarations, and blocks.

*Note, that you cannot assign value to variables directly when declaring them, you'll have to declare them first, and assign value to them in another statement*

Nearly everything, that returns a value, can be assigned to a variable (if it has the matching type of course):
* Reference to other variables, to copy their value to the other one
* Literals: string values (within two simple or double *quotemarks*), or integer values to variables with the same type
* Functioncalls: you can call System or costum functions, and if they have the proper type of return value, it can be assigned
* You can compare two values with the common logic operators ( *==* *!=* *<* *>* *<=* *>=*), it will return an integer value: 0 if the statement is false, 1 if true. (*For strings, < > <= >= will check the alphabetical order*)
* Arithmetical expressions (+, -, *, /, mod (modulo), ** (power) )

As for if block, and while and for cyclces, they works similar to C, with the following:
* You can check for any value: for integer, 0 stands for false, any other number will stand for true, while for strings "", the empty string stands for false, and any other string stands for true
* You can use the for cycle like this: **for(int iterator, startvalue, upper limit)
* You must use a block (*{...}*) after each cycle, and after the *else* branch, if your *if* statement had one

Simple supports commenting, you can comment out the remaining of the current line with *//* characters, and it will be skiped by the compiler. Also, widespaces, tabs and newlines are skipped as well, to allow the language for better formatting.

Simple also supports variable shadowing, meaning that in another scope, you can re-declare variables already declared before, in other scopes. In such case, until the code exits from that scope (a block stands for a scope), the new variable will be used for that reference. 

There are several built-in functions and string operators in Sipmple (assuming s, r as strings, and n,m as integers):
* s[n] will return the nth character of string s
* s[n,] will return the substring from nth character to the end of string s
* s[,n] will return the substring from the begining to the nth character of string s
* s[n,m] will return the substring from the nth character to the mth character of string s
* s#r will return the index of the first occurence of string r within s, and -1 if it's missing from s
* s##r will return the index of the last occurence of string r within s, and -1 if it's missing from s
* s%r will trim r from the beginning of s, and return r, if it's succesfully removed, or an empty string if s does not start with r
* s%r will trim r from the end of s, and return r, if it's succesfully removed, or an empty string if s does not end with r
* len(s) will return the number of characters in string s
* print(s) will print s to the console
* println(s) will print s to the console appending it with a new line
* read() will read one input from the console, and returns it.
* bm(s, r) returns the index of the first occurence of r in s, or -1 if r does not occur.
* kmp(r, s) returns the index of the first occurence of r in s, or -1 if r does not occur.

### Using the program to compile
This java program can read from input files, and tries to compile and run the code written in *Simple* from those files. Note, that it automatically calls the main() function, and you don't have to. The compiler will try to open the file wich is givan as an argument, or a default "input.dat" if there was no arguments given.

### Errors and warnings
Simple will abort the program whenever it finds a runtime exception, and will write you precisely what was the problem. It will also list you all warnings, after the program that occured during runtime (such as having dead code after a *return* statement).

### Examples
You can find some example codes in the *examples* folder.


