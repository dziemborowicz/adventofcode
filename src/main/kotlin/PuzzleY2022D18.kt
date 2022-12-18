class PuzzleY2022D18 : Puzzle {

  private lateinit var lava: List<Point3>

  override fun parse(input: String) {
    lava = input.lines().map { Point3(it) }
  }

  override fun solve1(): Int {
    return surfaceAreaOf(lava)
  }

  override fun solve2(): Int {
    return surfaceAreaOf(lava) - surfaceAreaOf(cubesEnclosedBy(lava))
  }

  private fun surfaceAreaOf(cubes: List<Point3>): Int {
    var surfaceArea = cubes.size * 6
    for (i in 1..cubes.lastIndex) {
      for (j in 0 until i) {
        if (cubes[i] isNeighborOf cubes[j]) {
          surfaceArea -= 2
        }
      }
    }
    return surfaceArea
  }

  private fun cubesEnclosedBy(cubes: List<Point3>): List<Point3> {
    require(cubes.none { it.x < 0 })
    require(cubes.none { it.y < 0 })
    require(cubes.none { it.z < 0 })

    val maxX = cubes.maxOf { it.x }
    val maxY = cubes.maxOf { it.y }
    val maxZ = cubes.maxOf { it.z }

    val space = Array(maxX + 2) { Array(maxY + 2) { Array(maxZ + 2) { false } } }
    cubes.forEach { space[it.x + 1][it.y + 1][it.z + 1] = true }

    val queue = dequeOf(Point3(0, 0, 0))
    while (queue.isNotEmpty()) {
      val point = queue.removeFirst()
      if (point in space && !space[point.x][point.y][point.z]) {
        space[point.x][point.y][point.z] = true
        queue.addAll(point.neighbors())
      }
    }

    val enclosedCubes = mutableListOf<Point3>()
    for (x in space.indices) {
      for (y in space[x].indices) {
        for (z in space[x][y].indices) {
          if (!space[x][y][z]) {
            enclosedCubes.add(Point3(x, y, z))
          }
        }
      }
    }
    return enclosedCubes
  }

  private operator fun <T> Array<Array<Array<T>>>.contains(point: Point3): Boolean {
    if (point.x < 0 || point.x > this.lastIndex) return false
    if (point.y < 0 || point.y > this[point.x].lastIndex) return false
    if (point.z < 0 || point.z > this[point.x][point.y].lastIndex) return false
    return true
  }

  companion object {
    val testInput1 = """
      2,2,2
      1,2,2
      3,2,2
      2,1,2
      2,3,2
      2,2,1
      2,2,3
      2,2,4
      2,2,6
      1,2,5
      3,2,5
      2,1,5
      2,3,5
      """.trimIndent()
    val testAnswer1 = 64

    val testInput2 = testInput1
    val testAnswer2 = 58
  }
}
