package com.example.criminal

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
data class Crime(
    val id: UUID = UUID.randomUUID(),
    var title: String = "",
    var date: String = formatDate(),
    var isSolved: Boolean = false,
)

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(): String {
    val date = LocalDate.now()

    val month = when(date.monthValue) {
        1 -> "Jan"
        2 -> "Feb"
        3 -> "March"
        4 -> "April"
        else -> "May"
    }

    return "$month ${date.dayOfMonth}, ${date.year}"
}


