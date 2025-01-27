package chapter11.exercises.ex6

import arrow.Kind
import chapter10.List
import chapter11.Functor

interface Monad<F> : Functor<F> {

    fun <A> unit(a: A): Kind<F, A>
    fun <A, B> flatMap(fa: Kind<F, A>, f: (A) -> Kind<F, B>): Kind<F, B>

    override fun <A, B> map(fa: Kind<F, A>, f: (A) -> B): Kind<F, B> =
        flatMap(fa) { a -> unit(f(a)) }

    // tag::init[]
    fun <A> filterM(
        ms: List<A>,
        f: (A) -> Kind<F, Boolean>
    ): Kind<F, List<A>> =
        ms.foldLeft(unit(List.empty<A>())) { accf, a ->
            flatMap(accf) { acc ->
                map(f(a)) { b ->
                    if (b) List.append(
                        acc,
                        List.of(a)
                    ) else acc
                }
            }
        }
    // end::init[]
}
