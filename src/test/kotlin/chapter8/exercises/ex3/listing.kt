package chapter8.exercises.ex3

// tag::init[]
interface Prop {
    fun check(): Boolean
    fun and(p: Prop): Prop = object : Prop {
        override fun check(): Boolean =
            this@Prop.check() && p.check()
    }
}
// end::init[]
