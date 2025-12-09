import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

data class Point3(val x: Int, val y: Int, val z: Int) {

  operator fun plus(other: Point3): Point3 = translate(other.x, other.y, other.z)

  operator fun minus(other: Point3): Point3 = translate(-other.x, -other.y, -other.z)

  operator fun times(other: Point3): Point3 = Point3(x * other.x, y * other.y, z * other.z)

  operator fun div(other: Point3): Point3 = Point3(x / other.x, y / other.y, z / other.z)

  operator fun times(other: Int): Point3 = Point3(x * other, y * other, z * other)

  operator fun div(other: Int): Point3 = Point3(x / other, y / other, z / other)

  fun translate(other: Point3): Point3 = translate(other.x, other.y, other.z)

  fun translate(dx: Int, dy: Int, dz: Int): Point3 = Point3(x + dx, y + dy, z + dz)

  infix fun distanceTo(other: Point3): Double {
    return sqrt(
      (x.toDouble() - other.x.toDouble()).pow(2) +
        (y.toDouble() - other.y.toDouble()).pow(2) +
        (z.toDouble() - other.z.toDouble()).pow(2)
    )
  }

  infix fun intDistanceTo(other: Point3): Int = distanceTo(other).toInt()

  infix fun manhattanDistanceTo(other: Point3): Int =
    abs(x - other.x) + abs(y - other.y) + abs(z - other.z)

  infix fun xDistanceTo(other: Point3): Int = abs(x - other.x)

  infix fun yDistanceTo(other: Point3): Int = abs(y - other.y)

  infix fun zDistanceTo(other: Point3): Int = abs(z - other.z)

  fun neighbors(): List<Point3> = neighborsWithDiagonals().filter { it isNeighborOf this }

  fun neighborsWithDiagonals(): List<Point3> {
    return buildList(26) {
      for (dx in -1..1) {
        for (dy in -1..1) {
          for (dz in -1..1) {
            if (dx == 0 && dy == 0 && dz == 0) continue
            add(Point3(x + dx, y + dy, z + dz))
          }
        }
      }
    }
  }

  infix fun isNeighborOf(other: Point3): Boolean = manhattanDistanceTo(other) == 1

  infix fun isNeighborWithDiagonalsOf(other: Point3): Boolean =
    this != other && xDistanceTo(other) < 2 && yDistanceTo(other) < 2 && zDistanceTo(other) < 2

  override fun toString(): String = "($x,$y,$z)"
}

operator fun Int.times(other: Point3): Point3 =
  Point3(this * other.x, this * other.y, this * other.z)

fun Point3(triple: Triple<Int, Int, Int>): Point3 =
  Point3(triple.first, triple.second, triple.third)

fun Point3(string: String): Point3 {
  val parts = string.removeSurrounding("(", ")").split(',', ' ')
  require(parts.size == 3) { "Invalid format for point: $string" }
  return Point3(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
}

fun DoublePoint3.toPoint3(): Point3 = Point3(x.toInt(), y.toInt(), z.toInt())

fun LongPoint3.toPoint3(): Point3 = Point3(x.toInt(), y.toInt(), z.toInt())

fun Triple<Int, Int, Int>.toPoint3(): Point3 = Point3(this)

fun String.toPoint3(): Point3 = Point3(this)
