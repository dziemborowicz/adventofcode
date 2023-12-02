class PuzzleY2023D3 : Puzzle {

  private lateinit var grid: List<String>
  private var partNumbers = mutableListOf<Int>()
  private var parts = hashMapOf<Index, MutableList<Int>>()

  override fun parse(input: String) {
    grid = input.lines()
    for (row in grid.indices) {
      Regex("\\d+").findAll(grid[row]).forEach { match ->
        val number = match.value.toInt()
        val adjacentParts =
          match.range.flatMap { Index(row, it).neighborsWithDiagonals() }.toSet().filter {
            val c = grid.getOrNull(it)
            c != null && c != '.' && !c.isDigit()
          }
        if (adjacentParts.isNotEmpty()) {
          partNumbers.add(number)
        }
        for (part in adjacentParts) {
          parts.putIfAbsent(part, mutableListOf())
          parts[part]!!.add(number)
        }
      }
    }
  }

  override fun solve1(): Int {
    return partNumbers.sum()
  }

  override fun solve2(): Int {
    return parts.entries.filter { (partIndex, adjacentPartNumbers) ->
      grid[partIndex] == '*' && adjacentPartNumbers.size == 2
    }.sumOf { (_, adjacentPartNumbers) -> adjacentPartNumbers.product() }
  }

  companion object {
    val testInput1 = """
      467..114..
      ...*......
      ..35..633.
      ......#...
      617*......
      .....+.58.
      ..592.....
      ......755.
      ...$.*....
      .664.598..
      """.trimIndent()
    val testAnswer1 = 4361

    val testInput2 = testInput1
    val testAnswer2 = 467835
  }
}
