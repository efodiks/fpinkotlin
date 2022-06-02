package chapter11.exercises.ex1

import arrow.Kind
import arrow.core.ForListK
import arrow.core.ForSequenceK
import arrow.core.ListK
import arrow.core.SequenceK
import arrow.core.fix
import chapter10.ForList
import chapter10.ForOption
import chapter10.fix
import chapter11.ForPar
import chapter11.fix
import chapter11.sec2.Monad

// tag::init1[]
object Monads {

    fun parMonad(): Monad<ForPar> =
        object : Monad<ForPar> {
            override fun <A> unit(a: A): Kind<ForPar, A> {
                return chapter11.Par.unit(a)
            }

            override fun <A, B> flatMap(
                fa: Kind<ForPar, A>,
                f: (A) -> Kind<ForPar, B>
            ): Kind<ForPar, B> {
                return fa.fix().flatMap { f(it).fix() }
            }
        }

    fun optionMonad(): Monad<ForOption> =
        object : Monad<ForOption> {
            override fun <A> unit(a: A): Kind<ForOption, A> {
                return chapter10.Some(a)
            }

            override fun <A, B> flatMap(
                fa: Kind<ForOption, A>,
                f: (A) -> Kind<ForOption, B>
            ): Kind<ForOption, B> {
                return fa.fix().flatMap { f(it).fix() }
            }
        }

    fun listMonad(): Monad<ForList> =
        object : Monad<ForList> {
            override fun <A> unit(a: A): Kind<ForList, A> {
                return chapter10.List.of(a)
            }

            override fun <A, B> flatMap(
                fa: Kind<ForList, A>,
                f: (A) -> Kind<ForList, B>
            ): Kind<ForList, B> {
                return fa.fix().flatMap { f(it).fix() }
            }
        }

    fun listKMonad(): Monad<ForListK> = object : Monad<ForListK> {
        override fun <A> unit(a: A): Kind<ForListK, A> {
            return ListK(listOf(a))
        }

        override fun <A, B> flatMap(
            fa: Kind<ForListK, A>,
            f: (A) -> Kind<ForListK, B>
        ): Kind<ForListK, B> {
            return fa.fix().flatMap { f(it).fix() }
        }
    }

    fun sequenceKMonad(): Monad<ForSequenceK> =
        object : Monad<ForSequenceK> {
            override fun <A> unit(a: A): Kind<ForSequenceK, A> {
                return SequenceK.just(a)
            }

            override fun <A, B> flatMap(
                fa: Kind<ForSequenceK, A>,
                f: (A) -> Kind<ForSequenceK, B>
            ): Kind<ForSequenceK, B> {
                return fa.fix().flatMap(f)
            }
        }
}
// end::init1[]
