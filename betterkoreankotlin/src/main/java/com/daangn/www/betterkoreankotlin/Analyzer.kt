package com.daangn.www.betterkoreankotlin

import java.util.*

/**
 * Created by kai on 2017. 5. 25..
 */

private const val HANGUL_SYLLABLES_BEGIN: Char = 0xAC00.toChar()
private const val HANGUL_SYLLABLES_END: Char = 0xD7A3.toChar()
private const val CHOSUNG_BASE = 588
private const val JUNGSUNG_BASE = 28

private val jongSungList = arrayOf(" ", "ㄱ", "ㄲ", "ㄳ", "ㄴ", "ㄵ", "ㄶ", "ㄷ", "ㄹ", "ㄺ", "ㄻ", "ㄼ", "ㄽ", "ㄾ", "ㄿ", "ㅀ", "ㅁ", "ㅂ", "ㅄ", "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ")
private val engCheckList = arrayOf("A", "a", "E", "e", "F", "f", "G", "g", "H", "h", "I", "i", "J", "j", "O", "o", "R", "r", "S", "s", "U", "u", "V", "v", "X", "x", "Y", "y", "Z", "z")

private val engType = arrayOf("NG", "LE", "ME")
private val engType2 = arrayOf("ND", "ED", "LT", "ST", "RD", "LD")

enum class Number(val value: Char) {
    ZERO('0'), ONE('1'), TWO('2'), THREE('3'), FOUR('4'), FIVE('5'), SIX('6'), SEVEN('7'), EIGHT('8'), NINE('9');
    companion object {
        fun from(s: Char): Number? = values().firstOrNull { it.value == s }
    }
}

enum class JosaType {
    Type_은는, Type_이가, Type_을를, Type_으로_로, Type_아야, Type_와과
}

class Analyzer(private val src: String){

    fun getJosa(josaType: JosaType): String {

        if (Locale.getDefault().language != Locale.KOREA.language) {
            return ""
        }

        val candidatePair: Pair<String, String> = when (josaType) {
            JosaType.Type_은는 -> Pair("은", "는")
            JosaType.Type_을를 -> Pair("을", "를")
            JosaType.Type_이가 -> Pair("이", "가")
            JosaType.Type_아야 -> Pair("아", "야")
            JosaType.Type_와과 -> Pair("과", "와")
            else -> throw IllegalArgumentException("invalid type")
        }

        return if (isHangul()) {
            if (isThere종성()) candidatePair.first else candidatePair.second
        } else if (isEnglish()) {
            if (isKindOf받침()) candidatePair.first else candidatePair.second
        } else if (isEndWithNumber()) {
            if (isKindOf받침Number()) candidatePair.first else candidatePair.second
        } else {
            ""
        }
    }

    fun getJosa_으_으로(): String {

        if (Locale.getDefault().language != Locale.KOREA.language) {
            return ""
        }

        return if (isHangul()) {
            if (isㄹ종성()) "로" else if (isThere종성()) "으로" else "로"
        } else if (isEnglish()) {
            if (isL()) "로" else if (isKindOf받침()) "으로" else "로"
        } else if (isEndWithNumber()) {
            if (isOne()) "로" else if (isKindOf받침Number()) "으로" else "로"
        } else {
            ""
        }
    }

    private fun get종정Idx(): Int {
        val ch = getLastChar()
        val base = ch - HANGUL_SYLLABLES_BEGIN
        val cs = base / CHOSUNG_BASE
        val jus = (base - CHOSUNG_BASE * cs) / JUNGSUNG_BASE
        return base - CHOSUNG_BASE * cs - JUNGSUNG_BASE * jus
    }

    private fun getLastChar(): Char {
        return src[src.length - 1]
    }

    private fun isThere종성(): Boolean {
        val jos = get종정Idx()

        return jongSungList[jos] != " "
    }

    private fun isㄹ종성(): Boolean {
        val jos = get종정Idx()
        return jongSungList[jos] == "ㄹ"
    }

    private fun isHangul(): Boolean {
        val ch = getLastChar()
        return ch in HANGUL_SYLLABLES_BEGIN..HANGUL_SYLLABLES_END
    }

    private fun isEnglish(): Boolean {
        val ch = getLastChar()
        return ch.toInt() in 65..90 || ch.toInt() in 92..122
    }

    private fun isKindOf받침(): Boolean {

        if (src.length >= 2) {
            val lastTwoString = src.substring(src.length - 2, src.length)
            engType.filter { lastTwoString.toUpperCase(Locale.getDefault()) == it }.forEach { _ -> return true }
            engType2.filter { lastTwoString.toUpperCase(Locale.getDefault()) == it }.forEach { _ -> return false }
        }

        val lastString = src.substring(src.length - 1, src.length)
        val list = ArrayList(listOf(*engCheckList))

        return !list.contains(lastString)
    }

    private fun isL(): Boolean {
        val lastChar = getLastChar()
        return lastChar.toInt() == 76 || lastChar.toInt() == 108
    }

    private fun isEndWithNumber(): Boolean {
        val last = getLastChar()
        return last.toInt() in 48..57
    }

    private fun isKindOf받침Number(): Boolean {
        val last = getLastChar()

        when (Number.from(last)) {
            Number.ZERO, Number.ONE, Number.THREE, Number.SIX, Number.SEVEN, Number.EIGHT -> return true
            Number.TWO, Number.FOUR, Number.FIVE, Number.NINE -> return false
        }
        return false
    }

    private fun isOne(): Boolean {
        val last = getLastChar()
        return last == '1'
    }
}
