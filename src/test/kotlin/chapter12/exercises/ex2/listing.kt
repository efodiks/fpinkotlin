package chapter12.exercises.ex2

import arrow.Kind
import chapter12.Functor
import utils.SOLUTION_HERE

//tag::init1[]
interface Applicative<F> : Functor<F> {

    fun <A, B> apply(
        fab: Kind<F, (A) -> B>,
        fa: Kind<F, A>
    ): Kind<F, B> =
        map2(fab, fa) { ab: (A) -> B, a: A ->
            ab(a)
        }

    fun <A> unit(a: A): Kind<F, A>

    override fun <A, B> map(
        fa: Kind<F, A>,
        f: (A) -> B
    ): Kind<F, B> =
        apply(unit(f), fa)

    fun <A, B, C> map2(
        fa: Kind<F, A>,
        fb: Kind<F, B>,
        f: (A, B) -> C
    ): Kind<F, C> {
        val curried = { a: A -> { b: B -> f(a, b) } }
        return apply(apply(unit(curried), fa), fb)
    }
}
//end::init1[]
