package LispInterpreter

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

/**
  * Test Class for a complex lisp code Merge-Sort
  */
class MergeSortTest extends FlatSpec with ShouldMatchers {
  val interpreter = new LispInterpreter()

  "Merge sort" should "work" in {
    interpreter.interprateAll(
      """
        (define msort
                (lambda (list)
        (if (<= (length list) 1)
        list
                (begin
                        (define split (/ (length list) 2))
        (merge
                (msort (subseq list 0 split))
        (msort (subseq list split))
          )
          )
          )
          )
          )

        (define merge
                (lambda (a b)
        (if (< (length a) 1)
        b
                (if (< (length b) 1)
        a
                (if (< (car a) (car b))
        (cons (car a) (merge (cdr a) b))
        (cons (car b) (merge a (cdr b)))
        )
        )
        )
        )
        )""")

    /**
      * Checking the merging
      */
    interpreter.interprateLine("(merge '(2 6) '())") should equal(List(2, 6))
    interpreter.interprateLine("(merge '(1 4) '(8 9))") should equal(List(1, 4, 8, 9))

    /**
      * Checking the sorting
      */
    interpreter.interprateLine("(msort '(10 7 9 8))") should equal(List(7, 8, 9, 10))
    interpreter.interprateLine("(msort '(100.7 23.5 -99))") should equal(List(-99, 23.5, 100.7))
  }

}





