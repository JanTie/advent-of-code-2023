import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.createFile
import kotlin.io.path.readLines

fun createFileForDay(name: String) {
    runCatching {
        Path("src/$name.txt").createFile()
        Path("src/${name}_test.txt").createFile()
    }
}

fun gcd(a: Long, b: Long): Long {
    if (a == 0L) return b

    return gcd(b % a, a)
}

fun lcm(list: List<Long>): Long = list.fold(1L) { acc, item ->
    (item * acc) / gcd(item, acc)
}

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)
