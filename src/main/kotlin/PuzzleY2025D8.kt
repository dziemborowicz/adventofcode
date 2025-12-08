class PuzzleY2025D8 : Puzzle {

  private lateinit var points: List<Point3>
  private lateinit var neighbors: List<List<Point3>>

  override fun parse(input: String) {
    points = input.lines().map { it.toPoint3() }
    neighbors = points.combinations(2).sortedBy { (a, b) -> a distanceTo b }.toList()
  }

  override fun solve1(): Int {
    val numConnections = if (points.isTestInput()) 10 else 1000
    val groups = connect(numConnections)
    return groups.map { it.size }.sorted().takeLast(3).product()
  }

  override fun solve2(): Int {
    connect(onEachConnection = { groups, a, b ->
      if (groups.singleOrNull()?.size == points.size) {
        return a.x * b.x
      }
    })
    fail()
  }

  private inline fun connect(
    numConnections: Int = Int.MAX_VALUE,
    onEachConnection: (List<Set<Point3>>, Point3, Point3) -> Unit = { _, _, _ -> },
  ): List<Set<Point3>> {
    val result = mutableListOf<HashSet<Point3>>()
    for ((a, b) in neighbors.take(numConnections)) {
      val groups = result.filter { a in it || b in it }
      when {
        groups.isEmpty() -> result += hashSetOf(a, b)

        groups.size == 1 -> {
          groups.single() += a
          groups.single() += b
        }

        groups.size == 2 -> {
          groups.first() += groups.second()
          result -= groups.second()
        }

        else -> fail()
      }
      onEachConnection(result, a, b)
    }
    return result
  }

  companion object {
    private fun List<Point3>.isTestInput() = size == 20

    val testInput1 = """
      162,817,812
      57,618,57
      906,360,560
      592,479,940
      352,342,300
      466,668,158
      542,29,236
      431,825,988
      739,650,466
      52,470,668
      216,146,977
      819,987,18
      117,168,530
      805,96,715
      346,949,466
      970,615,88
      941,993,340
      862,61,35
      984,92,344
      425,690,689
      """.trimIndent()
    val testAnswer1 = 40

    val testInput2 = testInput1
    val testAnswer2 = 25272
  }
}
