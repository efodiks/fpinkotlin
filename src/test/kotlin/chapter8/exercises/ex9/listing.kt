package chapter8.exercises.ex9

import chapter8.RNG

typealias TestCases = Int

sealed class Result {
    abstract fun isFalsified(): Boolean
}

object Passed : Result() {
    override fun isFalsified(): Boolean = false
}

typealias SuccessCount = Int
typealias FailedCase = String

data class Falsified(
    val failure: FailedCase,
    val successes: SuccessCount
) : Result() {
    override fun isFalsified(): Boolean = true
}

// tag::init[]
data class Prop(val run: (TestCases, RNG) -> Result) {
    fun and(p: Prop): Prop = Prop { t: TestCases, rng: RNG ->
        val r1 = this@Prop.run(t, rng)
        when {
            r1.isFalsified() -> r1
            else -> p.run(t, rng)
        }
    }

    fun or(p: Prop): Prop = Prop { t: TestCases, rng: RNG ->
        val r1 = this@Prop.run(t, rng)
        when {
            r1.isFalsified() -> p.run(t, rng)
            else -> r1
        }
    }
}
// end::init[]
