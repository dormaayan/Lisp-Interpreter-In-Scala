package LispInterpreter

object Interpreter {
  def eval(expression: Any, environment: Scope): Any = {
    var exp = expression
    var scope = environment
    while (true) {
      exp match {

        case statements: List[Any] => statements.head match {

          /**
            * In case of variable declaration
            */
          case "define" => return statements(1) match {
            case name: String => scope.define(name, eval(statements(2), scope))
            case _ => throw new InterpreterException("Name of a Variable Should be a String")
          }

          /**
            * In case of function declaration
            */
          case "defun" => return statements(1) match {
            case name: String => statements(2) match {
              case parms: List[Any] =>
                val p = parms.map {
                  case n: String => n
                  case _ => throw new InterpreterException("Name of a Parameter Should be a String")
                }
                if (statements.length != 4) throw new InterpreterException("Illegal Definition of Function")
                val f = Function(p, statements(3))
                scope.define(name, f)
              case _ => throw new InterpreterException("Illegal Definition of Function")
            }
            case _ => throw new InterpreterException("Illegal Definition of Function")
          }

          /**
            * The quote function returns its first argument, unevaluated
            */
          case "quote" => return statements(1)

          /**
            * In case of Anonymous Function declaration
            */

          case "lambda" => return statements(1) match {
            case parms: List[Any] =>
              val p = parms.map {
                case n: String => n
                case _ => throw new InterpreterException("Name of a Parameter Should be a String")
              }
              Function(p, statements(2))
            case _ => throw new InterpreterException("Illegal definition of Lambda Expression")
          }

          /**
            * In case of If statement
            */
          case "if" => eval(statements(1), scope) match {
            case false => exp = statements(3)
            case true => exp = statements(2)
          }

          case "set!" => return statements(1) match {
            case name: String => scope.update(name, eval(statements(2), scope))
            case _ => throw new InterpreterException("Name of a Variable Should be a String")
          }

          /**
            * the value of the last expression is returned.
            */
          case "begin" =>
            statements.tail.init.map(e => eval(e, scope))
            exp = statements.last

          /**
            * create new variable bindings and execute a series of forms that use these bindings
            */
          case "let" => return statements(1) match {
            case names: List[String] => statements(2) match {
              case vals: List[Any] =>
                val letBody = statements(3)
                val letContext = new Scope(scope)
                val args = vals.map(e => eval(e, scope))
                names.zip(args).foreach {
                  case (param: String, value: Any) => letContext.define(param, value)
                }
                eval(letBody, letContext)
              case _ => throw new InterpreterException("Illegal Definition of let")
            }
            case _ => throw new InterpreterException("Illegal Definition of let")
          }

          /**
            * In case we recognize a Lisp existing functionality
            */
          case n: String if LispFunctionality.lispFunctionality(statements, scope).isDefinedAt(n) =>
            return LispFunctionality.lispFunctionality(statements, scope)(n)

          /**
            * In case we recognize a function call
            */
          case s: String => scope.getFunction(s, statements.tail.length) match {
            case None =>
              val fun = LispFunctionality.lispFunctionality(statements, scope)
              val functionName = scope(s) match {
                case Some(s: String) => s
                case _ => throw new InterpreterException(s)
              }
              if (fun.isDefinedAt(functionName)) {
                return fun(functionName)
              } else {
                throw new InterpreterException(s)
              }
            case Some(Function(parms, body)) =>
              val strContext = new Scope(scope)
              val strArgs = statements.tail.map(e => eval(e, scope))
              parms.zip(strArgs).foreach {
                case (param: String, value: Any) => strContext.define(param, value)
              }
              exp = body
              scope = strContext
          }

          case _ => throw new InterpreterException("Illegal Call to Function")
        }

        case str: String => return scope(str) match {
          case None => throw new InterpreterException(str)
          case Some(v) => v
        }

        //Primitive Values
        case d: Double => return d
        case l: Long => return l
        case Name(l) => return l
      }
    }
  }
}

/**
  * Interpetations Exeptions Class
  *
  * @param s exception description
  */
class InterpreterException(s: String) extends Exception(s) {}
