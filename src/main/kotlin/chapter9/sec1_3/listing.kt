package chapter9.sec1_3

import arrow.core.Either
import arrow.core.Right

interface Parsers<PE> {

    interface Parser<A>

    fun <A> run(p: Parser<A>, input: String): Either<PE, A>

    // tag::init1[]
    fun <A> listOfN(n: Int, p: Parser<A>): Parser<List<A>>
    // end::init1[]

    infix fun String.or(other: String): Parser<String>
}

object ParseError

abstract class Example : Parsers<ParseError> {
    init {
        // tag::init2[]
        run(listOfN(3, "ab" or "cad"), "ababab") == Right("ababab")
        run(listOfN(3, "ab" or "cad"), "cadcadcad") == Right("cadcadcad")
        run(listOfN(3, "ab" or "cad"), "ababcad") == Right("ababcad")
        run(listOfN(3, "ab" or "cad"), "cadabab") == Right("cadabab")
        // end::init2[]
    }
}

interface Extension : Parsers<ParseError> {
    fun char(char: Char): Parsers.Parser<Char>

    fun <A, B> Parsers.Parser<A>.flatMap(
        f: (A) -> Parsers.Parser<B>
    ): Parsers.Parser<B>

    fun <A, B> Parsers.Parser<A>.map(f: (A) -> B): Parsers.Parser<B>

    fun <A> repeat(
        atLeast: Int,
        a: Parsers.Parser<A>
    ): Parsers.Parser<List<A>>

    infix fun <A, B> Parsers.Parser<A>.andThen(
        b: Parsers.Parser<B>
    ): Parsers.Parser<Pair<A, B>>

    fun <A> count(
        a: Parsers.Parser<List<A>>
    ): Parsers.Parser<Int> = a.map { it.count() }

    fun <A, B> count2(
        a: Parsers.Parser<Pair<List<A>, List<B>>>
    ): Parsers.Parser<Pair<Int, Int>> =
        a.map { (f, s) -> f.count() to s.count() }

    fun aaThenBParser(): Parsers.Parser<Pair<Int, Int>> =
        count2(repeat(0, char('a')) andThen repeat(1, char('b')))
}
