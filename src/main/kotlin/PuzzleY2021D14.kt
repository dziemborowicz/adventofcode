class PuzzleY2021D14 : Puzzle {

  private lateinit var polymerTemplate: String
  private lateinit var pairInsertionRules: Map<String, String>

  override fun parse(input: String) {
    val parts = input.lines().splitByBlank()
    polymerTemplate = parts[0].only()
    pairInsertionRules = parts[1].associate { it.split(" -> ").asPair() }
  }

  override fun solve1(): Long {
    return solve(10)
  }

  override fun solve2(): Long {
    return solve(40)
  }

  private fun solve(steps: Int): Long {
    val counts = " $polymerTemplate ".windowed(2).toLongCountMap()
    repeat(steps) {
      counts.incrementAll(pairInsertionRules.flatMap { rule ->
        val prevCount = counts.getCount(rule.key)
        listOf(
          (rule.key.first() + rule.value) to prevCount,
          (rule.value + rule.key.last()) to prevCount,
          rule.key to -prevCount,
        )
      })
    }
    val singleCounts =
      counts.flatMap { (key, count) -> listOf(key.first() to count, key.last() to count) }
        .filter { it.first != ' ' }
        .toLongCountMap()
        .also { it.replaceAll { _, count -> count / 2 } }
    return singleCounts.values.max() - singleCounts.values.filter { it != 0L }.min()
  }

  companion object {
    val testInput1 = """
      NNCB

      CH -> B
      HH -> N
      CB -> H
      NH -> C
      HB -> C
      HC -> B
      HN -> C
      NN -> C
      BH -> H
      NC -> B
      NB -> B
      BN -> B
      BB -> N
      BC -> B
      CC -> N
      CN -> C
      """.trimIndent()
    val testAnswer1 = 1588

    val testInput2 = testInput1
    val testAnswer2 = 2188189693529
  }
}
