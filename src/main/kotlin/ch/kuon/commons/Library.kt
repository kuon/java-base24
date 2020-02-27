package ch.kuon.commons


object Base24 {
    private val alphabet = "ZAC2B3EF4GH5TK67P8RS9WXY"
    private val alphabetLength = 24
    private val encodeMap: HashMap<Int, Char>
    private val decodeMap: HashMap<Char, Int>

    init {
        encodeMap = hashMapOf<Int, Char>()
        decodeMap = hashMapOf<Char, Int>()
        var idx = 0
        alphabet.forEach { char ->
            encodeMap.put(idx, char)
            decodeMap.put(char, idx)
            decodeMap.put(char.toLowerCase(), idx)
            idx++
        }
    }

    /**
     * Encode bytes to a string using base24
     * @param data A byte array. Length must be multiple of 4.
     * @return The encoded string
     */
    fun encode24(data: ByteArray): String {
        val dataLength = data.size
        if (dataLength % 4 != 0) {
            throw Exception("Encode 24 data length " +
                            "but be a multiple of 4 bytes (32bits)")
        }
        var result = StringBuilder()

        for (i in 0 until dataLength / 4) {
            val j = i * 4
            val mask = 0xFFL
            val b3 = data.get(j).toLong() and mask
            val b2 = data.get(j + 1).toLong() and mask
            val b1 = data.get(j + 2).toLong() and mask
            val b0 = data.get(j + 3).toLong() and mask

            var value: Long = 0xFFFFFFFFL and
                ((b3 shl 24) or (b2 shl 16) or (b1 shl 8) or b0)


            var subResult = StringBuilder()
            for (k in 0 until 7) {
                val idx = value % alphabetLength
                value = value / alphabetLength

                subResult.insert(0, encodeMap.get(idx.toInt()))
            }
            result.append(subResult)
        }

        return result.toString()
    }

    /**
     * Decode a string to byete arrary using base24
     * @param data A string encoded un base24. Length must be multiple of 7
     * @return The decoded bytes
     */
    fun decode24(data: String): ByteArray {
        val dataLength = data.length
        if (dataLength % 7 != 0) {
            throw Exception("Decode 24 data length " +
                            "but be a multiple of 7 chars")
        }

        val bytes = mutableListOf<Byte>()

        for (i in 0 until dataLength / 7) {
            val j = i * 7
            val subData = data.substring(j, j + 7)
            var value: Long = 0

            subData.forEach { s ->
                val idx = decodeMap.get(s)
                if (idx == null) {
                    throw Exception("Unsupported character in input: " + s)
                } else {
                    value = alphabetLength * value + idx
                }
            }

            val mask = 0xFFL
            val b0 = (value and (mask shl 24)) shr 24
            val b1 = (value and (mask shl 16)) shr 16
            val b2 = (value and (mask shl 8)) shr 8
            val b3 = value and mask
            bytes.add(b0.toByte())
            bytes.add(b1.toByte())
            bytes.add(b2.toByte())
            bytes.add(b3.toByte())
        }

        return bytes.toByteArray()
    }
}
