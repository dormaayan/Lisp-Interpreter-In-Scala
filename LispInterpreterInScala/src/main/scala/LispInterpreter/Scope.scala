package LispInterpreter


/**
  * The Scope of definitions (functions/variabels) that the interpreter knows
  * Contains 2 kind of deifinitions :
  *   - Predifined Definitions of built-in functions in Lisp
  *   - New Functions that the user adds
  */
class Scope(parent: Scope) {

  //A map of names of definitions to the definitions
  val nameToDef: collection.mutable.Map[String, Any] = collection.mutable.Map[String, Any]()

  /**
    * Add a new definition to the scope
    *
    * @param name       function's name
    * @param definition function's defintion
    */
  def define(name: String, definition: Any) {
    definition match {
      case f: Function =>
        nameToDef.get(name) match {
          case Some(t: FunctionTable) =>
            t.add(f)
          case _ =>
            val t = new FunctionTable()
            t.add(f)
            nameToDef(name) = t
        }
      case _ => nameToDef(name) = definition
    }
  }

  /**
    * Update an existing definiton in the scope
    *
    * @param name       the function's name
    * @param definition the function's body/value
    */
  def update(name: String, definition: Any) {
    nameToDef.get(name) match {
      case Some(_) => nameToDef(name) = definition
      case None => parent match {
        case null => throw new InterpreterException(name)
        case p => p(name) = definition
      }
    }
  }

  /**
    * Applies the function to the args.
    *
    * @param name the function's name
    * @return
    */
  def apply(name: String): Option[Any] = nameToDef.get(name) match {
    case None => parent match {
      case null => None
      case _ => parent(name)
    }
    case v => v
  }

  /**
    *
    * @param name   the function's name
    * @param degree the degree of the function
    * @return
    */
  def getFunction(name: String, degree: Int): Option[Function] = nameToDef.get(name) match {
    case None => parent match {
      case null => None
      case _ => parent.getFunction(name, degree)
    }
    case Some(t: FunctionTable) =>
      t(degree) match {
        case None => parent match {
          case null => None
          case _ => parent.getFunction(name, degree)
        }
        case f => f
      }
    case _ => None
  }
}

/**
  * A definition of the function table
  */
class FunctionTable() {
  val degreeToFuncitonMap = collection.mutable.Map[Int, Function]() //a map from the degree of a function to the function itself

  /**
    * Add a function with a specific degree to the function table
    *
    * @param f the function
    */
  def add(f: Function) {
    degreeToFuncitonMap(f.degree) = f
  }

  /**
    * Get a function with a specific degree if the one exists
    *
    * @param degree the degree (arity) of the function
    * @return
    */
  def apply(degree: Int): Option[Function] = degreeToFuncitonMap.get(degree)

}


/**
  * Definition of a function
  *
  * @param parms the parameters of the function
  * @param body  the body of the function
  */
case class Function(parms: List[String], body: Any) {
  def degree = parms.length //the number of parameters of the function
}