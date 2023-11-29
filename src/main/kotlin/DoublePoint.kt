import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

data class DoublePoint(val x: Double, val y: Double) {

  operator fun plus(other: DoublePoint): DoublePoint = translate(other.x, other.y)

  operator fun minus(other: DoublePoint): DoublePoint = translate(-other.x, -other.y)

  operator fun times(other: DoublePoint): DoublePoint = DoublePoint(x * other.x, y * other.y)

  operator fun div(other: DoublePoint): DoublePoint = DoublePoint(x / other.x, y / other.y)

  operator fun times(other: Int): DoublePoint = DoublePoint(x * other, y * other)

  operator fun div(other: Int): DoublePoint = DoublePoint(x / other, y / other)

  operator fun times(other: Long): DoublePoint = DoublePoint(x * other, y * other)

  operator fun div(other: Long): DoublePoint = DoublePoint(x / other, y / other)

  operator fun times(other: Double): DoublePoint = DoublePoint(x * other, y * other)

  operator fun div(other: Double): DoublePoint = DoublePoint(x / other, y / other)

  fun translate(other: DoublePoint): DoublePoint = translate(other.x, other.y)

  fun translate(dx: Double, dy: Double): DoublePoint = DoublePoint(x + dx, y + dy)

  fun transpose(): DoublePoint = DoublePoint(y, x)

  infix fun distanceTo(other: DoublePoint): Double =
    sqrt((x - other.x).pow(2) + (y - other.y).pow(2))

  infix fun manhattanDistanceTo(other: DoublePoint): Double = abs(x - other.x) + abs(y - other.y)

  infix fun xDistanceTo(other: DoublePoint): Double = abs(x - other.x)

  infix fun yDistanceTo(other: DoublePoint): Double = abs(y - other.y)

  fun rotateClockwise(times: Int = 1): DoublePoint {
    return when (times.mod(4)) {
      0 -> this
      1 -> DoublePoint(y, -x)
      2 -> DoublePoint(-x, -y)
      3 -> DoublePoint(-y, x)
      else -> fail()
    }
  }

  fun rotateCounterclockwise(times: Int = 1): DoublePoint = rotateClockwise(-times)

  fun neighbors(): List<DoublePoint> = listOf(up(), right(), down(), left())

  fun neighborsWithDiagonals(): List<DoublePoint> =
    listOf(up(), upRight(), right(), downRight(), down(), downLeft(), left(), upLeft())

  fun up(n: Double = 1.0): DoublePoint = DoublePoint(x, y + n)

  fun upRight(n: Double = 1.0): DoublePoint = DoublePoint(x + n, y + n)

  fun right(n: Double = 1.0): DoublePoint = DoublePoint(x + n, y)

  fun downRight(n: Double = 1.0): DoublePoint = DoublePoint(x + n, y - n)

  fun down(n: Double = 1.0): DoublePoint = DoublePoint(x, y - n)

  fun downLeft(n: Double = 1.0): DoublePoint = DoublePoint(x - n, y - n)

  fun left(n: Double = 1.0): DoublePoint = DoublePoint(x - n, y)

  fun upLeft(n: Double = 1.0): DoublePoint = DoublePoint(x - n, y + n)

  infix fun isAbove(other: DoublePoint): Boolean = (y > other.y)

  infix fun isUpRightOf(other: DoublePoint): Boolean = (x > other.x) && (y > other.y)

  infix fun isRightOf(other: DoublePoint): Boolean = (x > other.x)

  infix fun isDownRightOf(other: DoublePoint): Boolean = (x > other.x) && (y < other.y)

  infix fun isBelow(other: DoublePoint): Boolean = (y < other.y)

  infix fun isDownLeftOf(other: DoublePoint): Boolean = (x < other.x) && (y < other.y)

  infix fun isLeftOf(other: DoublePoint): Boolean = (x < other.x)

  infix fun isUpLeftOf(other: DoublePoint): Boolean = (x < other.x) && (y > other.y)

  infix fun isDirectlyAbove(other: DoublePoint): Boolean = (x == other.x) && (y > other.y)

  infix fun isDirectlyUpRightOf(other: DoublePoint): Boolean =
    (x > other.x) && (y > other.y) && xDistanceTo(other) == yDistanceTo(other)

