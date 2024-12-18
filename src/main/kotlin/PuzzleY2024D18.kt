class PuzzleY2024D18 : Puzzle {

  private lateinit var bytes: List<Index>

  override fun parse(input: String) {
    bytes = input.extractIntLists().map { (x, y) -> Index(y, x) }
  }

  override fun solve1(): Int {
    val memory = if (isTestInput) {
      val corruptions = bytes.take(12).toHashSet()
      Grid(7, 7) { if (it in corruptions) '#' else '.' }
    } else {
      val corruptions = bytes.take(1024).toHashSet()
      Grid(71, 71) { if (it in corruptions) '#' else '.' }
    }
    return memory.minStepsToExit()!!
  }

  override fun solve2(): String {
    val memory = if (isTestInput) {
      Grid(7, 7, '.')
    } else {
      Grid(71, 71, '.')
    }
    val corruptions = bytes.toDeque()
    while (true) {
      val index = corruptions.removeFirst()
      memory[index] = '#'
      if (memory.minStepsToExit() == null) {
        return "${index.column},${index.row}"
      }
    }
  }

  private fun Grid<Char>.minStepsToExit(): Int? {
    val start = Index.ZERO
    val end = indices.last()
    val visited = hashSetOf(start)
    val queue = priorityQueueOf(0 to start, compareBy { it.first })
    while (queue.isNotEmpty()) {
      val (cost, index) = queue.poll()
      if (index == end) return cost
      forEachNeighborIndexed(index) { i, c ->
        if (c != '#' && visited.add(i)) queue += (cost + 1) to i
      }
    }
    return null
  }

  private val isTestInput by lazy { bytes.size == 25 }

  companion object {
    val testInput1 = """
      5,4
      4,2
      4,5
      3,0
      2,1
      6,3
      2,4
      1,5
      0,6
      3,3
      2,6
      5,1
      1,2
      5,5
      2,5
      6,5
      1,4
      0,4
      6,4
      1,1
      6,1
      1,0
      0,5
      1,6
      2,0
      """.trimIndent()
    val testAnswer1 = 22

    val testInput2 = testInput1
    val testAnswer2 = "6,1"
  }
}
