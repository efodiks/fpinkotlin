package chapter12.exercises.ex12

import arrow.Kind
import chapter10.ForOption
import chapter10.None
import chapter10.Some
import chapter10.fix
import chapter12.Applicative
import chapter12.ForList
import chapter12.ForTree
import chapter12.Traversable
import chapter12.Tree
import chapter12.fix
import chapter12.solutions.ex19.applicative

//tag::init1[]
fun <A> optionTraversable(): Traversable<ForOption> = object : Traversable<ForOption> {
    override fun <G, A, B> traverse(
        fa: Kind<ForOption, A>,
        AG: Applicative<G>,
        f: (A) -> Kind<G, B>
    ): Kind<G, Kind<ForOption, B>> {
        return when(val a = fa.fix()) {
            is Some -> AG.map(f(a.get), ::Some)
            is None -> AG.unit(a)
        }
    }
}

//end::init1[]

//tag::init2[]
fun <A> listTraversable(): Traversable<ForList> = object : Traversable<ForList> {
    override fun <G, A, B> traverse(
        fa: Kind<ForList, A>,
        AG: Applicative<G>,
        f: (A) -> Kind<G, B>
    ): Kind<G, Kind<ForList, B>> {
        return fa.fix().foldLeft(AG.unit(chapter12.List.empty())) { accG, a ->
            AG.map2(accG, f(a)) { list, b -> chapter12.Cons(b, list.fix()) }
        }
    }
}
//end::init2[]

//tag::init3[]
fun <A> treeTraversable(): Traversable<ForTree> = object : Traversable<ForTree> {
    override fun <G, A, B> traverse(
        fa: Kind<ForTree, A>,
        AG: Applicative<G>,
        f: (A) -> Kind<G, B>
    ): Kind<G, Kind<ForTree, B>> {
        return AG.map2(
            f(fa.fix().head),
            listTraversable<A>().traverse(fa.fix().tail, AG) { tree -> traverse(tree, AG, f)
            }
        ) { b: B, kind: Kind<ForList, Kind<ForTree, B>> ->
            Tree(b, kind.fix().map { it.fix() })
        }
    }
}

//end::init3[]