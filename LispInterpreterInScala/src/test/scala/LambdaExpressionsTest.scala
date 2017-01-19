package LispInterpreter

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

/**
  * Test class for Lambda Expressions
  */
class LambdaExpressionsTest extends FlatSpec with ShouldMatchers {
  val interpreter = new LispInterpreter()

  "Lambdas" should "should be interprate" in {
    interpreter.interprateLine("(define test (lambda (r) (* 3.141592653 (* r r))))")
  }

  it should "execute correctly" in {
    interpreter.interprateLine("(define square (lambda (x) (* x x)))")
    interpreter.interprateLine("(square 4)") should equal(16)
  }


}
