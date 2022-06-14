package chapter15.exercises.ex1

import chapter10.None
import chapter10.Some
import chapter10.Option
import chapter15.sec2.Await
import chapter15.sec2.Emit
import chapter15.sec2.Halt
import chapter15.sec2.Process
import chapter15.sec2.toList
import chapter3.List
import chapter5.Stream
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

//tag::init[]
fun <I> take(n: Int): Process<I, I> =
    when (n) {
        0 -> Halt()
        else -> Await { i: Option<I> ->
            when (i) {
                is Some -> Emit(i.get, take(n - 1))
                is None -> Halt<I, I>()
            }
        }
    }

fun <I> drop(n: Int): Process<I, I> =
    when (n) {
        0 -> Await { i: Option<I> ->
            when (i) {
                is Some -> Emit(i.get, take(n - 1))
                is None -> Halt<I, I>()
            }
        }
        else -> Await { i: Option<I> ->
            drop<I>(n - 1)
        }
    }

fun <I> takeWhile(p: (I) -> Boolean): Process<I, I> =
    Await { i ->
        when (i) {
            is Some -> if (p(i.get)) {
                Emit(i.get, takeWhile(p))
            } else Halt()
            is None -> takeWhile(p)
        }
    }

fun <I> dropWhile(p: (I) -> Boolean): Process<I, I> =
    Await { i ->
        when (i) {
            is None -> Halt()
            is Some -> if (p(i.get)) {
                dropWhile(p)
            } else Emit(i.get, passThrough())
        }
    }

fun <I> passThrough(): Process<I, I> =
    Await { i ->
        when(i) {
            is Some -> Emit(i.get, passThrough())
            is None -> Halt()
        }
    }
//end::init[]

class Exercise1 : WordSpec({
    val stream = Stream.of(1, 2, 3, 4, 5, 6, 7, 6, 5, 4, 3, 2, 1)
    "take" should {
        "consume the given number of elements from a stream" {
            take<Int>(5)(stream).toList() shouldBe List.of(1, 2, 3, 4, 5)
        }
    }
    "drop" should {
        "drop the given number of elements from a stream" {
            drop<Int>(5)(stream).toList() shouldBe
                List.of(6, 7, 6, 5, 4, 3, 2, 1)
        }
    }
    "takeWhile" should {
        "consume elements from a stream while a predicate is true" {
            takeWhile<Int> { 5 > it }(stream).toList() shouldBe
                List.of(1, 2, 3, 4)
        }
    }
    "dropWhile" should {
        "drop elements from a stream while a predicate is true" {
            dropWhile<Int> { 5 > it }(stream).toList() shouldBe
                List.of(5, 6, 7, 6, 5, 4, 3, 2, 1)
        }
    }
})
