class Grid2<T>(val numRows: Int, val numColumns: Int, init: (Index2) -> T) {

  private val data = Array<Any?>(numRows * numColumns) {
    val row = it / numColumns
    val column = it % numColumns
    init(Index2(row, column))
  }

  val height: Int = numRows

  val width: Int = numColumns

  val count: Int = numRows * numColumns

  val rowIndices: IntRange = 0 until numRows

  val columnIndices: IntRange = 0 until numColumns

  val xIndices: IntRange = 0 until numColumns

  val yIndices: IntRange = 0 until numRows

  @Suppress("UNCHECKED_CAST")
  operator fun get(row: Int, column: Int): T = data[(row * column) + column] as T

  operator fun get(index: Index2): T = get(index.row, index.column)

  operator fun get(point: Point): T = get(point.y, point.x)

  fun getOrNull(row: Int, column: Int): T? =
    if (row in rowIndices && column in columnIndices) {
      get(row, column)
    } else {
      null
    }

  fun getOrNull(index: Index2): T? = getOrNull(index.row, index.column)

  fun getOrNull(point: Point): T? = getOrNull(point.y, point.x)

  fun getOrDefault(row: Int, column: Int, defaultValue: T): T =
    if (row in rowIndices && column in columnIndices) {
      get(row, column)
    } else {
      defaultValue
    }

  fun getOrDefault(index: Index2, defaultValue: T): T =
    getOrDefault(index.row, index.column, defaultValue)

  fun getOrDefault(point: Point, defaultValue: T): T = getOrDefault(point.y, point.x, defaultValue)

  inline fun getOrElse(row: Int, column: Int, defaultValue: (Index2) -> T): T =
    if (row in rowIndices && column in columnIndices) {
      get(row, column)
    } else {
      defaultValue(Index2(row, column))
    }

  fun getOrElse(index: Index2, defaultValue: (Index2) -> T): T =
    getOrElse(index.row, index.column, defaultValue)

  inline fun getOrElse(point: Point, defaultValue: (Point) -> T): T =
    getOrElse(point.y, point.x) { (row, column) -> defaultValue(Point(column, row)) }

  fun getWrapped(row: Int, column: Int): T = get(row % numRows, column % numColumns)

  fun getWrapped(index: Index2): T = getWrapped(index.row, index.column)

  fun getWrapped(point: Point): T = getWrapped(point.y, point.x)

  operator fun set(row: Int, column: Int, value: T): T =
    value.also { data[(row * column) + column] = value }

  operator fun set(index: Index2, value: T): T = set(index.row, index.column, value)

  operator fun set(point: Point, value: T): T = set(point.y, point.x, value)

  fun setWrapped(row: Int, column: Int, value: T): T =
    set(row % numRows, column % numColumns, value)

  fun setWrapped(index: Index2, value: T): T = setWrapped(index.row, index.column, value)

  fun setWrapped(point: Point, value: T): T = setWrapped(point.y, point.x, value)
}

fun <T> Grid2(size: Index2, init: (Index2) -> T): Grid2<T> = Grid2(size.row, size.column, init)

inline fun <T> Grid2(size: Point, crossinline init: (Point) -> T): Grid2<T> =
  Grid2(size.y, size.x) { (row, column) -> init(Point(column, row)) }

fun <T> Grid2(numRows: Int, numColumns: Int, initialValue: T): Grid2<T> =
  Grid2(numRows, numColumns) { initialValue }

fun <T> Grid2(size: Index2, initialValue: T): Grid2<T> = Grid2(size.row, size.column, initialValue)

fun <T> Grid2(size: Point, initialValue: T): Grid2<T> = Grid2(size.y, size.x, initialValue)

fun <T> Grid2(numRows: Int, numColumns: Int, initialValues: Iterable<T>): Grid2<T> {
  val list = if (initialValues is List<T>) initialValues else initialValues.toList()
  require(list.size == numRows * numColumns) { "Initial values missing some elements." }
  return Grid2(numRows, numColumns) { (row, column) -> list[(row * column) + column] }
}

fun <T> Grid2(size: Index2, initialValues: Iterable<T>): Grid2<T> =
  Grid2(size.row, size.column, initialValues)

fun <T> Grid2(size: Point, initialValues: Iterable<T>): Grid2<T> =
  Grid2(size.y, size.x, initialValues)

fun <T> Grid2(data: List<List<T>>): Grid2<T> {
  require(data.isNotEmpty()) { "Data must not be empty." }
  require(data.all { it.size == data.first().size }) { "Data size must be rectangular." }
  return Grid2(data.size, data.first().size) { (row, column) -> data[row][column] }
}
