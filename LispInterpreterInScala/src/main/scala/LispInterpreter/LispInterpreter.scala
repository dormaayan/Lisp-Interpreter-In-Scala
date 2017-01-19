package LispInterpreter

/**
  * Class Which Define an interpreter
  */
class LispInterpreter {

  /**
    * Define a scope to the interpreter and load it with common operations anf their mapping from
    * Lisp to Scala
    */
  val interpreterScope = new Scope(null) {
    override val nameToDef = collection.mutable.Map[String, Any](
      "true" -> true, "false" -> false, "unit" -> (),
      "+" -> "+", "-" -> "-", "*" -> "*", "/" -> "/", "%" -> "%",
      ">" -> ">", ">=" -> ">=", "<" -> "<", "<=" -> "<=", "=" -> "=", "!=" -> "!=",
      "min" -> "min", "max" -> "max"
    )
  }

  /**
    * Interprate and load to the scope of the interpreter existing functions in Lisp
    * That were to complicated to implement in Scsala
    */
  interprateAll(io.Source.fromFile("CommonLispFunctionsImplementations.lisp").mkString)

  /**
    * Interprate a single line from Lisp to Scala
    *
    * @param line a code line
    * @return
    */
  def interprateLine(line: String) = {
    val r = interprateAll(line)
    if (r.length < 1) () else r.last
  }

  /**
    * Interprate a full text with more than one line
    * from Lisp to Scala
    *
    * @param text the code
    * @return
    */
  def interprateAll(text: String) = {
    val ast = LispParser.parse(text.replaceAll(";[^\n$]*", " ").replace("\n", " "))
    ast.map(e => Interpreter.eval(e, interpreterScope))
  }
}