package chapter10.exercises.ex12

import arrow.Kind
import chapter10.Monoid
import chapter10.endoMonoid
import utils.SOLUTION_HERE

//tag::init1[]
interface Foldable<F> {

    fun <A, B> foldRight(fa: Kind<F, A>, z: B, f: (A, B) -> B): B =
        foldMap<A, (B) -> B>(fa, endoMonoid()) {a -> { b: B -> f(a, b) } }(z)

    fun <A, B> foldLeft(fa: Kind<F, A>, z: B, f: (B, A) -> B): B =
        foldMap<A, (B) -> B>(fa, endoMonoid()) { a: A -> { b: B -> f(b, a) }  }(z)

    fun <A, B> foldMap(fa: Kind<F, A>, m: Monoid<B>, f: (A) -> B): B =
        foldLeft(fa, m.nil) { b, a -> m.combine(b, f(a)) }
}
//end::init1[]
