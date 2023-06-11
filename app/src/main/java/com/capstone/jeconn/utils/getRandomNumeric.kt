package com.capstone.jeconn.utils

import kotlin.random.Random

fun getRandomNumeric(): Long {
    val randomGenerator = Random(System.currentTimeMillis())
    return randomGenerator.nextInt(100000000, 999999999).toLong()
}