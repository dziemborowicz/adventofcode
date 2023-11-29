import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

data class Index(val row: Int, val column: Int) {

  operator fun plus(other: Index): Index = translate(other.row, other.column)

  operator fun minus(other: Index): Index = translate(-other.row, -other.column)

  operator fun times(other: Index): Index = Index(row * other.row, column * other.column)

  operator fun div(other: Index): Index = Index(row / other.row, column / other.column)

  operator fun times(other: Int): Index = Index(row * other, column * other)

  operator fun div(other: Int): Index = Index(row / other, column / other)

  fun translate(other: Index): Index = translate(other.row, other.column)

  fun translate(dRow: Int, dColumn: Int): Index = Index(row + dRow, column + dColumn)

  fun transpose(): Index = Index(column, row)

  infix fun distanceTo(other: Index): Double = sqrt(
    (row.toDouble() - other.row.toDouble()).pow(2)
      + (column.toDouble() - other.column.toDouble()).pow(2)
  )

  infix fun intDistanceTo(other: Index): Int = distanceTo(other).toInt()

  infix fun manhattanDistanceTo(other: Index): Int =
    abs(row - other.row) + abs(column - other.column)

  infix fun rowDistanceTo(other: Index): Int = abs(row - other.row)

  infix fun columnDistanceTo(other: Index): Int = abs(column - other.column)

  fun rotateClockwise(times: Int = 1): Index {
    return when (times.mod(4)) {
      0 -> this
      1 -> Index(column, -row)
      2 -> Index(-row, -column)
      3 -> Index(-column, row)
      else -> fail()
    }
  }

  fun rotateCounterclockwise(times: Int = 1): Index = rotateClockwise(-times)

  fun neighbors(): List<Index> = listOf(up(), right(), down(), left())

  fun neighborsWithDiagonals(): List<Index> =
    listOf(up(), upRight(), right(), downRight(), down(), downLeft(), left(), upLeft())

  fun neighborsIn(grid: Grid<*>): List<Index> = neighbors().filter { it in grid.indices }

  fun neighborsWithDiagonalsIn(grid: Grid<*>): List<Index> =
    neighborsWithDiagonals().filter { it in grid.indices }

  fun up(n: Int = 1): Index = Index(row - n, column)

  fun upRight(n: Int = 1): Index = Index(row - n, column + n)

  fun right(n: Int = 1): Index = Index(row, column + n)

  fun downRight(n: Int = 1): Index = Index(row + n, column + n)

  fun down(n: Int = 1): Index = Index(row + n, column)

  fun downLeft(n: Int = 1): Index = Index(row + n, column - n)

  fun left(n: Int = 1): Index = Index(row, column - n)

  fun upLeft(n: Int = 1): Index = Index(row - n, column - n)

  infix fun isAbove(other: Index): Boolean = (row < other.row)

  infix fun isUpRightOf(other: Index): Boolean = (row < other.row) && (column > other.column)

  infix fun isRightOf(other: Index): Boolean = (column > other.column)

  infix fun isDownRightOf(other: Index): Boolean = (row > other.row) && (column > other.column)

  infix fun isBelow(other: Index): Boolean = (row > other.row)

  infix fun isDownLeftOf(other: Index): Boolean = (row > other.row) && (column < other.column)

  infix fun isLeftOf(other: Index): Boolean = (column < other.column)

  infix fun isUpLeftOf(other: Index): Boolean = (row < other.row) && (column < other.column)

  infix fun isDirectlyAbove(other: Index): Boolean = (row < other.row) && (column == other.column)

  infix fun isDirectlyUpRightOf(other: Index): Boolean =
    (row < other.row) && (column > other.column) && rowDistanceTo(other) == columnDistanceTo(other)

  infix fun isDirectlyRightOf(other: Index): Boolean = (row == other.row) && (column > other.column)

  infix fun isDirectlyDownRightOf(other: Index): Boolean =
    (row > other.row) && (column > other.column) && rowDistanceTo(other) == columnDistanceTo(other)

  infix fun isDirectlyBelow(other: Index): Boolean = (row > other.row) && (column == other.column)

  infix fun isDirectlyDownLeftOf(other: Index): Boolean =
    (row > other.row) && (column < other.column) && rowDistanceTo(other) == columnDistanceTo(other)

  infix fun isDirectlyLeftOf(other: Index): Boolean = (row == other.row) && (column < other.column)

  infix fun isDirectlyUpLeftOf(other: Index): Boolean =
    (row < other.row) && (column < other.column) && rowDistanceTo(other) == columnDistanceTo(other)

  infix fun isImmediatelyAbove(other: Index): Boolean =
    (row == other.row - 1) && (column == other.column)

  infix fun isImmediatelyUpRightOf(other: Index): Boolean =
    (row == other.row - 1) && (column == other.column + 1)

  infix fun isImmediatelyRightOf(other: Index): Boolean =
    (row == other.row) && (column == other.column + 1)

  infix fun isImmediatelyDownRightOf(other: Index): Boolean =
    (row == other.row + 1) && (column == other.column + 1)

  infix fun isImmediatelyBelow(other: Index): Boolean =
    (row == other.row + 1) && (column == other.column)

  infix fun isImmediatelyDownLeftOf(other: Index): Boolean =
    (row == other.row + 1) && (column == other.column - 1)

  infix fun isImmediatelyLeftOf(other: Index): Boolean =
    (row == other.row) && (column == other.column - 1)

  infix fun isImmediatelyUpLeftOf(other: Index): Boolean =
    (row == other.row - 1) && (column == other.column - 1)

  infix fun isNeighborOf(other: Index): Boolean = manhattanDistanceTo(other) == 1

  infix fun isNeighborWithDiagonalsOf(other: Index): Boolean =
    this != other && rowDistanceTo(other) < 2 && columnDistanceTo(other) < 2

  fun toPointIn(grid: Grid<*>): Point = Point(column, grid.numRows - 1 - row)

  override fun toString(): String = "(${row}r,${column}c)"

  fun wrappedIn(grid: Grid<*>): Index = Index(row.mod(grid.numRows), column.mod(grid.numColumns))

  companion object {
    val ZERO = Index(0, 0)

    val UP = Index(-1, 0)
    val UP_RIGHT = Index(-1, 1)
    val RIGHT = Index(0, 1)
    val DOWN_RIGHT = Index(1, 1)
    val DOWN = Index(1, 0)
    val DOWN_LEFT = Index(1, -1)
    val LEFT = Index(0, -1)
    val UP_LEFT = Index(-1, -1)

    val DIRECTIONS = listOf(UP, RIGHT, DOWN, LEFT)
    val DIRECTIONS_WITH_DIAGONALS =
      listOf(UP, UP_RIGHT, RIGHT, DOWN_RIGHT, DOWN, DOWN_LEFT, LEFT, UP_LEFT)
  }
}

operator fun Int.times(other: Index): Index = Index(this * other.row, this * other.column)

fun Index(pair: Pair<Int, Int>): Index = Index(pair.first, pair.second)

fun Index(string: String): Index {
  val parts = string.removeSurrounding("(", ")").split(',', ' ')
  require(parts.size == 2) { "Invalid format for index: $string" }
  return Index(parts[0].removeSuffix("r").toInt(), parts[1].removeSuffix("c").toInt())
}

fun Pair<Int, Int>.toIndex(): Index = Index(this)

fun String.toIndex(): Index = Index(this)

infix fun Int.rc(column: Int): Index = Index(this, column)
