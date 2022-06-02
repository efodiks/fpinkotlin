package chapter12.exercises.ex5

import chapter12.EitherMonad
import chapter12.EitherOf
import chapter12.Left
import chapter12.Right
import chapter12.fix

//tag::init1[]
fun <E> eitherMonad(): EitherMonad<E> = object : EitherMonad<E> {
    override fun <A> unit(a: A): EitherOf<E, A> =
        Right(a)


    override fun <A, B> flatMap(
        fa: EitherOf<E, A>,
        f: (A) -> EitherOf<E, B>
    ): EitherOf<E, B> =
        when(val faa = fa.fix()) {
            is Left -> faa
            is Right -> f(faa.value).fix()
        }
}
//end::init1[]
