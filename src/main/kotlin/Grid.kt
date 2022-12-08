import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class Grid<T>(data: List<List<T>>) : Iterable<T> {

  val data = data.map { it.toMutableList() }
  val numRows = data.size
  val numColumns = data.firstOrNull()?.size ?: 0
  val size = numRows * numColumns
  val rowIndices = 0 until numRows
  val columnIndices = 0 until numColumns
  val indices = rowIndices.flatMap { row -> columnIndices.map { column -> Index(row, column) } }

  init {
    require(numRows > 0) { "Number of rows must be greater than zero." }
    require(numColumns > 0) { "Number of columns must be greater than zero." }
    require(data.all { it.size == numColumns }) { "All columns must have the same size." }
  }

  constructor(grid: Grid<T>) : this(grid.data)

  constructor(
    numRows: Int,
    numColumns: Int,
    initializer: (Int, Int) -> T,
  ) : this(List(numRows) { row -> List(numColumns) { column -> initializer(row, column) } })

  constructor(
    numRows: Int,
    numColumns: Int,
    initialValue: T,
  ) : this(numRows, numColumns, { _, _ -> initialValue })

  override fun equals(other: Any?): Boolean {
    if (other !is Grid<*>) return false
    if (other.numRows != numRows) return false
    if (other.numColumns != numColumns) return false
    return indices.all { this[it] == other[it] }
  }

  override fun hashCode(): Int {
    var result = 1
    result = (result * 31) + numRows
    result = (result * 31) + numColumns
    indices.forEach { result = (result * 31) + this[it].hashCode() }
    return result
  }

  override fun iterator(): Iterator<T> =
    flatten().iterator()

  override fun toString(): String {
    return data.joinToString(",\n", "[\n", "\n]") { "  $it" }
  }

  operator fun get(index: Index): T =
    get(index.row, index.column)

  operator fun get(row: Int, column: Int): T =
    data[row][column]

  fun getOrNull(index: Index): T? =
    getOrNull(index.row, index.column)

  fun getOrNull(row: Int, column: Int): T? {
    return if (row !in rowIndices || column !in columnIndices) {
      null
    } else {
      get(row, column)
    }
  }

  fun getOrElse(index: Index, defaultValue: (Index) -> T): T =
    getOrElse(index.row, index.column, defaultValue)

  fun getOrElse(row: Int, column: Int, defaultValue: (Index) -> T): T {
    return if (row !in rowIndices || column !in columnIndices) {
      defaultValue(Index(row, column))
    } else {
      get(row, column)
    }
  }

  fun getWrapped(index: Index): T =
    getWrapped(index.row, index.column)

  fun getWrapped(row: Int, column: Int): T =
    data.getWrapped(row).getWrapped(column)

  fun getAdjacent(index: Index): List<T> =
    index.adjacentIn(this).map { this[it] }

  fun getAdjacent(row: Int, column: Int): List<T> =
    getAdjacent(Index(row, column))

  fun getAdjacentWrapped(index: Index): List<T> =
    index.adjacent().map { this.getWrapped(it) }

  fun getAdjacentWrapped(row: Int, column: Int): List<T> =
    getAdjacentWrapped(Index(row, column))

  fun getAdjacentWithDiagonals(index: Index): List<T> =
    index.adjacentWithDiagonalsIn(this).map { this[it] }

  fun getAdjacentWithDiagonals(row: Int, column: Int): List<T> =
    getAdjacentWithDiagonals(Index(row, column))

  fun getAdjacentWithDiagonalsWrapped(index: Index): List<T> =
    index.adjacentWithDiagonals().map { this.getWrapped(it) }

  fun getAdjacentWithDiagonalsWrapped(row: Int, column: Int): List<T> =
    getAdjacentWithDiagonalsWrapped(Index(row, column))

  inline fun forEachIndexed(action: (Int, Int, T) -> Unit) =
    indices.forEach { index -> action(index.row, index.column, this[index]) }

  inline fun forEachAdjacent(index: Index, action: (T) -> Unit) =
    forEachAdjacent(index.row, index.column, action)

  inline fun forEachAdjacent(row: Int, column: Int, action: (T) -> Unit) {
    if (row - 1 >= 0) action(this[row - 1, column])
    if (column + 1 < numColumns) action(this[row, column + 1])
    if (row + 1 < numRows) action(this[row + 1, column])
    if (column - 1 >= 0) action(this[row, column - 1])
  }

  inline fun forEachAdjacentWrapped(index: Index, action: (T) -> Unit) =
    forEachAdjacentWrapped(index.row, index.column, action)

  inline fun forEachAdjacentWrapped(row: Int, column: Int, action: (T) -> Unit) {
    action(getWrapped(row - 1, column))
    action(getWrapped(row, column + 1))
    action(getWrapped(row + 1, column))
    action(getWrapped(row, column - 1))
  }

  inline fun forEachAdjacentWithDiagonals(index: Index, action: (T) -> Unit) =
    forEachAdjacentWithDiagonals(index.row, index.column, action)

  inline fun forEachAdjacentWithDiagonals(row: Int, column: Int, action: (T) -> Unit) {
    if (row - 1 >= 0 && column - 1 >= 0) action(this[row - 1, column - 1])
    if (row - 1 >= 0) action(this[row - 1, column])
    if (row - 1 >= 0 && column + 1 < numColumns) action(this[row - 1, column + 1])
    if (column + 1 < numColumns) action(this[row, column + 1])
    if (row + 1 < numRows && column + 1 < numColumns) action(this[row + 1, column + 1])
    if (row + 1 < numRows) action(this[row + 1, column])
    if (row + 1 < numRows && column - 1 >= 0) action(this[row + 1, column - 1])
    if (column - 1 >= 0) action(this[row, column - 1])
  }

  inline fun forEachAdjacentWithDiagonalsWrapped(index: Index, action: (T) -> Unit) =
    forEachAdjacentWithDiagonalsWrapped(index.row, index.column, action)

  inline fun forEachAdjacentWithDiagonalsWrapped(row: Int, column: Int, action: (T) -> Unit) {
    action(getWrapped(row - 1, column - 1))
    action(getWrapped(row - 1, column))
    action(getWrapped(row - 1, column + 1))
    action(getWrapped(row, column + 1))
    action(getWrapped(row + 1, column + 1))
    action(getWrapped(row + 1, column))
    action(getWrapped(row + 1, column - 1))
    action(getWrapped(row, column - 1))
  }

  inline fun forEachAdjacentIndexed(index: Index, action: (Int, Int, T) -> Unit) =
    forEachAdjacentIndexed(index.row, index.column, action)

  inline fun forEachAdjacentIndexed(row: Int, column: Int, action: (Int, Int, T) -> Unit) {
    if (row - 1 >= 0) action(row - 1, column, this[row - 1, column])
    if (column + 1 < numColumns) action(row, column + 1, this[row, column + 1])
    if (row + 1 < numRows) action(row + 1, column, this[row + 1, column])
    if (column - 1 >= 0) action(row, column - 1, this[row, column - 1])
  }

  inline fun forEachAdjacentWrappedIndexed(index: Index, action: (Int, Int, T) -> Unit) =
    forEachAdjacentWrappedIndexed(index.row, index.column, action)

  inline fun forEachAdjacentWrappedIndexed(row: Int, column: Int, action: (Int, Int, T) -> Unit) {
    action(row - 1, column, getWrapped(row - 1, column))
    action(row, column + 1, getWrapped(row, column + 1))
    action(row + 1, column, getWrapped(row + 1, column))
    action(row, column - 1, getWrapped(row, column - 1))
  }

  inline fun forEachAdjacentWithDiagonalsIndexed(index: Index, action: (Int, Int, T) -> Unit) =
    forEachAdjacentWithDiagonalsIndexed(index.row, index.column, action)

  inline fun forEachAdjacentWithDiagonalsIndexed(
    row: Int,
    column: Int,
    action: (Int, Int, T) -> Unit,
  ) {
    if (row - 1 >= 0 && column - 1 >= 0)
      action(row - 1, column - 1, this[row - 1, column - 1])
    if (row - 1 >= 0)
      action(row - 1, column, this[row - 1, column])
    if (row - 1 >= 0 && column + 1 < numColumns)
      action(row - 1, column + 1, this[row - 1, column + 1])
    if (column + 1 < numColumns)
      action(row, column + 1, this[row, column + 1])
    if (row + 1 < numRows && column + 1 < numColumns)
      action(row + 1, column + 1, this[row + 1, column + 1])
    if (row + 1 < numRows)
      action(row + 1, column, this[row + 1, column])
    if (row + 1 < numRows && column - 1 >= 0)
      action(row + 1, column - 1, this[row + 1, column - 1])
    if (column - 1 >= 0)
      action(row, column - 1, this[row, column - 1])
  }

  inline fun forEachAdjacentWithDiagonalsWrappedIndexed(
    index: Index,
    action: (Int, Int, T) -> Unit,
  ) =
    forEachAdjacentWithDiagonalsWrappedIndexed(index.row, index.column, action)

  inline fun forEachAdjacentWithDiagonalsWrappedIndexed(
    row: Int,
    column: Int,
    action: (Int, Int, T) -> Unit,
  ) {
    action(row - 1, column - 1, getWrapped(row - 1, column - 1))
    action(row - 1, column, getWrapped(row - 1, column))
    action(row - 1, column + 1, getWrapped(row - 1, column + 1))
    action(row, column + 1, getWrapped(row, column + 1))
    action(row + 1, column + 1, getWrapped(row + 1, column + 1))
    action(row + 1, column, getWrapped(row + 1, column))
    action(row + 1, column - 1, getWrapped(row + 1, column - 1))
    action(row, column - 1, getWrapped(row, column - 1))
  }

  operator fun set(index: Index, element: T): T =
    set(index.row, index.column, element)

  operator fun set(row: Int, column: Int, element: T): T =
    data[row].set(column, element)

  fun setWrapped(index: Index, element: T): T =
    set(index.row, index.column, element)

  fun setWrapped(row: Int, column: Int, element: T): T =
    data.getWrapped(row).setWrapped(column, element)

  fun swap(a: Index, b: Index) = swap(a.row, a.column, b.row, b.column)

  fun swap(aRow: Int, aColumn: Int, bRow: Int, bColumn: Int) {
    this[aRow, aColumn] = this[bRow, bColumn].also { this[bRow, bColumn] = this[aRow, aColumn] }
  }

  fun flatten(): List<T> =
    data.flatten()

  fun count(predicate: (T) -> Boolean): Int = indices.count { predicate(this[it]) }

  fun <R> map(transform: (T) -> R): Grid<R> =
    Grid(numRows, numColumns) { row, column -> transform(this[row, column]) }

  fun subGrid(rows: IntRange, columns: IntRange): Grid<T> {
    return Grid(rows.size, columns.size) { row, column ->
      this[row + rows.first, column + columns.first]
    }
  }

  fun columns(): List<List<T>> =
    columnIndices.map { column(it) }

  fun column(column: Int): List<T> =
    data.map { it[column] }

  fun columnWrapped(column: Int): List<T> =
    data.map { it.getWrapped(column) }

  inline fun anyColumnAll(predicate: (T) -> Boolean) =
    columns().any { it.all(predicate) }

  inline fun anyColumnNone(predicate: (T) -> Boolean) =
    columns().any { it.none(predicate) }

  inline fun noColumnAll(predicate: (T) -> Boolean) =
    columns().none { it.all(predicate) }

  inline fun noColumnNone(predicate: (T) -> Boolean) =
    columns().none { it.none(predicate) }

  fun rows(): List<List<T>> =
    rowIndices.map { row(it) }

  fun row(row: Int): List<T> =
    data[row].toList()

  fun rowWrapped(row: Int): List<T> =
    data.getWrapped(row).toList()

  inline fun anyRowAll(predicate: (T) -> Boolean) =
    rows().any { it.all(predicate) }

  inline fun anyRowNone(predicate: (T) -> Boolean) =
    rows().any { it.none(predicate) }

  inline fun noRowAll(predicate: (T) -> Boolean) =
    rows().none { it.all(predicate) }

  inline fun noRowNone(predicate: (T) -> Boolean) =
    rows().none { it.none(predicate) }

  fun diagonals(): List<List<T>> =
    listOf(diagonalDown(), diagonalUp())

  fun diagonalDown(): List<T> {
    check(numRows == numColumns) { "Grid must be a square to have a diagonal." }
    return data.indices.map { data[it][it] }
  }

  fun diagonalUp(): List<T> {
    check(numRows == numColumns) { "Grid must be a square to have a diagonal." }
    return data.indices.map { data[data.size - it - 1][it] }
  }

  fun copy(): Grid<T> = Grid(this)

  fun transpose(): Grid<T> = Grid(numColumns, numRows) { row, column -> this[column, row] }

  data class Index(val row: Int, val column: Int) {
    fun transpose() = Index(column, row)

    fun upLeft() = Index(row - 1, column - 1)
    fun up() = Index(row - 1, column)
    fun upRight() = Index(row - 1, column + 1)
    fun right() = Index(row, column + 1)
    fun downRight() = Index(row + 1, column + 1)
    fun down() = Index(row + 1, column)
    fun downLeft() = Index(row + 1, column - 1)
    fun left() = Index(row, column - 1)

    fun adjacent() = listOf(
      up(),
      right(),
      down(),
      left(),
    )

    fun adjacentWithDiagonals() = listOf(
      upLeft(),
      up(),
      upRight(),
      right(),
      downRight(),
      down(),
      downLeft(),
      left(),
    )

    fun adjacentIn(grid: Grid<*>) = adjacent().filter {
      it.row in grid.rowIndices && it.column in grid.columnIndices
    }

    fun adjacentWithDiagonalsIn(grid: Grid<*>) = adjacentWithDiagonals().filter {
      it.row in grid.rowIndices && it.column in grid.columnIndices
    }

    fun offset(amount: Index) = offset(amount.row, amount.column)

    fun offset(row: Int, column: Int) = Index(this.row + row, this.column + column)

    fun distance(other: Index): Double =
      sqrt((row.toDouble() - other.row.toDouble()).pow(2) +
             (column.toDouble() - other.column.toDouble()).pow(2))

    fun intDistance(other: Index): Int = distance(other).toInt()

    fun longDistance(other: Index): Long = distance(other).toLong()

    fun manhattanDistance(other: Index): Int = abs(row - other.row) + abs(column - other.column)
  }
}

fun <T> List<Grid<T>>.copy(): List<Grid<T>> = map { it.copy() }

fun Grid<Boolean>.asImage(dot: Char = '#', empty: Char = '.'): String =
  map { if (it) dot else empty }.asImage()

fun Grid<Char>.asImage(): String =
  rows().joinToString("\n") { row -> row.joinToString("") }
