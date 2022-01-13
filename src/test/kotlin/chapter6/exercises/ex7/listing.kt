package chapter6.exercises.ex7

import chapter3.Cons
import chapter3.List
import chapter3.Nil
import chapter3.foldRight
import chapter6.RNG
import chapter6.Rand
import chapter6.rng1
import chapter6.solutions.ex6.map2
import chapter6.unit
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import utils.SOLUTION_HERE

class Exercise7 : WordSpec({

    // tag::init[]
    fun <A> sequence(fs: List<Rand<A>>): Rand<List<A>> {
        return when (fs) {
            is Nil -> { r1: RNG -> Nil to r1 }
            is Cons -> { r: RNG ->
                val (el, rng) = fs.head(r)
                val (list, finalRng) = sequence(fs.tail)(rng)
                Cons(el, list) to finalRng
            }
        }
    }
    // end::init[]

    // tag::init2[]
    fun <A> sequence2(fs: List<Rand<A>>): Rand<List<A>> =
        foldRight(fs, { it -> Nil to it }) { x, acc ->
            map2(x, acc) { a: A, b: List<A> -> Cons(a, b) }
        }
    // end::init2[]

    fun ints2(count: Int, rng: RNG): Pair<List<Int>, RNG> {
        tailrec fun go(count: Int, acc: List<Rand<Int>>): List<Rand<Int>> {
            return if (count <= 0) {
                acc
            } else {
                go(count - 1, Cons(
                    { it.nextInt() },
                    acc
                ))
            }
        }
        return sequence2(go(count, Nil))(rng)
    }

    "sequence" should {

        "combine the results of many actions using recursion" {

            val combined: Rand<List<Int>> =
                sequence(
                    List.of(
                        unit(1),
                        unit(2),
                        unit(3),
                        unit(4)
                    )
                )

            combined(rng1).first shouldBe
                List.of(1, 2, 3, 4)
        }

        """combine the results of many actions using
            foldRight and map2""" {

            val combined2: Rand<List<Int>> =
                sequence2(
                    List.of(
                        unit(1),
                        unit(2),
                        unit(3),
                        unit(4)
                    )
                )

            combined2(rng1).first shouldBe
                List.of(1, 2, 3, 4)
        }
    }

    "ints" should {
        "generate a list of ints of a specified length" {
            ints2(4, rng1).first shouldBe
                List.of(1, 1, 1, 1)
        }
    }
})
