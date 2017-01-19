package LispInterpreter

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

/**
  * Test Class for Operations Over Lists
  */
class ListsOperations extends FlatSpec with ShouldMatchers {

  val interpreter = new LispInterpreter()

  interpreter.interprateLine("(define lst '(555 222 345))");


  "car" should "give the head of the list" in {
    interpreter.interprateLine("(car (list 9999))") should equal(9999)
    interpreter.interprateLine("(car (list 10 9 8))") should equal(10)
    interpreter.interprateLine("(car (list (list 10 9) 8))") should equal(List(10, 9))
    interpreter.interprateLine("(car lst)") should equal(555)
  }

  "cdr" should "give the rest of the list" in {
    interpreter.interprateLine("(cdr (list 9999))") should equal(List())
    interpreter.interprateLine("(cdr (list 10 9 8))") should equal(List(9, 8))
    interpreter.interprateLine("(cdr (list (list 10 9) 8))") should equal(List(8))
    interpreter.interprateLine("(cdr lst)") should equal(List(222, 345))
  }

  "range" should "give a range of numbers" in {
    interpreter.interprateLine("(range 2)") should equal(List(0, 1))
    interpreter.interprateLine("(range 9 13)") should equal(List(9, 10, 11, 12))
  }



  "length" should "return the length of a list" in {
    interpreter.interprateLine("(length (range 65))") should equal(65)
    interpreter.interprateLine("(length (list 10 9 8))") should equal(3)
    interpreter.interprateLine("(length (cdr (list (list 10 9) 8)))") should equal(1)
    interpreter.interprateLine("(length (car (list (list 10 9) 8)))") should equal(2)
  }

  "append" should "return a merged list of all the given lists" in {
    interpreter.interprateLine("(append '(1 2) '(3 4))") should equal(List(1, 2, 3, 4))
    interpreter.interprateLine("(append lst lst )") should equal(List(555, 222, 345, 555, 222, 345))
  }

  "map" should "map the values of a list from a lanbda expression to a new list" in {
    interpreter.interprateLine("(map (lambda (t) (* t 5)) (list 9 8 6))") should equal(List(45, 40, 30))
    interpreter.interprateLine("(map (lambda (x) (+ x 1)) (range 3))") should equal(List(1, 2, 3))
  }

  "reduce" should "work right" in {
    interpreter.interprateLine("(reduce + (list 1 2 3))") should equal(6)
    interpreter.interprateLine("(reduce * (range 1 5))") should equal(24)
  }

  "filter" should "filter a list with a given Lambda expression" in {
    interpreter.interprateLine("(filter (lambda (y) (< y 3)) (range 15))") should equal(List(0, 1, 2))
    interpreter.interprateLine("(filter (lambda (y) (= (/ y 1) 0)) (range 15))") should equal(List(0))

  }

  "foldl" should "work right" in {
    interpreter.interprateLine("(foldl + 1 (range 6))") should equal(16)
    interpreter.interprateLine("(foldl * 2 (range 1 5))") should equal(48)
  }
}
