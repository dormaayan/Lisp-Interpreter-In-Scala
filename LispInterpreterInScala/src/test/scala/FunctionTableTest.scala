package LispInterpreter

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

/**
  * Test Class of the Function Table
  */
class FunctionTableTest extends FlatSpec with ShouldMatchers {
  val interpreter = new LispInterpreter()


  "Definitions of functions " should "works" in {
    interpreter.interprateAll("(defun func1 (t) 1)")
    interpreter.interprateLine("(+ 1 (func1 true))") should equal(2)
  }

  "Functions with the same name and a different degree" should "does not override each other in the function table" in {
    interpreter.interprateAll(
      """
      (defun func2 (a) 1)
      (defun func2 (a b) 2)
      (defun func2 (a b c) 3)
      (define func2 (lambda (a b c d) 4))
      """)

    interpreter.interprateLine("(func2 0)") should equal(1)
    interpreter.interprateLine("(func2 0 0)") should equal(2)
    interpreter.interprateLine("(func2 0 0 0)") should equal(3)
    interpreter.interprateLine("(func2 0 0 0 0)") should equal(4)
  }

}
