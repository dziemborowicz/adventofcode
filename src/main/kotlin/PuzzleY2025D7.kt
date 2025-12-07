class PuzzleY2025D7 : Puzzle {

  private lateinit var grid: Grid<Char>
  private lateinit var start: Index

  override fun parse(input: String) {
    grid = input.parseDenseCharGrid()
    start = grid.indexOf('S')
  }

  override fun solve1(): Long = solve().first

  override fun solve2(): Long = solve().second

  private fun solve(): Pair<Long, Long> {
    var numSplits = 0L
    val ways = grid.map { if (it == 'S') 1L else 0L }

    for (row in grid.rowIndices.drop(1)) {
      for (column in grid.columnIndices) {
        val waysHere = ways[row - 1, column]
        if (waysHere != 0L) {
          if (grid[row, column] == '^') {
            numSplits++
            ways[row, column - 1] += waysHere
            ways[row, column + 1] += waysHere
          } else {
            ways[row, column] += waysHere
          }
        }
      }
    }

    return numSplits to ways.rows().last().sum()
  }

  companion object {
    val testInput1 = """
      .......S.......
      ...............
      .......^.......
      ...............
      ......^.^......
      ...............
      .....^.^.^.....
      ...............
      ....^.^...^....
      ...............
      ...^.^...^.^...
      ...............
      ..^...^.....^..
      ...............
      .^.^.^.^.^...^.
      ...............
      """.trimIndent()
    val testAnswer1 = 21

    val testInput2 = testInput1
    val testAnswer2 = 40
  }
}
