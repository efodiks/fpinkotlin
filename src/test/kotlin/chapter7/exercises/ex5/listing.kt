package chapter7.exercises.ex5

import chapter7.sec1.splitAt
import chapter7.solutions.ex3.TimedMap2Future
import chapter7.solutions.ex4.unit
import chapter7.solutions.ex5.Pars.map
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

typealias Par<A> = (ExecutorService) -> Future<A>

// tag::init1[]
fun <A> sequence(ps: List<Par<A>>): Par<List<A>> =
    when {
        ps.isEmpty() -> unit(emptyList())
        ps.size == 1 -> map(ps.single()) { listOf(it) }
        else -> { it: ExecutorService ->
            ps.splitAt(ps.size / 2).let { (first, second) ->
                TimedMap2Future(
                    sequence(first)(it),
                    sequence(second)(it)
                ) { f, s -> f + s }
            }
        }
    }
// end::init1[]
