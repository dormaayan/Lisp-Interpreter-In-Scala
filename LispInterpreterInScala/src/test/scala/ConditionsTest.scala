package LispInterpreter

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

/**
  * Test Class for If Statements
  */
class ConditionsTest extends FlatSpec with ShouldMatchers {
  val interpreter = new LispInterpreter()

  "A condition" should "evaluate the right result" in {
    interpreter.interprateLine("(if true true false)") should equal(true)
    interpreter.interprateLine("(if false true false)") should equal(false)
    interpreter.interprateLine("(if true 12 13)") should equal(12)
    interpreter.interprateLine("(if false 12 13)") should equal(13)
    interpreter.interprateLine("(if (< 98 1000) \"small\" \"big\")") should equal("small")
    interpreter.interprateLine("(if (< 99 90) \"small\" \"big\")") should equal("big")

  }

}
