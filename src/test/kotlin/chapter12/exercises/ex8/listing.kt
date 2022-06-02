package chapter12.exercises.ex8

import arrow.Kind
import chapter12.Applicative
import chapter12.Product
import chapter12.ProductPartialOf
import chapter12.fix
import utils.SOLUTION_HERE

//tag::init1[]
fun <F, G> product(
    AF: Applicative<F>,
    AG: Applicative<G>
): Applicative<ProductPartialOf<F, G>> = object : Applicative<ProductPartialOf<F, G>> {
    override fun <A> unit(a: A): Kind<ProductPartialOf<F, G>, A> {
        return Product(AF.unit(a) to AG.unit(a))
    }

    override fun <A, B, C> map2(
        fa: Kind<ProductPartialOf<F, G>, A>,
        fb: Kind<ProductPartialOf<F, G>, B>,
        f: (A, B) -> C
    ): Kind<ProductPartialOf<F, G>, C> {
        val fc = AF.map2(
            fa.fix().value.first,
            fb.fix().value.first,
            f
        )
        val gc = AG.map2(
            fa.fix().value.second,
            fb.fix().value.second,
            f
        )
        return Product(fc to gc)
    }
}
//end::init1[]
