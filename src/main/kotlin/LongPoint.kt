import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

data class LongPoint(val x: Long, val y: Long) {

  operator fun plus(other: LongPoint): LongPoint = translate(other.x, other.y)

  operator fun minus(other: LongPoint): LongPoint = translate(-other.x, -other.y)

  operator fun times(other: LongPoint): LongPoint = LongPoint(x * other.x, y * other.y)

  operator fun div(other: LongPoint): LongPoint = LongPoint(x / other.x, y / other.y)

  fun translate(other: LongPoint): LongPoint = translate(other.x, other.y)

  fun translate(dx: Long, dy: Long): LongPoint = LongPoint(x + dx, y + dy)

  fun transpose(): LongPoint = LongPoint(y, x)

  infix fun distanceTo(other: LongPoint): Double =
    sqrt((x.toDouble() - other.x.toDouble()).pow(2) + (y.toDouble() - other.y.toDouble()).pow(2))

  infix fun longDistanceTo(other: LongPoint): Long = distanceTo(other).toLong()

  infix fun manhattanDistanceTo(other: LongPoint): Long = abs(x - other.x) + abs(y - other.y)

  infix fun xDistanceTo(other: LongPoint): Long = abs(x - other.x)

  infix fun yDistanceTo(other: LongPoint): Long = abs(y - other.y)

  fun neighbors(): List<LongPoint> = listOf(up(), right(), down(), left())

  fun neighborsWithDiagonals(): List<LongPoint> =
    listOf(up(), upRight(), right(), downRight(), down(), downLeft(), left(), upLeft())

  fun up(n: Long = 1): LongPoint = LongPoint(x, y + n)

  fun upRight(n: Long = 1): LongPoint = LongPoint(x + n, y + n)

  fun right(n: Long = 1): LongPoint = LongPoint(x + n, y)

  fun downRight(n: Long = 1): LongPoint = LongPoint(x + n, y - n)

  fun down(n: Long = 1): LongPoint = LongPoint(x, y - n)

  fun downLeft(n: Long = 1): LongPoint = LongPoint(x - n, y - n)

  fun left(n: Long = 1): LongPoint = LongPoint(x - n, y)

  fun upLeft(n: Long = 1): LongPoint = LongPoint(x - n, y + n)

  infix fun isAbove(other: LongPoint): Boolean = (y > other.y)

  infix fun isUpRightOf(other: LongPoint): Boolean = (x > other.x) && (y > other.y)

  infix fun isRightOf(other: LongPoint): Boolean = (x > other.x)

  infix fun isDownRightOf(other: LongPoint): Boolean = (x > other.x) && (y < other.y)

  infix fun isBelow(other: LongPoint): Boolean = (y < other.y)

  infix fun isDownLeftOf(other: LongPoint): Boolean = (x < other.x) && (y < other.y)

  infix fun isLeftOf(other: LongPoint): Boolean = (x < other.x)

  infix fun isUpLeftOf(other: LongPoint): Boolean = (x < other.x) && (y > other.y)

  infix fun isDirectlyAbove(other: LongPoint): Boolean = (x == other.x) && (y > other.y)

  infix fun isDirectlyUpRightOf(other: LongPoint): Boolean =
    (x > other.x) && (y > other.y) && xDistanceTo(other) == yDistanceTo(other)

  infix fun isDirectlyRightOf(other: LongPoint): Boolean = (x > other.x) && (y == other.y)

  infix fun isDirectlyDownRightOf(other: LongPoint): Boolean =
    (x > other.x) && (y < other.y) && xDistanceTo(other) == yDistanceTo(other)

  infix fun isDirectlyBelow(other: LongPoint): Boolean = (x == other.x) && (y < other.y)

  infix fun isDirectlyDownLeftOf(other: LongPoint): Boolean =
    (x < other.x) && (y < other.y) && xDistanceTo(other) == yDistanceTo(other)

  infix fun isDirectlyLeftOf(other: LongPoint): Boolean = (x < other.x) && (y == other.y)

  infix fun isDirectlyUpLeftOf(other: LongPoint): Boolean =
    (x < other.x) && (y > other.y) && xDistanceTo(other) == yDistanceTo(other)

  infix fun isImmediatelyAbove(other: LongPoint): Boolean = (x == other.x) && (y == other.y + 1)

  infix fun isImmediatelyUpRightOf(other: LongPoint): Boolean =
    (x == other.x + 1) && (y == other.y + 1)

  infix fun isImmediatelyRightOf(other: LongPoint): Boolean = (x == other.x + 1) && (y == other.y)

  infix fun isImmediatelyDownRightOf(other: LongPoint): Boolean =
    (x == other.x + 1) && (y == other.y - 1)

  infix fun isImmediatelyBelow(other: LongPoint): Boolean = (x == other.x) && (y == other.y - 1)

  infix fun isImmediatelyDownLeftOf(other: LongPoint): Boolean =
    (x == other.x - 1) && (y == other.y - 1)

  infix fun isImmediatelyLeftOf(other: LongPoint): Boolean = (x == other.x - 1) && (y == other.y)

  infix fun isImmediatelyUpLeftOf(other: LongPoint): Boolean =
    (x == other.x - 1) && (y == other.y + 1)

  infix fun isNeighborOf(other: LongPoint): Boolean = manhattanDistanceTo(other) == 1L

  infix fun isNeighborWithDiagonalsOf(other: LongPoint): Boolean =
    this != other && xDistanceTo(other) < 2 && yDistanceTo(other) < 2

  override fun toString(): String = "($x,$y)"

  companion object {
    val ZERO = LongPoint(0L, 0L)

    val UP = LongPoint(0L, 1L)
    val UP_RIGHT = LongPoint(1L, 1L)
    val RIGHT = LongPoint(1L, 0L)
    val DOWN_RIGHT = LongPoint(1L, -1L)
    val DOWN = LongPoint(0L, -1L)
    val DOWN_LEFT = LongPoint(-1L, -1L)
    val LEFT = LongPoint(-1L, 0L)
    val UP_LEFT = LongPoint(-1L, 1L)

    val DIRECTIONS = listOf(UP, RIGHT, DOWN, LEFT)
    val DIRECTIONS_WITH_DIAGONALS =
      listOf(UP, UP_RIGHT, RIGHT, DOWN_RIGHT, DOWN, DOWN_LEFT, LEFT, UP_LEFT)
  }
}

fun LongPoint(pair: Pair<Long, Long>): LongPoint = LongPoint(pair.first, pair.second)

fun LongPoint(string: String): LongPoint {
  val parts = string.removeSurrounding("(", ")").split(',', ' ')
  require(parts.size == 2) { "Invalid format for point: $string" }
  return LongPoint(parts[0].toLong(), parts[1].toLong())
}

fun Pair<Long, Long>.toLongPoint(): LongPoint = LongPoint(this)

fun String.toLongPoint(): LongPoint = LongPoint(this)

infix fun Long.xy(y: Long): LongPoint = LongPoint(this, y)
