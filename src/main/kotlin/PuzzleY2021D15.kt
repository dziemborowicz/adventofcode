class PuzzleY2021D15 : Puzzle {

  private lateinit var cavern: Grid<Int>

  override fun parse(input: String) {
    cavern = input.parseDenseIntGrid()
  }

  override fun solve1(): Int {
    return solve(cavern)
  }

  override fun solve2(): Int {
    val fullCavern = Grid(cavern.numRows * 5, cavern.numColumns * 5) { (row, column) ->
      val increment = (row / cavern.numRows) + (column / cavern.numColumns)
      (cavern.getWrapped(row, column) + increment - 1).mod(9) + 1
    }
    return solve(fullCavern)
  }

  private fun solve(cavern: Grid<Int>): Int {
    val minRisk = cavern.map { Int.MAX_VALUE }
    val queue = priorityQueueOf(Index.ZERO to 0, compareBy { it.second })
    while (queue.isNotEmpty()) {
      val (index, risk) = queue.poll()
      if (index == cavern.indices.last()) {
        return risk
      } else if (risk < minRisk[index]) {
        minRisk[index] = risk
        cavern.forEachNeighborIndexed(index) { nextIndex, value -> queue.add(nextIndex to risk + value) }
      }
    }
    fail()
  }

  companion object {
    val testInput1 = """
      1163751742
      1381373672
      2136511328
      3694931569
      7463417111
      1319128137
      1359912421
      3125421639
      1293138521
      2311944581
      """.trimIndent()
    val testAnswer1 = 40

    val testInput2 = testInput1
    val testAnswer2 = 315
  }
}
