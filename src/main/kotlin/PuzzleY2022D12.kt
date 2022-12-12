import Grid.Index

class PuzzleY2022D12 : Puzzle {

  private lateinit var heightmap: Grid<Char>
  private lateinit var start: Index
  private lateinit var end: Index

  override fun parse(input: String) {
    heightmap = input.parseDenseCharGrid()
    start = heightmap.indexOf('S').also { heightmap[it] = 'a' }
    end = heightmap.indexOf('E').also { heightmap[it] = 'z' }
  }

  override fun solve1(): Int {
    return minStepsFrom(listOf(start))
  }

  override fun solve2(): Int {
    return minStepsFrom(heightmap.indices.filter { heightmap[it] == 'a' })
  }

  private fun minStepsFrom(starts: List<Index>): Int {
    val minStepsTo = Grid(heightmap.numRows, heightmap.numColumns, Int.MAX_VALUE)
    val queue = starts.map { it to 0 }.toDeque()
    while (queue.isNotEmpty()) {
      val (index, stepsSoFar) = queue.removeFirst()
      if (stepsSoFar < minStepsTo[index]) {
        minStepsTo[index] = stepsSoFar
        queue.addAll(
          index.adjacentIn(heightmap)
            .filter { heightmap[it] <= heightmap[index] + 1 }
            .map { it to (stepsSoFar + 1) }
        )
      }
    }
    return minStepsTo[end]
  }

  companion object {
    val testInput1 = """
      Sabqponm
      abcryxxl
      accszExk
      acctuvwj
      abdefghi
      """.trimIndent()
    val testAnswer1 = 31

    val testInput2 = testInput1
    val testAnswer2 = 29
  }
}
