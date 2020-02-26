package ch.kuon.commons

import kotlin.test.Test
import kotlin.test.*
import ch.kuon.commons.Base24.encode24
import ch.kuon.commons.Base24.decode24
import java.security.SecureRandom

// A few hard coded values
val values = mapOf(
    "00000000" to "ZZZZZZZ",
    "000000000000000000000000" to "ZZZZZZZZZZZZZZZZZZZZZ",
    "00000001" to "ZZZZZZA",
    "000000010000000100000001" to "ZZZZZZAZZZZZZAZZZZZZA",
    "00000010" to "ZZZZZZP",
    "00000030" to "ZZZZZCZ",
    "88553311" to "5YEATXA",
    "FFFFFFFF" to "X5GGBH7",
    "FFFFFFFFFFFFFFFFFFFFFFFF" to "X5GGBH7X5GGBH7X5GGBH7",
    "1234567887654321" to "A64KHWZ5WEPAGG",
    "FF0001FF001101FF01023399" to "XGES63FZZ247C7ZC2ZA6G",
    "25896984125478546598563251452658" to "2FC28KTA66WRST4XAHRRCF237S8Z"
)

class LibraryTest {

    fun encode(str: String): String {
        val els = str.chunked(2) { s ->
            java.lang.Long.parseLong(s.toString(), 16)
            .toByte()
        }.toByteArray()
        return encode24(els)
    }

    fun decode(str: String): String {
        return decode24(str).map { b ->
            String.format("%02X", b)
        }.joinToString("")
    }

    @Test fun testFixed() {
        values.forEach { (k, v) ->
            assertEquals(v, encode(k))
            assertEquals(k, decode(v))
        }
    }

    @Test fun testRandom() {
        val rand = SecureRandom()

        for (words in 1 until 4) {
            val bytes = ByteArray(4 * words)

            for (i in 0 until 1_000_000) {
                rand.nextBytes(bytes)
                val encoded = encode24(bytes)
                val decoded = decode24(encoded)
                assertEquals(bytes.size, decoded.size)
                assertTrue(bytes contentEquals decoded)
            }
        }
    }
}
