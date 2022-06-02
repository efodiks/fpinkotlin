package chapter12.exercises.ex4

import chapter12.sec4.Stream

interface Listing {
    //tag::init1[]
    /**
     * Zips n streams. Returns a stream of list where each list
     * has one element from each stream.
     */
    fun <A> sequence(lsa: List<Stream<A>>): Stream<List<A>>
    //end::init1[]
}
