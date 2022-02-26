package chapter8.exercises.ex4

import chapter8.RNG
import chapter8.State

data class Gen<A>(val sample: State<RNG, A>) {
    companion object {
        //tag::init[]
        fun choose(start: Int, stopExclusive: Int): Gen<Int> =
            Gen(
                State { rng ->
                    val (i, rng2) = rng.nextInt()
                    val range = stopExclusive - start
                    (i % range) + start to rng2
                }
            )
        //end::init[]
    }
}
