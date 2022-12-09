import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

data class Point(val x: Int, val y: Int) {
  constructor(point: Pair<Int, Int>) : this(point.first, point.second)
  constructor(index: Grid.Index) : this(index.column, index.row)
  constructor(str: String) : this(str.split(',').map { it.toInt() }.toPair())
  fun transpose() = Point(y, x)
  fun upLeft() = Point(x - 1, y + 1)
  fun up() = Point(x, y + 1)
  fun upRight() = Point(x + 1, y + 1)
  fun right() = Point(x + 1, y)
  fun downRight() = Point(x + 1, y - 1)
  fun down() = Point(x, y - 1)
  fun downLeft() = Point(x - 1, y - 1)
  fun left() = Point(x - 1, y)
  fun isUpLeftOf(other: Point) = (x < other.x) && (y > other.y)
  fun isUpOf(other: Point) = (y > other.y)
  fun isAbove(other: Point) = isUpOf(other)
  fun isUpRightOf(other: Point) = (x > other.x) && (y > other.y)
  fun isRightOf(other: Point) = (x > other.x)
  fun isDownRightOf(other: Point) = (x > other.x) && (y < other.y)
  fun isDownOf(other: Point) = (y < other.y)
  fun isBelow(other: Point) = isDownOf(other)
  fun isDownLeftOf(other: Point) = (x < other.x) && (y < other.y)
  fun isLeftOf(other: Point) = (x < other.x)
  fun isImmediatelyUpLeftOf(other: Point) = (x == other.x - 1) && (y == other.y + 1)
  fun isImmediatelyUpOf(other: Point) = (x == other.x) && (y == other.y + 1)
  fun isImmediatelyAbove(other: Point) = isImmediatelyUpOf(other)
  fun isImmediatelyUpRightOf(other: Point) = (x == other.x + 1) && (y == other.y + 1)
  fun isImmediatelyRightOf(other: Point) = (x == other.x + 1) && (y == other.y)
  fun isImmediatelyDownRightOf(other: Point) = (x == other.x + 1) && (y == other.y - 1)
  fun isImmediatelyDownOf(other: Point) = (x == other.x) && (y == other.y - 1)
  fun isImmediatelyBelow(other: Point) = isImmediatelyDownOf(other)
  fun isImmediatelyDownLeftOf(other: Point) = (x == other.x - 1) && (y == other.y - 1)
  fun isImmediatelyLeftOf(other: Point) = (x == other.x - 1) && (y == other.y)
  fun adjacent() = listOf(up(), right(), down(), left())
  fun adjacentWithDiagonals() =
    listOf(upLeft(), up(), upRight(), right(), downRight(), down(), downLeft(), left())
  fun offset(amount: Point): Point = offset(amount.x, amount.y)
  fun offset(x: Int, y: Int): Point = Point(this.x + x, this.y + y)
  fun distanceTo(other: Point): Double =
    sqrt((x.toDouble() - other.x.toDouble()).pow(2) + (y.toDouble() - other.y.toDouble()).pow(2))
  fun intDistanceTo(other: Point): Int = distanceTo(other).toInt()
  fun longDistanceTo(other: Point): Long = distanceTo(other).toLong()
  fun manhattanDistanceTo(other: Point): Int = abs(x - other.x) + abs(y - other.y)
  fun xDistanceTo(other: Point): Int = abs(x - other.x)
  fun yDistanceTo(other: Point): Int = abs(y - other.y)
  fun toLongPoint() = LongPoint(x.toLong(), y.toLong())
  fun toDoublePoint() = DoublePoint(x.toDouble(), y.toDouble())
  override fun toString() = "($x,$y)"
}

