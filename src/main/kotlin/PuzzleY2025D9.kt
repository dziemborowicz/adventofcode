import kotlin.math.absoluteValue

class PuzzleY2025D9 : Puzzle {

  private lateinit var redTiles: List<LongPoint>

  override fun parse(input: String) {
    redTiles = input.lines().map { it.toLongPoint() }
  }

  override fun solve1(): Long {
    return redTiles.combinations(2).maxOf { (a, b) ->
      areaOfRectangle(a, b)
    }
  }

  override fun solve2(): Long {
    return redTiles.combinations(2).sortedByDescending { (a, b) ->
      areaOfRectangle(a, b)
    }.first { (a, b) ->
      !redTiles.hasEdgeThatIntersectsSquare(a, b) && redTiles.containsCenterOfSquare(a, b)
    }.let { (a, b) -> areaOfRectangle(a, b) }
  }

  private fun areaOfRectangle(a: LongPoint, b: LongPoint): Long {
    return ((a.x - b.x).absoluteValue + 1) * ((a.y - b.y).absoluteValue + 1)
  }

  private fun List<LongPoint>.hasEdgeThatIntersectsSquare(a: LongPoint, b: LongPoint): Boolean {
    val polygon = this

    val xRangeInside = minOf(a.x, b.x) + 1..maxOf(a.x, b.x) - 1
    val yRangeInside = minOf(a.y, b.y) + 1..maxOf(a.y, b.y) - 1

    for ((e1, e2) in (polygon + polygon.first()).windowed(2)) {
      val exRange = minOf(e1.x, e2.x)..maxOf(e1.x, e2.x)
      val eyRange = minOf(e1.y, e2.y)..maxOf(e1.y, e2.y)

      when {
        e1.x == e2.x -> {
          if (e1.x in xRangeInside && yRangeInside.containsAny(eyRange)) {
            return true
          }
        }

        e1.y == e2.y -> {
          if (e1.y in yRangeInside && xRangeInside.containsAny(exRange)) {
            return true
          }
        }

        else -> fail("The polygon must be rectilinear.")
      }
    }

    return false
  }

  private fun List<LongPoint>.containsCenterOfSquare(a: LongPoint, b: LongPoint): Boolean {
    val center = DoublePoint((a.x + b.x) / 2.0, (a.y + b.y) / 2.0)
    return center.isOnBoundaryOfPolygon(this) || center.isInsidePolygon(this)
  }

  companion object {
    val testInput1 = """
      7,1
      11,1
      11,7
      9,7
      9,5
      2,5
      2,3
      7,3
      """.trimIndent()
    val testAnswer1 = 50

    val testInput2 = testInput1
    val testAnswer2 = 24
  }
}
