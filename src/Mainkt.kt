import java.io.File
import kotlin.math.pow

fun main() {
    /*    val test2 = File("test2.txt").readText()
        val input2 = File("input2.txt").readText()
        day2(test2)
        day2(input2)*/

    /*val test3 = File("test3.txt").readText()
    val input3 = File("input3.txt").readText()
    day3(test3)
    day3(input3)*/

    /*val test4 = File("test4.txt").readText().replace("\r", "")
    val input4 = File("input4.txt").readText().replace("\r", "")
    day4(test4)
    day4(input4)*/

    /*val test5 = File("test5.txt").readText().replace("\r", "")
    val input5 = File("input5.txt").readText().replace("\r", "")
    //day5(test5)
    day5(input5)*/

    /*val test6 = File("test6.txt").readText().replace("\r", "")
    val input6 = File("input6.txt").readText().replace("\r", "")
    day6(test6)
    day6(input6)*/

    val test7 = File("test7.txt").readText().replace("\r", "")
    val input7 = File("input7.txt").readText().replace("\r", "")
    day7(test7)
    day7(input7)
}

fun day7(input: String) {
    val rows = input.split("\n")
    val startX = rows[0].indexOfFirst { c -> c == 'S' }
    /*var tachyonPositions = listOf(startX to 0)*/
    var tachyonPositions = mutableMapOf(startX to 1L)
    var tachyonPaths = mutableSetOf<String>("C")

    rows.slice(1..<rows.size-1).forEach { row ->
        println(row)
        var newTachyonPositions = mutableMapOf<Int, Long>()
        tachyonPositions.forEach { pair ->
            val position = pair.key
            val newPositions = mutableSetOf<String>()
            if(row[position] == '^')
            {
                tachyonPaths = newPositions
                val old = tachyonPositions[position] ?: 0L
                val left = newTachyonPositions[position+1] ?: 0L;
                val right = newTachyonPositions[position-1] ?: 0L

                newTachyonPositions.put(position+1, left+old)
                newTachyonPositions.put(position-1, right+old)
            }else {
                val center = tachyonPositions[position] ?: 0L;
                val center2 = newTachyonPositions[position] ?: 0L;

                newTachyonPositions[position] = center + center2
            }
        }
        println(newTachyonPositions)
        tachyonPositions = newTachyonPositions
    }
    val efe = tachyonPositions.values.sum()
    println("final $efe")
    //1630
}



fun day6(input: String) {
    println(input)
    val rows = input.split("\n")
    val length = input.split(" ").map { it.length }
    println(length)
    val longest = length.reduce { acc, numb -> if (numb > acc ) numb else acc }
    val operators = rows[rows.size - 1].split(" ").filter(String::isNotBlank)
    var total = 0L
    val height = rows.size

    var x = 0
    operators.forEachIndexed { index, operator ->

        var sum = if(operator == "*") 1L else 0L
        while (x < rows[0].length) {
            var number = 0L
            for (y in 0 until height-1) {
                val entry = rows[y][x].digitToIntOrNull()
                if(entry != null) {
                    number = number*10L + entry
print(entry)
                }
            }
            x++
            if(number == 0L) break
            println("number " +number)
            if(operator == "*")
                sum *= number
            else
                sum += number
        }
        total += sum
    }
    // Too low 10512390

    println("Total " + total)
}

