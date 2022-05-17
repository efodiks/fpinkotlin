package chapter9.exercises.ex9

sealed class JSON

object JNull : JSON()
data class JNumber(val get: Double) : JSON()
data class JString(val get: String) : JSON()
data class JBoolean(val get: Boolean) : JSON()
data class JArray(val get: List<JSON>) : JSON()
data class JObject(val get: Map<String, JSON>) : JSON()

object ParseError

interface Parser<out A>

// tag::init[]
abstract class Parsers<PE> {

    // primitives

    internal abstract fun string(s: String): Parser<String>

    internal abstract fun regex(r: String): Parser<String>

    internal abstract fun <A> slice(p: Parser<A>): Parser<String>

    internal abstract fun <A> succeed(a: A): Parser<A>

    internal abstract fun <A, B> flatMap(
        p1: Parser<A>,
        f: (A) -> Parser<B>
    ): Parser<B>

    internal abstract fun <A> or(
        p1: Parser<A>,
        p2: () -> Parser<A>
    ): Parser<A>

    // other combinators here
    fun comma() = string(",")
    fun period() = string(".")
}

abstract class ParsersDsl<PE> : Parsers<PE>() {
    internal fun whiteSpace(): Parser<String> =
        regex("\\s")
    internal fun <A, B> map(
        ap: Parser<A>,
        f: (A) -> B
    ): Parser<B> = flatMap(ap) { a -> succeed(f(a)) }

    internal fun <A, B, C> map2(
        ap: Parser<A>,
        bp: Parser<B>,
        f: (A, B) -> C
    ): Parser<C> = flatMap(ap) { a ->
        map(bp) { b -> f(a, b) }
    }

    internal fun <A, B, C, D> map3(
        ap: Parser<A>,
        bp: Parser<B>,
        cp: Parser<C>,
        f: (A, B, C) -> D
    ): Parser<D> = flatMap(ap) { a ->
        map2(bp, cp) { b, c ->
            f(a, b, c)
        }
    }

    fun <A> optional(other: Parser<A>): Parser<A?> =
        map(or(map(other) { listOf(it) }) { succeed(emptyList()) }) {
            it.firstOrNull()
        }

    fun int() = map(regex("-?\\d+")) { it.toInt() }
    fun double() = map3(int(), period(), int()) { a, b, c ->
        "$a$b$c".toDouble()
    }
}

abstract class JSONParsers : ParsersDsl<ParseError>() {
    private fun notAQuotation() = regex("[^\"]")
    private fun leftObjectBracket() = string("{")
    private fun rightObjectBracket() = string("}")
    private fun leftArrayBracket() = string("[")
    private fun rightArrayBracket() = string("]")

    abstract val jObjectParser: Parser<JObject>

    abstract val jArrayParser: Parser<JArray>
    abstract val jNumberParser: Parser<JNumber>
    val jStringParser: Parser<JString>
        get() = map3(
            string("\""),
            notAQuotation(),
            string("\"")
        ) { _, s, _ -> JString(s) }

    abstract val jBooleanParser: Parser<JBoolean>
    abstract val JNullParser: Parser<JNull>

    val jsonParser: Parser<JSON>
        get() = jObjectParser
}
// end::init[]
