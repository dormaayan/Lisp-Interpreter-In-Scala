package LispInterpreter

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class VariablesTest extends FlatSpec with ShouldMatchers {
  val interpreter = new LispInterpreter()

  "A variable" should "save its content" in {
    interpreter.interprateLine("(define a 5)")
    interpreter.interprateLine("a") should equal(5)

    interpreter.interprateLine("(define b (+ a a))")
    interpreter.interprateLine("b") should equal(10)
  }


}
