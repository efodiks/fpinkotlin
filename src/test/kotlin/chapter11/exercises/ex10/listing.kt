package chapter11.exercises.ex10

import arrow.core.ForOption
import arrow.core.None
import arrow.core.OptionOf
import arrow.core.Some
import arrow.core.fix
import chapter11.Monad

interface Listing<A> : Monad<ForOption> {

    val v: A
    val f: (A) -> OptionOf<A>

    @Suppress("USELESS_IS_CHECK")
    fun exercise() {
        // l:
        val x: OptionOf<A> = f(v)
        val l = flatMap(x, { a -> unit(a)}) == x
        val l2 = when(val xFixed = x.fix()) {
            is Some -> unit(xFixed.t)
            is None -> x
        } == x
        val l3 = when(x.fix()) {
            is Some -> x
            is None -> x
        } == x
        val l4 = x == x

        // r:
        val r = flatMap((unit(v)), f) == f(v)
        val r2 = flatMap(Some(v), f) == f(v)
        val r3 = when(Some(v)) {
            is Some -> f(v)
            else -> throw IllegalStateException()
        } == f(v)
        val r4 = f(v) == f(v)
    }
}