  infix fun isDirectlyRightOf(other: DoublePoint): Boolean = (x > other.x) && (y == other.y)

  infix fun isDirectlyDownRightOf(other: DoublePoint): Boolean =
    (x > other.x) && (y < other.y) && xDistanceTo(other) == yDistanceTo(other)

  infix fun isDirectlyBelow(other: DoublePoint): Boolean = (x == other.x) && (y < other.y)

  infix fun isDirectlyDownLeftOf(other: DoublePoint): Boolean =
    (x < other.x) && (y < other.y) && xDistanceTo(other) == yDistanceTo(other)

  infix fun isDirectlyLeftOf(other: DoublePoint): Boolean = (x < other.x) && (y == other.y)

  infix fun isDirectlyUpLeftOf(other: DoublePoint): Boolean =
    (x < other.x) && (y > other.y) && xDistanceTo(other) == yDistanceTo(other)

  infix fun isImmediatelyAbove(other: DoublePoint): Boolean = (x == other.x) && (y == other.y + 1)

  infix fun isImmediatelyUpRightOf(other: DoublePoint): Boolean =
    (x == other.x + 1) && (y == other.y + 1)

  infix fun isImmediatelyRightOf(other: DoublePoint): Boolean = (x == other.x + 1) && (y == other.y)

  infix fun isImmediatelyDownRightOf(other: DoublePoint): Boolean =
    (x == other.x + 1) && (y == other.y - 1)

  infix fun isImmediatelyBelow(other: DoublePoint): Boolean = (x == other.x) && (y == other.y - 1)

  infix fun isImmediatelyDownLeftOf(other: DoublePoint): Boolean =
    (x == other.x - 1) && (y == other.y - 1)

  infix fun isImmediatelyLeftOf(other: DoublePoint): Boolean = (x == other.x - 1) && (y == other.y)

  infix fun isImmediatelyUpLeftOf(other: DoublePoint): Boolean =
    (x == other.x - 1) && (y == other.y + 1)

  infix fun isNeighborOf(other: DoublePoint): Boolean = manhattanDistanceTo(other) == 1.0

  infix fun isNeighborWithDiagonalsOf(other: DoublePoint): Boolean =
    this != other && xDistanceTo(other) < 2 && yDistanceTo(other) < 2

  override fun toString(): String = "($x,$y)"

  companion object {
    val ZERO = DoublePoint(0.0, 0.0)

    val UP = DoublePoint(0.0, 1.0)
    val UP_RIGHT = DoublePoint(1.0, 1.0)
    val RIGHT = DoublePoint(1.0, 0.0)
    val DOWN_RIGHT = DoublePoint(1.0, -1.0)
    val DOWN = DoublePoint(0.0, -1.0)
    val DOWN_LEFT = DoublePoint(-1.0, -1.0)
    val LEFT = DoublePoint(-1.0, 0.0)
    val UP_LEFT = DoublePoint(-1.0, 1.0)

    val DIRECTIONS = listOf(UP, RIGHT, DOWN, LEFT)
    val DIRECTIONS_WITH_DIAGONALS =
      listOf(UP, UP_RIGHT, RIGHT, DOWN_RIGHT, DOWN, DOWN_LEFT, LEFT, UP_LEFT)
  }
}

operator fun Int.times(other: DoublePoint): DoublePoint =
  DoublePoint(this * other.x, this * other.y)

operator fun Long.times(other: DoublePoint): DoublePoint =
  DoublePoint(this * other.x, this * other.y)

operator fun Double.times(other: DoublePoint): DoublePoint =
  DoublePoint(this * other.x, this * other.y)

fun DoublePoint(pair: Pair<Double, Double>): DoublePoint = DoublePoint(pair.first, pair.second)

fun DoublePoint(string: String): DoublePoint {
  val parts = string.removeSurrounding("(", ")").split(',', ' ')
  require(parts.size == 2) { "Invalid format for point: $string" }
  return DoublePoint(parts[0].toDouble(), parts[1].toDouble())
}

fun Pair<Double, Double>.toDoublePoint(): DoublePoint = DoublePoint(this)

fun String.toDoublePoint(): DoublePoint = DoublePoint(this)

infix fun Double.xy(y: Double): DoublePoint = DoublePoint(this, y)
