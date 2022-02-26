package chapter8.exercises.ex11

import chapter8.RNG
import chapter8.State
import utils.SOLUTION_HERE

data class Gen<A>(val sample: State<RNG, A>) {

    fun <B> map(f: (A) -> B): Gen<B> =
        Gen(sample.map(f))

    fun <B> flatMap(f: (A) -> Gen<B>): Gen<B> =
        Gen(sample.flatMap { a -> f(a).sample })
}

//tag::init[]
data class SGen<A>(val forSize: (Int) -> Gen<A>) {

    operator fun invoke(i: Int): Gen<A> =
        forSize(i)

    fun <B> map(f: (A) -> B): SGen<B> =
        SGen { forSize(it).map(f) }

    fun <B> flatMap(f: (A) -> Gen<B>): SGen<B> =
        SGen { forSize(it).flatMap(f) }
}
//end::init[]
