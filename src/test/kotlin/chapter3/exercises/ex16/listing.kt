package chapter3.exercises.ex16

import chapter3.Cons
import chapter3.List
import chapter3.foldLeft
import chapter3.reverse
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

// tag::init[]
fun doubleToString(xs: List<Double>): List<String> =
    foldLeft(reverse(xs), List.empty()) { acc, x ->
        Cons(
            x.toString(),
            acc
        )
    }
// end::init[]

class Exercise16 : WordSpec({
    "list doubleToString" should {
        "convert every double element to a string" {
            doubleToString(List.of(1.1, 1.2, 1.3, 1.4)) shouldBe
                List.of("1.1", "1.2", "1.3", "1.4")
        }
    }
})
