class PuzzleY2022D4 : Puzzle {

  private lateinit var pairs: List<Pair<IntRange, IntRange>>

  override fun parse(input: String) {
    pairs = input.lines().map { line ->
      line.split(',').map { it.toIntRange() }.toPair()
    }
  }

  override fun solve1(): Int {
    return pairs.count { it.first.containsAll(it.second) || it.second.containsAll(it.first) }
  }

  override fun solve2(): Int {
    return pairs.count { it.first.containsAny(it.second) }
  }

  companion object {
    val testInput1 = """
      2-4,6-8
      2-3,4-5
      5-7,7-9
      2-8,3-7
      6-6,4-6
      2-6,4-8
      """.trimIndent()
    val testAnswer1 = 2

    val testInput2 = testInput1
    val testAnswer2 = 4
  }
}
