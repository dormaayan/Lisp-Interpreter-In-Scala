#Lisp Interpreter (Scala Implementation)
#Dor Ma’ayan & Liran Farhi
#Winter Semester 2016-2017, the Technion </align>


To run the code please follow the steps:
-	Extract the project and import it to IntelliJ IDE.
-	In the Console, enter SBT.
-	To run our test packages, simply type “test”
-	To run the interpreter, type “run”
o	You can write a Lisp code and after pressing “enter” you will see it interpretation.
o	Note that you can write multiline code simply by keeping the expression unbalanced, the interpretation process will begin only if the already written code is balanced.
o	
We Guarantee that the Interpreter Supports the following Lisp Commands: 

-	Arithmetic Operations
-	Logic Operations
-	List Definition & Operations
-	Conditions (If Statements)
-	Variable Declaration & Use
-	Methods Definitions & Invocation
-	Lambda Expressions


The Implementation in a Nutshell: 

Each line of Lisp code goes through a parsing process using the grammar of Lisp, defined using regular expressions.
The parsing process produce an “AST” which contains all the parsed expressions, then, we execute the appropriate code according to the kind of the expression.
We have a structure which stores all the current scope of the interpreter (i.e. Declared functions and variables). 
Note that some of the functionality was easy to implement in Scala, and some of the functionalities, for example, some operations over lists were implemented in a Lisp code and then, passed and interpretation with our Interpreter, that’s also helped us to test our interpreter.
In case we detected any error during the parsing/interpretation process, we inform the user with an appropriate message
We also wrote tests for different and complex inputs to guarantee that our code works.
For more details on our implementation, please have a look in the internal documentation of the code.

