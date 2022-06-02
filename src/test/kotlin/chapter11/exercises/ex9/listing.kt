package chapter11.exercises.ex9

import arrow.Kind
import chapter11.Monad

interface Listing<F, A> : Monad<F> {

    //tag::init0[]
    val f: (A) -> Kind<F, A>
    val x: Kind<F, A>
    val v: A
    //end::init0[]

    fun listing() {

        //tag::initl1[]
        compose(f, { a: A -> unit(a) })(v) == f(v)
        compose({ a: A -> unit(a) }, f)(v) == f(v)
        //end::initl1[]

        val l = compose(f, { a: A -> unit(a) })(v) == f(v) // start
        val l2 = { aa: A -> flatMap(f(aa), { a -> unit(a) }) }(v) == f(v) // express compose in terms of flatMap
        val l3 = flatMap(x, { a -> unit(a)}) == x // replace f with a applied with x

        val r1 = compose({ a: A -> unit(a) }, f)(v) == f(v) // start
        val r2 = { aa: A -> flatMap( { a: A -> unit(a)}(aa), f) }(v) == f(v) // express compose in terms of flatMap
        val r3 = flatMap(unit(v), f) == f(v) // simplify functions, replace argument a with value v

        //tag::init2[]
        flatMap(x) { a -> unit(a) } == x
        flatMap(unit(v), f) == f(v)
        //end::init2[]
    }
}
