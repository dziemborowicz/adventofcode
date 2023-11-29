class PuzzleY2020D13 : Puzzle {

  private var timeAtBusStop: Long = -1
  private lateinit var busIds: List<Long?>

  override fun parse(input: String) {
    val (firstLine, secondLine) = input.lines()
    timeAtBusStop = firstLine.toLong()
    busIds = secondLine.split(',').map { it.toLongOrNull() }
  }

  override fun solve1(): Long {
    val busId = busIds.filterNotNull().minBy { it - timeAtBusStop.mod(it) }
    return busId * (busId - timeAtBusStop.mod(busId))
  }

  override fun solve2(): Long {
    var timestamp = 0L
    var product = 1L
    for ((i, busId) in busIds.withIndex()) {
      if (busId == null) continue
      while ((timestamp + i).mod(busId) != 0L) {
        timestamp += product
      }
      product *= busId
    }
    return timestamp
  }

  companion object {
    val testInput1 = """
      939
      7,13,x,x,59,x,31,19
      """.trimIndent()
    val testAnswer1 = 295

    val testInput2_1 = testInput1
    val testAnswer2_1 = 1068781

    val testInput2_2 = """
      -1
      17,x,13,19
      """.trimIndent()
    val testAnswer2_2 = 3417

    val testInput2_3 = """
      -1
      67,7,59,61
      """.trimIndent()
    val testAnswer2_3 = 754018

    val testInput2_4 = """
      -1
      67,x,7,59,61
      """.trimIndent()
    val testAnswer2_4 = 779210

    val testInput2_5 = """
      -1
      67,7,x,59,61
      """.trimIndent()
    val testAnswer2_5 = 1261476

    val testInput2_6 = """
      -1
      1789,37,47,1889
      """.trimIndent()
    val testAnswer2_6 = 1202161486
  }
}
