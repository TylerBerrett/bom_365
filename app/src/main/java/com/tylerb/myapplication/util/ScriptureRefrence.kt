package com.tylerb.myapplication.util

import java.util.*


class ScriptureRefrence(private val month: Int, private val day: Int) {

    fun getScritureRefrence(): List<String>?{
        return months[month]?.get(day)
    }

    private val january = mapOf(
        1 to listOf("1-ne/1", "1", "15"),
        2 to listOf("1-ne/1", "16", "20", "1-ne/2", "1", "15"),
        3 to listOf("1-ne/2", "16", "24", "1-ne/3", "1", "8"),
        4 to listOf("1-ne/3", "9", "31"),
        5 to listOf("1-ne/4", "1", "18"),
        6 to listOf("1-ne/4", "19", "38", "1-ne/5", "1", "6"),
        7 to listOf("1-ne/5", "7", "22", "1-ne/6", "1", "6"),
        8 to listOf("1-ne/7", "1", "15"),
        9 to listOf("1-ne/7", "16", "22", "1-ne/8", "1", "15"),
        10 to listOf("1-ne/8", "16", "38"),
        11 to listOf("1-ne/9", "1", "6", "1-ne/10", "1", "10"),
        12 to listOf("1-ne/10", "10", "22", "1-ne/11", "1", "11"),
        13 to listOf("1-ne/11", "12", "30"),
        14 to listOf("1-ne/11", "31", "36", "1-ne/12", "1", "10"),
        15 to listOf("1-ne/12", "11", "23", "1-ne/13", "1", "9"),
        16 to listOf("1-ne/13", "10", "27"),
        17 to listOf("1-ne/13", "28", "39"),
        18 to listOf("1-ne/13", "40", "42", "1-ne/14", "1", "8"),
        19 to listOf("1-ne/14", "9", "30"),
        20 to listOf("1-ne/15", "1", "18"),
        21 to listOf("1-ne/15", "19", "36"),
        22 to listOf("1-ne/16", "1", "16"),
        23 to listOf("1-ne/16", "17", "30"),
        24 to listOf("1-ne/17", "1", "16"),
        25 to listOf("1-ne/17", "17", "30"),
        26 to listOf("1-ne/17", "31", "47"),
        27 to listOf("1-ne/17", "48", "55", "1-ne/18", "1", "4"),
        28 to listOf("1-ne/18", "5", "20"),
        29 to listOf("1-ne/18", "21", "25", "1-ne/19", "1", "6"),
        30 to listOf("1-ne/19", "7", "24"),
        31 to listOf("1-ne/20", "1", "22")
    )
    private val february = mapOf(1 to listOf(""))
    private val march = mapOf(
        1 to listOf("2-ne/23", "1", "22"),
        2 to listOf("2-ne/24", "1", "32"),
        3 to listOf("2-ne/25", "1", "13"),
        4 to listOf("2-ne/25", "14", "26"),
        5 to listOf("2-ne/25", "27", "30", "2-ne/26", "1", "11"),
        6 to listOf("2-ne/26", "12", "30"),
        7 to listOf("2-ne/26", "31", "33", "2-ne/27", "1", "11"),
        8 to listOf("2-ne/27", "12", "35"),
        9 to listOf("2-ne/28", "1", "16"),
        10 to listOf("2-ne/28", "17", "32"),
        11 to listOf("2-ne/29", "1", "14"),
        12 to listOf("2-ne/30", "1", "18"),
        13 to listOf("2-ne/31", "1", "21"),
        14 to listOf("2-ne/32", "1", "9"),
        15 to listOf("2-ne/33", "1", "15"),
        16 to listOf("jacob/1", "1", "19"),
        17 to listOf("jacob/2", "1", "16"),
        18 to listOf("jacob/2", "17", "35"),
        19 to listOf("jacob/3", "1", "14"),
        20 to listOf("jacob/4", "1", "18"),
        21 to listOf("jacob/5", "1", "18"),
        22 to listOf("jacob/5", "19", "34"),
        23 to listOf("jacob/5", "35", "48"),
        24 to listOf("jacob/5", "49", "69"),
        25 to listOf("jacob/5", "70", "77"),
        26 to listOf("jacob/6", "1", "13", "jacob/7", "1", "5"),
        27 to listOf("jacob/7", "6", "27"),
        28 to listOf("enos/1", "1", "18"),
        29 to listOf("enos/1", "19", "27"),
        30 to listOf("jarom/1", "1", "15"),
        31 to listOf("omni/1", "1", "11")
    )
    private val april= mapOf(1 to listOf(""))
    private val may= mapOf(1 to listOf(""))
    private val june= mapOf(1 to listOf(""))
    private val july= mapOf(1 to listOf(""))
    private val august= mapOf(1 to listOf(""))
    private val september= mapOf(1 to listOf(""))
    private val october= mapOf(1 to listOf(""))
    private val november= mapOf(1 to listOf(""))
    private val december= mapOf(1 to listOf(""))


    private val months = mapOf(
        Calendar.JANUARY to january,
        Calendar.FEBRUARY to february,
        Calendar.MARCH to march,
        Calendar.APRIL to april,
        Calendar.MAY to may,
        Calendar.JUNE to june,
        Calendar.JULY to july,
        Calendar.AUGUST to august,
        Calendar.SEPTEMBER to september,
        Calendar.OCTOBER to october,
        Calendar.NOVEMBER to november,
        Calendar.DECEMBER to december
    )

}