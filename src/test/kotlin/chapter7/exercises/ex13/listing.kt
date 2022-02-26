package chapter7.exercises.ex13

import chapter7.exercises.ex6.map
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

typealias Par<A> = (ExecutorService) -> Future<A>

// tag::init[]
fun <A> join(a: Par<Par<A>>): Par<A> = {
    a(it).get()(it)
}
// end::init[]

fun <A, B> flatMap(a: Par<A>, f: (A) -> Par<B>): Par<B> = join(
    map(a, f)
)

fun <A> join2(a: Par<Par<A>>): Par<A> =
    flatMap(a) { it }
