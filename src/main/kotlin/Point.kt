import kotlin.math.pow
import kotlin.math.sqrt

data class Point(val x: Int, val y: Int) {
  constructor(point: Pair<Int, Int>) : this(point.first, point.second)
  constructor(index: Grid.Index) : this(index.column, index.row)
  fun distance(other: Point): Double =
    sqrt((x.toDouble() - other.x.toDouble()).pow(2) + (y.toDouble() - other.y.toDouble()).pow(2))
  fun intDistance(other: Point): Int = distance(other).toInt()
  fun longDistance(other: Point): Long = distance(other).toLong()
  fun toLongPoint() = LongPoint(x.toLong(), y.toLong())
  fun toDoublePoint() = DoublePoint(x.toDouble(), y.toDouble())
}

data class LongPoint(val x: Long, val y: Long) {
  constructor(point: Pair<Long, Long>) : this(point.first, point.second)
  constructor(index: Grid.Index) : this(index.column.toLong(), index.row.toLong())
  fun distance(other: LongPoint): Double =
    sqrt((x.toDouble() - other.x.toDouble()).pow(2) + (y.toDouble() - other.y.toDouble()).pow(2))
  fun intDistance(other: LongPoint): Int = distance(other).toInt()
  fun longDistance(other: LongPoint): Long = distance(other).toLong()
  fun toPoint() = Point(x.toInt(), y.toInt())
  fun toDoublePoint() = DoublePoint(x.toDouble(), y.toDouble())
}

data class DoublePoint(val x: Double, val y: Double) {
  constructor(point: Pair<Double, Double>) : this(point.first, point.second)
  constructor(index: Grid.Index) : this(index.column.toDouble(), index.row.toDouble())
  fun distance(other: DoublePoint): Double = sqrt((x - other.x).pow(2) + (y - other.y).pow(2))
  fun intDistance(other: DoublePoint): Int = distance(other).toInt()
  fun longDistance(other: DoublePoint): Long = distance(other).toLong()
  fun toPoint() = Point(x.toInt(), y.toInt())
  fun toLongPoint() = LongPoint(x.toLong(), y.toLong())
}
