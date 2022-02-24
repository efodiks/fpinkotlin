package chapter3.exercises.ex5

import chapter3.Cons
import chapter3.List
import chapter3.Nil
import chapter3.reverse
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.WordSpec

// tag::init[]
fun <A> init(l: List<A>): List<A> {
    fun go(acc: List<A>, ll: List<A>): List<A> {
        return when {
            ll is Nil -> throw IllegalStateException()
            (ll as Cons).tail is Nil -> reverse(acc)
            else -> go(Cons(ll.head, acc), ll.tail)
        }
    }
    return go(Nil, l)
}
// end::init[]

class Exercise5 : WordSpec({

    "list init" should {
        "return all but the last element" {
            init(List.of(1, 2, 3, 4, 5)) shouldBe
                List.of(1, 2, 3, 4)
        }

        "return Nil if only one element exists" {
            init(List.of(1)) shouldBe Nil
        }

        "throw an exception if no elements exist" {
            shouldThrow<IllegalStateException> {
                init(List.empty<Int>())
            }
        }
    }
})
