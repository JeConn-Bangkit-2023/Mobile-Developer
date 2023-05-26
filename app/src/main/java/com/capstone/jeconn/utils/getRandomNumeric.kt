package com.capstone.jeconn.utils

import kotlin.random.Random

fun getRandomNumeric(): String {
    val randomGenerator = Random(System.currentTimeMillis())
    val result = randomGenerator.nextInt(100000000, 999999999)
    return result.toString()
}