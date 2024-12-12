package com.alexthekap.numerology2_appp.util

data class Matrix2(

    val numberList: List<Int>,
    val _1s: String,
    val _2s: String,
    val _3s: String,
    val _4s: String,
    val _5s: String,
    val _6s: String,
    val _7s: String,
    val _8s: String,
    val _9s: String
) {
    companion object {
        operator fun invoke(timeInMillis: Long): Matrix2 {

            val intList = Utils.longToStrDateNoSeparator(timeInMillis).toCharArray().map{ char ->
                char.toString().toInt() // parseInt
            }

            val list = intList.toMutableList()
            val extra11 = intList.sum()/10
            val extra12 = intList.sum()%10
            val extra2 = extra11 + extra12
            val extra21 = extra2/10
            val extra22 = extra2%10
            val extra3 = list.sum() - 2 * list.get(0)
            val extra31 = extra3/10
            val extra32 = extra3%10
            val extra4 = extra31 + extra32
            val extra41 = extra4/10
            val extra42 = extra4%10

            list.add(extra11)
            list.add(extra12)
            list.add(extra21)
            list.add(extra22)
            list.add(extra31)
            list.add(extra32)
            list.add(extra41)
            list.add(extra42)

            var _1 = 0
            var _2 = 0
            var _3 = 0
            var _4 = 0
            var _5 = 0
            var _6 = 0
            var _7 = 0
            var _8 = 0
            var _9 = 0
            var _0 = 0

            var _1str = ""
            var _2str = ""
            var _3str = ""
            var _4str = ""
            var _5str = ""
            var _6str = ""
            var _7str = ""
            var _8str = ""
            var _9str = ""

            list.forEach{ number ->
                when (number) {
                    1 -> {_1++; _1str = _1str.plus("1")}
                    2 -> {_2++; _2str = _2str.plus("2")}
                    3 -> {_3++; _3str = _3str.plus("3")}
                    4 -> {_4++; _4str = _4str.plus("4")}
                    5 -> {_5++; _5str = _5str.plus("5")}
                    6 -> {_6++; _6str = _6str.plus("6")}
                    7 -> {_7++; _7str = _7str.plus("7")}
                    8 -> {_8++; _8str = _8str.plus("8")}
                    9 -> {_9++; _9str = _9str.plus("9")}
                    0 -> _0++
                }
            }

            return Matrix2(
                listOf(_0, _1, _2, _3, _4, _5, _6, _7, _8, _9),
                _1str, _2str, _3str, _4str, _5str, _6str, _7str, _8str, _9str
            )
        }
    }
}
