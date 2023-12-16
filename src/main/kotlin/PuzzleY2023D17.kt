import Index.Companion.DIRECTIONS

class PuzzleY2023D17 : Puzzle {

  private val NO_DIRECTION = Index(-2, -2)

  private lateinit var grid: Grid<Int>
  private lateinit var start: Index
  private lateinit var end: Index

  override fun parse(input: String) {
    grid = input.parseDenseIntGrid()
    start = grid.indices.first()
    end = grid.indices.last()
  }

  override fun solve1(): Int = solve(minMoves = 0, maxMoves = 3)

  override fun solve2(): Int = solve(minMoves = 4, maxMoves = 10)

  private fun solve(minMoves: Int, maxMoves: Int): Int {
    val heatLosses = cacheOf<Int>()

    val queue = priorityQueueOf(
      Quadruple(start, NO_DIRECTION, 0, 0),
      compareBy { it.fourth },
    )

    while (queue.isNotEmpty()) {
      val (index, direction, moves, heatLoss) = queue.poll()

      if (index == end && moves >= minMoves) return heatLoss
      if (!heatLosses.setMin(index, direction, moves, value = heatLoss)) continue

      queue.addAll(DIRECTIONS.filter { nextDirection ->
        index + nextDirection in grid.indices &&
          nextDirection != direction * -1 &&
          (direction == NO_DIRECTION || nextDirection == direction || moves >= minMoves) &&
          (direction == NO_DIRECTION || nextDirection != direction || moves < maxMoves)
      }.map { nextDirection ->
        Quadruple(
          index + nextDirection,
          nextDirection,
          if (nextDirection == direction) moves + 1 else 1,
          heatLoss + grid[index + nextDirection],
        )
      })
    }

    fail()
  }

  companion object {
    val testInput1 = """
      2413432311323
      3215453535623
      3255245654254
      3446585845452
      4546657867536
      1438598798454
      4457876987766
      3637877979653
      4654967986887
      4564679986453
      1224686865563
      2546548887735
      4322674655533
      """.trimIndent()
    val testAnswer1 = 102

    val testInput2_1 = testInput1
    val testAnswer2_1 = 94

    val testInput2_2 = """
      111111111111
      999999999991
      999999999991
      999999999991
      999999999991
      """.trimIndent()
    val testAnswer2_2 = 71
  }
}
