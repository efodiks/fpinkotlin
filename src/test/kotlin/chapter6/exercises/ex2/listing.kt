package chapter6.exercises.ex2

import chapter6.RNG
import chapter6.solutions.ex1.nonNegativeInt
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class Exercise2 : WordSpec({

    // tag::init[]
    fun double(rng: RNG): Pair<Double, RNG> {
        val (nextInt, nextRng) = nonNegativeInt(rng)
        return (nextInt / (Int.MAX_VALUE.toDouble() + 1)) to nextRng
    }
    // end::init[]

    "double" should {

        val unusedRng = object : RNG {
            override fun nextInt(): Pair<Int, RNG> = TODO()
        }

        "generate a max value approaching 1 based on Int.MAX_VALUE" {

            val rngMax = object : RNG {
                override fun nextInt(): Pair<Int, RNG> =
                    Int.MAX_VALUE to unusedRng
            }

            double(rngMax) shouldBe (0.9999999995343387 to unusedRng)
        }

        "generate a min value of 0 based on 0" {
            val rngMin = object : RNG {
                override fun nextInt(): Pair<Int, RNG> =
                    0 to unusedRng
            }

            double(rngMin) shouldBe (0.0 to unusedRng)
        }
    }
})
