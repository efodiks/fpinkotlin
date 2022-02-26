package chapter7.exercises.ex6

import chapter7.exercises.ex3.TimedMap2Future
import chapter7.exercises.ex5.sequence
import chapter7.solutions.ex4.asyncF
import chapter7.solutions.ex4.lazyUnit
import chapter7.solutions.ex4.unit
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

typealias Par<A> = (ExecutorService) -> Future<A>

fun <A> fork(
    a: () -> Par<A>
): Par<A> =
    { es: ExecutorService ->
        es.submit(Callable<A> { a()(es).get() })
    }

fun <A, B> map(
    a: Par<A>,
    f: (A) -> B
): Par<B> =
    map2(a, unit(Unit)) { it, _ ->
        f(it)
    }

// tag::init[]
fun <A> parFilter(
    sa: List<A>,
    f: (A) -> Boolean
): Par<List<A>> = fork {
    val saPar = sa.map { lazyUnit { it } }
    map(sequence(saPar)) {
        it.filter(f)
    }
}
// end::init[]

fun words(paragraphs: List<String>): Par<Int> {
    val f = asyncF<String, Int> { it.split(" ").count() }
    return map(sequence(paragraphs.map(f))) {
        it.fold(0) { a, b -> a + b }
    }
}

fun <A, B, C> map2(
    a: Par<A>,
    b: Par<B>,
    f: (A, B) -> C
): Par<C> = {
    TimedMap2Future(
        a(it),
        b(it),
        f
    )
}

fun <A, B, C, D> map3(
    a: Par<A>,
    b: Par<B>,
    c: Par<C>,
    f: (A, B, C) -> D
): Par<D> =
    map2(map2(a, b) { a2, b2 -> { c: C -> f(a2, b2, c) } }, c) { f2, c2 ->
        f2(c2)
    }

fun <A, B, C, D, E> map4(
    a: Par<A>,
    b: Par<B>,
    c: Par<C>,
    d: Par<D>,
    f: (A, B, C, D) -> E
): Par<E> =
    map2(
        map2(a, b) { a2, b2 -> { c: C, d: D -> f(a2, b2, c, d) } },
        map2(c, d) { c2, d2 -> { f2: (C, D) -> E -> f2(c2, d2) } }
    ) { f2, f3 ->
        f3(f2)
    }

fun <A, B, C, D, E, F> map5(
    a: Par<A>,
    b: Par<B>,
    c: Par<C>,
    d: Par<D>,
    e: Par<E>,
    f: (A, B, C, D, E) -> F
) = map2(
    map4(a, b, c, d) { a2, b2, c2, d2 ->
        { e2: E -> f(a2, b2, c2, d2, e2) }
    },
    e
) { f2, e2 ->
    f2(e2)
}
