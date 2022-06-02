package chapter12.exercises.ex13

import arrow.Kind
import arrow.core.ForId
import arrow.core.extensions.IdApplicative
import arrow.core.fix
import chapter11.exercises.ex16.Id
import chapter12.Applicative
import chapter12.Functor
import utils.SOLUTION_HERE

fun idApplicative(): Applicative<ForId> = SOLUTION_HERE()

//tag::init1[]
interface Traversable<F> : Functor<F> {
    //tag::ignore[]
    fun <G, A, B> traverse(
        fa: Kind<F, A>,
        AG: Applicative<G>,
        f: (A) -> Kind<G, B>
    ): Kind<G, Kind<F, B>> =
        sequence(map(fa, f), AG)

    fun <G, A> sequence(
        fga: Kind<F, Kind<G, A>>,
        AG: Applicative<G>
    ): Kind<G, Kind<F, A>> =
        traverse(fga, AG) { it }
    //end::ignore[]

    override fun <A, B> map(
        fa: Kind<F, A>,
        f: (A) -> B
    ): Kind<F, B> =
        traverse(
            fa,
            object : Applicative<ForId> {
                override fun <A> unit(a: A): Kind<ForId, A> = arrow.core.Id.just(a)
                override fun <A, B, C> map2(
                    fa: Kind<ForId, A>,
                    fb: Kind<ForId, B>,
                    f: (A, B) -> C
                ): Kind<ForId, C> {
                    return fa.fix().flatMap { a -> fb.fix().map { b -> f(a, b) } }
                }
            }
        ) { a: A -> arrow.core.Id.just(f(a)) }.fix().extract()
}
//end::init1[]
