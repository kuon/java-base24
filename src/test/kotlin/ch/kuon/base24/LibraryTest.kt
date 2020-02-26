package ch.kuon.base24

import kotlin.test.Test
import kotlin.test.*
import ch.kuon.base24.Base24.encode24
import ch.kuon.base24.Base24.decode24

val values = mapOf(
    "00000000" to "ZZZZZZZ",
    "00000001" to "ZZZZZZA",
    "00000010" to "ZZZZZZP",
    "00000030" to "ZZZZZCZ",
    "88553311" to "5YEATXA",
    "FFFFFFFF" to "X5GGBH7",
    "1234567887654321" to "A64KHWZ5WEPAGG",
    "FF0001FF001101FF01023399" to "XGES63FZZ247C7ZC2ZA6G"
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

    @Test fun testEncode() {
        values.forEach { (k,v) ->
            assertEquals(v, encode(k))
            assertEquals(k, decode(v))
        }
    }
}
