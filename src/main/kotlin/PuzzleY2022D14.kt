class PuzzleY2022D14 : Puzzle {

  private lateinit var cave: Grid<Char>

  override fun parse(input: String) {
    cave = Grid(1000, 1000, '.')
    input.lines().forEach { line ->
      line.split(" -> ").map { it.toPoint() }.windowed(2).forEach { (start, end) ->
        check(start.x == end.x || start.y == end.y)
        for (row in start.y upOrDownTo end.y) {
          for (column in start.x upOrDownTo end.x) {
            cave[row, column] = '#'
          }
        }
      }
    }
  }

  override fun solve1(): Int {
    val cave = cave.copy()
    cave.fillWithSand()
    return cave.count { it == 'o' }
  }

  override fun solve2(): Int {
    val cave = cave.copy()
    val floor = cave.indices.filter { cave[it] == '#' }.maxOf { it.row } + 2
    for (column in cave.columnIndices) {
      cave[floor, column] = '#'
    }
    cave.fillWithSand()
    return cave.count { it == 'o' }
  }

  private fun Grid<Char>.fillWithSand() {
    while (true) {
      var index = Index(0, 500)
      if (this[index] != '.') return
      while (true) {
        index = when {
          index.row + 1 == numRows -> return
          this[index.down()] == '.' -> index.down()
          this[index.downLeft()] == '.' -> index.downLeft()
          this[index.downRight()] == '.' -> index.downRight()
          else -> {
            this[index] = 'o'
            break
          }
        }
      }
    }
  }

  companion object {
    val testInput1 = """
      498,4 -> 498,6 -> 496,6
      503,4 -> 502,4 -> 502,9 -> 494,9
      """.trimIndent()
    val testAnswer1 = 24

    val testInput2 = testInput1
    val testAnswer2 = 93
  }
}
