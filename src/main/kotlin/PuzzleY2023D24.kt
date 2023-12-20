class PuzzleY2023D24 : Puzzle {

  private data class Hailstone(
    val x: Long,
    val y: Long,
    val z: Long,
    val vx: Long,
    val vy: Long,
    val vz: Long,
  )

  private lateinit var hailstones: List<Hailstone>

  override fun parse(input: String) {
    hailstones = input.lines().map { line ->
      val (x, y, z, vx, vy, vz) = line.extractLongs()
      Hailstone(x, y, z, vx, vy, vz)
    }
  }

  override fun solve1(): Int = solve1(200000000000000L..400000000000000L)

  private fun solve1(range: LongRange): Int {
    return hailstones.combinations(2).count { (a, b) ->
      val intersection = intersectionOf(a.toLine2D(), b.toLine2D())
      intersection != null &&
        intersection.x.toLong() in range &&
        intersection.y.toLong() in range &&
        (intersection.x - a.x) * a.vx > 0 &&
        (intersection.y - a.y) * a.vy > 0 &&
        (intersection.x - b.x) * b.vx > 0 &&
        (intersection.y - b.y) * b.vy > 0
    }
  }

  /** Solved using equation solver. */
  override fun solve2() = NoAnswer

  /** A line in the standard form `ax + by = c`. */
  private data class Line2D(val a: Double, val b: Double, val c: Double)

  private fun intersectionOf(first: Line2D, second: Line2D): DoublePoint? {
    val determinant = first.a * second.b - second.a * first.b
    if (determinant == 0.0) return null

    return DoublePoint(
      (second.b * first.c - first.b * second.c) / determinant,
      (first.a * second.c - second.a * first.c) / determinant,
    )
  }

  private fun Hailstone.toLine2D(): Line2D {
    val a = vy
    val b = -vx
    val c = a * x + b * y
    return Line2D(a.toDouble(), b.toDouble(), c.toDouble())
  }

  companion object {
    val testInput2 = """
      19, 13, 30 @ -2,  1, -2
      18, 19, 22 @ -1, -1, -2
      20, 25, 34 @ -2, -2, -4
      12, 31, 28 @ -1, -2, -1
      20, 19, 15 @  1, -5, -3
      """.trimIndent()
    val testAnswer2 = 47

    fun test() {
      PuzzleY2023D24().apply {
        parse(testInput2)
        check(solve1(7L..27L) == 2)
      }
    }
  }
}
