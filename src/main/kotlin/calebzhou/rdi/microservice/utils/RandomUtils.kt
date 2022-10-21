package calebzhou.rdi.microservice.utils

import java.util.*

object RandomUtils {
    val random = SplittableRandom()
    fun generateRandomInt(min: Int, max: Int): Int {
        return random.nextInt(max - min + 1) + min
    }

    fun randomPercentage(perc: Double): Boolean {
        val ranMax = 10000
        val ranPerc = (ranMax * perc).toInt()
        val ran = generateRandomInt(0, ranMax)
        return ran < ranPerc
    }

    fun generateRandomChar(): Char {
        val chars = "qwertyuiopasdfghjklzxcvbnm1234567890QWERTYUIOPASDFGHJKLZXCVBNM"
        return chars[random.nextInt(chars.length)]
    }

    fun getRandomString(len: Int): String {
        val iid = StringBuilder()
        for (i in 0 until len) {
            iid.append(generateRandomChar())
        }
        return iid.toString()
    }
}