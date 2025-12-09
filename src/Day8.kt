import java.io.File
import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    val test8 = File("test8.txt").readText().replace("\r", "")
    val input8 = File("input8.txt").readText().replace("\r", "")
    day8(test8)
    //day8(input8)
}

fun day8(input: String) {
    val coordinates = input.split("\n").map { it.split(",").map { it.toInt() } }
    val distanceMap = mutableMapOf<Pair<Int, Int>, Long>()
    val connections = mutableMapOf<Int, Int>()

    coordinates.forEachIndexed { index, list ->
/*        var smallest = 10000000000L
        var smallestIndex = -1*/
        coordinates.forEachIndexed { index2, list2 ->
            if (index == index2) return@forEachIndexed
            var eucledianDistance = 0L
            if (index2 < index) eucledianDistance = distanceMap[index2 to index] ?: 0L
            else {
                eucledianDistance = sqrt(
                    (list[0].toDouble() - list2[0].toDouble()).pow(2) + (list[1].toDouble() - list2[1].toDouble()).pow(2) + (list[2].toDouble() - list2[2].toDouble()).pow(
                        2
                    )
                ).toLong()
                distanceMap.put(index to index2, eucledianDistance)
            }
        }
    }

    val sortedDistances =
        distanceMap.map { it.value to it.key }.toMap().toSortedMap().toSortedMap { o1, o2 -> o1.compareTo(o2) }
    println(distanceMap)
    println(sortedDistances)
    val connectionAmount = if (coordinates.size < 100) 10 else 1000
    val smallestDistances = sortedDistances.toList().slice(0..connectionAmount - 1).associate { it.second to it.first }

    smallestDistances.forEach { println(coordinates[it.key.first] + coordinates[it.key.second] + ", dist: " + distanceMap[it.key.first to it.key.second] + it.key.first + it.key.second) }

/*
    val firstMap = smallestDistances.map { it.key.first to it.key.second }.toMap()
    val secondMap = smallestDistances.map { it.key.second to it.key.first }.toMap()
*/

    var completedConnections = setOf<Int>()
    var largestCircuits = listOf<Int>()
    var largestCircuitsIndeces = listOf<Int>()

    var usedConnections = setOf<Pair<Int, Int>>()
    smallestDistances.entries.forEach { value ->

        val index = value.key.first
        if (completedConnections.contains(index)) return@forEach
        var sumConnections = 1

        fun recursion(index: Int) {
            print(index.toString() + ", ")

            smallestDistances.entries.filter{ key ->}

            if (newIndex1 != null && !usedConnections.contains(index to newIndex1) && newIndex1 != index) {
                usedConnections = usedConnections.plus(index to newIndex1)
                completedConnections = completedConnections.plus(newIndex1)
                sumConnections++
                recursion(newIndex1)
            }
            if (newIndex2 != null && !usedConnections.contains(newIndex2 to index) && newIndex1 != index) {
                usedConnections = usedConnections.plus(newIndex2 to index)
                completedConnections = completedConnections.plus(newIndex2)
                sumConnections++
                recursion(newIndex2)
            }
            return
        }
        recursion(index)

        println("sum " + sumConnections)
        if (sumConnections > 1) {
            largestCircuits = largestCircuits.plus(sumConnections)

        }
    }

    val sortedCircuits = largestCircuits.sortedDescending().slice(0..2)

    println(largestCircuits)
    println(sortedCircuits)
    println("Final " + sortedCircuits.reduce { acc, i -> acc * i })
}