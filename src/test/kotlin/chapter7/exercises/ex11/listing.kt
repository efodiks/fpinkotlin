package chapter7.exercises.ex11

import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

typealias Par<A> = (ExecutorService) -> Future<A>

// tag::init[]
fun <K, V> choiceMap(
    key: Par<K>,
    choices: Map<K, Par<V>>
): Par<V> = {
    choices.getValue(key(it).get())(it)
}
// end::init[]
