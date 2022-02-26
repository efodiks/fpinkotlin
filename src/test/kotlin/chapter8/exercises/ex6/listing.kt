package chapter8.exercises.ex6

import chapter8.RNG
import chapter8.State

// tag::init[]
data class Gen<A>(val sample: State<RNG, A>) {

    companion object {
        fun <A> sequence(ls: List<Gen<A>>): Gen<List<A>> =
            Gen(State.sequence(ls.map { it.sample }))

        fun <A> listOfN(gn: Gen<Int>, ga: Gen<A>): Gen<List<A>> =
            gn.flatMap { n ->
                sequence(List(n) { ga })
            }
    }

/*    fun <B> flatMap(f: (A) -> Gen<B>): Gen<B> = Gen(
        State { rng ->
            val (bGen, rng2) = sample.map(f).run(rng)
            bGen.sample.run(rng2)
        }
    )*/

    fun <B> flatMap(f: (A) -> Gen<B>): Gen<B> = Gen(
        sample.flatMap { f(it).sample }
    )
}
// end::init[]
