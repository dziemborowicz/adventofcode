import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

data class DoublePoint3(val x: Double, val y: Double, val z: Double) {

  operator fun plus(other: DoublePoint3): DoublePoint3 = translate(other.x, other.y, other.z)

  operator fun minus(other: DoublePoint3): DoublePoint3 = translate(-other.x, -other.y, -other.z)

  operator fun times(other: DoublePoint3): DoublePoint3 =
    DoublePoint3(x * other.x, y * other.y, z * other.z)

  operator fun div(other: DoublePoint3): DoublePoint3 =
    DoublePoint3(x / other.x, y / other.y, z / other.z)

  operator fun times(other: Int): DoublePoint3 = DoublePoint3(x * other, y * other, z * other)

  operator fun div(other: Int): DoublePoint3 = DoublePoint3(x / other, y / other, z / other)

  operator fun times(other: Long): DoublePoint3 = DoublePoint3(x * other, y * other, z * other)

  operator fun div(other: Long): DoublePoint3 = DoublePoint3(x / other, y / other, z / other)

  operator fun times(other: Double): DoublePoint3 = DoublePoint3(x * other, y * other, z * other)

  operator fun div(other: Double): DoublePoint3 = DoublePoint3(x / other, y / other, z / other)

  fun translate(other: DoublePoint3): DoublePoint3 = translate(other.x, other.y, other.z)

  fun translate(dx: Double, dy: Double, dz: Double): DoublePoint3 =
    DoublePoint3(x + dx, y + dy, z + dz)

  infix fun distanceTo(other: DoublePoint3): Double =
    sqrt((x - other.x).pow(2) + (y - other.y).pow(2) + (z - other.z).pow(2))

  infix fun manhattanDistanceTo(other: DoublePoint3): Double =
    abs(x - other.x) + abs(y - other.y) + abs(z - other.z)

  infix fun xDistanceTo(other: DoublePoint3): Double = abs(x - other.x)

  infix fun yDistanceTo(other: DoublePoint3): Double = abs(y - other.y)

  infix fun zDistanceTo(other: DoublePoint3): Double = abs(z - other.z)

  fun neighbors(): List<DoublePoint3> = neighborsWithDiagonals().filter { it isNeighborOf this }

  fun neighborsWithDiagonals(): List<DoublePoint3> {
    return buildList(26) {
      for (dx in -1..1) {
        for (dy in -1..1) {
          for (dz in -1..1) {
            if (dx == 0 && dy == 0 && dz == 0) continue
            add(DoublePoint3(x + dx, y + dy, z + dz))
          }
        }
      }
    }
  }

  infix fun isNeighborOf(other: DoublePoint3): Boolean = manhattanDistanceTo(other) == 1.0

  infix fun isNeighborWithDiagonalsOf(other: DoublePoint3): Boolean =
    this != other && xDistanceTo(other) < 2 && yDistanceTo(other) < 2 && zDistanceTo(other) < 2

  override fun toString(): String = "($x,$y,$z)"
}

operator fun Int.times(other: DoublePoint3): DoublePoint3 =
  DoublePoint3(this * other.x, this * other.y, this * other.z)

operator fun Long.times(other: DoublePoint3): DoublePoint3 =
  DoublePoint3(this * other.x, this * other.y, this * other.z)

operator fun Double.times(other: DoublePoint3): DoublePoint3 =
  DoublePoint3(this * other.x, this * other.y, this * other.z)

fun DoublePoint3(triple: Triple<Double, Double, Double>): DoublePoint3 =
  DoublePoint3(triple.first, triple.second, triple.third)

fun DoublePoint3(string: String): DoublePoint3 {
  val parts = string.removeSurrounding("(", ")").split(',', ' ')
  require(parts.size == 3) { "Invalid format for point: $string" }
  return DoublePoint3(parts[0].toDouble(), parts[1].toDouble(), parts[2].toDouble())
}

fun Triple<Double, Double, Double>.toDoublePoint3(): DoublePoint3 = DoublePoint3(this)

fun String.toDoublePoint3(): DoublePoint3 = DoublePoint3(this)
