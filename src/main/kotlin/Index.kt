import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

data class Index2(val row: Int, val column: Int) {

  operator fun plus(other: Index2): Index2 = translate(other.row, other.column)

  operator fun minus(other: Index2): Index2 = translate(-other.row, -other.column)

  operator fun times(other: Index2): Index2 = Index2(row * other.row, column * other.column)

  operator fun div(other: Index2): Index2 = Index2(row / other.row, column / other.column)

  fun translate(other: Index2): Index2 = translate(other.row, other.column)

  fun translate(dRow: Int, dColumn: Int): Index2 = Index2(row + dRow, column + dColumn)

  fun transpose(): Index2 = Index2(column, row)

  infix fun distanceTo(other: Index2): Double =
    sqrt(
      (row.toDouble() - other.row.toDouble()).pow(2)
        + (column.toDouble() - other.column.toDouble()).pow(2)
    )

  infix fun intDistanceTo(other: Index2): Int = distanceTo(other).toInt()

  infix fun manhattanDistanceTo(other: Index2): Int =
    abs(row - other.row) + abs(column - other.column)

  infix fun rowDistanceTo(other: Index2): Int = abs(row - other.row)

  infix fun columnDistanceTo(other: Index2): Int = abs(column - other.column)

  fun neighbors(): List<Index2> = listOf(up(), right(), down(), left())

  fun neighborsIncludingDiagonals(): List<Index2> =
    listOf(up(), upRight(), right(), downRight(), down(), downLeft(), left(), upLeft())

  fun up(n: Int = 1): Index2 = Index2(row - 1, column)

  fun upRight(n: Int = 1): Index2 = Index2(row - 1, column + 1)

  fun right(n: Int = 1): Index2 = Index2(row, column + 1)

  fun downRight(n: Int = 1): Index2 = Index2(row + 1, column + 1)

  fun down(n: Int = 1): Index2 = Index2(row + 1, column)

  fun downLeft(n: Int = 1): Index2 = Index2(row + 1, column - 1)

  fun left(n: Int = 1): Index2 = Index2(row, column - 1)

  fun upLeft(n: Int = 1): Index2 = Index2(row - 1, column - 1)

  infix fun isAbove(other: Index2): Boolean = (row < other.row)

  infix fun isUpRightOf(other: Index2): Boolean = (row < other.row) && (column > other.column)

  infix fun isRightOf(other: Index2): Boolean = (column > other.column)

  infix fun isDownRightOf(other: Index2): Boolean = (row > other.row) && (column > other.column)

  infix fun isBelow(other: Index2): Boolean = (row > other.row)

  infix fun isDownLeftOf(other: Index2): Boolean = (row > other.row) && (column < other.column)

  infix fun isLeftOf(other: Index2): Boolean = (column < other.column)

  infix fun isUpLeftOf(other: Index2): Boolean = (row < other.row) && (column < other.column)

  infix fun isDirectlyAbove(other: Index2): Boolean = (row < other.row) && (column == other.column)

  infix fun isDirectlyUpRightOf(other: Index2): Boolean =
    (row < other.row) && (column > other.column) && rowDistanceTo(other) == columnDistanceTo(other)

  infix fun isDirectlyRightOf(other: Index2): Boolean =
    (row == other.row) && (column > other.column)

  infix fun isDirectlyDownRightOf(other: Index2): Boolean =
    (row > other.row) && (column > other.column) && rowDistanceTo(other) == columnDistanceTo(other)

  infix fun isDirectlyBelow(other: Index2): Boolean = (row > other.row) && (column == other.column)

  infix fun isDirectlyDownLeftOf(other: Index2): Boolean =
    (row > other.row) && (column < other.column) && rowDistanceTo(other) == columnDistanceTo(other)

  infix fun isDirectlyLeftOf(other: Index2): Boolean = (row == other.row) && (column < other.column)

  infix fun isDirectlyUpLeftOf(other: Index2): Boolean =
    (row < other.row) && (column < other.column) && rowDistanceTo(other) == columnDistanceTo(other)

  infix fun isImmediatelyAbove(other: Index2): Boolean =
    (row == other.row - 1) && (column == other.column)

  infix fun isImmediatelyUpRightOf(other: Index2): Boolean =
    (row == other.row - 1) && (column == other.column + 1)

  infix fun isImmediatelyRightOf(other: Index2): Boolean =
    (row == other.row) && (column == other.column + 1)

  infix fun isImmediatelyDownRightOf(other: Index2): Boolean =
    (row == other.row + 1) && (column == other.column + 1)

  infix fun isImmediatelyBelow(other: Index2): Boolean =
    (row == other.row + 1) && (column == other.column)

  infix fun isImmediatelyDownLeftOf(other: Index2): Boolean =
    (row == other.row + 1) && (column == other.column - 1)

  infix fun isImmediatelyLeftOf(other: Index2): Boolean =
    (row == other.row) && (column == other.column - 1)

  infix fun isImmediatelyUpLeftOf(other: Index2): Boolean =
    (row == other.row - 1) && (column == other.column - 1)

  infix fun isNeighborOf(other: Index2): Boolean = manhattanDistanceTo(other) == 1

  infix fun isNeighborIncludingDiagonalsOf(other: Index2): Boolean =
    this != other && rowDistanceTo(other) < 2 && columnDistanceTo(other) < 2

  override fun toString(): String = "(${row}r,${column}c)"
}

fun Index2(pair: Pair<Int, Int>): Index2 = Index2(pair.first, pair.second)

fun Index2(point: Point): Index2 = Index2(point.y, point.x)

fun Index2(string: String): Index2 {
  val parts = string.removeSurrounding("(", ")").split(',', ' ')
  require(parts.size == 2) { "Invalid format for point: $string" }
  return Index2(parts[0].removeSuffix("r").toInt(), parts[1].removeSuffix("c").toInt())
}

fun Pair<Int, Int>.toIndex2(): Index2 = Index2(this)

fun Point.toIndex2(): Index2 = Index2(this)

fun String.toIndex2(): Index2 = Index2(this)

infix fun Int.rc(column: Int): Index2 = Index2(this, column)
