class PuzzleY2023D14 : Puzzle {

  private lateinit var dish: Grid<Char>

  override fun parse(input: String) {
    dish = input.parseDenseCharGrid()
  }

  override fun solve1(): Int {
    val dish = dish.copy()
    dish.tiltNorth()
    return dish.load
  }

  override fun solve2(): Int {
    val numCycles = 1_000_000_000
    val dish = dish.copy()
    val history = mutableListOf<Grid<Char>>()
    repeat(numCycles) { i ->
      val indexInHistory = history.indexOf(dish)
      if (indexInHistory != -1) {
        val cycle = history.subList(indexInHistory, history.size)
        return cycle[(numCycles - i).mod(cycle.size)].load
      }
      history.add(dish.copy())

      dish.tiltNorth()
      dish.rotateClockwise()
      dish.tiltNorth()
      dish.rotateClockwise()
      dish.tiltNorth()
      dish.rotateClockwise()
      dish.tiltNorth()
      dish.rotateClockwise()
    }
    return dish.load
  }

  private fun Grid<Char>.tiltNorth() {
    val cubes = columnIndices.map { Index(-1, it) } + indices.filter { this[it] == '#' }
    for (cube in cubes) {
      val cubeBelowRow =
        rowIndices.filter { it > cube.row }.firstOrNull { row -> this[row, cube.column] == '#' }
          ?: numRows
      val rowsBetweenCubes = (cube.row + 1)..(cubeBelowRow - 1)
      var numRoundRocksBetweenCubes = rowsBetweenCubes.count { this[it, cube.column] == 'O' }
      for (row in rowsBetweenCubes) {
        this[row, cube.column] = if (numRoundRocksBetweenCubes > 0) {
          'O'.also { numRoundRocksBetweenCubes-- }
        } else {
          '.'
        }
      }
    }
  }

  private val Grid<Char>.load: Int
    get() = indices.filter { this[it] == 'O' }.sumOf { numRows - it.row }

  companion object {
    val testInput1 = """
      O....#....
      O.OO#....#
      .....##...
      OO.#O....O
      .O.....O#.
      O.#..O.#.#
      ..O..#O..O
      .......O..
      #....###..
      #OO..#....
      """.trimIndent()
    val testAnswer1 = 136

    val testInput2 = testInput1
    val testAnswer2 = 64
  }
}
