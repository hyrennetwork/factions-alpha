package net.hyren.factions.alpha.misc.util

import net.hyren.core.shared.misc.utils.ChatColor
import java.nio.charset.Charset

/**
 * @author Gutyerrez
 */
object HiddenStringUtils {

    private val SEQUENCE_HEADER = "" + ChatColor.RESET + ChatColor.UNDERLINE + ChatColor.RESET
    private val SEQUENCE_FOOTER = "" + ChatColor.RESET + ChatColor.ITALIC + ChatColor.RESET

    fun encodeString(hiddenString: String): String {
        return quote(stringToColors(hiddenString))
    }

    fun hasHiddenString(input: String) = input.indexOf(SEQUENCE_HEADER) > -1 && input.indexOf(SEQUENCE_FOOTER) > -1

    fun extractHiddenString(input: String) = colorsToString(extract(input))

    fun replaceHiddenString(
        input: String,
        hiddenString: String
    ): String {
        val start = input.indexOf(SEQUENCE_HEADER)
        val end = input.indexOf(SEQUENCE_FOOTER)

        return if (start < 0 || end < 0) {
            throw NullPointerException()
        } else {
            input.substring(0, start + SEQUENCE_HEADER.length) + stringToColors(hiddenString) + input.substring(end, input.length)
        }
    }

    private fun quote(input: String) = SEQUENCE_HEADER + input + SEQUENCE_FOOTER

    private fun extract(input: String): String {
        val start = input.indexOf(SEQUENCE_HEADER)
        val end = input.indexOf(SEQUENCE_FOOTER)

        return if (start < 0 || end < 0) {
            throw NullPointerException()
        } else {
            input.substring(start + SEQUENCE_HEADER.length, end)
        }
    }

    private fun stringToColors(normal: String): String {
        val bytes = normal.toByteArray(Charset.forName("UTF-8"))
        val chars = CharArray(bytes.size * 4)

        for (i in bytes.indices) {
            val hex = byteToHex(bytes[i])
            chars[i * 4] = ChatColor.COLOR_CHAR
            chars[i * 4 + 1] = hex[0]
            chars[i * 4 + 2] = ChatColor.COLOR_CHAR
            chars[i * 4 + 3] = hex[1]
        }

        return String(chars)
    }

    private fun colorsToString(colors: String): String {
        var colors = colors.lowercase().replace("" + ChatColor.COLOR_CHAR, "")

        if (colors.length % 2 != 0) {
            colors = colors.substring(0, colors.length / 2 * 2)
        }

        val chars = colors.toCharArray()
        val bytes = ByteArray(chars.size / 2)
        var i = 0

        while (i < chars.size) {
            bytes[i / 2] = hexToByte(chars[i], chars[i + 1])
            i += 2
        }

        return String(bytes, Charset.forName("UTF-8"))
    }

    private fun hexToUnsignedInt(c: Char): Int {
        return if (c >= '0' && c <= '9') {
            c.code - 48
        } else if (c >= 'a' && c <= 'f') {
            c.code - 87
        } else {
            throw IllegalArgumentException("Invalid hex char: out of range")
        }
    }

    private fun unsignedIntToHex(i: Int): Char {
        return if (i >= 0 && i <= 9) {
            (i + 48).toChar()
        } else if (i >= 10 && i <= 15) {
            (i + 87).toChar()
        } else {
            throw IllegalArgumentException("Invalid hex int: out of range")
        }
    }

    private fun hexToByte(hex1: Char, hex0: Char): Byte {
        return ((hexToUnsignedInt(hex1) shl 4 or hexToUnsignedInt(hex0)) + Byte.MIN_VALUE).toByte()
    }

    private fun byteToHex(b: Byte): CharArray {
        val unsignedByte = b.toInt() - Byte.MIN_VALUE

        return charArrayOf(
            unsignedIntToHex(unsignedByte shr 4 and 0xf),
            unsignedIntToHex(unsignedByte and 0xf)
        )
    }
}