package chapter7.exercises.ex10

// import chapter7.sec3.Pars
import chapter7.exercises.ex6.map
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

typealias Par<A> = (ExecutorService) -> Future<A>

fun <A> choiceN(n: Par<Int>, choices: List<Par<A>>): Par<A> =
    {
        val nValue = n(it).get()
        choices[nValue](it)
    }

fun <A> choice(cond: Par<Boolean>, t: Par<A>, f: Par<A>): Par<A> =
    choiceN(
        map(cond) {
            if (it) 0
            else 1
        },
        listOf(t, f)
    )
