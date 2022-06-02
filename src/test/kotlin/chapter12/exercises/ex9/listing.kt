package chapter12.exercises.ex9

import arrow.Kind
import chapter12.Applicative
import chapter12.Composite
import chapter12.CompositePartialOf
import chapter12.fix
import utils.SOLUTION_HERE

//tag::init1[]
fun <F, G> compose(
    AF: Applicative<F>,
    AG: Applicative<G>
): Applicative<CompositePartialOf<F, G>> = object : Applicative<CompositePartialOf<F, G>> {
    override fun <A> unit(a: A): Kind<CompositePartialOf<F, G>, A> {
        return Composite(AF.unit(AG.unit(a)))
    }

    override fun <A, B, C> map2(
        fa: Kind<CompositePartialOf<F, G>, A>,
        fb: Kind<CompositePartialOf<F, G>, B>,
        f: (A, B) -> C
    ): Kind<CompositePartialOf<F, G>, C> {
        return Composite(
            AF.map2(
                fa.fix().value,
                fb.fix().value
            ) { ag, bg ->
                AG.map2(ag, bg, f)
            }
        )
    }
}
//end::init1[]
