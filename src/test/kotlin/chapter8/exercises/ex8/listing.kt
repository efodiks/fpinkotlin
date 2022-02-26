package chapter8.exercises.ex8

import chapter8.RNG
import chapter8.State
import chapter8.double

data class Gen<A>(val sample: State<RNG, A>) {
    companion object {

        // tag::init[]
        fun <A> weighted(
            pga: Pair<Gen<A>, Double>,
            pgb: Pair<Gen<A>, Double>
        ): Gen<A> =
            Gen<Double>(
                State {
                    double(it)
                }
            ).flatMap {
                val sum = pga.second + pgb.second
                if (pga.second / sum < it) {
                    pga.first
                } else pgb.first
            }
        // end::init[]
    }

    fun <B> flatMap(f: (A) -> Gen<B>): Gen<B> =
        Gen(sample.flatMap { a -> f(a).sample })
}
