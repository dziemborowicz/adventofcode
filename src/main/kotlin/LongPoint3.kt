import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

data class LongPoint3(val x: Long, val y: Long, val z: Long) {

  operator fun plus(other: LongPoint3): LongPoint3 = translate(other.x, other.y, other.z)

  operator fun minus(other: LongPoint3): LongPoint3 = translate(-other.x, -other.y, -other.z)

  operator fun times(other: LongPoint3): LongPoint3 =
    LongPoint3(x * other.x, y * other.y, z * other.z)

  operator fun div(other: LongPoint3): LongPoint3 =
    LongPoint3(x / other.x, y / other.y, z / other.z)

  operator fun times(other: Int): LongPoint3 = LongPoint3(x * other, y * other, z * other)

  operator fun div(other: Int): LongPoint3 = LongPoint3(x / other, y / other, z / other)

  operator fun times(other: Long): LongPoint3 = LongPoint3(x * other, y * other, z * other)

  operator fun div(other: Long): LongPoint3 = LongPoint3(x / other, y / other, z / other)

  fun translate(other: LongPoint3): LongPoint3 = translate(other.x, other.y, other.z)

  fun translate(dx: Long, dy: Long, dz: Long): LongPoint3 = LongPoint3(x + dx, y + dy, z + dz)

  infix fun distanceTo(other: LongPoint3): Double {
    return sqrt(
      (x.toDouble() - other.x.toDouble()).pow(2) +
        (y.toDouble() - other.y.toDouble()).pow(2) +
        (z.toDouble() - other.z.toDouble()).pow(2)
    )
  }

  infix fun longDistanceTo(other: LongPoint3): Long = distanceTo(other).toLong()

  infix fun manhattanDistanceTo(other: LongPoint3): Long =
    abs(x - other.x) + abs(y - other.y) + abs(z - other.z)

  infix fun xDistanceTo(other: LongPoint3): Long = abs(x - other.x)

  infix fun yDistanceTo(other: LongPoint3): Long = abs(y - other.y)

  infix fun zDistanceTo(other: LongPoint3): Long = abs(z - other.z)

  fun neighbors(): List<LongPoint3> = neighborsWithDiagonals().filter { it isNeighborOf this }

  fun neighborsWithDiagonals(): List<LongPoint3> {
    return buildList(26) {
      for (dx in -1..1) {
        for (dy in -1..1) {
          for (dz in -1..1) {
            if (dx == 0 && dy == 0 && dz == 0) continue
            add(LongPoint3(x + dx, y + dy, z + dz))
          }
        }
      }
    }
  }

  infix fun isNeighborOf(other: LongPoint3): Boolean = manhattanDistanceTo(other) == 1L

  infix fun isNeighborWithDiagonalsOf(other: LongPoint3): Boolean =
    this != other && xDistanceTo(other) < 2 && yDistanceTo(other) < 2 && zDistanceTo(other) < 2

  override fun toString(): String = "($x,$y,$z)"
}

operator fun Int.times(other: LongPoint3): LongPoint3 =
  LongPoint3(this * other.x, this * other.y, this * other.z)

operator fun Long.times(other: LongPoint3): LongPoint3 =
  LongPoint3(this * other.x, this * other.y, this * other.z)

fun LongPoint3(triple: Triple<Long, Long, Long>): LongPoint3 =
  LongPoint3(triple.first, triple.second, triple.third)

fun LongPoint3(string: String): LongPoint3 {
  val parts = string.removeSurrounding("(", ")").split(',', ' ')
  require(parts.size == 3) { "Invalid format for point: $string" }
  return LongPoint3(parts[0].toLong(), parts[1].toLong(), parts[2].toLong())
}

fun Point3.toLongPoint3(): LongPoint3 = LongPoint3(x.toLong(), y.toLong(), z.toLong())

fun DoublePoint3.toLongPoint3(): LongPoint3 = LongPoint3(x.toLong(), y.toLong(), z.toLong())

fun Triple<Long, Long, Long>.toLongPoint3(): LongPoint3 = LongPoint3(this)

fun String.toLongPoint3(): LongPoint3 = LongPoint3(this)
