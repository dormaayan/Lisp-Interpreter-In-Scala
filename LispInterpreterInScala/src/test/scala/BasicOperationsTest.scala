package LispInterpreter

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

/**
  * Test Class For Basic Operations
  */
class BasicOperationsTest extends FlatSpec with ShouldMatchers {

  val interpreter = new LispInterpreter()

  "Add & Subtract" should "Return the right result" in {
    interpreter.interprateLine("(+ 1 2 3 9 -8)") should equal(7)
    interpreter.interprateLine("(+ 1 2 3)") should equal(6)
    interpreter.interprateLine("(- 1 2 3)") should equal(-4)
    interpreter.interprateLine("(+ (- 1 2 3) 99)") should equal(95)
  }

  "	Multiply & Divide" should "Return the right result" in {
    interpreter.interprateLine("(* 1 2 3 9 -8)") should equal(-432)
    interpreter.interprateLine("(* 1 2 3)") should equal(6)
    interpreter.interprateLine("(/ 1 2 3)") should equal(1 / 6)
    interpreter.interprateLine("(* (/ 1 2 3.0) 99)") should equal(16.5)
  }

  "Compare Operations" should "Return the right result" in {
    interpreter.interprateLine("(< 1 2)") should equal(true)
    interpreter.interprateLine("(< 99 198.6)") should equal(true)
    interpreter.interprateLine("(< 1 2 3 4 5)") should equal(true)
    interpreter.interprateLine("(< 98 2 111 4)") should equal(false)
    interpreter.interprateLine("(< 555 555)") should equal(false)

    interpreter.interprateLine("(<= 555 555)") should equal(true)

    interpreter.interprateLine("(> 2 1)") should equal(true)
    interpreter.interprateLine("(> 99 198.6)") should equal(false)
    interpreter.interprateLine("(> 1 2 3 4 5)") should equal(false)
    interpreter.interprateLine("(> 98 2 111 4)") should equal(false)
    interpreter.interprateLine("(> 555 555)") should equal(false)

    interpreter.interprateLine("(>= 555 555)") should equal(true)

    interpreter.interprateLine("(= 21.5 21.6)") should equal(false)
    interpreter.interprateLine("(= 3.3 3.3)") should equal(true)
    interpreter.interprateLine("(= 111 111)") should equal(true)
    interpreter.interprateLine("(= 2 2 99 2 2)") should equal(false)

  }



  "Multi-line code" should "not crash" in {
    interpreter.interprateAll(
      """
			(define a 1)
			(define b 2)
			(define l '(1 2 3 4))
      			""")

    interpreter.interprateLine("(+ a b)") should equal(3)
    interpreter.interprateLine("(- a b)") should equal(-1)

    interpreter.interprateLine("(< a b)") should equal(true)
    interpreter.interprateLine("(< b a)") should equal(false)

    interpreter.interprateLine("(= a b)") should equal(false)
    interpreter.interprateLine("(= a a)") should equal(true)

    interpreter.interprateLine("(> a b)") should equal(false)
    interpreter.interprateLine("(> b a)") should equal(true)


  }


  it should "still work if only one element is passed" in {
    interpreter.interprateLine("(+ 7)") should equal(7)

    interpreter.interprateLine("(- 7)") should equal(7)
  }


}
