package chapter10.exercises.ex5

import arrow.core.extensions.list.foldable.foldLeft
import chapter10.sec1.Monoid

// tag::init1[]
fun <A, B> foldMap(la: List<A>, m: Monoid<B>, f: (A) -> B): B =
    la.foldLeft(m.nil) { b, a ->
        m.combine(f(a), b)
    }
// end::init1[]
