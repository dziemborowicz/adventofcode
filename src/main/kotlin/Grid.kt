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

  fun <R> map(transform: (T) -> R): Grid<R> =
    Grid(numRows, numColumns) { row, column -> transform(this[row, column]) }

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

  fun transpose(): Grid<T> = Grid(numColumns, numRows) { row, column -> this[column, row] }

  data class Index(val row: Int, val column: Int) {
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
  }
}

fun <T> List<Grid<T>>.copy(): List<Grid<T>> = map { it.copy() }

fun Grid<Boolean>.asImage(dot: Char = '#', empty: Char = '.'): String =
  map { if (it) dot else empty }.asImage()

fun Grid<Char>.asImage(): String =
  rows().joinToString("\n") { row -> row.joinToString("") }
