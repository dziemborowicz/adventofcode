import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

data class Point(val x: Int, val y: Int) {

  operator fun plus(other: Point): Point = translate(other.x, other.y)

  operator fun minus(other: Point): Point = translate(-other.x, -other.y)

  operator fun times(other: Point): Point = Point(x * other.x, y * other.y)

  operator fun div(other: Point): Point = Point(x / other.x, y / other.y)

  fun translate(other: Point): Point = translate(other.x, other.y)

  fun translate(dx: Int, dy: Int): Point = Point(x + dx, y + dy)

  fun transpose(): Point = Point(y, x)

  infix fun distanceTo(other: Point): Double =
    sqrt((x.toDouble() - other.x.toDouble()).pow(2) + (y.toDouble() - other.y.toDouble()).pow(2))

  infix fun intDistanceTo(other: Point): Int = distanceTo(other).toInt()

  infix fun manhattanDistanceTo(other: Point): Int = abs(x - other.x) + abs(y - other.y)

  infix fun xDistanceTo(other: Point): Int = abs(x - other.x)

  infix fun yDistanceTo(other: Point): Int = abs(y - other.y)

  fun neighbors(): List<Point> = listOf(up(), right(), down(), left())

  fun neighborsIncludingDiagonals(): List<Point> =
    listOf(up(), upRight(), right(), downRight(), down(), downLeft(), left(), upLeft())

  fun up(n: Int = 1): Point = Point(x, y + n)

  fun upRight(n: Int = 1): Point = Point(x + n, y + n)

  fun right(n: Int = 1): Point = Point(x + n, y)

  fun downRight(n: Int = 1): Point = Point(x + n, y - n)

  fun down(n: Int = 1): Point = Point(x, y - n)

  fun downLeft(n: Int = 1): Point = Point(x - n, y - n)

  fun left(n: Int = 1): Point = Point(x - n, y)

  fun upLeft(n: Int = 1): Point = Point(x - n, y + n)

  infix fun isAbove(other: Point): Boolean = (y > other.y)

  infix fun isUpRightOf(other: Point): Boolean = (x > other.x) && (y > other.y)

  infix fun isRightOf(other: Point): Boolean = (x > other.x)

  infix fun isDownRightOf(other: Point): Boolean = (x > other.x) && (y < other.y)

  infix fun isBelow(other: Point): Boolean = (y < other.y)

  infix fun isDownLeftOf(other: Point): Boolean = (x < other.x) && (y < other.y)

  infix fun isLeftOf(other: Point): Boolean = (x < other.x)

  infix fun isUpLeftOf(other: Point): Boolean = (x < other.x) && (y > other.y)

  infix fun isDirectlyAbove(other: Point): Boolean = (x == other.x) && (y > other.y)

  infix fun isDirectlyUpRightOf(other: Point): Boolean =
    (x > other.x) && (y > other.y) && xDistanceTo(other) == yDistanceTo(other)

  infix fun isDirectlyRightOf(other: Point): Boolean = (x > other.x) && (y == other.y)

  infix fun isDirectlyDownRightOf(other: Point): Boolean =
    (x > other.x) && (y < other.y) && xDistanceTo(other) == yDistanceTo(other)

  infix fun isDirectlyBelow(other: Point): Boolean = (x == other.x) && (y < other.y)

  infix fun isDirectlyDownLeftOf(other: Point): Boolean =
    (x < other.x) && (y < other.y) && xDistanceTo(other) == yDistanceTo(other)

  infix fun isDirectlyLeftOf(other: Point): Boolean = (x < other.x) && (y == other.y)

  infix fun isDirectlyUpLeftOf(other: Point): Boolean =
    (x < other.x) && (y > other.y) && xDistanceTo(other) == yDistanceTo(other)

  infix fun isImmediatelyAbove(other: Point): Boolean = (x == other.x) && (y == other.y + 1)

  infix fun isImmediatelyUpRightOf(other: Point): Boolean = (x == other.x + 1) && (y == other.y + 1)

  infix fun isImmediatelyRightOf(other: Point): Boolean = (x == other.x + 1) && (y == other.y)

  infix fun isImmediatelyDownRightOf(other: Point): Boolean =
    (x == other.x + 1) && (y == other.y - 1)

  infix fun isImmediatelyBelow(other: Point): Boolean = (x == other.x) && (y == other.y - 1)

  infix fun isImmediatelyDownLeftOf(other: Point): Boolean =
    (x == other.x - 1) && (y == other.y - 1)

  infix fun isImmediatelyLeftOf(other: Point): Boolean = (x == other.x - 1) && (y == other.y)

  infix fun isImmediatelyUpLeftOf(other: Point) = (x == other.x - 1) && (y == other.y + 1)

  infix fun isNeighborOf(other: Point): Boolean = manhattanDistanceTo(other) == 1

  infix fun isNeighborIncludingDiagonalsOf(other: Point): Boolean =
    this != other && xDistanceTo(other) < 2 && yDistanceTo(other) < 2

  override fun toString(): String = "($x,$y)"
}

fun Point(pair: Pair<Int, Int>): Point = Point(pair.first, pair.second)

fun Point(string: String): Point {
  val parts = string.removeSurrounding("(", ")").split(',', ' ')
  require(parts.size == 2) { "Invalid format for point: $string" }
  return Point(parts[0].toInt(), parts[1].toInt())
}

fun Pair<Int, Int>.toPoint(): Point = Point(this)

fun String.toPoint(): Point = Point(this)
