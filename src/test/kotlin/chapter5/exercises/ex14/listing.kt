package chapter5.exercises.ex14

import chapter4.None
import chapter4.Some
import chapter5.Stream
import chapter5.exercises.ex13.zipAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class Exercise14 : WordSpec({

    // tag::startswith[]
    fun <A> Stream<A>.startsWith(that: Stream<A>): Boolean =
        zipAll(that).foldRight(
            { true },
            { (first, second), acc ->
                if (!acc()) {
                    false
                } else {
                    when {
                        first is None && second is Some -> false
                        first is Some && second is Some ->
                            first.get == second.get
                        else -> true
                    }
                }
            }
        )
    // end::startswith[]

    "Stream.startsWith" should {
        "detect if one stream is a prefix of another" {
            Stream.of(1, 2, 3).startsWith(
                Stream.of(1, 2)
            ) shouldBe true
        }
        "detect if one stream is not a prefix of another" {
            Stream.of(1, 2, 3).startsWith(
                Stream.of(2, 3)
            ) shouldBe false
        }
    }
})
