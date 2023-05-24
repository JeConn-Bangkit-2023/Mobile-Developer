package com.capstone.jeconn.component

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.capstone.jeconn.R

object Font {
    val QuickSand = FontFamily(
        Font(R.font.quicksand_light, FontWeight.Light),
        Font(R.font.quicksand_regular, FontWeight.Normal),
        Font(R.font.quicksand_medium, FontWeight.Medium),
        Font(R.font.quicksand_semi_bold, FontWeight.SemiBold),
        Font(R.font.quicksand_bold, FontWeight.Bold),
    )
}