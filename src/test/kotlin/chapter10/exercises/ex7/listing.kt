package chapter10.exercises.ex7

import chapter10.Monoid
import chapter10.stringMonoid
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

// tag::init1[]
fun <A, B> foldMap(la: List<A>, m: Monoid<B>, f: (A) -> B): B {
    return when (val size = la.size) {
        0 -> m.nil
        1 -> f(la.single())
        else -> {
            val first = la.subList(0, size / 2)
            val second = la.subList(size / 2, size)
            m.combine(foldMap(first, m, f), foldMap(second, m, f))
        }
    }
}
// end::init1[]

class Exercise7 : WordSpec({
    "balanced folding foldMap" should {
        "fold a list with an even number of values" {
            foldMap(
                listOf("lorem", "ipsum", "dolor", "sit"),
                stringMonoid
            ) { it } shouldBe "loremipsumdolorsit"
        }
        "fold a list with an odd number of values" {
            foldMap(
                listOf("lorem", "ipsum", "dolor"),
                stringMonoid
            ) { it } shouldBe "loremipsumdolor"
        }
        "fold a list with a single value" {
            foldMap(
                listOf("lorem"),
                stringMonoid
            ) { it } shouldBe "lorem"
        }
        "fold an empty list" {
            foldMap(
                emptyList<String>(),
                stringMonoid
            ) { it } shouldBe ""
        }
    }
})
