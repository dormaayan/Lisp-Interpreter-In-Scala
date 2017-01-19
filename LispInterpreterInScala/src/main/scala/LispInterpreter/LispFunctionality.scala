package LispInterpreter

class Converter[T](l: Seq[T]) {
  def toDouble = try {
    l.map {
      case l: Long => l.toDouble
      case d: Double => d
    }
  } catch {
    case e: MatchError => throw new InterpreterException("couldn't convert to double: " + e)
  }

  def toLong = try {
    l.map {
      case l: Long => l
      case d: Double => d.toLong
    }
  } catch {
    case e: MatchError => throw new InterpreterException("couldn't convert to long: " + e)
  }

  def allLong = l.forall {
    case l: Long => true
    case _ => false
  }

  def eval(env: Scope) = l.map(e => Interpreter.eval(e, env))
}

object Aux {
  implicit def converter[T](l: Seq[T]) = new Converter[T](l)
}

import LispInterpreter.Aux._

object LispFunctionality {
  /**
    * Definitions of operation's groups
    */
  val arithmeticOperations = List("+", "-", "*", "/", "%", "min", "max")
  val comparisonOperations = List("<", ">", ">=", "<=", "=", "!=")
  val listOperations = List("list", "car", "cdr", "last", "init", "cons", "append", "shuffle")
  val stringOperations = List("to-string", "concat", "print")

  def opD(l: List[Any], f: (Double, Double) => Double, env: Scope) = {
    l.eval(env).toDouble.reduce(f)
  }

  def opL(l: List[Any], f: (Long, Long) => Long, env: Scope) = {
    l.eval(env).toLong.reduce(f)
  }

  def lispFunctionality(l: List[Any], env: Scope): PartialFunction[String, Any] = {


    case "atom" => argCount(l, 1)
      Interpreter.eval(l(1), env) match {
        case l: List[Any] => false
        case _ => true
      }

    //Handle arithmetic operations
    case ao: String if arithmeticOperations.contains(ao) =>
      val args = l.tail.eval(env)
      ao match {
        case "+" => if (args.allLong) args.toLong.sum else args.toDouble.sum
        case "*" => if (args.allLong) args.toLong.product else args.toDouble.product
        case "min" => if (args.allLong) args.toLong.min else args.toDouble.min
        case "max" => if (args.allLong) args.toLong.max else args.toDouble.max
        case "-" => if (args.allLong) args.toLong.reduce(_ - _) else args.toDouble.reduce(_ - _)
        case "/" => if (args.allLong) args.toLong.reduce(_ / _) else args.toDouble.reduce(_ / _)
      }

    //Handle comparison operations
    case co: String if comparisonOperations.contains(co) =>
      co match {
        case "<" => compare(_ < _, -1, l.tail, env)
        case ">" => compare(_ > _, 1, l.tail, env)
        case ">=" => compare(_ >= _, 0, l.tail, env)
        case "<=" => compare(_ <= _, 0, l.tail, env)
        case "=" => l.tail.eval(env).distinct.length == 1
        case "!=" => l.tail.eval(env).distinct.length > 1
      }

    //Handle string operations that are implemented here
    case so: String if stringOperations.contains(so) =>
      so match {
        case "to-string" => argCount(l, 1); Interpreter.eval(l(1), env).toString
        case "concat" => l.tail.eval(env).mkString
        case "print" => println(l.tail.eval(env).mkString)
      }

    //Handle operations over lists
    case lo: String if listOperations.contains(lo) =>
      lo match {

        case "list" => l.tail.eval(env)

        case "car" => argCount(l, 1)
          Interpreter.eval(l(1), env) match {
            case list: List[Any] => list.head
            case Name(s) => s.head
            case str: String => str.head
            case _ => throw new InterpreterException("can't get head of non-list")
          }

        case "cons" => argCount(l, 2)
          Interpreter.eval(l(2), env) match {
            case list: List[Any] => Interpreter.eval(l(1), env) :: list
            case _ => throw new InterpreterException("can't cons to non-list")
          }

        case "append" => l.tail.eval(env).flatMap {
          case list: List[Any] => list
          case _ => throw new InterpreterException("can't append non-lists")
        }

        case "shuffle" => argCount(l, 1)
          Interpreter.eval(l(1), env) match {
            case list: List[Any] => util.Random.shuffle(list)
            case _ => throw new InterpreterException("can't shuffle a non-list")
          }

        case "cdr" => argCount(l, 1)
          Interpreter.eval(l(1), env) match {
            case list: List[Any] => list.tail
            case Name(s) => s.tail
            case str: String => str.tail

            case _ => throw new InterpreterException("can't get tail of non-list")
          }

        case "last" => argCount(l, 1)
          Interpreter.eval(l(1), env) match {
            case list: List[Any] => list.last
            case Name(s) => s.last
            case str: String => str.last

            case _ => throw new InterpreterException("can't get tail of non-list")
          }

        case "init" => argCount(l, 1)
          Interpreter.eval(l(1), env) match {
            case list: List[Any] => list.init
            case Name(s) => s.init
            case str: String => str.init
            case _ => throw new InterpreterException("can't get tail of non-list")
          }

      }

  }

  def compare(
               op: (Double, Double) => Boolean,
               initOffset: Double,
               n: Seq[Any],
               scope: Scope
             ): Boolean = {
    val xs = n.eval(scope).toDouble
    xs.fold((xs.head + initOffset, true)) {
      case ((prev: Double, valid: Boolean), cur: Double) =>
        if (valid && op(prev, cur)) (cur, true) else (cur, false)
    } match {
      case (_, flag: Boolean) => flag
    }
  }

  def argCount(l: List[Any], n: Int) {
    if (l.length - 1 != n) throw new InterpreterException(
      "incorrect number of arguments".format())
  }
}