class PuzzleY2020D6 : Puzzle {

  private lateinit var groups: List<List<Set<Char>>>

  override fun parse(input: String) {
    groups = input.lines().splitByBlank().map { group -> group.map { it.toSet() } }
  }

  override fun solve1(): Int {
    return groups.sumOf { it.reduce { a, b -> a union b }.size }
  }

  override fun solve2(): Int {
    return groups.sumOf { it.reduce { a, b -> a intersect b }.size }
  }

  companion object {
    val testInput1 = """
      abc
      
      a
      b
      c
      
      ab
      ac
      
      a
      a
      a
      a
      
      b
      """.trimIndent()
    val testAnswer1 = 11

    val testInput2 = testInput1
    val testAnswer2 = 6
  }
}