data class LongPoint(val x: Long, val y: Long) {
  constructor(point: Pair<Long, Long>) : this(point.first, point.second)
  constructor(index: Grid.Index) : this(index.column.toLong(), index.row.toLong())
  constructor(str: String) : this(str.split(',').map { it.toLong() }.toPair())
  fun transpose() = LongPoint(y, x)
  fun upLeft() = LongPoint(x - 1, y + 1)
  fun up() = LongPoint(x, y + 1)
  fun upRight() = LongPoint(x + 1, y + 1)
  fun right() = LongPoint(x + 1, y)
  fun downRight() = LongPoint(x + 1, y - 1)
  fun down() = LongPoint(x, y - 1)
  fun downLeft() = LongPoint(x - 1, y - 1)
  fun left() = LongPoint(x - 1, y)
  fun isUpLeftOf(other: LongPoint) = (x < other.x) && (y > other.y)
  fun isUpOf(other: LongPoint) = (y > other.y)
  fun isAbove(other: LongPoint) = isUpOf(other)
  fun isUpRightOf(other: LongPoint) = (x > other.x) && (y > other.y)
  fun isRightOf(other: LongPoint) = (x > other.x)
  fun isDownRightOf(other: LongPoint) = (x > other.x) && (y < other.y)
  fun isDownOf(other: LongPoint) = (y < other.y)
  fun isBelow(other: LongPoint) = isDownOf(other)
  fun isDownLeftOf(other: LongPoint) = (x < other.x) && (y < other.y)
  fun isLeftOf(other: LongPoint) = (x < other.x)
  fun isImmediatelyUpLeftOf(other: LongPoint) = (x == other.x - 1) && (y == other.y + 1)
  fun isImmediatelyUpOf(other: LongPoint) = (x == other.x) && (y == other.y + 1)
  fun isImmediatelyAbove(other: LongPoint) = isImmediatelyUpOf(other)
  fun isImmediatelyUpRightOf(other: LongPoint) = (x == other.x + 1) && (y == other.y + 1)
  fun isImmediatelyRightOf(other: LongPoint) = (x == other.x + 1) && (y == other.y)
  fun isImmediatelyDownRightOf(other: LongPoint) = (x == other.x + 1) && (y == other.y - 1)
  fun isImmediatelyDownOf(other: LongPoint) = (x == other.x) && (y == other.y - 1)
  fun isImmediatelyBelow(other: LongPoint) = isImmediatelyDownOf(other)
  fun isImmediatelyDownLeftOf(other: LongPoint) = (x == other.x - 1) && (y == other.y - 1)
  fun isImmediatelyLeftOf(other: LongPoint) = (x == other.x - 1) && (y == other.y)
  fun adjacent() = listOf(up(), right(), down(), left())
  fun adjacentWithDiagonals() =
    listOf(upLeft(), up(), upRight(), right(), downRight(), down(), downLeft(), left())
  fun offset(amount: LongPoint): LongPoint = offset(amount.x, amount.y)
  fun offset(x: Long, y: Long): LongPoint = LongPoint(this.x + x, this.y + y)
  fun distanceTo(other: LongPoint): Double =
    sqrt((x.toDouble() - other.x.toDouble()).pow(2) + (y.toDouble() - other.y.toDouble()).pow(2))
  fun intDistanceTo(other: LongPoint): Int = distanceTo(other).toInt()
  fun longDistanceTo(other: LongPoint): Long = distanceTo(other).toLong()
  fun manhattanDistanceTo(other: LongPoint): Long = abs(x - other.x) + abs(y - other.y)
  fun xDistanceTo(other: LongPoint): Long = abs(x - other.x)
  fun yDistanceTo(other: LongPoint): Long = abs(y - other.y)
  fun toPoint() = Point(x.toInt(), y.toInt())
  fun toDoublePoint() = DoublePoint(x.toDouble(), y.toDouble())
  override fun toString() = "($x,$y)"
}

