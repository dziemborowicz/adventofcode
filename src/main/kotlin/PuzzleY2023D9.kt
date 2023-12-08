class PuzzleY2023D9 : Puzzle {

  private lateinit var histories: List<List<Long>>

  override fun parse(input: String) {
    histories = input.lines().map { it.extractLongs() }
  }

  override fun solve1(): Long = histories.sumOf { extrapolate(it).last() }

  override fun solve2(): Long = histories.sumOf { extrapolate(it).first() }

  private fun extrapolate(history: List<Long>): List<Long> {
    val deltas = mutableListOf(history.toMutableList())
    while (deltas.last().any { it != 0L }) {
      deltas += deltas.last().windowed(2).map { (a, b) -> b - a }.toMutableList()
    }
    for ((delta, nextDelta) in deltas.windowed(2).reversed()) {
      delta.add(0, delta.first() - nextDelta.first())
      delta.add(delta.last() + nextDelta.last())
    }
    return deltas.first()
  }

  companion object {
    val testInput1 = """
      0 3 6 9 12 15
      1 3 6 10 15 21
      10 13 16 21 30 45
      """.trimIndent()
    val testAnswer1 = 114

    val testInput2 = testInput1
    val testAnswer2 = 2
  }
}
