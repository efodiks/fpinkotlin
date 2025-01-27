package chapter11.exercises.ex11

import arrow.Kind
import chapter11.Functor
import utils.SOLUTION_HERE

interface Monad<F> : Functor<F> {

    fun <A> unit(a: A): Kind<F, A>

    fun <A, B> flatMap(fa: Kind<F, A>, f: (A) -> Kind<F, B>): Kind<F, B>

    override fun <A, B> map(fa: Kind<F, A>, f: (A) -> B): Kind<F, B> =
        flatMap(fa) { a -> unit(f(a)) }

    //tag::init[]
    fun <A> join(mma: Kind<F, Kind<F, A>>): Kind<F, A> =
        flatMap(mma) { x -> x }
    //end::init[]
}
