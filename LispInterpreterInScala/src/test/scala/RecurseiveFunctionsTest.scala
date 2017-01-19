package LispInterpreter

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

/**
  * Test Class for Checking Recursive Functions
  */
class RecurseiveFunctionsTest extends FlatSpec with ShouldMatchers {
  val interpreter = new LispInterpreter()


  "Fibonacci" should "return the right result" in {

    /**
      * The definition of the function
      */
    interpreter.interprateLine("(define fib (lambda (n) (if (< n 3) 1 (+ (fib (- n 1)) (fib (- n 2))))))")

    interpreter.interprateLine("(fib 1)") should equal(1)
    interpreter.interprateLine("(fib 2)") should equal(1)
    interpreter.interprateLine("(fib 10)") should equal(55)
    interpreter.interprateLine("(fib 15)") should equal(610)
  }

  "Factorial" should "return the right result" in {

    /**
      * The definition of the function
      */
    interpreter.interprateLine("(define fact (lambda (n) (if (< n 2) 1 (* n (fact (- n 1))))))")

    interpreter.interprateLine("(fact 0)") should equal(1)
    interpreter.interprateLine("(fact 2)") should equal(2)
    interpreter.interprateLine("(fact 9)") should equal(362880)
  }


}
