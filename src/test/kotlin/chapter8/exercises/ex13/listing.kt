package chapter8.exercises.ex13

import chapter8.Gen
import chapter8.Prop
import chapter8.Prop.Companion.forAll
import chapter8.SGen
import chapter8.State
import chapter8.sec4_1.run
import utils.SOLUTION_HERE

fun main() {
    //tag::init1[]
    fun <A> nonEmptyListOf(ga: Gen<A>): SGen<List<A>> = SGen {
        Gen(State.sequence(List(it.coerceAtLeast(1)) { ga.sample }))
    }
    //end::init1[]

    val smallInt = Gen.choose(-10, 10)

    //tag::init2[]
    fun maxProp(): Prop =
        forAll(nonEmptyListOf(smallInt)) { ns ->
            val mx = ns.max()
                ?: throw IllegalStateException("max on empty list")
            ns.all { mx > it }
        }
    //end::init2[]
    run(maxProp())
}
