class PuzzleY2024D19 : Puzzle {

  private lateinit var towels: List<String>
  private lateinit var patterns: List<String>
  private lateinit var waysToMake: HashMap<String, Long>

  override fun parse(input: String) {
    val (first, second) = input.lines().splitByBlank()
    towels = first.extractStrings()
    patterns = second
    waysToMake = hashMapOf()
  }

  override fun solve1(): Int = patterns.count { waysToMake(it) != 0L }

  override fun solve2(): Long = patterns.sumOf { waysToMake(it) }

  private fun waysToMake(pattern: String): Long {
    if (pattern in waysToMake) return waysToMake.getValue(pattern)
    if (pattern.isEmpty()) return 1L
    return towels.sumOf {
      if (pattern.startsWith(it)) {
        waysToMake(pattern.removePrefix(it))
      } else {
        0L
      }
    }.also { waysToMake[pattern] = it }
  }

  companion object {
    val testInput1 = """
      r, wr, b, g, bwu, rb, gb, br
      
      brwrr
      bggr
      gbbr
      rrbgbr
      ubwu
      bwurrg
      brgr
      bbrgwb
      """.trimIndent()
    val testAnswer1 = 6

    val testInput2 = testInput1
    val testAnswer2 = 16
  }
}
