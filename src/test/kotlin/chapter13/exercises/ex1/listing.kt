package chapter13.exercises.ex1

import arrow.Kind
import chapter11.Monad
import chapter13.boilerplate.free.FlatMap
import chapter13.boilerplate.free.Free
import chapter13.boilerplate.free.FreePartialOf
import chapter13.boilerplate.free.Return
import chapter13.boilerplate.free.fix
import utils.SOLUTION_HERE

//tag::init1[]
fun <F, A, B> Free<F, A>.flatMap(f: (A) -> Free<F, B>): Free<F, B> =
    FlatMap(this, f)

fun <F, A, B> Free<F, A>.map(f: (A) -> B): Free<F, B> =
    FlatMap(this) { Return<F, B>(f(it)) }
//end::init1[]

//tag::init2[]
fun <F> freeMonad(): Monad<FreePartialOf<F>> = object : Monad<FreePartialOf<F>> {
    override fun <A> unit(a: A): Kind<FreePartialOf<F>, A> =
        Return(a)

    override fun <A, B> flatMap(
        fa: Kind<FreePartialOf<F>, A>,
        f: (A) -> Kind<FreePartialOf<F>, B>
    ): Kind<FreePartialOf<F>, B> = fa.fix().flatMap { f(it).fix() }
}
//end::init2[]
