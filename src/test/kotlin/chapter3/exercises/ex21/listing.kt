package chapter3.exercises.ex21

import chapter3.Cons
import chapter3.List
import chapter3.Nil
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

// tag::init[]
fun add(xa: List<Int>, xb: List<Int>): List<Int> {
    tailrec fun go(
        acc: List<Int>,
        xa2: List<Int>,
        xb2: List<Int>
    ): List<Int> {
        return when {
            xa2 is Nil || xb2 is Nil -> acc
            else -> go(
                Cons((xa2 as Cons).head + (xb2 as Cons).head, acc),
                xa2.tail,
                xb2.tail
            )
        }
    }
    return go(List.empty(), xa, xb).reverse()
}

// end::init[]

class Exercise21 : WordSpec({
    "list add" should {
        "add elements of two corresponding lists" {
            add(List.of(1, 2, 3), List.of(4, 5, 6)) shouldBe
                List.of(5, 7, 9)
        }
    }
})
