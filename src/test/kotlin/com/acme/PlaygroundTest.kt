package com.acme

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.junit.Test

class PlaygroundTest {


    @Test
    fun flatMapEntry() {
        val map = mapOf(
                Pair("one", 1),
                Pair("two", 2),
                Pair("three", mapOf(
                        Pair("four", 4),
                        Pair("five", mapOf(
                                Pair("six", 6)
                        ))
                ))
        )

        fun flatMapEntry(entry: Map.Entry<*, *>, keyPrefix: String = ""): List<Map.Entry<*, *>> {
            if (entry.value !is Map<*, *>) {
                return listOf(entry("${keyPrefix}${entry.key}", entry.value))
            } else {
                val m: Map<*, *> = entry.value as Map<*, *>
                return m.flatMap { e -> flatMapEntry(e, "${keyPrefix}${entry.key}.") }
            }
        }

        val result = map.flatMap { entry -> flatMapEntry(entry) }


        assertThat(result).contains(
                entry("one", 1),
                entry("two", 2),
                entry("three.four", 4),
                entry("three.five.six", 6)
        )

    }
}