data class DoublePoint(val x: Double, val y: Double) {
  constructor(point: Pair<Double, Double>) : this(point.first, point.second)
  constructor(index: Grid.Index) : this(index.column.toDouble(), index.row.toDouble())
  constructor(str: String) : this(str.split(',').map { it.toDouble() }.toPair())
  fun transpose() = DoublePoint(y, x)
  fun upLeft() = DoublePoint(x - 1, y + 1)
  fun up() = DoublePoint(x, y + 1)
  fun upRight() = DoublePoint(x + 1, y + 1)
  fun right() = DoublePoint(x + 1, y)
  fun downRight() = DoublePoint(x + 1, y - 1)
  fun down() = DoublePoint(x, y - 1)
  fun downLeft() = DoublePoint(x - 1, y - 1)
  fun left() = DoublePoint(x - 1, y)
  fun isUpLeftOf(other: DoublePoint) = (x < other.x) && (y > other.y)
  fun isUpOf(other: DoublePoint) = (y > other.y)
  fun isAbove(other: DoublePoint) = isUpOf(other)
  fun isUpRightOf(other: DoublePoint) = (x > other.x) && (y > other.y)
  fun isRightOf(other: DoublePoint) = (x > other.x)
  fun isDownRightOf(other: DoublePoint) = (x > other.x) && (y < other.y)
  fun isDownOf(other: DoublePoint) = (y < other.y)
  fun isBelow(other: DoublePoint) = isDownOf(other)
  fun isDownLeftOf(other: DoublePoint) = (x < other.x) && (y < other.y)
  fun isLeftOf(other: DoublePoint) = (x < other.x)
  fun isImmediatelyUpLeftOf(other: DoublePoint) = (x == other.x - 1) && (y == other.y + 1)
  fun isImmediatelyUpOf(other: DoublePoint) = (x == other.x) && (y == other.y + 1)
  fun isImmediatelyAbove(other: DoublePoint) = isImmediatelyUpOf(other)
  fun isImmediatelyUpRightOf(other: DoublePoint) = (x == other.x + 1) && (y == other.y + 1)
  fun isImmediatelyRightOf(other: DoublePoint) = (x == other.x + 1) && (y == other.y)
  fun isImmediatelyDownRightOf(other: DoublePoint) = (x == other.x + 1) && (y == other.y - 1)
  fun isImmediatelyDownOf(other: DoublePoint) = (x == other.x) && (y == other.y - 1)
  fun isImmediatelyBelow(other: DoublePoint) = isImmediatelyDownOf(other)
  fun isImmediatelyDownLeftOf(other: DoublePoint) = (x == other.x - 1) && (y == other.y - 1)
  fun isImmediatelyLeftOf(other: DoublePoint) = (x == other.x - 1) && (y == other.y)
  fun adjacent() = listOf(up(), right(), down(), left())
  fun adjacentWithDiagonals() =
    listOf(upLeft(), up(), upRight(), right(), downRight(), down(), downLeft(), left())
  fun offset(amount: DoublePoint): DoublePoint = offset(amount.x, amount.y)
  fun offset(x: Double, y: Double): DoublePoint = DoublePoint(this.x + x, this.y + y)
  fun distanceTo(other: DoublePoint): Double = sqrt((x - other.x).pow(2) + (y - other.y).pow(2))
  fun intDistanceTo(other: DoublePoint): Int = distanceTo(other).toInt()
  fun longDistanceTo(other: DoublePoint): Long = distanceTo(other).toLong()
  fun manhattanDistanceTo(other: DoublePoint): Double = abs(x - other.x) + abs(y - other.y)
  fun xDistanceTo(other: DoublePoint): Double = abs(x - other.x)
  fun yDistanceTo(other: DoublePoint): Double = abs(y - other.y)
  fun toPoint() = Point(x.toInt(), y.toInt())
  fun toLongPoint() = LongPoint(x.toLong(), y.toLong())
  override fun toString() = "($x,$y)"
}

data class Point3(val x: Int, val y: Int, val z: Int) {
  constructor(point: Triple<Int, Int, Int>) : this(point.first, point.second, point.third)
  constructor(str: String) : this(str.split(',').map { it.toInt() }.toTriple())
  fun offset(amount: Point3): Point3 = offset(amount.x, amount.y, amount.z)
  fun offset(x: Int, y: Int, z: Int): Point3 = Point3(this.x + x, this.y + y, this.z + z)
  fun distanceTo(other: Point3): Double {
    return sqrt(
      (x.toDouble() - other.x.toDouble()).pow(2) +
        (y.toDouble() - other.y.toDouble()).pow(2) +
        (z.toDouble() - other.z.toDouble()).pow(2)
    )
  }
  fun intDistanceTo(other: Point3): Int = distanceTo(other).toInt()
  fun longDistanceTo(other: Point3): Long = distanceTo(other).toLong()
  fun manhattanDistanceTo(other: Point3): Int =
    abs(x - other.x) + abs(y - other.y) + abs(z - other.z)
  fun xDistanceTo(other: Point3): Int = abs(x - other.x)
  fun yDistanceTo(other: Point3): Int = abs(y - other.y)
  fun zDistanceTo(other: Point3): Int = abs(z - other.z)
  fun toLongPoint3() = LongPoint3(x.toLong(), y.toLong(), z.toLong())
  fun toDoublePoint3() = DoublePoint3(x.toDouble(), y.toDouble(), z.toDouble())
  override fun toString() = "($x,$y,$z)"
}

