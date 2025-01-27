package chapter6.exercises.ex6

import chapter6.Rand
import chapter6.rng1
import chapter6.unit
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class Exercise6 : WordSpec({

    // tag::init[]
    fun <A, B, C> map2(
        ra: Rand<A>,
        rb: Rand<B>,
        f: (A, B) -> C
    ): Rand<C> = {
        val (a, aRng) = ra(it)
        val (b, bRng) = rb(aRng)
        f(a, b) to bRng
    }

    // end::init[]

    "map2" should {
        "combine the results of two actions" {

            val combined: Rand<String> =
                map2(
                    unit(1.0),
                    unit(1), { d, i ->
                        ">>> $d double; $i int"
                    }
                )

            combined(rng1).first shouldBe ">>> 1.0 double; 1 int"
        }
    }
})
