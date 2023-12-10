class PuzzleY2023D11 : Puzzle {

  private lateinit var space: Grid<Char>

  override fun parse(input: String) {
    space = input.parseDenseCharGrid()
  }

  override fun solve1(): Long = solve(expansion = 2)

  override fun solve2(): Long = solve(expansion = 1_000_000)

  fun solve(expansion: Long): Long {
    val emptyRows = space.rowIndices.filter { space.row(it).none { it == '#' } }.toSet()
    val emptyColumns = space.columnIndices.filter { space.column(it).none { it == '#' } }.toSet()
    return space.indices.filter { space[it] == '#' }.combinations(2).sumOf { (a, b) ->
      val emptyRowsTransited = (a.row upOrDownTo b.row).count { it in emptyRows }
      val emptyColumnsTransited = (a.column upOrDownTo b.column).count { it in emptyColumns }
      (a manhattanDistanceTo b) + ((expansion - 1) * (emptyRowsTransited + emptyColumnsTransited))
    }
  }

  companion object {
    val testInput1 = """
      ...#......
      .......#..
      #.........
      ..........
      ......#...
      .#........
      .........#
      ..........
      .......#..
      #...#.....
      """.trimIndent()
    val testAnswer1 = 374

    fun test2() {
      PuzzleY2023D11().apply {
        parse(testInput1)
        check(solve(10) == 1030L)
        check(solve(100) == 8410L)
      }
    }
  }
}
