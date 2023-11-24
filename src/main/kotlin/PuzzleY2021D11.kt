class PuzzleY2021D11 : Puzzle {

  private lateinit var grid: Grid<Int>

  override fun parse(input: String) {
    grid = input.parseDenseIntGrid()
  }

  override fun solve1(): Int {
    return steps().take(101).sumOf { grid -> grid.count { it == 0 } }
  }

  override fun solve2(): Int {
    return steps().indexOfFirst { grid -> grid.all { it == 0 } }
  }

  private fun steps(): Sequence<Grid<Int>> {
    return sequence {
      var grid = grid
      while (true) {
        yield(grid)
        grid = grid.copy()
        grid.indices.forEach { grid[it]++ }
        val flashed = mutableSetOf<Index>()
        val queue = grid.indices.filter { grid[it] > 9 }.toMutableSet()
        while (queue.isNotEmpty()) {
          val index = queue.removeFirst()
          if (flashed.add(index)) {
            grid.forEachNeighborWithDiagonalsIndexed(index) { i, _ -> grid[i]++ }
            queue.addAll(grid.indices.filter { grid[it] > 9 })
          }
        }
        flashed.forEach { grid[it] = 0 }
      }
    }
  }

  companion object {
    val testInput1 = """
      5483143223
      2745854711
      5264556173
      6141336146
      6357385478
      4167524645
      2176841721
      6882881134
      4846848554
      5283751526
      """.trimIndent()
    val testAnswer1 = 1656

    val testInput2 = testInput1
    val testAnswer2 = 195
  }
}