data class LongPoint3(val x: Long, val y: Long, val z: Long) {
  constructor(point: Triple<Long, Long, Long>) : this(point.first, point.second, point.third)
  constructor(str: String) : this(str.split(',').map { it.toLong() }.toTriple())
  fun offset(amount: LongPoint3): LongPoint3 = offset(amount.x, amount.y, amount.z)
  fun offset(x: Long, y: Long, z: Long): LongPoint3 = LongPoint3(this.x + x, this.y + y, this.z + z)
  fun distanceTo(other: LongPoint3): Double {
    return sqrt(
      (x.toDouble() - other.x.toDouble()).pow(2) +
        (y.toDouble() - other.y.toDouble()).pow(2) +
        (z.toDouble() - other.z.toDouble()).pow(2)
    )
  }
  fun intDistanceTo(other: LongPoint3): Int = distanceTo(other).toInt()
  fun longDistanceTo(other: LongPoint3): Long = distanceTo(other).toLong()
  fun manhattanDistanceTo(other: LongPoint3): Long =
    abs(x - other.x) + abs(y - other.y) + abs(z - other.z)
  fun xDistanceTo(other: LongPoint3): Long = abs(x - other.x)
  fun yDistanceTo(other: LongPoint3): Long = abs(y - other.y)
  fun zDistanceTo(other: LongPoint3): Long = abs(z - other.z)
  fun toPoint3() = Point3(x.toInt(), y.toInt(), z.toInt())
  fun toDoublePoint3() = DoublePoint3(x.toDouble(), y.toDouble(), z.toDouble())
  override fun toString() = "($x,$y,$z)"
}

data class DoublePoint3(val x: Double, val y: Double, val z: Double) {
  constructor(point: Triple<Double, Double, Double>) : this(point.first, point.second, point.third)
  constructor(str: String) : this(str.split(',').map { it.toDouble() }.toTriple())
  fun offset(amount: DoublePoint3): DoublePoint3 = offset(amount.x, amount.y, amount.z)
  fun offset(x: Double, y: Double, z: Double): DoublePoint3 =
    DoublePoint3(this.x + x, this.y + y, this.z + z)
  fun distanceTo(other: DoublePoint3): Double =
    sqrt((x - other.x).pow(2) + (y - other.y).pow(2) + (z - other.z).pow(2))
  fun intDistanceTo(other: DoublePoint3): Int = distanceTo(other).toInt()
  fun longDistanceTo(other: DoublePoint3): Long = distanceTo(other).toLong()
  fun manhattanDistanceTo(other: DoublePoint3): Double =
    abs(x - other.x) + abs(y - other.y) + abs(z - other.z)
  fun xDistanceTo(other: DoublePoint3): Double = abs(x - other.x)
  fun yDistanceTo(other: DoublePoint3): Double = abs(y - other.y)
  fun zDistanceTo(other: DoublePoint3): Double = abs(z - other.z)
  fun toPoint3() = Point3(x.toInt(), y.toInt(), z.toInt())
  fun toLongPoint3() = LongPoint3(x.toLong(), y.toLong(), z.toLong())
  override fun toString() = "($x,$y,$z)"
}

fun String.toPoint() = Point(this)
fun String.toLongPoint() = LongPoint(this)
fun String.toDoublePoint() = DoublePoint(this)

fun String.toPoint3() = Point3(this)
fun String.toLongPoint3() = LongPoint3(this)
fun String.toDoublePoint3() = DoublePoint3(this)
