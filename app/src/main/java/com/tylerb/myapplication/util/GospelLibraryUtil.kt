package com.tylerb.myapplication.util

import android.net.Uri
import java.util.*

fun gospelLibraryUrl(mainTitle: String): Uri {
    val split = mainTitle.split(":")[0].split(" ")
    val chapter = split.last()

    val book = split.first()
    var finalBook = book.toLowerCase(Locale.getDefault())

    if (book.contains("Nephi")){
        val bookNum = book[0]
        finalBook = "$bookNum-ne"
    }
    when (book){
        "Words" -> finalBook = "w-of-m"
        "Helaman" -> finalBook = "hel"
        "Mormon" -> finalBook = "morm"
        "Moroni" -> finalBook = "moro"
    }
    val url = "https://www.churchofjesuschrist.org/study/scriptures/bofm/$finalBook/$chapter"
    return Uri.parse(url)
}