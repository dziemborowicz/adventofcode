class PuzzleY2023D21 : Puzzle {

  private lateinit var grid: Grid<Char>
  private lateinit var start: Index

  override fun parse(input: String) {
    grid = input.parseDenseCharGrid()
    start = grid.indexOf('S')
  }

  override fun solve1(): Int {
    return solve(64)
  }

  override fun solve2(): Long {
    val steps = 26501365

    check(grid.numRows == grid.numColumns)
    check(start.row == grid.numRows / 2)
    check(start.column == grid.numColumns / 2)

    val y = (0..2).map { x ->
      solve((x * grid.numRows) + start.row, wrap = true).toLong()
    }

    val a = (y[0] - (2 * y[1]) + y[2]) / 2
    val b = ((-3 * y[0]) + (4 * y[1]) - y[2]) / 2
    val c = y[0]

    val n = steps / grid.numRows
    return (a * n * n) + (b * n) + c
  }

  private fun solve(steps: Int, wrap: Boolean = false): Int {
    val seen = hashMapOf<Index, Int>()
    var next = setOf(start)
    repeat(steps + 1) { step ->
      next = next.flatMap { index ->
        if (seen.putIfAbsent(index, step) == null) {
          if (wrap) {
            index.neighbors().filter { grid.getWrapped(it) != '#' }
          } else {
            index.neighborsIn(grid).filter { grid[it] != '#' }
          }
        } else {
          listOf()
        }
      }.toSet()
    }
    return seen.values.count { it % 2 == steps % 2 }
  }

  companion object {
    fun test() {
      val input = """
        ...........
        .....###.#.
        .###.##..#.
        ..#.#...#..
        ....#.#....
        .##..S####.
        .##..#...#.
        .......##..
        .##.#.####.
        .##..##.##.
        ...........
        """.trimIndent()

      PuzzleY2023D21().apply {
        parse(input)

        check(solve(6) == 16)

        check(solve(6, wrap = true) == 16)
        check(solve(10, wrap = true) == 50)
        check(solve(50, wrap = true) == 1594)
        check(solve(100, wrap = true) == 6536)
        check(solve(500, wrap = true) == 167004)
        check(solve(1000, wrap = true) == 668697)
      }
    }
  }
}
