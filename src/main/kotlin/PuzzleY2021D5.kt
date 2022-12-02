class PuzzleY2021D5 : Puzzle {

  private lateinit var lines: List<Pair<Grid.Index, Grid.Index>>
  private var numRows = -1
  private var numColumns = -1

  override fun parse(input: String) {
    lines = input.lines().map {
      val (x1, y1, x2, y2) = it.split(" -> ").flatMap { it.split(',').map(String::toInt) }
      Pair(Grid.Index(y1, x1), Grid.Index(y2, x2))
    }
    numRows = lines.flatMap { listOf(it.first.row, it.second.row) }.max() + 1
    numColumns = lines.flatMap { listOf(it.first.column, it.second.column) }.max() + 1
  }

  override fun solve1(): Int {
    val floor = Grid(numRows, numColumns, 0)
    lines
      .filter { it.first.row == it.second.row || it.first.column == it.second.column }
      .forEach { markLine(floor, it) }
    return floor.flatten().count { it > 1 }
  }

  override fun solve2(): Int {
    val floor = Grid(numRows, numColumns, 0)
    lines.forEach { markLine(floor, it) }
    return floor.flatten().count { it > 1 }
  }

  private fun markLine(floor: Grid<Int>, line: Pair<Grid.Index, Grid.Index>) {
    var index = line.first
    floor[index] += 1
    do {
      index = when {
        line.second.row > index.row -> Grid.Index(index.row + 1, index.column)
        line.second.row < index.row -> Grid.Index(index.row - 1, index.column)
        else -> index
      }
      index = when {
        line.second.column > index.column -> Grid.Index(index.row, index.column + 1)
        line.second.column < index.column -> Grid.Index(index.row, index.column - 1)
        else -> index
      }
      floor[index] += 1
    } while (index != line.second)
  }

  companion object {
    val testInput1 = """
      0,9 -> 5,9
      8,0 -> 0,8
      9,4 -> 3,4
      2,2 -> 2,1
      7,0 -> 7,4
      6,4 -> 2,0
      0,9 -> 2,9
      3,4 -> 1,4
      0,0 -> 8,8
      5,5 -> 8,2
      """.trimIndent()
    val testAnswer1 = 5

    val testInput2 = testInput1
    val testAnswer2 = 12
  }
}
