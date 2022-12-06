import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

data class Point(val x: Int, val y: Int) {
  constructor(point: Pair<Int, Int>) : this(point.first, point.second)
  constructor(index: Grid.Index) : this(index.column, index.row)
  constructor(str: String) : this(str.split(',').map { it.toInt() }.asPair())
  fun offset(amount: Point): Point = offset(amount.x, amount.y)
  fun offset(x: Int, y: Int): Point = Point(this.x + x, this.y + y)
  fun distance(other: Point): Double =
    sqrt((x.toDouble() - other.x.toDouble()).pow(2) + (y.toDouble() - other.y.toDouble()).pow(2))
  fun intDistance(other: Point): Int = distance(other).toInt()
  fun longDistance(other: Point): Long = distance(other).toLong()
  fun manhattanDistance(other: Point): Int = abs(x - other.x) + abs(y - other.y)
  fun toLongPoint() = LongPoint(x.toLong(), y.toLong())
  fun toDoublePoint() = DoublePoint(x.toDouble(), y.toDouble())
}

data class LongPoint(val x: Long, val y: Long) {
  constructor(point: Pair<Long, Long>) : this(point.first, point.second)
  constructor(index: Grid.Index) : this(index.column.toLong(), index.row.toLong())
  constructor(str: String) : this(str.split(',').map { it.toLong() }.asPair())
  fun offset(amount: LongPoint): LongPoint = offset(amount.x, amount.y)
  fun offset(x: Long, y: Long): LongPoint = LongPoint(this.x + x, this.y + y)
  fun distance(other: LongPoint): Double =
    sqrt((x.toDouble() - other.x.toDouble()).pow(2) + (y.toDouble() - other.y.toDouble()).pow(2))
  fun intDistance(other: LongPoint): Int = distance(other).toInt()
  fun longDistance(other: LongPoint): Long = distance(other).toLong()
  fun manhattanDistance(other: LongPoint): Long = abs(x - other.x) + abs(y - other.y)
  fun toPoint() = Point(x.toInt(), y.toInt())
  fun toDoublePoint() = DoublePoint(x.toDouble(), y.toDouble())
}

data class DoublePoint(val x: Double, val y: Double) {
  constructor(point: Pair<Double, Double>) : this(point.first, point.second)
  constructor(index: Grid.Index) : this(index.column.toDouble(), index.row.toDouble())
  constructor(str: String) : this(str.split(',').map { it.toDouble() }.asPair())
  fun offset(amount: DoublePoint): DoublePoint = offset(amount.x, amount.y)
  fun offset(x: Double, y: Double): DoublePoint = DoublePoint(this.x + x, this.y + y)
  fun distance(other: DoublePoint): Double = sqrt((x - other.x).pow(2) + (y - other.y).pow(2))
  fun intDistance(other: DoublePoint): Int = distance(other).toInt()
  fun longDistance(other: DoublePoint): Long = distance(other).toLong()
  fun manhattanDistance(other: DoublePoint): Double = abs(x - other.x) + abs(y - other.y)
  fun toPoint() = Point(x.toInt(), y.toInt())
  fun toLongPoint() = LongPoint(x.toLong(), y.toLong())
}

data class Point3(val x: Int, val y: Int, val z: Int) {
  constructor(point: Triple<Int, Int, Int>) : this(point.first, point.second, point.third)
  constructor(str: String) : this(str.split(',').map { it.toInt() }.asTriple())
  fun offset(amount: Point3): Point3 = offset(amount.x, amount.y, amount.z)
  fun offset(x: Int, y: Int, z: Int): Point3 = Point3(this.x + x, this.y + y, this.z + z)
  fun distance(other: Point3): Double {
    return sqrt(
      (x.toDouble() - other.x.toDouble()).pow(2) +
        (y.toDouble() - other.y.toDouble()).pow(2) +
        (z.toDouble() - other.z.toDouble()).pow(2)
    )
  }
  fun intDistance(other: Point3): Int = distance(other).toInt()
  fun longDistance(other: Point3): Long = distance(other).toLong()
  fun manhattanDistance(other: Point3): Int = abs(x - other.x) + abs(y - other.y) + abs(z - other.z)
  fun toLongPoint3() = LongPoint3(x.toLong(), y.toLong(), z.toLong())
  fun toDoublePoint3() = DoublePoint3(x.toDouble(), y.toDouble(), z.toDouble())
}

data class LongPoint3(val x: Long, val y: Long, val z: Long) {
  constructor(point: Triple<Long, Long, Long>) : this(point.first, point.second, point.third)
  constructor(str: String) : this(str.split(',').map { it.toLong() }.asTriple())
  fun offset(amount: LongPoint3): LongPoint3 = offset(amount.x, amount.y, amount.z)
  fun offset(x: Long, y: Long, z: Long): LongPoint3 = LongPoint3(this.x + x, this.y + y, this.z + z)
  fun distance(other: LongPoint3): Double {
    return sqrt(
      (x.toDouble() - other.x.toDouble()).pow(2) +
        (y.toDouble() - other.y.toDouble()).pow(2) +
        (z.toDouble() - other.z.toDouble()).pow(2)
    )
  }
  fun intDistance(other: LongPoint3): Int = distance(other).toInt()
  fun longDistance(other: LongPoint3): Long = distance(other).toLong()
  fun manhattanDistance(other: LongPoint3): Long =
    abs(x - other.x) + abs(y - other.y) + abs(z - other.z)
  fun toPoint3() = Point3(x.toInt(), y.toInt(), z.toInt())
  fun toDoublePoint3() = DoublePoint3(x.toDouble(), y.toDouble(), z.toDouble())
}

data class DoublePoint3(val x: Double, val y: Double, val z: Double) {
  constructor(point: Triple<Double, Double, Double>) : this(point.first, point.second, point.third)
  constructor(str: String) : this(str.split(',').map { it.toDouble() }.asTriple())
  fun offset(amount: DoublePoint3): DoublePoint3 = offset(amount.x, amount.y, amount.z)
  fun offset(x: Double, y: Double, z: Double): DoublePoint3 =
    DoublePoint3(this.x + x, this.y + y, this.z + z)
  fun distance(other: DoublePoint3): Double =
    sqrt((x - other.x).pow(2) + (y - other.y).pow(2) + (z - other.z).pow(2))
  fun intDistance(other: DoublePoint3): Int = distance(other).toInt()
  fun longDistance(other: DoublePoint3): Long = distance(other).toLong()
  fun manhattanDistance(other: DoublePoint3): Double =
    abs(x - other.x) + abs(y - other.y) + abs(z - other.z)
  fun toPoint3() = Point3(x.toInt(), y.toInt(), z.toInt())
  fun toLongPoint3() = LongPoint3(x.toLong(), y.toLong(), z.toLong())
}

fun String.toPoint() = Point(this)
fun String.toLongPoint() = LongPoint(this)
fun String.toDoublePoint() = DoublePoint(this)

fun String.toPoint3() = Point3(this)
fun String.toLongPoint3() = LongPoint3(this)
fun String.toDoublePoint3() = DoublePoint3(this)
