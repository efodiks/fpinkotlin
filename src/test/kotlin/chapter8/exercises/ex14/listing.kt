package chapter8.exercises.ex14

import chapter8.Gen
import chapter8.Prop
import chapter8.SGen
import chapter8.sec4_1.run

val smallInt = Gen.choose(-10, 10)

fun List<Int>.prepend(i: Int) = listOf(i) + this

fun maxProp(): Prop = Prop.forAll(SGen.listOf(smallInt)) {
    val nss = it.sorted()
    nss.size == it.size &&
        nss.containsAll(it) &&
        nss.zipWithNext().all { (p, n) -> p <= n }
}

fun main() {
    run(maxProp())
}
