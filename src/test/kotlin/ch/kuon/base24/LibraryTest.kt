package ch.kuon.base24

import kotlin.test.Test
import kotlin.test.*
import ch.kuon.base24.Base24.encode24
import ch.kuon.base24.Base24.decode24

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
        assertEquals("5YEATXA", encode("88553311"))
        assertEquals("88553311", decode("5YEATXA"))
    }
}
