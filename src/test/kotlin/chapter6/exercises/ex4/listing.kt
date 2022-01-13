package chapter6.exercises.ex4

import chapter3.Cons
import chapter3.List
import chapter3.Nil
import chapter6.RNG
import chapter6.rng1
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class Exercise4 : WordSpec({

    // tag::init[]
    fun ints(count: Int, rng: RNG): Pair<List<Int>, RNG> {
        tailrec fun go(
            il: List<Int>,
            rng: RNG,
            count: Int
        ): Pair<List<Int>, RNG> =
            if (count == 0) {
                il to rng
            } else {
                val (i, rng2) = rng.nextInt()
                go(Cons(i, il), rng2, count - 1)
            }
        return go(Nil, rng, count)
    }
    // end::init[]

    "ints" should {
        "generate a list of ints of a specified length" {

            ints(5, rng1) shouldBe (List.of(1, 1, 1, 1, 1) to rng1)
        }
    }
})
