import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    /*    val test8 = File("test8.txt").readText().replace("\r", "")
        val input8 = File("input8.txt").readText().replace("\r", "")
        day8(test8, true)
        day8(input8, false)*/

    val test9 = File("test9.txt").readText().replace("\r", "")
    val input9 = File("input9.txt").readText().replace("\r", "")
    day9(test9, true)
    day9(input9, false)
}

fun day9(input: String, test: Boolean) {
    val coordinates = input.split("\n").map { it.split(",").map { it.toLong() } }.map { it[0] to it[1] }
    var biggest = 0L
    coordinates.forEachIndexed { index, (x1, y1) ->
        println("$x1,$y1")
        run thisSquare@{
            coordinates.forEachIndexed { index2, (x2, y2) ->
                val xMax = maxOf(x1, x2)
                val xMin = minOf(x1, x2)
                val yMax = maxOf(y1, y2)
                val yMin = minOf(y1, y2)

                coordinates.forEachIndexed { i3, (x3, y3) ->
                    /*if(index2 == i3 || index == i3) return
                    if(x3 ==  i3 || index == i3) return*/
                    val (x3prev, y3prev) = coordinates[(i3 - 1 + coordinates.size) % coordinates.size]

                    val xRange = minOf(x3prev, x3)..maxOf(x3prev, x3)
                    val yRange = minOf(y3prev, y3)..maxOf(y3prev, y3)

                    if ((xMin in xRange) || (xMax in xRange)) {
                        if (y3 in (yMin + 1)..<yMax) return@thisSquare
                    }
                    if ((yMin in yRange) || (yMax in yRange)) {
                        if (x3 in (xMin + 1)..<xMax) return@thisSquare
                    }
                }
                val size = ((x1 - x2).absoluteValue + 1) * ((y1 - y2).absoluteValue + 1)
                if (biggest < size) {
                    println("$x1,$y1 to $x2,$y2 is $size")
                    biggest = size
                }
            }
        }
    }
    // 140736344 too low
    // 592881071
    println("Total " +biggest)
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

            var eucledianDistance = dist1 * dist1 + dist2 * dist2 + dist3 * dist3
            distanceMap[index to index2] = eucledianDistance
        }
    }

    val sortedDistances =
        distanceMap.map { it.value to it.key }.toMap().toSortedMap { o1, o2 -> o1.compareTo(o2) }
    val connectionAmount = if (test) 10 else 1000
    val smallestDistances = sortedDistances.toList().map { it.second }
    println(sortedDistances.toList().take(connectionAmount).map { it.first })
    var usedConnections = mutableListOf<MutableSet<Int>>()

    println("Smallest $smallestDistances")
    run breaking@{
        smallestDistances.forEach { value ->
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
            println(usedConnections)
            if (usedConnections.size == 1 && usedConnections[0].size >= coordinates.size) {

                val secondLast = coordinates[value.first][0]
                val last = coordinates[value.second][0]
                println("Final " + secondLast * last)
                println(secondLast)
                println(last)

                return@breaking
            }
        }
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