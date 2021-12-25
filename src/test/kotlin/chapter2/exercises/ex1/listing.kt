package chapter2.exercises.ex1

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import kotlinx.collections.immutable.persistentMapOf

class Exercise1 : WordSpec({
    // tag::init[]
    fun fib(i: Int): Int {
        tailrec fun go(curr: Int, next: Int, counter: Int): Int {
            return if (counter == i) {
                curr
            } else go(next, curr + next, counter + 1)
        }
        return go(0, 1, 0) // end::init[]
    }

    "fib" should {
        "return the nth fibonacci number" {
            persistentMapOf(
                1 to 1,
                2 to 1,
                3 to 2,
                4 to 3,
                5 to 5,
                6 to 8,
                7 to 13,
                8 to 21
            ).forEach { (n, num) ->
                fib(n) shouldBe num
            }
        }
    }
})
