package LispInterpreter


import org.clapper.argot.{ArgotParser, ArgotUsageException}

import scala.tools.jline

object Main {
  val parser = new ArgotParser("scalisp", preUsage = Some("Version 1.0"))

  val intrpreter = new LispInterpreter()

  def main(args: Array[String]) {
    try {
      parser.parse(args)
      val consoleReader = new jline.console.ConsoleReader()
      Iterator.continually(consoleReader.readLine("LispInterpreter> ")).takeWhile(_ != "").foreach {
        case "exit" | null => sys.exit(0)
        case line =>
          try {
            var src = line
            //if the input is not yet balanced, open a new line, else interprate the input
            while (checkBalance(src)) {
              val input = consoleReader.readLine("       >> ")
              if (input == null || input.length == 0)
                println(intrpreter.interprateLine(src))
              src += " " + input
            }
            println(intrpreter.interprateLine(src))
          }
          catch {
            case e: InterpreterException => println(e)
            case e: MatchError => println(e)
          }
      }
    } catch {
      case e: ArgotUsageException => println(e.message)
    }
  }

  /**
    * Check if the input is balanced or not
    * (In term of paranthesis)
    *
    * @param src the current input
    * @return
    */
  def checkBalance(src: String): Boolean = {
    src.count(_ == '(') != src.count(_ == ')')
  }
}
