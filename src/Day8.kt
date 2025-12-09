import java.io.File
import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    val test8 = File("test8.txt").readText().replace("\r", "")
    val input8 = File("input8.txt").readText().replace("\r", "")
    day8(test8, true)
    day8(input8, false)
}

fun day8(input: String, test: Boolean) {
    val coordinates = input.split("\n").map { it.split(",").map { it.toLong() } }
    val distanceMap = mutableMapOf<Pair<Int, Int>, Long>()
    val connections = mutableMapOf<Int, Int>()

    coordinates.forEachIndexed { index, list ->
        coordinates.forEachIndexed { index2, list2 ->
            if (index == index2) return@forEachIndexed
            val dist1 = list[0] - list2[0]
            val dist2 = list[1] - list2[1]
            val dist3 = list[2] - list2[2]

            var eucledianDistance = dist1 * dist1 + dist2 * dist2 + dist3* dist3
            distanceMap[index to index2] = eucledianDistance
        }
    }

    val sortedDistances =
        distanceMap.map { it.value to it.key }.toMap().toSortedMap { o1, o2 -> o1.compareTo(o2) }
    val connectionAmount = if (test) 10 else 1000
    val smallestDistances = sortedDistances.toList().take(connectionAmount).map { it.second }
    println(sortedDistances.toList().take(connectionAmount).map { it.first })
    var usedConnections = mutableListOf<MutableSet<Int>>()

    smallestDistances.forEach { value ->
        //println(usedConnections)
        val set1 = usedConnections.find { it.contains(value.first) }
        val set2 = usedConnections.find { it.contains(value.second) }
        if (set1 == null && set2 == null) {
            val newSet = mutableSetOf(value.first, value.second)
            usedConnections.addLast(newSet)
        } else if (set1 === set2) {
            return@forEach
        } else if (set1 != null && set2 != null) {
            usedConnections.remove(set2)
            set1.addAll(set2)
        } else if (set1 != null)
            set1.add(value.second)
        else if (set2 != null)
            set2.add(value.first)
        else
            println("error")

    }
    //5712 too low
    // 79464 too low
    val largestThreeCircuits = usedConnections.sortedByDescending { it.size }.take(3)
    val map = largestThreeCircuits.map { it.size }
    val largestMultiplied = map.reduce { acc, numb -> acc * numb }
    println("used: " + usedConnections)
    println(largestThreeCircuits)
    println(map)
    println(largestMultiplied)
}