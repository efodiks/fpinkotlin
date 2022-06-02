package chapter12.exercises.ex10

import arrow.Kind
import chapter11.Monad
import chapter12.Composite
import chapter12.CompositePartialOf
import chapter12.fix
import utils.SOLUTION_HERE

interface Listing<F, G> {

    //tag::init1[]
    fun <F, G> compose(
        mf: Monad<F>,
        mg: Monad<G>
    ): Monad<CompositePartialOf<F, G>> = object : Monad<CompositePartialOf<F, G>> {
        override fun <A> unit(a: A): Kind<CompositePartialOf<F, G>, A> {
            return Composite(mf.unit(mg.unit(a)))
        }

        override fun <A, B> flatMap(
            fa: Kind<CompositePartialOf<F, G>, A>,
            f: (A) -> Kind<CompositePartialOf<F, G>, B>
        ): Kind<CompositePartialOf<F, G>, B> {
            throw Exception()
        }
/*            return mf.flatMap(fa.fix().value) { ga: Kind<G, A> ->
                mg.flatMap(ga) { a: A ->
                    f(a)
                }
            }
        }*/
    }
    //end::init1[]
}
