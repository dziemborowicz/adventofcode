class PuzzleY2024D5 : Puzzle {

  private lateinit var orderingRules: Map<Pair<Int, Int>, Int>
  private lateinit var manuals: List<List<Int>>

  override fun parse(input: String) {
    val (orderingRules, manuals) = input.lines().splitByBlank().map { it.map { it.extractInts() } }
    this.orderingRules =
      orderingRules.flatMap { listOf(it.toPair() to -1, it.reversed().toPair() to 1) }.toMap()
    this.manuals = manuals
  }

  override fun solve1(): Int = manuals.filter { it == it.reordered() }.sumOf { it.middle() }

  override fun solve2(): Int =
    manuals.filter { it != it.reordered() }.sumOf { it.reordered().middle() }

  private fun List<Int>.reordered(): List<Int> =
    sortedWith { a, b -> orderingRules.getValue(a to b) }

  companion object {
    val testInput1 = """
      47|53
      97|13
      97|61
      97|47
      75|29
      61|13
      75|53
      29|13
      97|29
      53|29
      61|53
      97|53
      61|29
      47|13
      75|47
      97|75
      47|61
      75|61
      47|29
      75|13
      53|13
      
      75,47,61,53,29
      97,61,53,29,13
      75,29,13
      75,97,47,61,53
      61,13,29
      97,13,75,29,47
      """.trimIndent()
    val testAnswer1 = 143

    val testInput2 = testInput1
    val testAnswer2 = 123
  }
}
