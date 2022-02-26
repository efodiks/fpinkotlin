package chapter8.exercises.ex15

import chapter7.sec4.Par
import chapter7.sec4.map
import chapter7.sec4.unit
import chapter8.Gen

fun pint2(): Gen<Par<Int>> = Gen.choose(0, 10).map { i ->
    map(unit(i)) {
        it
    }
}
