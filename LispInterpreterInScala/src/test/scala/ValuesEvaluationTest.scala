package LispInterpreter

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers


/**
  * Test Class for Values Evaluation
  */
class ValuesEvaluationTest extends FlatSpec with ShouldMatchers {
  val interpreter = new LispInterpreter()

  "Numbers" should "should be evaluated propely" in {
    interpreter.interprateLine("99") should equal(99)
    interpreter.interprateLine("-21.3") should equal(-21.3)
    interpreter.interprateLine("75.3412") should equal(75.3412)
    interpreter.interprateLine("2e4") should equal(2e4)
    interpreter.interprateLine("-8e-23") should equal(-8e-23)
  }

  "Booleans" should "should be evaluated propely" in {
    interpreter.interprateLine("true") should equal(true)
    interpreter.interprateLine("false") should equal(false)
  }

  "Strings" should "should be evaluated propely" in {
    interpreter.interprateLine("\"Dor & Liran\"") should equal("Dor & Liran")
  }

}
