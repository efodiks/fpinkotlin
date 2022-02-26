package chapter7.exercises.ex12

import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

typealias Par<A> = (ExecutorService) -> Future<A>

// tag::init[]
fun <A, B> chooser(pa: Par<A>, choices: (A) -> Par<B>): Par<B> =
    {
        choices(pa(it).get())(it)
    }
// end::init[]

fun <A> choice(pa: Par<Boolean>, a: Par<A>, aa: Par<A>) =
    chooser(pa) { if (it) a else aa }

fun <A> choiceN(pa: Par<Int>, aList: List<Par<A>>) =
    chooser(pa) { aList[it] }

fun <A, B> choiceMap(k: Par<A>, map: Map<A, Par<B>>) =
    chooser(k) { map.getValue(it) }
