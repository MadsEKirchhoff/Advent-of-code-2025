import java.io.File
import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    val test8 = File("test8.txt").readText().replace("\r", "")
    val input8 = File("input8.txt").readText().replace("\r", "")
    day8(test8)
    day8(input8)
}

fun day8(input: String) {
    println("final $input")
    val coordinates = input.split("\n").map { it.split(",").map { it.toInt() } }
    val distanceMap = mutableMapOf<Int, Long>()
    val connections = mutableMapOf<Int, Int>()

    coordinates.forEachIndexed { index, list ->
/*        var smallest = 10000000000L
        var smallestIndex = -1*/
        coordinates.forEachIndexed { index2, list2 ->
            if(index == index2) return@forEachIndexed
            var eucledianDistance = 0L
            if(index2 < index)
                eucledianDistance = distanceMap[index2 to index] ?: 0L
            else
            {  eucledianDistance = sqrt((list[0].toDouble() - list2[0].toDouble()).pow(2) + (list[1].toDouble() - list2[1].toDouble()).pow(2)).toLong()
               distanceMap.put(index to index2, eucledianDistance)
            }
           /* if (eucledianDistance < smallest) {
                smallest = eucledianDistance
                smallestIndex = index2
            }*/
        }
/*
        connections.put(index, smallestIndex)
*/
    }

    val sortedDistances = distanceMap.map { it.value to it.key}.toMap().toSortedMap().toSortedMap { o1, o2 -> o1.compareTo(o2) }
    val connectionAmount = if(coordinates.size < 100) 10 else 1000
    val smallestDistances = sortedDistances.toList().slice(0..connectionAmount-1).associate { it.second to it.first }

    val completedConnections = setOf<Int>()
    var final = 1L
    coordinates.forEach { (index, value) ->
        var nextIndex  = -1
        var sumConnections = 1
        while (index != nextIndex) {
            nextIndex = smallestDistances[index] ?: -1

            sumConnections++
        }
    }

}