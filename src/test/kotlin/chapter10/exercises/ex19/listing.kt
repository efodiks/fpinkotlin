package chapter10.exercises.ex19

import arrow.Kind
import chapter10.ForList
import chapter10.List
import chapter10.Monoid
import chapter10.asConsList
import chapter10.fix
import chapter10.intAdditionMonoid
import chapter10.mapMergeMonoid
import chapter10.solutions.ex16.Foldable
import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import utils.SOLUTION_HERE

object ListFoldable : Foldable<ForList> {
    override fun <A, B> foldLeft(
        fa: Kind<ForList, A>,
        z: B,
        f: (B, A) -> B
    ): B = fa.fix().foldLeft(z, f)

    //tag::init1[]
    fun <A> bag(la: List<A>): Map<A, Int> =
        foldMap(la, mapMergeMonoid<A, Int>(intAdditionMonoid)) { a ->
            mapOf(a to 1)
        }
    //end::init1[]
}

class Exercise19 : WordSpec({
    "bag" should {
        "bin the contents of a list into a map" {
            assertAll(Gen.list(Gen.choose(0, 10))) { ls ->
                val actual = ListFoldable.bag(ls.asConsList())
                val expected =
                    ls.groupBy { it }.mapValues { it.value.count() }
                actual shouldBe expected
            }
        }
    }
})
