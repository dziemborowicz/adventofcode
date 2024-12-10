class PuzzleY2024D10 : Puzzle {

  private lateinit var topographicMap: Grid<Int>

  override fun parse(input: String) {
    topographicMap = input.parseDenseIntGrid()
  }

  override fun solve1(): Int =
    topographicMap.trails().groupBy { it.first() }.values.sumOf { trails ->
      trails.map { it.last() }.countDistinct()
    }

  override fun solve2(): Int = topographicMap.trails().size

  private fun Grid<Int>.trails(): List<List<Index>> =
    indices.filter { this[it] == 0 }.flatMap { trailsFrom(it) }

  private fun Grid<Int>.trailsFrom(index: Index): List<List<Index>> {
    val result = mutableListOf<List<Index>>()
    val queue = dequeOf(listOf(index))
    while (queue.isNotEmpty()) {
      val path = queue.removeFirst()
      if (this[path.last()] == 9) {
        result += path
      } else {
        val neighbors = path.last().neighborsIn(this)
        queue.addAll(neighbors.filter { this[it] == this[path.last()] + 1 }.map { path + it })
      }
    }
    return result
  }

  companion object {
    val testInput1_1 = """
      0123
      1234
      8765
      9876
      """.trimIndent()
    val testAnswer1_1 = 1

    val testInput1_2 = """
      89010123
      78121874
      87430965
      96549874
      45678903
      32019012
      01329801
      10456732
      """.trimIndent()
    val testAnswer1_2 = 36

    val testInput2 = testInput1_2
    val testAnswer2 = 81
  }
}
