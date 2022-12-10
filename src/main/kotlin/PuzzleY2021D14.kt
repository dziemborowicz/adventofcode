class PuzzleY2021D14 : Puzzle {

  private lateinit var polymerTemplate: String
  private lateinit var pairInsertionRules: Map<String, String>

  override fun parse(input: String) {
    val parts = input.lines().splitByBlank()
    polymerTemplate = parts[0].single()
    pairInsertionRules = parts[1].associate { it.split(" -> ").toPair() }
  }

  override fun solve1(): Long {
    return solve(10)
  }

  override fun solve2(): Long {
    return solve(40)
  }

  private fun solve(steps: Int): Long {
    val pairCounts = " $polymerTemplate ".windowed(2).toLongCounter()
    repeat(steps) {
      pairCounts.incrementAll(pairInsertionRules.flatMap { rule ->
        val prevCount = pairCounts[rule.key]
        listOf(
          (rule.key.first() + rule.value) to prevCount,
          (rule.value + rule.key.last()) to prevCount,
          rule.key to -prevCount,
        )
      })
    }
    val counts =
      pairCounts.counts()
        .flatMap { (key, count) -> listOf(key.first() to count, key.last() to count) }
        .filter { it.first != ' ' }
        .toLongCounter()
        .also { it.divideAllBy(2) }
    return counts.max().count - counts.min().count
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
