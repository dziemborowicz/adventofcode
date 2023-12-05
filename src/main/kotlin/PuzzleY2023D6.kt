class PuzzleY2023D6 : Puzzle {

  private lateinit var timesLine: String
  private lateinit var distancesLine: String

  override fun parse(input: String) {
    val lines = input.lines()
    timesLine = lines[0]
    distancesLine = lines[1]
  }

  override fun solve1(): Long {
    val times = timesLine.extractLongs()
    val distances = distancesLine.extractLongs()
    return times.zip(distances).productOf { (time, distance) -> countWaysToWin(time, distance) }
  }

  override fun solve2(): Long {
    val time = timesLine.filter { it.isDigit() }.toLong()
    val distance = distancesLine.filter { it.isDigit() }.toLong()
    return countWaysToWin(time, distance)
  }

  private fun countWaysToWin(time: Long, distance: Long): Long =
    (1..time).longCount { (time - it) * it > distance }

  companion object {
    val testInput1 = """
      Time:      7  15   30
      Distance:  9  40  200
      """.trimIndent()
    val testAnswer1 = 288

    val testInput2 = testInput1
    val testAnswer2 = 71503
  }
}