fun day5(input: String) {
    val sections = input.split("\n\n")
    val freshArr = sections[0].split("\n")
    val freshRanges: List<Pair<Long, Long>> = freshArr.map { rangeStr ->
        val pairArr = rangeStr.split("-")
        val min = pairArr[0].toLong()
        val max = pairArr[1].toLong()
        val pair = min to max
        return@map pair
    }

    val sortedRanges = freshRanges.sortedBy { pair -> pair.first }
    /*    var freshMap = hashMapOf<Int, List<Pair<Long, Long>()

        freshArr.forEach { rangeStr ->
            val pairArr = rangeStr.split("-")
            val min = pairArr[0].toLong()
            val max = pairArr[1].toLong()
            val pair = min to max
            val stringLength = pairArr[0].length
            val firstDigit = rangeStr[0]
            val firstDigitMax = pairArr[1][0]
            val firstRangeMax = firstDigitMax.digitToInt()+1
            val firstRangeMin = firstDigit.digitToInt()

            for (i in firstRangeMin until firstRangeMax) {
                val existingList: List<Pair<Long, Long>>? = freshMap[stringLength]?.get(i)
                val newList = existingList?.plus(pair) ?: listOf(pair)
                val map = freshMap[stringLength] ?: hashMapOf()
                map[i] = newList
                freshMap[stringLength] = map
                println(stringLength.toString() + i.toString() + "" + newList)
            }
        }*/

    var currentPair: Pair<Long, Long> = sortedRanges[0].first to sortedRanges[0].second
    var normalizedRanges = listOf<Pair<Long, Long>>()
    sortedRanges.forEachIndexed { index, (first, second) ->
        if (first <= currentPair.second) {
            if (second > currentPair.second) {
                currentPair = currentPair.first to second
            }
            if (index == sortedRanges.size - 1)
                normalizedRanges = normalizedRanges.plus(currentPair)
        } else {
            normalizedRanges = normalizedRanges.plus(currentPair)
            currentPair = first to second
            if (index == sortedRanges.size - 1)
                normalizedRanges = normalizedRanges.plus(currentPair)
        }
    }

    var total = 0L
    normalizedRanges.forEach { (first, second) ->
        println(first.toString() + ", " + second.toString())
        total += second - first + 1
    }

    println(total)

    //Too low: 348778560947231

    /*val ingredients = sections[1].split("\n")
    var total = 0

    for (ingredient in ingredients) {
        val firstDigitMap = freshMap[ingredient.length]
        var fresh = false
        if(firstDigitMap == null) println("No length for ${ingredient.length}, ${ingredient}")
        else {
            val ranges = firstDigitMap.get(ingredient[0].digitToInt())
            val ingredientNr = ingredient.toLong()
            if(ranges == null) println("No first digit for ${ingredient[0]}, ${ingredient}")
            else {
                for(range in ranges) {
                    if(ingredientNr >= range.first && ingredientNr <= range.second) {
                        fresh = true
                        break
                    }
                }
            }
        }

        if(fresh) {
            total++
        }
    }*/
}

fun day4(input: String) {
    val rows = input.split("\n")
    val grid = Array(rows.size + 2) { Array(rows[0].length + 2) { '.' } }
    var result = 0
    rows.forEachIndexed { y, row ->
        row.forEachIndexed { x, c ->
            grid[x + 1][y + 1] = c
        }
    }

    var oldResult = -1
    while (oldResult != result) {
        oldResult = result
        grid.forEachIndexed { y, row ->
            row.forEachIndexed { x, here2 ->
                if (x > 0 && y > 0 && x < grid.size - 1 && y < grid[0].size - 1) {
                    val here = grid[x][y]
                    if (here == '@') {
                        var filledNeighbors = 0
                        for (adjacentX in -1..1)
                            for (adjacentY in -1..1)
                                grid[x + adjacentX][y + adjacentY]?.let { if (it == '@') filledNeighbors++ }
                        if (filledNeighbors < 5) {
                            result++
                            grid[x][y] = '.'
                            print("X")
                        } else
                            print(here)
                    } else
                        print(here)
                }
            }
            println()
        }
        println(result)
    }
    // 8282 too low
}


fun day3(input: String) {
    val banks = input.split("\n")
    var totalPower = 0.0
    banks.forEach { str ->
        val bank = str.replace("\r", "")
        var highList = (0..11).toList()
        bank.forEachIndexed { index, character ->
            if (character.isDigit()) {
                val battery = character.digitToInt()
                run inner@{
                    highList.forEachIndexed { highIndex, highest ->
                        val maxIndex = (bank.length + 1 - (highList.size - highIndex))
                        print(maxIndex)
                        if (battery > highest && index < (maxIndex)) {
                            highList =
                                highList.mapIndexed { ind, higher -> if (ind < highIndex) higher else if (ind == highIndex) battery else 0 }
                            return@inner
                        }
                    }
                }
            }
        }
        val fefe: Double = 10.0
        val finalList = highList.mapIndexed { ind, finalBattery -> fefe.pow(highList.size - ind - 1) * finalBattery }
        println("\n" + highList)
        println(finalList.sum())
        totalPower += finalList.sum()
    }
    println("Final result " + totalPower.toBigDecimal().toPlainString())

    //1701471287534550 too high
}

fun day2(input: String) {
    var badIdSum = 0L

    val ranges = input.split(",")
    ranges.forEach {
        val pair = it.split("-")
        if (pair[0][0] == '0')
            badIdSum += pair[0].toLong()
        if (pair[1][0] == '0')
            badIdSum += pair[0].toLong()

        val start = pair[0].toLong()
        val end = pair[1].toLong()

        for (i in start..end) {
            //Split the number in the middle by digit
            val charId = i.toString()
            if (charId.length > 1)
                for (size in 1 until (charId.length)) {
                    val array = charId.chunked(size)
                    // if every chunk is the same
                    if (array.all { it == array[0] }) {
                        println(i)
                        badIdSum += i
                        break
                    }
                }
        }
        println("result $badIdSum")
    }
}
