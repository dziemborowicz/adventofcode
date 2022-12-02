class Grid<T>(data: List<List<T>>) {

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

  operator fun get(index: Index) =
    get(index.row, index.column)

  operator fun get(row: Int, column: Int): T =
    data[row][column]

  fun getWrapped(index: Index): T =
    getWrapped(index.row, index.column)

  fun getWrapped(row: Int, column: Int): T =
    data.getWrapped(row).getWrapped(column)

  operator fun set(index: Index, element: T): T =
    set(index.row, index.column, element)

  operator fun set(row: Int, column: Int, element: T): T =
    data[row].set(column, element)

  fun setWrapped(index: Index, element: T): T =
    set(index.row, index.column, element)

  fun setWrapped(row: Int, column: Int, element: T): T =
    data.getWrapped(row).setWrapped(column, element)

  fun flatten(): List<T> =
    data.flatten()

  fun columns(): List<List<T>> =
    columnIndices.map { column(it) }

  fun column(column: Int): List<T> =
    data.map { it[column] }

  fun columnWrapped(column: Int): List<T> =
    data.map { it.getWrapped(column) }

  fun anyColumnAll(predicate: (T) -> Boolean) =
    columns().any { it.all(predicate) }

  fun anyColumnNone(predicate: (T) -> Boolean) =
    columns().any { it.none(predicate) }

  fun noColumnAll(predicate: (T) -> Boolean) =
    columns().none { it.all(predicate) }

  fun noColumnNone(predicate: (T) -> Boolean) =
    columns().none { it.none(predicate) }

  fun rows(): List<List<T>> =
    rowIndices.map { row(it) }

  fun row(row: Int): List<T> =
    data[row].toList()

  fun rowWrapped(row: Int): List<T> =
    data.getWrapped(row).toList()

  fun anyRowAll(predicate: (T) -> Boolean) =
    rows().any { it.all(predicate) }

  fun anyRowNone(predicate: (T) -> Boolean) =
    rows().any { it.none(predicate) }

  fun noRowAll(predicate: (T) -> Boolean) =
    rows().none { it.all(predicate) }

  fun noRowNone(predicate: (T) -> Boolean) =
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

  data class Index(val row: Int, val column: Int)
}

fun <T> List<Grid<T>>.copy(): List<Grid<T>> = map { it.copy() }
