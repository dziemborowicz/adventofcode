class PuzzleY2024D5 : Puzzle {

  private lateinit var pagesBefore: Map<Int, List<Int>>
  private lateinit var manuals: List<List<Int>>

  override fun parse(input: String) {
    val (orderingRules, manuals) = input.lines().splitByBlank().map { it.map { it.extractInts() } }
    this.pagesBefore = orderingRules.groupBy({ it[1] }, { it[0] })
    this.manuals = manuals
  }

  override fun solve1(): Int = manuals.filter { it.isValid() }.sumOf { it.middle() }

  override fun solve2(): Int = manuals.filter { !it.isValid() }.sumOf { it.reordered().middle() }

  private fun List<Int>.isValid(): Boolean {
    return indices.all { index -> pagesBefore[this[index]]?.all { indexOf(it) < index } ?: true }
  }

  private fun List<Int>.reordered(): List<Int> {
    val list = toMutableList()
    var i = 0
    while (i in list.indices) {
      val j = pagesBefore[list[i]]?.maxOf { list.indexOf(it) } ?: -1
      if (j > i) {
        list.add(j, list.removeAt(i))
      } else {
        i++
      }
    }
    return list
  }

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
