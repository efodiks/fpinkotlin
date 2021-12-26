package chapter3.exercises.ex22

import chapter3.Cons
import chapter3.List
import chapter3.Nil
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

// tag::init[]
fun <A> zipWith(xa: List<A>, xb: List<A>, f: (A, A) -> A): List<A> {
    tailrec fun go(
        acc: List<A>,
        xa2: List<A>,
        xb2: List<A>
    ): List<A> {
        return when (xa2) {
            is Nil -> acc
            is Cons -> when (xb2) {
                is Nil -> acc
                is Cons -> go(
                    Cons(f(xa2.head, xb2.head), acc),
                    xa2.tail,
                    xb2.tail
                )
            }
        }
    }
    return go(List.empty(), xa, xb).reverse()
}
// end::init[]

class Exercise22 : WordSpec({
    "list zipWith" should {
        "apply a function to elements of two corresponding lists" {
            zipWith(
                List.of(1, 2, 3),
                List.of(4, 5, 6)
            ) { x, y -> x + y } shouldBe List.of(5, 7, 9)
        }
    }
})
