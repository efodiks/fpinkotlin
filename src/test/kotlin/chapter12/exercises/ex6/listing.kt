package chapter12.exercises.ex6

import arrow.Kind
import chapter12.exercises.ex3.Applicative
import chapter12.sec4.Failure
import chapter12.sec4.Success
import chapter12.sec4.Validation
import chapter12.sec4.ValidationOf
import chapter12.sec4.ValidationPartialOf
import chapter12.sec4.fix
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import java.time.Instant
import java.util.Date

//tag::init1[]
fun <E> validation(): Applicative<ValidationPartialOf<E>> =
    object : Applicative<ValidationPartialOf<E>> {
        override fun <A, B> map(
            fa: Kind<ValidationPartialOf<E>, A>,
            f: (A) -> B
        ): Kind<ValidationPartialOf<E>, B> {
            return apply(unit(f), fa)
        }

        override fun <A, B> apply(
            fab: Kind<ValidationPartialOf<E>, (A) -> B>,
            fa: Kind<ValidationPartialOf<E>, A>
        ): Kind<ValidationPartialOf<E>, B> {
            return when(val faa = fa.fix()) {
                is Success -> when(val fabb = fab.fix()) {
                    is Success -> unit(fabb.a(faa.a))
                    is Failure -> Failure(fabb.head, fabb.tail)
                }
                is Failure -> when(val fabb = fab.fix()) {
                    is Success -> Failure(faa.head, faa.tail)
                    is Failure -> Failure(fabb.head, fabb.tail + listOf(faa.head) + faa.tail)
                }
            }
        }

        override fun <A> unit(a: A): Kind<ValidationPartialOf<E>, A> {
            return Success(a)
        }
    }
//end::init1[]

class ValidationSpec : WordSpec({
    "validation" should {

        data class WebForm(val f1: String, val f2: Date, val f3: String)

        fun validName(name: String): Validation<String, String> =
            Success(name)

        fun validDateOfBirth(dob: Date): Validation<String, Date> =
            Success(dob)

        fun validPhone(phone: String): Validation<String, String> =
            Success(phone)

        fun <A> invalidInput(input: A) = Failure("invalid: $input")

        val F = validation<String>()

        val name = "Claire"
        val dob = Date.from(Instant.now())
        val phone = "6060-842"

        "pass for valid inputs" {

            val result: ValidationOf<String, WebForm> = F.map3(
                validName(name).fix(),
                validDateOfBirth(dob).fix(),
                validPhone(phone).fix()
            ) { n, d, p ->
                WebForm(n, d, p)
            }

            result shouldBe Success(WebForm(name, dob, phone))
        }

        "fail for single invalid input" {

            val result: ValidationOf<String, WebForm> = F.map3(
                validName(name).fix(),
                invalidInput(dob).fix(),
                validPhone(phone).fix()
            ) { n, d, p ->
                WebForm(n, d, p)
            }

            result shouldBe Failure("invalid: $dob")
        }

        "accumulate failures for multiple invalid input" {

            val result: ValidationOf<String, WebForm> = F.map3(
                invalidInput(name).fix(),
                invalidInput(dob).fix(),
                invalidInput(phone).fix()
            ) { n, d, p ->
                WebForm(n, d, p)
            }

            result shouldBe Failure(
                "invalid: $name",
                listOf("invalid: $dob", "invalid: $phone")
            )
        }
    }
})
