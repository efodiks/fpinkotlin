package chapter10.exercises.ex10

import chapter10.Monoid

sealed class WC
data class Stub(val chars: String) : WC()
data class Part(val ls: String, val words: Int, val rs: String) : WC()

// tag::init1[]
fun wcMonoid(): Monoid<WC> = object : Monoid<WC> {
    override fun combine(a1: WC, a2: WC): WC {
        return when {
            a1 is Stub && a2 is Stub -> Stub(a1.chars + a2.chars)
            a1 is Stub && a2 is Part -> Part(a1.chars + a2.ls, a2.words, a2.rs)
            a1 is Part && a2 is Stub -> Part(a1.ls, a1.words, a1.rs + a2.chars)
            a1 is Part && a2 is Part -> Part(a1.ls, a1.words + a2.words + (if ((a1.rs + a2.ls).isEmpty()) 0 else 1), a2.rs)
            else -> throw IllegalStateException()
        }
    }

    override val nil: WC = Stub("")
}
// end::init1[]
