class PuzzleY2021D9 : Puzzle {

  private lateinit var heightmap: Grid<Int>

  override fun parse(input: String) {
    heightmap = input.parseDenseIntGrid()
  }

  override fun solve1(): Int {
    return heightmap.indices.filter { heightmap.isLowPoint(it) }.sumOf { heightmap[it] + 1 }
  }

  override fun solve2(): Int {
    return heightmap.indices
      .filter { heightmap.isLowPoint(it) }
      .map { heightmap.basinSize(lowPoint = it) }
      .sorted().takeLast(3).product()
  }

  private fun Grid<Int>.isLowPoint(index: Grid.Index) =
    getAdjacent(index).none { it <= this[index] }

  private fun Grid<Int>.basinSize(lowPoint: Grid.Index): Int {
    val basin = mutableSetOf<Grid.Index>()
    val queue = mutableListOf(lowPoint)
    while (queue.isNotEmpty()) {
      val index = queue.removeFirst()
      if (basin.add(index)) {
        queue.addAll(index.adjacentIn(this).filter { this[it] > this[index] && this[it] < 9 })
      }
    }
    return basin.size
  }

  companion object {
    val testInput1 = """
      2199943210
      3987894921
      9856789892
      8767896789
      9899965678
      """.trimIndent()
    val testAnswer1 = 15

    val testInput2 = testInput1
    val testAnswer2 = 1134
  }
}
